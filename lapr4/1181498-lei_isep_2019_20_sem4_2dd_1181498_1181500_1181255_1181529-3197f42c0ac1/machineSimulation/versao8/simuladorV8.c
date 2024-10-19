#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <pthread.h>
#include <ctype.h>

#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define SERVER_SSL_CERT_FILE "B.pem"

#define BUF_SIZE 512
#define SERVER_PORT_TCP "9990"
#define SERVER_PORT_UDP "9999"

#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

short _id_machine;
short _sending_latency;
int _receivedCode;
char *_validationMessage;
char *_configureMessage;

bool _resetCall = false;
int file_progress = 0;
bool tcpRunning = true;

pthread_mutex_t muxter;
pthread_cond_t condMsg;

void writeMessageToServer(SSL *sslConn, unsigned char version, unsigned char code, short length, unsigned char *message);
int readMessageFromServer(SSL *sslConn);
void sendMessagesToServer(SSL *sslConn);
void generateByteMessage(char version, char code, short id,short length,unsigned char* message, unsigned char* byteMsg);
int buildandPrintReceivedMessage(char* content);

struct DataStruct{
	char *ip;
	char *id;
	char *latency;
	char *certificate;
};

//Thread to handle TCP protocol
void* tcpHandler(void *arg){
	tcpRunning = true;
	struct DataStruct* data = (struct DataStruct *) arg;
	int err, sock;
	char line[BUF_SIZE];
	struct addrinfo  req, *list;
	struct timeval to;
	bzero((char *)&req,sizeof(req));
	
	req.ai_family = AF_UNSPEC;
	req.ai_socktype = SOCK_STREAM;
	err=getaddrinfo(data->ip, SERVER_PORT_TCP , &req, &list);
	
	if(err) {
		printf("[TCP]Failed to get server address, error: %s\n",gai_strerror(err)); 
		exit(1);
	}

	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
	
	if(sock==-1) {
		perror("[TCP] Failed to open socket"); 
		freeaddrinfo(list); 
		exit(1);
	}

	if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("[TCP] Failed connect"); 
        freeaddrinfo(list); 
        close(sock); 
        exit(1);
	}

	const SSL_METHOD *method=SSLv23_client_method();
        SSL_CTX *ctx = SSL_CTX_new(method);

    if(data->certificate!=NULL){
	    strcpy(line,data->certificate);strcat(line,".pem");    
	    SSL_CTX_use_certificate_file(ctx, line, SSL_FILETYPE_PEM);
		strcpy(line,data->certificate);strcat(line,".key");
		SSL_CTX_use_PrivateKey_file(ctx, line, SSL_FILETYPE_PEM);
	    if (!SSL_CTX_check_private_key(ctx)) {
	        puts("Error loading client's certificate/key");
			close(sock);
	        exit(1);
		}
	}

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT,NULL);

	//THE SERVER'S CERTIFICATE IS TRUSTED
    SSL_CTX_load_verify_locations(ctx,SERVER_SSL_CERT_FILE,NULL);

	//Restrict TLS version and cypher suites
    SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
	SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

	SSL *sslConn = SSL_new(ctx);

    SSL_set_fd(sslConn, sock);
    if(SSL_connect(sslConn)!=1) {
		puts("TLS handshake error");
        SSL_free(sslConn);
        close(sock);
        exit(1);
	}

	printf("TLS version: %s\nCypher suite: %s\n",SSL_get_cipher_version(sslConn),SSL_get_cipher(sslConn));

	if(SSL_get_verify_result(sslConn)!=X509_V_OK) {
		puts("Sorry: invalid server certificate");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }

    X509* cert=SSL_get_peer_certificate(sslConn);
    X509_free(cert);

    if(cert==NULL) {
        puts("Sorry: no certificate provided by the server");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    } 

	//Create variables to build the HELLO MESSAGE
	unsigned char version = 1, code = 0;
	unsigned char *message = "hello message";
	int hello_length = 13;
	_id_machine = (unsigned short) atoi(data->id);
	char final_message[6+hello_length];

	//Build message and store it on final_message
	generateByteMessage(version,code,_id_machine,hello_length,message,final_message);
	
	//Write message to server
	for(int i = 0; i < 6+hello_length; i++) 
		SSL_write(sslConn,&final_message[i], 1);
	
	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; ID: %d; MSG: %s\n",version,code,hello_length,_id_machine,message);
	printf("[TCP] Waiting for server to check id...\n");

	//Storing the code and respective message to be further accessed on UDP thread
	_receivedCode = readMessageFromServer(sslConn);
	
	//receiving the configuration file
	printf("[TCP] Machine verified in the central system. Waiting to receive configuration...\n");

	//Sending the signal to the UDP thread
	pthread_cond_signal(&condMsg);

	_sending_latency = (unsigned short) atoi(data->latency);
	
	//Continue tcp thread only if the received code is 150
	if(_receivedCode==150){
		
		to.tv_sec = 1;
		to.tv_usec = 0;
		setsockopt (sock,SOL_SOCKET,SO_RCVTIMEO,(char *)&to, sizeof(to));
		
		int currentCode;
		//Read the config message received and stored the current code
		currentCode = readMessageFromServer(sslConn);
		
		if(currentCode == 0){
			printf("No config file received. Machine will start with default config\n");
		}else{
			//Setup the config file
			char filename[16];
			snprintf(filename,16,"ConfigFile%d",_id_machine);
			strcat(filename,".txt");

			FILE *file =fopen(filename, "w");

			if(NULL == file){
	    		fprintf(stderr, "Cannot open file: %s\n", filename);
			}
			
			if(currentCode == 2){
				//Writes configure message received from server
				fputs(_configureMessage, file);
				_configureMessage[0] = '\0';
				fputc('\n',file);
				printf("Config file has been updated\n");
			fclose(file);
			}else{
				printf("Invalid config file received\n");
			}
		}

		//Start sending messages
		sendMessagesToServer(sslConn);
	}

	SSL_free(sslConn);
	close(sock);
	tcpRunning = false;
	pthread_exit(NULL);
}

//Thread method to handle UDP protocol
void* udpHandler(void *arg){
	struct DataStruct* data = (struct DataStruct *) arg;
	struct sockaddr_storage client;
	int err, sock, res;
	unsigned int adl;
	char line[BUF_SIZE];
	char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
	struct addrinfo  req, *list;
	
	bzero((char *)&req,sizeof(req));
	req.ai_family = AF_INET6;
	req.ai_socktype = SOCK_DGRAM;		
	req.ai_flags = AI_PASSIVE;

	err=getaddrinfo(NULL, SERVER_PORT_UDP , &req, &list);

	if(err) {
        printf("Failed to get local address, error: %s\n",gai_strerror(err)); 
        exit(1); 
    }

	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

	if(sock==-1) {
        perror("Failed to open socket"); 
        freeaddrinfo(list); 
        exit(1);
    }
	if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }

	//Receive the signal from TCP thread
	pthread_cond_wait(&condMsg,&muxter);
	puts("    	[UDP] Listening for UDP requests (both over IPv6 or IPv4)");
	freeaddrinfo(list);
	adl=sizeof(client);	
	
	int counter = 0;
	
	while(tcpRunning){		
		//Receive hello message
		res=recvfrom(sock,line,BUF_SIZE,0,(struct sockaddr *)&client,&adl);

		if(!getnameinfo((struct sockaddr *)&client,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV)) 
			printf("	[UDP] Request from node %s, port number %s\n", cliIPtext, cliPortText);
		else puts("    	[UDP] Got request, but failed to get client address");	
		
		//Builds the line received from client to prints it, stores the received code from udp client
		int upd_recv_code = buildandPrintReceivedMessage(line);
		
		//Create variables to build the received message
		char version = 1;
		short id = 0;	
		short length;
		char final_message[6+length];
		char* ptr;

		//After the first interaction start sending ack messages
		if(counter>0){
			ptr = "ack message";
			length = strlen(ptr);
		}else{
			ptr = _validationMessage;
			length=0;
			char* auxPtr = ptr;
			//Finds the length of the message
			while(*auxPtr){
				length++;
				auxPtr++;
			}
		}
		
		//Build message and store it on final_message
		generateByteMessage(version,_receivedCode,_id_machine,length,ptr,final_message);
		
		printf("	[UDP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s\n",version,_receivedCode,
		length,_id_machine,ptr);		

		sendto(sock,final_message,6+length,0,(struct sockaddr *)&client,adl);
		
		counter++;

		//If the received is 3 it's a reset message
		if(upd_recv_code==3){
			_resetCall = true;
			break;
		}		

	}

	close(sock);
	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	struct DataStruct *data;

	//if missing any arguments on the terminal line
	if (argc < 5) {
        printf("Error: [Missing arguments]\n");
        printf("Correct format: %s [ipv4/ipv6] [machine_id] [sending_latency] [client_name]\n", argv[0]);
        exit(0);
    }

    pthread_mutex_init(&muxter, NULL);
	pthread_cond_init(&condMsg,NULL);

	//Filling the struct to use it on the threads
    data->ip = argv[1];
    data->id = argv[2];
    data->latency = argv[3];
    data->certificate = argv[4];

	pthread_t tcpThread, udpThread;

	pthread_t tcpT, udpT;

    if(pthread_create(&tcpThread, NULL, tcpHandler,(void*)data)) {
        perror("Error creating thread.");
    }

    if(pthread_create(&udpThread, NULL, udpHandler,(void*)data)) {
        perror("Error creating thread.");
    }
    pthread_join(tcpThread,NULL);     
    pthread_join(udpThread, NULL);
    
    if(_resetCall){
    	_resetCall = false;
    	
    	printf("Machine resetting. Wait 20 seconds please...\n");
    	
    	sleep(20);
		
		if(pthread_create(&tcpT, NULL, tcpHandler,(void*)data)) {
	        perror("Error creating thread.");
	    } 
	   
	   	if(pthread_create(&udpT, NULL, udpHandler,(void*)data)) {
        	perror("Error creating thread.");
    	}
		pthread_join(tcpT,NULL);
		pthread_join(udpT,NULL);
    }		

    pthread_cond_destroy(&condMsg);
	pthread_mutex_destroy(&muxter);
}

//Write message to server and prints the content sent
void writeMessageToServer(SSL *sslConn, unsigned char version, unsigned char code, short length, unsigned char *message){
	int j;
	unsigned char bt;
	message[length];

	SSL_write(sslConn, &version, 1);
	SSL_write(sslConn, &code, 1);
	
	short changable_id = _id_machine;
	for(j = 0; j < 2; j++){
		bt = (unsigned char) changable_id;
		SSL_write(sslConn, &bt, 1);
		changable_id = changable_id>>8;		
	}
		
	short changable_length = length;
	for(j = 0; j < 2; j++){
		bt = (unsigned char) changable_length;
		SSL_write(sslConn, &bt, 1);
		changable_length = changable_length>>8;
	}			
	
	for(j = 0; j < length; j++){
		SSL_write(sslConn,&message[j], 1);
	}
		
	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s",
		version,code,length,_id_machine,message);
}

//Read message from server and print the content received
int readMessageFromServer(SSL *sslConn){
	unsigned char responseVersion = 0, responseCode = 0;
	int idLess = 0, idMost = 0, lengthLess = 0, lengthMost = 0;
	int i;
	short id = 0, length = 0;
	
	SSL_read(sslConn,&responseVersion,1);
	SSL_read(sslConn,&responseCode,1);
	SSL_read(sslConn,&idLess,1);
	SSL_read(sslConn,&idMost,1);
	SSL_read(sslConn,&lengthLess,1);
	SSL_read(sslConn,&lengthMost,1);	
	
	//Id Calculations
	idMost <<= 8;
	id = idMost;
	id |= idLess;

	//Length Calculations
	lengthMost <<= 8;
	length = lengthMost;
	length |= lengthLess;
	
	//Message
	unsigned char msg[length+1];
	int msgChar = 0;
	
	for(i = 0;i < length; i++){
		SSL_read(sslConn,&msgChar,1);
		msg[i] = (char) msgChar;
	}

	msg[i]='\0';

	//Depending if the code is 150/151 or 2 store the message into the respective global pointer	
	if(responseCode==150 || responseCode==151){
		_validationMessage = (char *)malloc(strlen(msg)+1);
		char* ptr = _validationMessage;
		for(int i = 0;i<length;i++){
			*ptr = msg[i];	
			ptr++;
		}
	}else if(responseCode==2){
		_configureMessage = (char *)malloc(strlen(msg)+1);
		char* ptr = _configureMessage;
		for(int i = 0;i<length;i++){
			*ptr = msg[i];	
			ptr++;
		}
	}
	
	//Prints the content only if the content is valid
	if(id!=0 && responseCode!=0){
		printf("[TCP] RECV <- VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s\n",responseVersion,responseCode,
		length,id,msg);
	}

	return responseCode;
}

//Send TCP messages to the server
void sendMessagesToServer(SSL *sslConn){
	//Setup vars to build/open the file of the respective machine
	char filename[15];
	int local_file_progress = 1;
	snprintf(filename,15,"Mensagens%d",_id_machine);
	strcat(filename,".txt");
	
	FILE *file =fopen(filename, "r");

	if(NULL == file){
	    fprintf(stderr, "Cannot open file: %s\n", filename);
	    return ;
	}

	char msg[100];
	size_t len = sizeof(msg);

	//buffer_line will handle with each message of the file
	char *buffer_line = malloc(len+1);

	if(buffer_line == NULL){
	    perror("Unable to allocate memory for the line buffer.");
	    exit(1);
	}

	buffer_line[0] = '\0';

	printf("[TCP] Start sending messages...\n");

	//Search throught the file content until it's empty
	while(fgets(msg,sizeof(msg),file)!=NULL){				
		
		//When reset occurs stores the number of the current msg to restart on that
		if(_resetCall){
			if(file_progress < local_file_progress){
          		file_progress = local_file_progress;
          	}
          	break;
		}

		//If there isn't enough space to handle the msg the buffer_line will be realloced
		if(len - strlen(buffer_line) < sizeof(msg)){			
			len *=2;
			if((buffer_line = realloc(buffer_line,len)) == NULL){
			    perror("Unable to allocate memory for the line buffer.");
			    free(buffer_line);
			    exit(1);	
			}
		}

		strcat(buffer_line,msg);
		unsigned char version = 0;
		unsigned char code = 1;

		//If finds break line or end of file
		if(buffer_line[strlen(buffer_line) - 1] == '\n' || buffer_line[strlen(buffer_line) - 1] != EOF){
			
			if(local_file_progress >= file_progress){
				//Stores the size of the line into a variable
				int size_linha = strlen(buffer_line)-1;
				
				//Just to better format the last line
				if(buffer_line[size_linha] != '\n'){
					buffer_line[size_linha+1] = '\n';buffer_line[size_linha+2] = '\0';					
				}
				
				//Call the message to write on the terminal the message
				writeMessageToServer(sslConn,version,code,size_linha,buffer_line);				    	
				//Reads and prints the message validation done of the TCP server in java 
				readMessageFromServer(sslConn);
				buffer_line[0]='\0'; 
			}
			buffer_line[0]='\0';
			local_file_progress++;
		}   
		sleep(_sending_latency);  
	}
	fclose(file);
}

//Generate message to be sent
void generateByteMessage(char version, char code, short id,short length,unsigned char* message, unsigned char* byteMsg) {
    byteMsg[0] = version;
    byteMsg[1] = code;

    byteMsg[2] = (char)id;
    id >>= 8;
    byteMsg[3] = (char) id;
		
	short changable_length = length;
    byteMsg[4] = (char) changable_length;
    changable_length >>= 8;
    byteMsg[5] = (char) changable_length;

    int j = 0;
    
    for(int i = 6; i<6+length;i++){
        byteMsg[i] = message[j];
        j++;
    }
}

//Store the content received into vars, print the content and return the received code
int buildandPrintReceivedMessage(char* content){
	unsigned char responseVersion, responseCode;
	int idLess = 0, idMost = 0, lengthMost = 0, lengthLess = 0;
	short id = 0, length = 0;
	
	//Storing the message content into the variables
	responseVersion = content[0];
	responseCode = content[1];
	idLess = content[2];
	idMost = content[3];
	lengthLess = content[4];
	lengthMost = content[5];

	//Converting the char into their respective short variables
	idMost <<= 8;
	id = idMost;
	id |= idLess;

	lengthMost <<= 8;
	length = lengthMost;
	length |= lengthLess;

	char msg[length];
	
	//Storing the rest of the content
	int j=0;
	for(int k = 6; k < 6+length; k++){
		msg[j] = content[k];
		j++;
	}
	msg[j] = '\0';

	printf("	[UDP] RECV <- VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s\n",responseVersion,responseCode,
		length,id,msg);

	return responseCode;
}

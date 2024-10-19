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

#define BUF_SIZE 512
#define SERVER_PORT_TCP "9998"
#define SERVER_PORT_UDP "9999"
#define TIMEOUT 20

#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

short _id_machine;
short _sending_latency;
short _msg_length = 0;
int _receivedCode;
char *_validationMessage;
//char *_configureMessage;
//bool _resetCall = false;
int file_progress = 0;
bool tcpRunning = true;

pthread_mutex_t muxter;
pthread_cond_t condMsg;

void writeMessageToServer(int sock, unsigned char version, unsigned char code, short length, unsigned char *message);
int readMessageFromServer(int sock);
int sendMessagesToServer(int sock);
void generateByteMessage(char version, char code, short id,short length,unsigned char* message, unsigned char* byteMsg);
int buildandPrintReceivedMessage(char* content);

struct DataStruct{
	char *ip;
	char *id;
	char *latency;
};

/*Thread to handle TCP protocol*/
void* tcpHandler(void *arg){
	struct DataStruct* data = (struct DataStruct *) arg;
	int err, sock;
	unsigned long f, i, n, num;
	char line[BUF_SIZE];
	struct addrinfo  req, *list;
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
		perror("[TCP]Failed to open socket"); 
		freeaddrinfo(list); 
		exit(1);
	}

	if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("[TCP]Failed connect"); 
        freeaddrinfo(list); 
        close(sock); 
        exit(1);
	}

	/*Create variables to build the hello message*/
	unsigned char version = 1, code = 0;
	unsigned char *message = "hello message";
	_msg_length = strlen(message);
	_id_machine = (unsigned short) atoi(data->id);
	char final_message[6+_msg_length];

	/*Build message and store it on final_message*/
	generateByteMessage(version,code,_id_machine,_msg_length,message,final_message);
	
	/*Write message to server*/
	for(int i = 0; i < 6+_msg_length; i++) write(sock,&final_message[i], 1);
	printf("****************************************************************************************************************\n");
	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; ID: %d; MSG: %s\n",version,code,_msg_length,_id_machine,message);
	printf("[TCP] Waiting for server to check id...\n");

	/*Lock the thread to handle the global variable*/
	//pthread_mutex_lock(&muxter);
	/*Storing the code and respective message (global, so that can further be 
	access to the udp thread) received from the TCP server
	150/151*/
	_receivedCode = readMessageFromServer(sock);
	
	//
	/*receiving the configuration file*/
	printf("[TCP] Machine verified in the central system. Waiting to receive configuration...\n");
	/*Sending the signal to the UDP thread*/
	pthread_cond_signal(&condMsg);

	//pthread_mutex_unlock(&muxter);

	_sending_latency = (unsigned short) atoi(data->latency);
	/*If the received code is ACK start sending messages*/

	if(_receivedCode==150){
		/*int currentCode;
		currentCode = readMessageFromServer(sock);

		//Creates/Updates file
		FILE * fPtr;	
		fPtr = fopen("textFile.txt" ,"w");
		///currentCode == 2 means it's config file
		if(currentCode == 2){
			//writes configure message received from server
			fputs(_configureMessage, fPtr);
			//clean the pointer
			_configureMessage[0] = '\0';
			fputc('\n',fPtr);
			printf("Config file has been updated\n");
		}else{
			printf("No config file received. Machine will start with default config\n");
		}
		fclose(fPtr);*/

		/*Start sending messages*/
		sendMessagesToServer(sock);
	}

	close(sock);
	tcpRunning = false;
	pthread_exit(NULL);
}

//thread method to handle UDP protocol
void* udpHandler(void *arg){
	struct DataStruct* data = (struct DataStruct *) arg;
	struct sockaddr_storage client;
	int err, sock, res, i;
	unsigned int adl;
	char line[BUF_SIZE];
	char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
	struct addrinfo  req, *list;
	struct timeval to; 
	
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

	/*Receive the signal from TCP thread*/
	pthread_cond_wait(&condMsg,&muxter);
	puts("    	[UDP] Listening for UDP requests (both over IPv6 or IPv4)");
	freeaddrinfo(list);
	adl=sizeof(client);	
	to.tv_sec = TIMEOUT;
	to.tv_usec = 0;
	setsockopt (sock,SOL_SOCKET,SO_RCVTIMEO,(char *)&to, sizeof(to));
	int counter = 0;
	
	while(tcpRunning){
		//*******************************************************
		/*Receive hello message*/
		res=recvfrom(sock,line,BUF_SIZE,0,(struct sockaddr *)&client,&adl);

		if(!getnameinfo((struct sockaddr *)&client,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV)) 
			printf("	[UDP] Request from node %s, port number %s\n", cliIPtext, cliPortText);
		else puts("    	[UDP] Got request, but failed to get client address");	

		/*Builds the line received from client and prints it,
		  Stores the received code from udp client*/	
		int upd_recv_code = buildandPrintReceivedMessage(line);
		/*Build message and store it on final_message*/
		char version = 1;
		short id = 0;	
		/*Stores the validation message into the pointer*/
		short length;

		char final_message[6+length];
		char* ptr;
		if(counter>0){
			ptr = "ack message";
			length = strlen(ptr);
		}else{
			ptr = _validationMessage;
			length=0;
			char* auxPtr = ptr;
			/*Finds the length of the message*/
			while(*auxPtr){
				length++;
				auxPtr++;
			}
		}
		generateByteMessage(version,_receivedCode,_id_machine,length,ptr,final_message);
		printf("	[UDP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s\n",version,_receivedCode,
		length,_id_machine,ptr);
		sendto(sock,final_message,6+length,0,(struct sockaddr *)&client,adl);
		counter++;
		//*******************************************************
	}


	//receber hellos ou resests
	/*Receive hello message ou reset*/
	//res=recvfrom(sock,line,BUF_SIZE,0,(struct sockaddr *)&client,&adl);
	/*Builds the line received from client and prints it,
	  Stores the received code from udp client*/
	//HELLO OU RESET
	//sleep(8);
	//int upd_recv_code2 = buildandPrintReceivedMessage(line);
	//if(upd_recv_code2==3){
	//	_resetCall = true;
	//}

	close(sock);
	printf("****************************************************************************************************************\n");
	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	struct DataStruct *data;

	//if missing any arguments on the terminal line
	if (argc < 4) {
        printf("Error: [Missing arguments]\n");
        printf("Correct format: %s [ipv4/ipv6] [machine_id] [sending_latency]\n", argv[0]);
        exit(0);
    }

    pthread_mutex_init(&muxter, NULL);
	pthread_cond_init(&condMsg,NULL);

    data->ip = argv[1];
    data->id = argv[2];
    data->latency = argv[3];

    /*Initialize the first two threads*/
	pthread_t tcpThread, udpThread;

	pthread_t tcpT;

    if(pthread_create(&tcpThread, NULL, tcpHandler,(void*)data)) {
        perror("Error creating thread.");
    }

    if(pthread_create(&udpThread, NULL, udpHandler,(void*)data)) {
        perror("Error creating thread.");
    }
    pthread_join(tcpThread,NULL);     
    
    /*if(_resetCall){
    	_resetCall = false;
    	printf("Machine resetting. Wait 5 seconds please.\n");
    	sleep(5);
		if(pthread_create(&tcpT, NULL, tcpHandler,(void*)data)) {
	        perror("Error creating thread.");
	    } 
		pthread_join(tcpT,NULL);
    }*/
	
	pthread_join(udpThread, NULL);

    pthread_cond_destroy(&condMsg);
	pthread_mutex_destroy(&muxter);
}

void writeMessageToServer(int sock, unsigned char version, unsigned char code, short length, unsigned char *message){
	int i,j;
	unsigned char bt;
	message[length];

	write(sock, &version, 1);
	write(sock, &code, 1);
	
	short changable_id = _id_machine;
	for(j = 0; j < 2; j++){
		bt = (unsigned char) changable_id;
		write(sock, &bt, 1);
		changable_id = changable_id>>8;		
	}
		
	short changable_length = length;
	for(j = 0; j < 2; j++){
		bt = (unsigned char) changable_length;
		write(sock, &bt, 1);
		changable_length = changable_length>>8;
	}			
	
	for(j = 0; j < length; j++){
		write(sock,&message[j], 1);
	}
		
	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s",
		version,code,length,_id_machine,message);
}

int readMessageFromServer(int sock){

	unsigned char responseVersion = 0, responseCode = 0;
	int idLess = 0, idMost = 0, lengthLess = 0, lengthMost = 0;
	int i;
	
	read(sock,&responseVersion,1);
	read(sock,&responseCode,1);
	read(sock,&idLess,1);
	read(sock,&idMost,1);
	read(sock,&lengthLess,1);
	read(sock,&lengthMost,1);		
	
	//ID CALCULATIONS
	idMost <<= 8;
	_id_machine = idMost;
	_id_machine |= idLess;

	//LENGTH CALCULATIONS
	lengthMost <<= 8;
	_msg_length = lengthMost;
	_msg_length |= lengthLess;
	
	//MESSAGE
	unsigned char msg[_msg_length+1];
	int msgChar = 0;
	
	for(i = 0;i < _msg_length; i++){
		read(sock,&msgChar,1);
		msg[i] = (char) msgChar;
	}

	msg[i]='\0';

	/*Stored "request accepted/denied into global pointer _validationMessage*/
	if(responseCode==150 || responseCode==151){
		_validationMessage = (char *)malloc(strlen(msg)+1);
		char* ptr = _validationMessage;
		for(int i = 0;i<_msg_length;i++){
			*ptr = msg[i];	
			ptr++;
		}
	}/*else if(responseCode==2){
		_configureMessage = (char *)malloc(strlen(msg)+1);
		char* ptr = _configureMessage;
		for(int i = 0;i<_msg_length;i++){
			*ptr = msg[i];	
			ptr++;
		}
	}
*/
	printf("[TCP] RECV <- VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MSG: %s\n",responseVersion,responseCode,
		_msg_length,_id_machine,msg);
	return responseCode;
}

int sendMessagesToServer(int sock){
	char filename[15];
	//int local_file_progress = 1;
	snprintf(filename,15,"Mensagens%d",_id_machine);
	strcat(filename,".txt");

	printf("[TCP] Start sending messages...\n");
	FILE *file =fopen(filename, "r");

	if(NULL == file){
	    fprintf(stderr, "Cannot open file: %s\n", filename);
	    return 1;
	}

	char msg[100];
	size_t len = sizeof(msg);

	char *buffer_line = malloc(len+1);

	if(buffer_line == NULL){
	    perror("Unable to allocate memory for the line buffer.");
	    exit(1);
	}

	buffer_line[0] = '\0';

		//search throught the file content
		while(fgets(msg,sizeof(msg),file)!=NULL){
				
			/*if(_resetCall){
				if(file_progress < local_file_progress){
          			file_progress = local_file_progress;
          		}
          		break;
			}*/

			//if the remained size is less than the necessary for the msg
			if(len - strlen(buffer_line) < sizeof(msg)){
			    //reallocate more memory by simply multiply by 2 the len
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

				//if when search through the file finds break line 
			    if(buffer_line[strlen(buffer_line) - 1] == '\n'){
			    	//if(local_file_progress >= file_progress){
				    	//stores the size of that line into a variable
				    	int size_linha = strlen(buffer_line)-1;
				    	//call the message to write on the terminal the message
				    	writeMessageToServer(sock,version,code,size_linha,buffer_line);
				    	
				    	//reads and prints the message validation done of the TCP server in java 
				    	readMessageFromServer(sock);
				    	buffer_line[0]='\0'; 
			    	//}
			    	//buffer_line[0]='\0';
			    	//local_file_progress++;
			    } sleep(_sending_latency);  
		}

	fclose(file);
}	
/*Generate message to be sent*/
void generateByteMessage(char version, char code, short id,short length,unsigned char* message, unsigned char* byteMsg) {
        //setup of every msg
        byteMsg[0] = version;
        byteMsg[1] = code;

        byteMsg[2] = (char)id;
        id >>= 8;
        byteMsg[3] = (char) id;
		
		short changable_length = length;
        byteMsg[4] = (char) changable_length;
        changable_length >>= 8;
        byteMsg[5] = (char) changable_length;
        //*******************
        int j = 0;
        for(int i = 6; i<6+length;i++){
        	byteMsg[i] = message[j];
        	j++;
        }
}

int buildandPrintReceivedMessage(char* content){
	unsigned char responseVersion, responseCode;
	int idLess = 0, idMost = 0, lengthMost = 0, lengthLess = 0;
	short id = 0, length = 0;
	
	/*Storing the message content into the variables*/
	responseVersion = content[0];
	responseCode = content[1];
	idLess = content[2];
	idMost = content[3];
	lengthLess = content[4];
	lengthMost = content[5];

	/*Converting the char into their respective short variables*/
	idMost <<= 8;
	id = idMost;
	id |= idLess;

	lengthMost <<= 8;
	length = lengthMost;
	length |= lengthLess;

	/*hello/reset*/
	char msg[length];
	
	/*Storing the rest of the content*/
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

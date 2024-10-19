#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
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
#define MAXCHAR 1000

short id_machine;
short sending_latency;
short msg_length = 0;

#define GETS(B,S) {fgets(B,S-2,stdin);B[strlen(B)-1]=0;}

void writeMessageToServer(int sock, unsigned char version, unsigned char code, short length, unsigned char *message);
int readSignalRequestFromServer(int sock);
int sendMessagesToServer(int sock);
int validate_number(char *str);
void generateByteMessage(char version, char code, short id,short length,unsigned char* message, unsigned char* byteMsg);

struct DataStruct{
	char *ip;
	char *id;
	char *latency;
};

pthread_mutex_t muxter;
pthread_cond_t condMsg;

int receivedCode;
char *validationMessage;

//thread method to handle TCP protocol
void* tcpHandler(void *arg){
	struct DataStruct* data = (struct DataStruct *) arg;
	int err, sock;
	unsigned long f, i, n, num;
	char line[BUF_SIZE];
	struct addrinfo  req, *list;
	//TCP protocol setup
	bzero((char *)&req,sizeof(req));
	
	req.ai_family = AF_UNSPEC;
	req.ai_socktype = SOCK_STREAM;
	err=getaddrinfo(data->ip, SERVER_PORT_TCP , &req, &list);
	
	if(err) {printf("[TCP]Failed to get server address, error: %s\n",gai_strerror(err)); exit(1);}

	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
	
	if(sock==-1) {
		perror("[TCP]Failed to open socket"); freeaddrinfo(list); exit(1);
	}

	if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("[TCP]Failed connect"); freeaddrinfo(list); close(sock); exit(1);
	}

	//fill global variables with respective values curated from terminal line
	id_machine = (unsigned short) atoi(data->id);
	sending_latency = (unsigned short) atoi(data->latency);

	//setup var do build hello message to be sent to server
	unsigned char version = 1;
	unsigned char code = 0;
	msg_length = 13;
	unsigned char *message = "hello message";
	char messageToBeSent[6+msg_length];

	//method that stores the message [6+length] sorted in the variable messageToBeSent
	generateByteMessage(version,code,id_machine,msg_length,message,messageToBeSent);

	//writing to the server char by char the messageToBeSent
	for(int i=0;i<6+msg_length;i++){
		write(sock,&messageToBeSent[i], 1);
	}

	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MESSAGE: %s\n",
		version,code,msg_length,id_machine,message);
	printf("[TCP] Waiting for server to check id...\n");
	
	//setup vars to handle the response from TCP server
	unsigned char responseVersion = 0;
	unsigned char responseCode = 0;
	int idLess = 0, idMost = 0, lengthLess = 0, lengthMost = 0;
	pthread_mutex_lock(&muxter);
	//store the code into a variable (the code will be 150(ack) or 151(nack))
	receivedCode = readSignalRequestFromServer(sock);
	printf("-----------------------------------------------------------------------------------------------------\n");
	//Right after receiving the request response code send a signal to the UDP thread
	pthread_cond_signal(&condMsg);
	pthread_mutex_unlock(&muxter);
	
	//if the code is 150 that means server reply with ack (other words tcp server recognized the machine id)
	if(receivedCode==150){
		sendMessagesToServer(sock);
	}
	pthread_mutex_unlock(&muxter);
	close(sock);
	pthread_exit(NULL);
}
//thread method to handle UDP protocol
void* udpHandler(void *arg){
	struct DataStruct* data = (struct DataStruct *) arg;

	struct sockaddr_storage client;
	int err, sock, res, i;
	unsigned int adl;
	char line[BUF_SIZE], line1[BUF_SIZE];
	char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
	struct addrinfo  req, *list;
	//UDP protocol setup
	bzero((char *)&req,sizeof(req));

	req.ai_family = AF_INET6;
	req.ai_socktype = SOCK_DGRAM;		
	req.ai_flags = AI_PASSIVE;	// local address

	err=getaddrinfo(NULL, SERVER_PORT_UDP , &req, &list);
	if(err) {
        printf("Failed to get local address, error: %s\n",gai_strerror(err)); exit(1); 
    }

	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);

	if(sock==-1) {
        perror("Failed to open socket"); freeaddrinfo(list); exit(1);
    }
	if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        perror("Bind failed");close(sock);freeaddrinfo(list);exit(1);
    }
    //Receive the signal from the tcp thread
	pthread_cond_wait(&condMsg,&muxter);
	freeaddrinfo(list);
	puts("[UDP] Listening for UDP requests (both over IPv6 or IPv4)");
	adl=sizeof(client);
			
	res=recvfrom(sock,line,BUF_SIZE,0,(struct sockaddr *)&client,&adl);
	//setup vars to handle the hello request from UDP client
	unsigned char responseVersion;
	unsigned char responseCode;
	int  myIdLess = 0,myIdMost = 0,myLengthMost = 0,myLengthLess = 0;

	short myId = 0, myLength = 0;
	//Storing byte by byte into the respective created vars
	responseVersion = line[0];
	responseCode = line[1];
	myIdLess = line[2];
	myIdMost = line[3];
	myLengthLess = line[4];
	myLengthMost = line[5];

	//ID CALCULATIONS
	myIdMost <<= 8;
	myId = myIdMost;
	myId |= myIdLess;

	//LENGTH CALCULATIONS
	myLengthMost <<= 8;
	myLength = myLengthMost;
	myLength |= myLengthLess;

	char myMsg[myLength];

	int j=0;
	for(int k=6;k<6+myLength;k++){
		myMsg[j] = line[k];
		//preencher o array para msg
		j++;
	}
	printf("-----------------------------------------------------------------------------------------------------\n");
	printf("[UDP] RECEIVED <- VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MESSAGE: %s\n",responseVersion,responseCode,
		myLength,myId,myMsg);
	
	if(!getnameinfo((struct sockaddr *)&client,adl,
		cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV)) 
		printf("[UDP] Request from node %s, port number %s\n", cliIPtext, cliPortText);
	else puts("[UDP] Got request, but failed to get client address");

	char version = 1;
	short id = 0;
	
	char* ptr = validationMessage;
	short length=0;
	while(*ptr){
		length++;
		ptr++;
	}

	char messageToBeSent[6+length];
	//setup the message to their final form
	generateByteMessage(version,receivedCode,11111,length,validationMessage,messageToBeSent);

	printf("[UDP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MESSAGE: %s\n",version,receivedCode,
		messageToBeSent[4],0,validationMessage);
	printf("-----------------------------------------------------------------------------------------------------\n");
	sendto(sock,messageToBeSent,6+length,0,(struct sockaddr *)&client,adl);
	
	close(sock);
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

    printf("==================== Machine: %s (Ip: %s) (Latency: %s sec) Turned ON ====================\n\n",argv[2],argv[1],argv[3]);

    pthread_mutex_init(&muxter, NULL);
	pthread_cond_init(&condMsg,NULL);

    data->ip = argv[1];
    data->id = argv[2];
    data->latency = argv[3];

	pthread_t tcpThread, udpThread;

    if(pthread_create(&tcpThread, NULL, tcpHandler,(void*)data)) {
        perror("Error creating thread.");
    }

    if(pthread_create(&udpThread, NULL, udpHandler,(void*)data)) {
        perror("Error creating thread.");
    }

    pthread_join(tcpThread, NULL);
    pthread_join(udpThread, NULL);

    pthread_cond_destroy(&condMsg);
	pthread_mutex_destroy(&muxter);
}

void writeMessageToServer(int sock, unsigned char version, unsigned char code, short length,
	unsigned char *message){
	int i,j;
	unsigned char bt;
	message[length];

	for(i = 0; i <= 4; i++){
		if(i==0){
			write(sock, &version, 1);
		}else if(i==1){
			write(sock, &code, 1);
		}else if(i==2){
			short changable_id = id_machine;
			for(j = 0; j < 2; j++){
				bt = (unsigned char) changable_id;
				write(sock, &bt, 1);
				changable_id = changable_id>>8;
				
			}
		}else if(i==3){
			short changable_length = length;
			for(j = 0; j < 2; j++){
				bt = (unsigned char) changable_length;
				write(sock, &bt, 1);
				changable_length = changable_length>>8;
			}			
		}else{
			for(j = 0; j < length; j++){
				write(sock,&message[j], 1);
			}
		}
	}
	printf("[TCP] SEND -> VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MESSAGE: %s",
		version,code,length,id_machine,message);
}

int readSignalRequestFromServer(int sock){
	
	unsigned char responseVersion = 0;
	unsigned char responseCode = 0;
	int idLess = 0, idMost = 0, lengthLess = 0, lengthMost = 0;

	int i;
	for(int i = 0; i < 6; i++){
		if(i==0) read(sock,&responseVersion,1);
		else if(i==1) read(sock,&responseCode,1);
		else if(i==2) read(sock,&idLess,1);
		else if(i==3) read(sock,&idMost,1);
		else if(i==4) read(sock,&lengthLess,1);
		else read(sock,&lengthMost,1);		
	}

	//ID CALCULATIONS
	idMost <<= 8;
	id_machine = idMost;
	id_machine |= idLess;

	//LENGTH CALCULATIONS
	lengthMost <<= 8;
	msg_length = lengthMost;
	msg_length |= lengthLess;
	
	//MESSAGE
	unsigned char msg[msg_length+1];
	int msgChar = 0;
	for(i = 0;i < msg_length; i++){
		read(sock,&msgChar,1);
		msg[i] = (char) msgChar;

	}
	msg[i]='\0';

	if(responseCode==150 || responseCode==151){
		validationMessage = (char *)malloc(strlen(msg)+1);
		char* ptr = validationMessage;
		for(int i = 0;i<msg_length;i++){
			*ptr = msg[i];	
			ptr++;
		}
	}
	printf("[TCP] RECEIVED <- VERSION: %d; CODE: %d; LENGTH: %d; MACHINE ID: %d; MESSAGE: %s\n",responseVersion,responseCode,
		msg_length,id_machine,msg);

	return responseCode;
}

int sendMessagesToServer(int sock){
		char filename[15];
		snprintf(filename,15,"Mensagens%d",id_machine);
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
	    		//stores the size of that line into a variable
	    		int size_linha = strlen(buffer_line)-1;
	    		//call the message to write on the terminal the message
	    		writeMessageToServer(sock,version,code,size_linha,buffer_line);
	    		//reads and prints the message validation done of the TCP server in java 
	    		readSignalRequestFromServer(sock);
	    		buffer_line[0]='\0';
	    	}	
	    	sleep(sending_latency);
		}
		fclose(file);
}	

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
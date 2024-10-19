package eapli.base.services.application.scm;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;

import java.net.Socket;

public class TCP implements Runnable {
    static final int SERVER_PORT=9990;
    static final String TRUSTED_STORE="B.jks";
    static final String KEYSTORE_PASS="secret";

    /**
     * Creates a new tcp thread every time a connection is established with a machine.
     */
    @Override
    public void run() {
        SSLServerSocket sock = null;
        Socket cliSock;
        System.out.println("TCP Thread Running...");

        //Trust these certificates provided by authorized clients
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword",KEYSTORE_PASS);

        //Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore",TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword",KEYSTORE_PASS);

        SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            sock = (SSLServerSocket) sslF.createServerSocket(SERVER_PORT);
            sock.setNeedClientAuth(true);

        }catch(IOException ex) {
            System.out.println("Server failed to open local port " + SERVER_PORT);
            System.exit(1);
        }

        //runs until program is closed
        while(true) {
            try {
                //if communication is established create new thread
                cliSock = sock.accept();
                new Thread(new TCPThread(cliSock)).start();
            } catch (Exception e){
                System.out.println("Failed to create thread");
                System.exit(1);
            }
        }
    }
}
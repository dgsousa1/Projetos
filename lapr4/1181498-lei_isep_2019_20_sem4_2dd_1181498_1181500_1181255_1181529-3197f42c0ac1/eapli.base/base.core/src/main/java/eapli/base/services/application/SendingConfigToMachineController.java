package eapli.base.services.application;

import ch.qos.logback.core.net.ssl.SSL;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.List;
import java.io.*;
import java.net.*;

public class SendingConfigToMachineController {
    private MachineRepository machineRepository = PersistenceContext.repositories().machine();

    static final int SERVER_PORT = 9990;
    static final String TRUSTED_STORE = "B.jks";
    static final String KEYSTORE_PASS = "secret";

    static InetAddress serverIP;
    static SSLSocket sock;

    public List<Machine> getAllMachines() {
        return machineRepository.listAllMachines();
    }

    public boolean sendConfigToTCPServer(Machine machine, ConfigurationFile configurationFile) {

        // Trust these certificates provided by servers
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key for client certificate when requested by the server
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();


        try {
            serverIP = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + serverIP);
            System.exit(1);
        }

        try {
            sock = (SSLSocket) sf.createSocket(serverIP,SERVER_PORT);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        try {
            sock.startHandshake();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());

            String message = readMessageFromConfigFile(configurationFile.configurationFilePath());

            char code = 2;
            sOut.write((byte) code);

            short changeableId = machine.getSerialNumber().shortValue();

            for (int j = 0; j < 2; j++) {
                sOut.write((byte) changeableId);
                changeableId >>= 8;
            }

            short changeableLength = (short) message.length();

            for (int j = 0; j < 2; j++) {
                sOut.write((byte) changeableLength);
                changeableLength >>= 8;
            }

            byte[] arrayOfBytes = message.getBytes();

            for (int j = 0; j < message.length(); j++) {
                sOut.write(arrayOfBytes[j]);
            }

            sock.close();
            return true;
        } catch (IOException e) {
            System.out.println("IOException");
            return false;
        }
    }

    private String readMessageFromConfigFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String message = "";
            String line;
            while ((line = br.readLine()) != null) {
                message += line + '\n';
            }
            return message;
        } catch (IOException e) {
            System.out.println("Problems reading file");
            return null;
        }
    }
}

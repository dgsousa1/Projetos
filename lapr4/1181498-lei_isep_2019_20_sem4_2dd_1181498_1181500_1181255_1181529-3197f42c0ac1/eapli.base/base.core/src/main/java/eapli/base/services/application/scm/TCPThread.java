package eapli.base.services.application.scm;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.services.application.AddRawMessageToLogController;
import eapli.base.services.application.IPRepository;

import java.io.*;
import java.net.*;

public class TCPThread implements Runnable {
    private final Socket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    private Long machineId = 0L;

    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();
    private final AddRawMessageToLogController rawMessageController = new AddRawMessageToLogController();

    InetAddress clientIP;

    /**
     * TCP thread constructor.
     *
     * @param cli_s socket used to communicate.
     */
    public TCPThread(Socket cli_s) {
        s = cli_s;
    }

    /**
     * Runnable override that is going to be the tcp threads.
     */
    public void run() {
        //get client address
        clientIP = s.getInetAddress();
        System.out.println("[TCP] New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());

        try {
            if (clientIP.getHostAddress().equals(InetAddress.getLocalHost().getHostAddress())) {
                BackOfficeConnect connect = new BackOfficeConnect(s);
                connect.backOfficeCommunication();
            } else {
                runMachine();
            }
        } catch (UnknownHostException e) {
            System.out.println("Error reading IP address");
        }
    }

    private void runMachine() {
        try {
            //initialize input/output streams
            sOut = new DataOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());
            //read hello message
            readMessageFromMachine(clientIP.getHostAddress());
            //validate machine given
            if (machineRepository.findBySerialNumber(machineId).isPresent()) {
                //writes ack message because machine is valid
                writeMessageToMachine((char) 150, "request accepted");
                //adds machines to IP repository
                IPRepository.addIPToRepository(machineId, clientIP.getHostAddress());
            } else {
                //writes nack message because machine is invalid
                writeMessageToMachine((char) 151, "request refused");
                return;
            }

            for (String s : IPRepository.getConfigList()) {
                String[] splits = s.split(";");
                //id
                try {
                    long id = Long.parseLong(splits[1]);
                    if (id == machineId) {
                        writeMessageToMachine((char) 2, splits[3]);
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            boolean val;
            //while messages are sent
            do {
                //reads messages
                val = readMessageFromMachine(clientIP.getHostAddress());
            } while (val);

            //closes connection
            System.out.println("[TCP] Client " + clientIP.getHostAddress() + ", port number: " + s.getPort() +
                    " disconnected");
            System.out.println("[TCP] Thread closing...");
            s.close();
        } catch (IOException ex) {
            System.out.println("[TCP] IOException");
        }
    }

    /**
     * Reads message sent by tcp.
     *
     * @param address client address.
     * @return true if success and false if failure.
     */
    public boolean readMessageFromMachine(String address) {
        try {
            int version = 0;
            int code = 0;
            int idLess = 0, idMost = 0;
            int lengthLess = 0, lengthMost = 0;

            //the msgs are made up of 6 bytes(unsigned char) + the length given on the last two bytes
            for (int i = 0; i < 6; i++) {
                if (i == 0) {
                    version = sIn.read();
                } else if (i == 1) {
                    code = sIn.read();
                } else if (i == 2) {
                    idLess = sIn.read();
                } else if (i == 3) {
                    idMost = sIn.read();
                } else if (i == 4) {
                    lengthLess = sIn.read();
                } else {
                    lengthMost = sIn.read();
                }
            }

            //validates values
            if (version == -1 && code == -1 && idLess == -1 && lengthLess == -1 && idMost == -1) {
                return false;
            }

            /*calculates id:
                shift left of the most significant byte
                or in other to join the both bytes
            */
            idMost <<= 8;
            int id = idMost;
            id |= idLess;

            /*calculates length:
                shift left of the most significant byte
                or in other to join the both bytes
            */
            lengthMost <<= 8;
            int length = lengthMost;
            length |= lengthLess;

            /*generates message:
                the message is sent byte by byte (char by char) and added to the string
            */
            int msgChar;
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < length; i++) {
                msgChar = sIn.read();
                msg.append((char) msgChar);
            }

            machineId = (long) id;

            System.out.println("[TCP | " + address + "] RECV <- VERSION: " + version + "; CODE: " + code + "; ID: " + id + "; LENGTH: " + length + "; MSG: " + msg);

            if (code == 0) {
                return true;
            }

            //validates message
            long validationId = rawMessageController.validateRawMessage(msg.toString());

            if (validationId != -1) {
                //adds raw message to the logs
                rawMessageController.addRawMessageToLogs(machineId, msg.toString());
                writeMessageToMachine((char) 150, "valid message");
            } else {
                writeMessageToMachine((char) 151, "invalid message");
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Writes message to be sent by tcp.
     *
     * @param code    message code.
     * @param message raw message.
     */
    public void writeMessageToMachine(char code, String message) {
        short length = (short) message.length();

        //creates and sends message attributes
        try {
            char version = 0;
            for (int i = 0; i <= 4; i++) {
                //writes version
                if (i == 0) {
                    sOut.write((byte) version);
                    //writes code
                } else if (i == 1) {
                    sOut.write((byte) code);
                    //writes id
                } else if (i == 2) {
                    short changeableId = machineId.shortValue();
                    for (int j = 0; j < 2; j++) {
                        sOut.write((byte) changeableId);
                        changeableId >>= 8;
                    }
                    //writes length
                } else if (i == 3) {
                    short changeableLength = length;
                    for (int j = 0; j < 2; j++) {
                        sOut.write((byte) changeableLength);
                        changeableLength >>= 8;
                    }
                    //writes message
                } else {
                    byte[] arrayOfBytes = message.getBytes();
                    for (int j = 0; j < length; j++) {
                        sOut.write(arrayOfBytes[j]);
                    }
                }
            }

            System.out.println("[TCP | " + clientIP.getHostAddress() + "] SEND -> VERSION: " + (int) version + "; CODE: " + (int) code + "; ID: " + machineId +
                    "; LENGTH: " + length + "; MSG: " + message);

        } catch (IOException ex) {
            System.out.println("[TCP] Client " + clientIP.getHostAddress() + ": Connection not allowed");
        }
    }
}



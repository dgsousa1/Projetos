package eapli.base.services.application.scm;

import eapli.base.services.application.IPRepository;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class BackOfficeConnect {

    private final Socket s;
    private DataInputStream sIn;

    public BackOfficeConnect(Socket s) {
        this.s = s;
    }

    public void backOfficeCommunication() {
        try {
            //initialize input/output streams
            sIn = new DataInputStream(s.getInputStream());
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        boolean val = true;
        do {
            String message = readMessage();
            if (message == null) {
                val = true;
            } else {
                if (message.split(";")[0].equals("2")) {
                    IPRepository.addConfigToString(message);
                    val = false;
                } else if (message.split(";")[0].equals("3")) {
                    IPRepository.addResetToRepository(message);
                    val = false;
                }
            }
        } while (val);
    }

    private String readMessage() {
        //(short)id;(short)length;(string)path
        int idLess, idMost, id, code;
        int lengthLess, lengthMost, length;

        try {
            code = sIn.read();
            idLess = sIn.read();
            idMost = sIn.read();
            lengthLess = sIn.read();
            lengthMost = sIn.read();

            /*calculates id:
                shift left of the most significant byte
                or in other to join the both bytes
            */
            idMost <<= 8;
            id = idMost;
            id |= idLess;

            /*calculates length:
                shift left of the most significant byte
                or in other to join the both bytes
            */
            lengthMost <<= 8;
            length = lengthMost;
            length |= lengthLess;

            if (id == -1 && length == -1) {
                return null;
            }

            /*generates message:
                the message is sent byte by byte (char by char) and added to the string
            */
            int pathChar;
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < length; i++) {
                pathChar = sIn.read();
                message.append((char) pathChar);
            }

            return code + ";" + id + ";" + length + ";" + message;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

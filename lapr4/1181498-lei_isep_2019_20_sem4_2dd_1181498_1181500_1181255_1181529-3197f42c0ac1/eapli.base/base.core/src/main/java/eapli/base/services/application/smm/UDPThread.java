package eapli.base.services.application.smm;

import eapli.base.services.application.IPRepository;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UDPThread implements Runnable {
    static InetAddress targetIP;
    DatagramSocket sock;
    DatagramPacket udpPacket;
    String address;


    public UDPThread(String ip) {
        try {
            targetIP = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.out.println("        [UDP] Invalid server address supplied: " + ip);
        }
    }

    public void run() {
        byte[] data = new byte[512];
        address = targetIP.getHostAddress();
        try {
            sock = new DatagramSocket();
            udpPacket = new DatagramPacket(data, data.length, targetIP, 9999);
            //sock.setSoTimeout(15000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            if (address.equals(InetAddress.getLocalHost().getHostAddress())) {
                runRequestReboot(data);
            } else {
                runMachine();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    private void runRequestReboot(byte[] data) {
        while (true) {
            udpPacket.setData(data);
            udpPacket.setLength(data.length);
            try {
                sock.receive(udpPacket);
                byte[] d = udpPacket.getData();
                String message = new String(d, 0, d.length);
                IPRepository.addResetToRepository(message);
            } catch (IOException e) {
                System.out.println("IOException reboot");
            }
        }
    }

    private void runMachine() {
        int timer_falha = 1;
        LocalDateTime currentTimer;
        currentTimer = LocalDateTime.now().plusYears(1);

        //LocalDateTime targetTimer = currentTimer.plusYears(1);

        char version = 1, code = 0;
        short id = 0;
        String message = "";

        byte[] data = new byte[512];
        String msg;

        byte[] d;
        byte[] sentMessage = null;
        int receivedVersion = 0, receivedCode = 0;
        int idLess, idMost = 0, receivedId = 0;
        int lengthLess, lengthMost = 0, receivedlength = 0;
        boolean val = true;
        boolean timeSet = false;
        int globalId = 0;
        try {
            //enquanto o tempo do inicio com inferior ao tempo inicial + 1 minuto
            //14:50:00 for antes de 14:50:30
            sock.setSoTimeout(5000);
            while (LocalDateTime.now().isBefore(currentTimer.plusMinutes(timer_falha)) && val) {
                val = true;
                List<String> resetList = IPRepository.getResetList();
                for (String s : resetList) {
                    String[] splits = s.split(";");
                    if (Integer.parseInt(splits[1]) == receivedId) {
                        if (splits[3].equals("true")) {
                            code = 3;
                            message = "reset message";
                            sentMessage = generateByteMessage(version, code, id, message);
                            IPRepository.removeResetFromRepository(s);
                            val = false;
                            break;
                        }
                    }
                }
                if (val) {
                    code = 0;
                    message = "hello message";
                    sentMessage = generateByteMessage(version, code, id, message);
                }
                System.out.println("        [UDP | " + address + "] SEND -> VERSION: " + (int) version + "; CODE: " +
                        (int) code + "; ID: " + (int) id + "; LENGTH: " + message.length() + "; MSG: " + message);

                udpPacket.setData(sentMessage);
                udpPacket.setLength(sentMessage.length);
                sock.send(udpPacket);

                if (val) {
                    udpPacket.setData(data);
                    udpPacket.setLength(data.length);

                    try {
                        sock.receive(udpPacket);

                        d = udpPacket.getData();
                        receivedVersion = Byte.toUnsignedInt(d[0]);
                        receivedCode = Byte.toUnsignedInt(d[1]);
                        idLess = Byte.toUnsignedInt(d[2]);
                        idMost = Byte.toUnsignedInt(d[3]);

                        idMost <<= 8;
                        receivedId = idMost;
                        receivedId |= idLess;

                        globalId = receivedId;
                        lengthLess = Byte.toUnsignedInt(d[4]);
                        lengthMost = Byte.toUnsignedInt(d[5]);

                        lengthMost <<= 8;
                        receivedlength = lengthMost;
                        receivedlength |= lengthLess;

                        msg = new String(d, 6, receivedlength);

                        System.out.println("        [UDP | " + address + "] RECV <- VERSION: " + receivedVersion + "; CODE: " +
                                receivedCode + "; ID: " + receivedId + "; LENGTH: " + receivedlength + "; MSG: " + msg);
                        try {
                            TimeUnit.SECONDS.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        if(!timeSet){
                            currentTimer = LocalDateTime.now();
                            timeSet = true;
                        }
                        System.out.println("        [UDP | " + address + "] No response from server");
                    }
                }
            }
            System.out.println("        [UDP | " + address + "] Thread closing...");
            sock.close();

            IPRepository.removeIPToRepository((long) globalId);
        } catch (IOException e) {
            System.out.println("        [UDP | " + address + "] ] IOException : No response from server!");
        }
    }


    //********************

    /*SETUP DO RESET*/

            /*char version2 = 1;
            char code2 = 3;
            String message2 = "reset message";
            byte[] sentMessage2 = generateByteMessage(version2, code2, (short) receivedId, message2);

            udpPacket.setData(sentMessage2);
            udpPacket.setLength(sentMessage2.length);
            sock.send(udpPacket);
            System.out.println("Client " + targetIP.getHostAddress() + " disconnected");
*/


    //build message and returns the bytes sorted;
    public byte[] generateByteMessage(char version, char code, short id, String message) {

        short length = (short) message.length();
        byte[] byteMsg = new byte[6 + length];
        //setup of every msg
        byteMsg[0] = (byte) version;
        byteMsg[1] = (byte) code;
        byteMsg[2] = (byte) id;
        id <<= 8;
        byteMsg[3] = (byte) id;
        byteMsg[4] = (byte) length;
        length <<= 8;
        byteMsg[5] = (byte) length;
        //*******************
        int j = 0;
        byte[] auxMsg = message.getBytes();
        for (int i = 6; i < byteMsg.length; i++) {
            byteMsg[i] = auxMsg[j];
            j++;
        }
        return byteMsg;
    }
}
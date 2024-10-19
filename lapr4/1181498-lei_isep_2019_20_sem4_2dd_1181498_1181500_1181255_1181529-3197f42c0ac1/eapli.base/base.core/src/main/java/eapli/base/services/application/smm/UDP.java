package eapli.base.services.application.smm;

import eapli.base.services.application.IPRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class UDP implements Runnable {

    @Override
    public void run() {

        System.out.println("UDP Thread Running...");
        List<String> ips = IPRepository.getMachineIPList();
        int length = ips.size();

        while (true) {
            ips = IPRepository.getMachineIPList();
            if (ips.size() > length) {
                for (int i = length; i < ips.size(); i++) {
                    new Thread(new UDPThread(ips.get(i))).start();
                }

            }
            length = ips.size();
        }

    }
}
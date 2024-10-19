package eapli.base.services.application;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class IPRepository {

    private static final Map<Long, String> machineIPList = new LinkedHashMap<>();
    private static final List<String> configList = new LinkedList<>();
    private static final List<String> resetList = new LinkedList<>();
    private static final ReentrantLock lock = new ReentrantLock();

    public static void addResetToRepository(String resetMessage) {
        lock.lock();
        try {
            resetList.add(resetMessage);
        } finally {
            lock.unlock();
        }
    }


    public static List<String> getResetList() {
        lock.lock();
        List<String> list = null;
        try {
            list = resetList;//ip;boolean
        } finally {
            lock.unlock();
            return list;
        }
    }


    public static void removeResetFromRepository(String resetMessage) {
        lock.lock();
        try {
            resetList.remove(resetMessage);

        } finally {
            lock.unlock();
        }
    }

    public static void addConfigToString(String config) {
        lock.lock();
        try {
            configList.add(config);
        } finally {
            lock.unlock();
        }
    }

    public static List<String> getConfigList() {
        lock.lock();
        List<String> list = null;
        try {
            list = configList;
        } finally {
            lock.unlock();
            return list;
        }
    }

    public static void removeConfigFromRepository(String configMessage) {
        lock.lock();
        try {
            configList.remove(configMessage);
        } finally {
            lock.unlock();
        }
    }

    public static void addIPToRepository(Long id, String ip) {
        lock.lock();
        try {
            machineIPList.put(id, ip);
        } finally {
            lock.unlock();
        }
    }

    public static void removeIPToRepository(Long id) {
        lock.lock();
        try {
            machineIPList.remove(id);

        } finally {
            lock.unlock();
        }
    }

    public static List<String> getMachineIPList() {
        lock.lock();
        List<String> list = null;
        try {
            list = new ArrayList<>(machineIPList.values());
        } finally {
            lock.unlock();
            return list;
        }
    }
}

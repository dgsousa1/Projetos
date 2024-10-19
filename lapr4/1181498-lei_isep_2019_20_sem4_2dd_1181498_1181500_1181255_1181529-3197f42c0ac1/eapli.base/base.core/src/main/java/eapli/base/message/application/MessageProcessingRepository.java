package eapli.base.message.application;

import eapli.base.productionline.domain.ProductionLine;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class MessageProcessingRepository {
    private static Map<ProductionLine, Boolean> productionLineStatus = new LinkedHashMap<>();
    private static final ReentrantLock lock = new ReentrantLock();

    public static void addProductionLineStatus(ProductionLine pL, Boolean status) {
        lock.lock();
        try {
            productionLineStatus.put(pL, status);
        } finally {
            lock.unlock();
        }
    }

    public static List<ProductionLine> listProductionLineStatus(){
        lock.lock();
        List<ProductionLine> list = new LinkedList<>();
        try {
            for(Map.Entry<ProductionLine, Boolean> entry : productionLineStatus.entrySet()){
                if(entry.getValue()){
                    list.add(entry.getKey());
                }
            }
        } finally {
            lock.unlock();
            return list;
        }
    }
}

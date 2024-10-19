package eapli.base.message.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.Machine;
import eapli.base.message.domain.Message;
import eapli.base.message.domain.MessageTypeEnum;
import eapli.base.message.domain.MessageTypes;
import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.base.processementerrornotification.domain.ErrorNotificationState;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionline.repositories.ProductionLineRepository;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.base.services.domain.RawMessage;
import eapli.base.storagearea.domain.Batch;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.storagearea.repositories.BatchRepository;
import eapli.base.storagearea.repositories.StorageAreaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ProcessMessageController {
    private final static String DIVIDER = ";";
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();
    private final StorageAreaRepository storageAreaRepository = PersistenceContext.repositories().storageArea();
    private final BatchRepository batchRepository = PersistenceContext.repositories().batches();

    /**
     * The method receives the start and end date for the search and creates threads for each
     * production line for the raw messages to be processed.
     * @param start date when the processing will start.
     * @param finish date when the processing will end.
     */
    public void processRawMessages(LocalDateTime start, LocalDateTime finish, long interval, List<ProductionLine> productionLines) {
        if(productionLines.isEmpty()) {
            productionLines = productionLineRepository.listAllProductionLines();
        }
        for (ProductionLine pL : productionLines) {
            new Thread(new ProcessingMessageThread(pL, start, finish, interval)).start();
        }
    }

    /**
     * The method is used by each production line thread in order to create a new message from a
     * raw message that is in the logs.
     * @param machine machine that sent the raw message
     * @param message raw message that will be processed
     * @return processed message.
     */
    public Message processMessage(Machine machine, RawMessage message) {
        //separates message parameters
        String[] values = message.getMessage().split(DIVIDER);

        //builds new date from the raw message parameters
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime date = LocalDateTime.parse(buildDateString(values[2]), formatter);

        //gets message type from the raw message parameters
        String type = values[1];

        //analysis the type of the raw message creates a new message with the respective format
        switch (type) {
            case "C0":
            case "C9":
            case "P2":
                return messageTypeC0C9P2(machine, values, date, type);
            case "P1":
                return messageTypeP1(machine, values, date);
            case "S8":
                return messageTypeS8(machine, date);
            case "S0":
            case "S9":
                return messageTypeS0S9(machine, values, date, type);
            case "S1":
                return messageTypeS1(machine, values, date);
            default:
                return null;
        }
    }

    /**
     * Creates a new message with the S8 type format.
     * @param machine machine that sent the raw message.
     * @param date raw message date.
     * @return processed type S8 message.
     */
    private Message messageTypeS8(Machine machine, LocalDateTime date) {
        return new Message(machine, new MessageTypes(MessageTypeEnum.FORCEDSTOPPING), date);
    }

    /**
     * Creates a new message with the S1 type format.
     * @param machine machine that sent the raw message.
     * @param values raw message values to be used in the processing of the message.
     * @param date raw message date.
     * @return processed type S1 message.
     */
    private Message messageTypeS1(Machine machine, String[] values, LocalDateTime date) {
        long errorNotificationID;
        try {
            errorNotificationID = Long.parseLong(values[3]);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Message(machine, new MessageTypes(MessageTypeEnum.RETURNTOACTIVITY), date,
                new ErrorNotification(errorNotificationID, ErrorNotificationState.ACTIVE));
    }

    /**
     * Creates a new message with the S0/S9 type format.
     * @param machine machine that sent the raw message.
     * @param values raw message values to be used in the processing of the message.
     * @param date raw message date.
     * @param type message type.
     * @return processed type S0/S9 message.
     */
    private Message messageTypeS0S9(Machine machine, String[] values, LocalDateTime date, String type) {
        Optional<ProductionOrder> productionOrder = productionOrderRepository.findProductionOrderByCode(values[3]);
        if (!productionOrder.isPresent()) {
            return null;
        }
        if (type.equals("S0")) {
            return new Message(machine, new MessageTypes(MessageTypeEnum.STARTOFACTIVITY), date, productionOrder.get());
        }
        return new Message(machine, new MessageTypes(MessageTypeEnum.ENDOFACTIVITY), date, productionOrder.get());
    }

    /**
     * Creates a new message with the C0/C9/P2 type format.
     * @param machine machine that sent the raw message.
     * @param values raw message values to be used in the processing of the message.
     * @param date raw message date.
     * @param type message type.
     * @return processed type C0/C9/P2 message.
     */
    private Message messageTypeC0C9P2(Machine machine, String[] values, LocalDateTime date, String type) {
        Optional<Product> product = productRepository.findByCommercialCode(values[3]);
        if (!product.isPresent()) {
            return null;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(values[4]);
        } catch (NumberFormatException e) {
            return null;
        }
        Optional<StorageArea> storageArea = storageAreaRepository.findStorageAreaByDescription(values[5]);
        if (!storageArea.isPresent()) {
            return null;
        }
        if (type.equals("C0")) {
            return new Message(machine, new MessageTypes(MessageTypeEnum.CONSUMPTION), date,
                    product.get(), quantity, storageArea.get());
        } else if (type.equals("C9")) {
            return new Message(machine, new MessageTypes(MessageTypeEnum.PRODUCTDELIVERY), date,
                    product.get(), quantity, storageArea.get());
        } else{
            return new Message(machine, new MessageTypes(MessageTypeEnum.EXCESS), date,
                    product.get(), quantity, storageArea.get());
        }
    }

    private Message messageTypeP1(Machine machine, String[] values, LocalDateTime date) {
        Optional<Product> product = productRepository.findByCommercialCode(values[3]);
        if (!product.isPresent()) {
            return null;
        }
        Optional<Batch> batch = batchRepository.getBatchByCode(values[3]);
        if(!batch.isPresent()){
            return null;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(values[4]);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Message(machine, new MessageTypes(MessageTypeEnum.EXCESS), date,
                product.get(), quantity, batch.get());
    }

    /**
     * Builds s string in the correct date format.
     * @param date string that will be turned into the correct format.
     * @return date in the correct format.
     */
    private String buildDateString(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        String hour = date.substring(8, 10);
        String minute = date.substring(10, 12);
        String second = date.substring(12);

        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    /**
     * Lists production lines not processing messages
     * @return list of all the production lines that are not processing messages
     */
    public List<ProductionLine> listProductionsLinesNotProcessing(){
        List<ProductionLine> productionLines = productionLineRepository.listAllProductionLines();
        productionLines.removeAll(MessageProcessingRepository.listProductionLineStatus());
        return productionLines;
    }

}

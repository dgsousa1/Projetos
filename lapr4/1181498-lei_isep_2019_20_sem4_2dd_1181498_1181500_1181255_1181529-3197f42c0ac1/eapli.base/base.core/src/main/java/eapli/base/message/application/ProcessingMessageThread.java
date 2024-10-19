package eapli.base.message.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.Machine;
import eapli.base.message.domain.Message;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.services.domain.RawMessage;
import eapli.base.services.repositories.RawMessageRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProcessingMessageThread implements Runnable {

    private final ProductionLine productionLine;
    private LocalDateTime start;
    private LocalDateTime finish;
    private final long interval;

    private final RawMessageRepository rawMessageRepository = PersistenceContext.repositories().rawMessage();
    private final AddMessageController addMessageController = new AddMessageController();
    private final ProcessMessageController processMessageController = new ProcessMessageController();

    /**
     * Thread constructor.
     * @param productionLine production line from where the messages are from.
     * @param start date when the processing will start.
     * @param finish date when the processing will end.
     */
    public ProcessingMessageThread(ProductionLine productionLine,LocalDateTime start, LocalDateTime finish, long interval) {
        this.productionLine = productionLine;
        this.start = start;
        this.interval = interval;
        if(finish == null){
            this.finish = start.plusMinutes(interval);
        }else {
            this.finish = finish;
        }
    }

    /**
     * Runnable override that is going to be the production line threads.
     */
    @Override
    public void run() {
        //add production line to repository in order to be known that for this PL messages are being processed
        MessageProcessingRepository.addProductionLineStatus(productionLine, true);
        //repeat processing until time is up, in case of new raw messages being added
        do {
            for (Machine machine : productionLine.getMachines()) {
                //wait for starting time
                if (start.isAfter(LocalDateTime.now())) {
                    try {
                        Thread.sleep(LocalDateTime.now().until(start, ChronoUnit.MILLIS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //case finishing time in not up
                if (finish.isAfter(LocalDateTime.now())) {
                    for (RawMessage message : rawMessageRepository.findMessagesByMachineSerialNumber(machine.getSerialNumber())) {
                        Message processedMessage = processMessageController.processMessage(machine, message);
                        if (processedMessage != null) {
                            //add processed message to data base
                            addMessageController.addMessage(processedMessage);
                            //remove raw message from logs after being processed
                            rawMessageRepository.remove(message);
                        }
                    }
                }
                //add interval or 0 if the processing mode is 1
                start = start.plusMinutes(interval);
                finish = finish.plusMinutes(interval);
            }
        } while(finish.isAfter(LocalDateTime.now()));
        //change production line status after the processing is finished
        MessageProcessingRepository.addProductionLineStatus(productionLine, false);
    }
}

package eapli.base.infrastructure.smoketests;

import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.message.application.ProcessMessageController;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.services.domain.RawMessage;
import eapli.framework.actions.Action;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProcessingMessageTester implements Action {

    public ProcessMessageController processMessageController = new ProcessMessageController();

    private LocalDateTime start;
    private LocalDateTime finish;
    private long interval;
    List<ProductionLine> productionLines;
    private Machine machine;
    private RawMessage rawMessage;

    @Override
    public boolean execute() {
        processRawMessageWithInvalidFinalDate();
        processRawMessageWithInvalidInterval();
        processRawMessageWithValidFinalDate();
        processRawMessageWithValidValuesInterval();
        processMessageWithTypeC0C9P2();
        processMessageWithTypeS8();
        return true;
    }

    /**
     * MODE 1:
     * Doesn't process any messages because the finish date is now(),
     * the tread has no time to process messages.
     */
    private void processRawMessageWithInvalidFinalDate(){
        start = LocalDateTime.now();
        finish = LocalDateTime.now();
        interval = 0;
        //because the list is empty every production line available is used
        productionLines = new ArrayList<>();

        processMessageController.processRawMessages(start, finish, interval, productionLines);
    }

    /**
     * MODE 2:
     * Doesn't process any messages because the interval is 0, making the finish date start + 0m
     * the tread has no time to process messages.
     */
    private void processRawMessageWithInvalidInterval(){
        start = LocalDateTime.now();
        interval = 0;
        //because the list is empty every production line available is used
        productionLines = new ArrayList<>();

        processMessageController.processRawMessages(start, null, interval, productionLines);
    }

    /**
     * MODE 1:
     * Process all messages of every production line from the start until finish dates.
     */
    private void processRawMessageWithValidFinalDate(){
        start = LocalDateTime.now();
        //finish date = start + 10m
        finish = start.plusMinutes(10);
        interval = 0;
        //because the list is empty every production line available is used
        productionLines = new ArrayList<>();

        processMessageController.processRawMessages(start, finish, interval, productionLines);
    }

    /**
     * MODE 2:
     * Process all messages of every production line from the start until finish dates.
     */
    private void processRawMessageWithValidValuesInterval(){
        start = LocalDateTime.now();
        interval = 10;
        //because the list is empty every production line available is used
        productionLines = new ArrayList<>();

        processMessageController.processRawMessages(start, null, interval, productionLines);
    }

    /**
     * Process a message type C0, C9, P2
     */
    public void processMessageWithTypeC0C9P2(){
        //example of machine from production line
        machine = processMessageController.listProductionsLinesNotProcessing().get(0).getMachines().get(0);
        rawMessage = new RawMessage(machine.getSerialNumber(), "Brand1;C0;20201026151817;2222;7517;StorageArea1");

        processMessageController.processMessage(machine, rawMessage);
    }

    /**
     * Process a message type S8
     */
    public void processMessageWithTypeS8(){
        //example of machine from production line
        machine = processMessageController.listProductionsLinesNotProcessing().get(0).getMachines().get(0);
        rawMessage = new RawMessage(machine.getSerialNumber(), "Brand2;S8;20201027151717");

        processMessageController.processMessage(machine, rawMessage);
    }
}

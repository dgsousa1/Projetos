package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.machine.domain.Machine;
import eapli.base.message.application.ProcessMessageController;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ProcessMessageUI extends AbstractUI {

    ProcessMessageController processMessageController = new ProcessMessageController();

    /**
     * Process Message UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        LocalDateTime startDate;
        LocalDateTime finishDate = null;
        long interval = 0;
        boolean end;

        //lists all production lines that are not processing messages
        List<ProductionLine> productionLines = processMessageController.listProductionsLinesNotProcessing();
        if(productionLines.isEmpty()){
            System.out.println("All production lines are processing messages");
            return false;
        }

        //list of all the production lines that will start processing messages
        List<ProductionLine> processingList = new LinkedList<>();
        int index;
        int choice;
        do{
            end = true;
            index = 1;
            System.out.println("Production Lines available:");
            System.out.println("0: All production lines available");
            for(ProductionLine pL : productionLines){
                System.out.println(index + ": Production Line");
                for(Machine m : pL.getMachines()) {
                    System.out.print("Machine " + m.getSerialNumber() + " - " + m.getMachineModel().getModelName());
                    System.out.println();
                }
            }
            choice = Console.readInteger("Choose a production line: ");
            if(choice == 0){
                processingList = productionLines;
                end = false;
            }else if(choice >= 1 && choice <= productionLines.size()){
                processingList.add(productionLines.get(choice - 1));
                productionLines.remove(choice - 1);
            }
            if(end) {
                end = UIMethods.askYN("Add another production line?");
            }
        }while(end);


        end = true;
        int mode;

        //get operation mode
        do {
            System.out.println("Operation Modes:");
            System.out.println("1: Process for a time slot");
            System.out.println("2: Process with an iterable time slot");
            mode = Console.readInteger("What operation mode would you like?");
        } while (mode != 1 && mode != 2);

        //ask for the time intervals for the messages to be processed in
        do {
            System.out.println("Write the starting date:");
            startDate = UIMethods.askForDate();
            if (startDate != null) {
                if (mode == 1) {
                    System.out.println("Write the end date:");
                    finishDate = UIMethods.askForDate();
                    if (finishDate != null) {
                        if (startDate.isAfter(finishDate)) {
                            System.out.println("Start day is after end date");
                        } else {
                            end = false;
                        }
                    }
                } else {
                    do {
                        interval = Console.readLong("Write time to be iterated:");
                    } while (interval < 0);
                    end = false;
                }
            } else {
                end = UIMethods.askYN("Add another starting date?");
            }
        } while (end);

        if (startDate != null) {
            processMessageController.processRawMessages(startDate, finishDate, interval, processingList);
            System.out.println("Messages are being processed");
        } else {
            System.out.println("Invalid dates, no messages are going to be processed");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Process Message UI";
    }
}

package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.machine.domain.Machine;
import eapli.base.productionline.application.AddProductionLineController;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.LinkedList;
import java.util.List;

public class AddProductionLineUI extends AbstractUI {
    private final AddProductionLineController controller = new AddProductionLineController();

    /**
     * Specify production line UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        boolean val = true;
        int index, chosenMachine;
        //lists all machines without production lines
        List<Machine> machinesWithoutPL = controller.getMachinesWithoutProductionLine();
        List<Machine> machines = new LinkedList<>();
        //if there are no machines without production line in the data base ends process
        if(machinesWithoutPL.isEmpty()){
            System.out.println("There are no machines available");
        }else {
            while (val) {
                //validates if there are still machines without production lines
                if (machinesWithoutPL.isEmpty()) {
                    System.out.println("There are no more machines available");
                    val = false;
                } else {
                    //chooses a machines to be added to a production line
                    index = 1;
                    System.out.println("Add machine to the production line:");
                    for (Machine m : machinesWithoutPL) {
                        System.out.println(index + ": Machine " + m.getSerialNumber());
                        index++;
                    }
                    chosenMachine = Console.readInteger("Choose a machine to be added");
                    try {
                        //adds chosen machine to production line machine list
                        machines.add(machinesWithoutPL.get(chosenMachine - 1));
                        //removes machine from list of machines available (without production line)
                        machinesWithoutPL.remove(chosenMachine - 1);
                    } catch (Exception e) {
                        System.out.println("Machine is not available");
                    }
                    val = UIMethods.askYN("Add a new machine?");
                }
            }
            //lists production line
            System.out.println("Production Line: ");
            index = 1;
            for (Machine m : machines) {
                System.out.println(index + ": Machine " + m.getSerialNumber());
            }
            //adds production line
            val = UIMethods.askYN("Add production line?");
            if (val) {
                try {
                    controller.addProductionLine(machines);
                    System.out.println("Success");
                } catch (Exception e) {
                    System.out.println("Failure");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Specify production line headline.
     * @return headline
     */
    @Override
    public String headline() {
        return "Add production line";
    }
}

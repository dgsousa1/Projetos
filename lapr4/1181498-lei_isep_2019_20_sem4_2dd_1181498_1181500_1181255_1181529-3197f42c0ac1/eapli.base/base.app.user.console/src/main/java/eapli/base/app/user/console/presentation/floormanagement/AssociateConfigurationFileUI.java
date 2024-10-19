package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.machine.application.AssociateConfigurationFileController;
import eapli.base.machine.domain.Machine;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.List;

public class AssociateConfigurationFileUI extends AbstractUI {

    private final AssociateConfigurationFileController associateConfigurationFileController = new AssociateConfigurationFileController();

    /**
     * Associate configuration file to machine UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        String filePath;
        boolean val;
        //reads file
        do {
            filePath = Console.readLine("File Path: ");
            //validates if file exists
            if (associateConfigurationFileController.validateFile(filePath)) {
                val = false;
            } else {
                System.out.println("File does not exist");
                val = UIMethods.askYN("Try again?");
                if (!val) {
                    return false;
                }
            }
        } while (val);

        //reads description
        String description = Console.readLine("File Description: ");

        //gets all machines from data base
        Iterable<Machine> it = associateConfigurationFileController.allMachines();
        List<Machine> machines = new ArrayList<>();
        int index = 1;
        //lists all machines
        System.out.println("Associate machine: ");
        for (Machine m : it) {
            machines.add(m);
            System.out.println(index + ": Machine " + m.getSerialNumber());
            index++;
        }
        Machine machine = null;
        //reads machine
        do {
            int chosenMachine = Console.readInteger("Choose a machine to be associated: ");
            try {
                machine = machines.get(chosenMachine - 1);
                val = false;
            } catch (Exception e) {
                System.out.println("Machine is not available");
                val = UIMethods.askYN("Add a new machine?");
            }
        }while(val);
        if(machine != null) {
            if (associateConfigurationFileController.associateConfigurationFile(machine, filePath, description)) {
                System.out.println("Success");
                return true;
            }
        }
        System.out.println("Failure");
        return false;
    }

    /**
     * Associate configuration file to machine headline.
     * @return headline
     */
    @Override
    public String headline() {
        return "Associate Configuration File";
    }
}

package eapli.base.app.backoffice.console.presentation.floormanagement;

import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.base.services.application.smm.RequestMachineRebootController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class RequestMachineRebootUI extends AbstractUI {

    RequestMachineRebootController requestMachineRebootController = new RequestMachineRebootController();


    @Override
    protected boolean doShow() {
        List<Machine> machines = requestMachineRebootController.getAllMachines();
        Machine machine = null;
        int choice = 0;
        boolean val;
        do {
            val = false;
            int index = 1;
            System.out.println("Machines:");
            for (Machine m : machines) {
                System.out.println(index + ": Machine " + m.getSerialNumber());
                index++;
            }
            choice = Console.readInteger("Choose one machine:");
            if (choice < 1 || choice > machines.size()) {
                System.out.println("Invalid machine");
                val = true;
            }
        } while (val);

        machine = machines.get(choice-1);
        return requestMachineRebootController.requestRebootMachine(machine);
    }

    @Override
    public String headline() {
        return "Request Machine Reboot";
    }
}
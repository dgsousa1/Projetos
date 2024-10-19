package eapli.base.app.backoffice.console.presentation.floormanagement;

import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.base.services.application.SendingConfigToMachineController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class SendingConfigToMachineUI extends AbstractUI {

    SendingConfigToMachineController sendingConfigToMachineController = new SendingConfigToMachineController();

    @Override
    protected boolean doShow() {
        List<Machine> machines = sendingConfigToMachineController.getAllMachines();
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
            if(choice < 1 || choice > machines.size()){
                System.out.println("Invalid machine");
                val = true;
            }else{
                machine = machines.get(choice - 1);
                if(machine.getConfigurationFileList().isEmpty()){
                    System.out.println("Machine has no configuration messages");
                    val = true;
                }
            }
        }while(val);
        List<ConfigurationFile> configurationFiles = machine.getConfigurationFileList();
        ConfigurationFile configurationFile = null;
        do {
            int index = 1;
            System.out.println("Configuration files from machine:");
            for (ConfigurationFile cf : configurationFiles) {
                System.out.println(index + ": Description - " + cf.configurationFileDescription()
                + ", File path - " + cf.configurationFilePath());
                index++;
            }
            choice = Console.readInteger("Choose the configuration to be used:");
            if(choice < 1 || choice > configurationFiles.size()){
                System.out.println("Invalid configuration file");
            }
        }while(choice < 1 || choice > configurationFiles.size());
        configurationFile = configurationFiles.get(choice - 1);
        if (sendingConfigToMachineController.sendConfigToTCPServer(machine, configurationFile)){
            System.out.println("Success");
            return true;
        }else{
            System.out.println("Failure");
            return false;
        }
    }

    @Override
    public String headline() {
        return "Send configuration to machine";
    }
}

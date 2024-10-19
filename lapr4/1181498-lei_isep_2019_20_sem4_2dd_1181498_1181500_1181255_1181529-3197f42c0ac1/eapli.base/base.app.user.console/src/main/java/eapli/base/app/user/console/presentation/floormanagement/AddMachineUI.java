package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.application.AddMachineController;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.Optional;

public class AddMachineUI extends AbstractUI {

    AddMachineController controller = new AddMachineController();


    /**
     * Add Machine UI.
     * @return true if the operation ran successfully and false the opposite occurs.
     */
    @Override
    protected boolean doShow() {
        boolean flag = true;
        String serialNumberString = null;
        while(flag){
            serialNumberString = Console.readLine("Serial Number: ");
            Optional<Machine>  m = controller.findBySerialNumber(Long.parseLong(serialNumberString));
            if(!m.isPresent()){
                flag=false;
            }else if(m.isPresent()){
                System.out.println("Machine already exists!");
            }
        }
        final String modelName = Console.readLine("Model Name: ");
        final String description = Console.readLine("Description: ");
        final String brand = Console.readLine("Brand: ");
        final Long serialNumber;

        try{
            serialNumber = Long.parseLong(serialNumberString);
            controller.addMachine(modelName, description, brand, serialNumber);
            System.out.println("Success");
            return true;
        }catch (NumberFormatException e){
            System.out.println("Invalid serial number");
        }
        System.out.println("Failure");
        return false;
    }

    @Override
    public String headline() {
        return "Define new Machine";
    }
}
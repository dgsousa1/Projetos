package eapli.base.machine.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.domain.MachineModel;
import eapli.base.machine.repositories.MachineRepository;

import java.util.Optional;

public class AddMachineController {
    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();

    /**
     * Add machines to the system.
     * @param modelName
     * @param description
     * @param brand
     * @param serialnumber
     * @return machine
     */
    public Machine addMachine(String modelName, String description,
                              String brand, Long serialnumber){

        MachineModel model = new MachineModel(modelName,description,brand);
        Machine machine = new Machine(serialnumber,model);
        return machineRepository.save(machine);
    }

    /**
     * Finds all machines by a certain serial number.
     * @param number
     * @return machine
     */
    public Optional<Machine> findBySerialNumber(Long number){
        return this.machineRepository.findBySerialNumber(number);
    }

}

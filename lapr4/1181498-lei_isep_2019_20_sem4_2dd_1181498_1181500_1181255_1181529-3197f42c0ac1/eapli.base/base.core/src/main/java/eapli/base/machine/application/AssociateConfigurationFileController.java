package eapli.base.machine.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class AssociateConfigurationFileController {

    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();

    /**
     * Validates if the configuration file exists.
     * @param filePath path where the config file is.
     * @return return true if the file exists and false otherwise.
     */
    public boolean validateFile(String filePath){
        try {
            new FileReader(filePath);
            return true;
        }catch(FileNotFoundException e){
            return false;
        }
    }

    /**
     * Associates configuration file to machine.
     * @param machine where the configuration file will be saved.
     * @param filePath path where the path is.
     * @param description description of the file.
     * @return true if success false if failure.
     */
    public boolean associateConfigurationFile(Machine machine, String filePath, String description){
        ConfigurationFile configurationFile = new ConfigurationFile(filePath, description);
        machine.addConfigurationFile(configurationFile);
        try {
            machineRepository.save(machine);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Lists all machines saved in the data base.
     * @return list with all the machines.
     */
    public Iterable<Machine> allMachines(){return this.machineRepository.findAll();}
}

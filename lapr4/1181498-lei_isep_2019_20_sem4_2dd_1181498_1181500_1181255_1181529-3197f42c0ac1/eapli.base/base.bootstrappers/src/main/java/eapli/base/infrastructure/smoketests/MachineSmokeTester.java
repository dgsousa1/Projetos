package eapli.base.infrastructure.smoketests;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.application.AddMachineController;
import eapli.base.machine.application.AssociateConfigurationFileController;

import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.actions.Action;

import java.util.Optional;

public class MachineSmokeTester implements Action{
    private static final Logger LOGGER = LogManager
            .getLogger(MachineSmokeTester.class);

    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();
    final AddMachineController addMachineController = new AddMachineController();
    final AssociateConfigurationFileController associateConfigurationFileController = new AssociateConfigurationFileController();



    @Override
    public boolean execute() {
        addMachineSmokeTester();
        findAllMachinesCodeSmokeTester();
        findMachineBySerialNumberSmokeTest();
        return true;
    }

    private void addMachineSmokeTester(){
        final Machine machine = addMachineController.addMachine("MAC001","Machine001",
                "Brand001",1000001L);
        Invariants.noneNull(machine);
        LOGGER.info("add machine");
    }

    private void findAllMachinesCodeSmokeTester(){
        final Iterable<Machine> allMachines = machineRepository.findAll();
        Invariants.nonNull(allMachines);
        Invariants.nonNull(allMachines.iterator());
        Invariants.ensure(allMachines.iterator().hasNext());
        LOGGER.info("find all machines");
    }

    private void findMachineBySerialNumberSmokeTest(){
        final Optional<Machine> machine = machineRepository.findBySerialNumber(1000001L);
        Invariants.nonNull(machine);
        LOGGER.info("find material by code");
    }

    private void associateConfigurationFileSmokeTester(){
        final Optional<Machine> machine = machineRepository.findBySerialNumber(1000001L);
        associateConfigurationFileController.associateConfigurationFile(machine.get(), "ConfigFile.txt", "description");
        Invariants.noneNull(machine.get().getConfigurationFileList());
        LOGGER.info("associate configuration file");
    }

}

package eapli.base.infrastructure.smoketests;

import eapli.base.machine.domain.Machine;
import eapli.base.machine.domain.MachineModel;
import eapli.base.productionline.application.AddProductionLineController;
import eapli.base.productionline.domain.ProductionLine;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eapli.framework.actions.Action;

import java.util.ArrayList;
import java.util.List;


public class ProductionLineSmokeTester implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ProductionLineSmokeTester.class);
    final AddProductionLineController productionLineController = new AddProductionLineController();

    @Override
    public boolean execute() {
        addProductionLineSmokeTester();
        listAllMachinesWithNoProductionLine();
        return true;
    }

    private void addProductionLineSmokeTester() {
        final Machine machine = new Machine(10000002L, new MachineModel("MAC002","Machine002","Brand002"));
        final Machine machine1 = new Machine(10000003L, new MachineModel("MAC003","Machine003","Brand003"));
        List<Machine> machines = new ArrayList<>();
        machines.add(machine);
        machines.add(machine1);
        final ProductionLine productionLine = productionLineController.addProductionLine(machines);
        Invariants.noneNull(productionLine);
        LOGGER.info("»»» add productionLine");
    }

    private boolean listAllMachinesWithNoProductionLine(){
        LOGGER.info("Listing all machines with no production line:");
        Iterable<Machine> machines = productionLineController.getMachinesWithoutProductionLine();
        for(Machine m : machines)
            System.out.println(m.toString());

        return true;
    }

}

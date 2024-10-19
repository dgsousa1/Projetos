package eapli.base.app.user.console.test;

import eapli.base.machine.domain.Machine;
import eapli.base.machine.domain.MachineModel;
import eapli.base.productionline.application.AddProductionLineController;

import java.util.LinkedList;
import java.util.List;

public class ProductLineControllerTest {
    public static void main(String[] args) {
        AddProductionLineController productionLineController = new AddProductionLineController();
        List<Machine> machines = new LinkedList<>();

        List<Machine> machinesWithoutPO = productionLineController.getMachinesWithoutProductionLine();

        for(Machine m : machinesWithoutPO){
            System.out.println(m.getSerialNumber());
        }
    }
}

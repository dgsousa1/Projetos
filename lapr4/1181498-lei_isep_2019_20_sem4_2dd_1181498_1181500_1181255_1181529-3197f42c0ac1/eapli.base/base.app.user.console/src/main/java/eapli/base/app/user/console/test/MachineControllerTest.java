package eapli.base.app.user.console.test;

import eapli.base.machine.application.AddMachineController;
import eapli.base.material.application.AddMaterialController;

public class MachineControllerTest {
    public static void main(String[] args) {
        AddMachineController machineController = new AddMachineController();
        machineController.addMachine("Model X", "Thank Elon very cool",
                "Tesla", Long.valueOf(1214584));
    }
}
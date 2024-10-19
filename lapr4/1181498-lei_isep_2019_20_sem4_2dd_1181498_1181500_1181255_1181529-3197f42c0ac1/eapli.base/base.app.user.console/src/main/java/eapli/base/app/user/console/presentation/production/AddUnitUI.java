package eapli.base.app.user.console.presentation.production;

import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.utils.application.UnitController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.Optional;

public class AddUnitUI extends AbstractUI {

    UnitController unitController = new UnitController();

    /**
     * Add Unit UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        boolean loop = true;
        String unitName;

        while (loop) {
            unitName = Console.readLine("Insert new Unit name: ");
            if (unitName.isEmpty() || unitName == null) {
                System.out.println("Order Code null or empty");
                loop = true;
            } else {
                if (unitController.isUnitPresent(unitName)) {
                    System.out.println("Unit with that name already exists");
                    loop = true;
                } else {
                    unitController.addUnit(unitName);
                    System.out.println("Success!");
                    loop = false;
                }
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "Add a new Unit";
    }
}


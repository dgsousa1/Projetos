package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.storagearea.application.AddStorageAreaController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class AddStorageAreaUI extends AbstractUI {

    AddStorageAreaController addStorageAreaController = new AddStorageAreaController();

    /**
     * Add Storage Area UI.
     * @return true if the operation ran successfully and false the opposite occurs.
     */
    @Override
    protected boolean doShow() {
        final String briefDescription = Console.readLine("Storage area description:");
        addStorageAreaController.addStorageArea(briefDescription);
        System.out.println("Success!");
        return true;
    }

    @Override
    public String headline() {
        return "Add new Storage Area";
    }
}

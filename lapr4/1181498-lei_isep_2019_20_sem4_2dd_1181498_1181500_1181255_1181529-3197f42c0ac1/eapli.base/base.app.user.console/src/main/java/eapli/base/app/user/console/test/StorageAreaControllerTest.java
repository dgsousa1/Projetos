package eapli.base.app.user.console.test;

import eapli.base.storagearea.application.AddStorageAreaController;

public class StorageAreaControllerTest {
    public static void main(String[] args) {
        AddStorageAreaController addStorageAreaController = new AddStorageAreaController();
        addStorageAreaController.addStorageArea("SA001");
    }
}

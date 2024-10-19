package eapli.base.infrastructure.smoketests;

import eapli.base.storagearea.application.AddStorageAreaController;
import eapli.base.storagearea.domain.StorageArea;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eapli.framework.actions.Action;

public class StorageAreaSmokeTester implements Action{
    private static final Logger LOGGER = LogManager.getLogger(StorageAreaSmokeTester.class);
    final AddStorageAreaController storageController = new AddStorageAreaController();

    @Override
    public boolean execute() {
        addStorageAreaSmokeTester();
        return true;
    }

    private void addStorageAreaSmokeTester(){
        final StorageArea storage = storageController.addStorageArea("StorageTest");
        Invariants.noneNull(storage);
        LOGGER.info("»»» add storage area");
    }
}

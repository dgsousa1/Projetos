package eapli.base.storagearea.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.storagearea.repositories.StorageAreaRepository;
import eapli.framework.application.UseCaseController;

@UseCaseController
public class AddStorageAreaController {
    private final StorageAreaRepository storageAreaRepository = PersistenceContext.repositories().storageArea();

    /**
     * Adds storage area to the system.
     * @param storageDescription
     * @return storageArea
     */
    public StorageArea addStorageArea(String storageDescription){
        return storageAreaRepository.save(new StorageArea(storageDescription));
    }
}

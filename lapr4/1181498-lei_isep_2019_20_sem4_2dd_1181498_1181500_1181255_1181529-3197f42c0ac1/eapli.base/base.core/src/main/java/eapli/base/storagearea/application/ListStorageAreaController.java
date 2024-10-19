package eapli.base.storagearea.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.storagearea.repositories.StorageAreaRepository;

import java.util.List;
import java.util.Optional;

public class ListStorageAreaController {

    private final StorageAreaRepository storageAreaRepository = PersistenceContext.repositories().storageArea();

    /**
     * Lists all storage areas.
     * @return storageArea
     */
    public List<StorageArea> listAllStorageAreas(){
        return this.storageAreaRepository.listAllStorageAreas();
    }

    /**
     * Finds storage area by a certain description.
     * @param description
     * @return storage area
     */
    public Optional<StorageArea> findStorageAreaByDescription(final String description){
        return this.storageAreaRepository.findStorageAreaByDescription(description);
    }
}

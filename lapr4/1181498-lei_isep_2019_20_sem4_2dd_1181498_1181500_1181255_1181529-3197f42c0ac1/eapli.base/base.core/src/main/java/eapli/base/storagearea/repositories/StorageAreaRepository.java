package eapli.base.storagearea.repositories;

import eapli.base.storagearea.domain.StorageArea;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface StorageAreaRepository extends DomainRepository<Long, StorageArea> {

    public List<StorageArea> listAllStorageAreas();

    public Optional<StorageArea> findStorageAreaByDescription(String description);

}

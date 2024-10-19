package eapli.base.storagearea.repositories;

import eapli.base.storagearea.domain.Batch;

import java.util.List;
import java.util.Optional;

public interface BatchRepository {

    List<Batch> listAllBatches();

    Optional<Batch> getBatchByCode(String code);


}

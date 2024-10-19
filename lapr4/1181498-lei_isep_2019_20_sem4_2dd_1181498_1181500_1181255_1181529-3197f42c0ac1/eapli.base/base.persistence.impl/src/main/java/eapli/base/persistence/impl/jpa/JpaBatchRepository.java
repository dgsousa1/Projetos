package eapli.base.persistence.impl.jpa;

import eapli.base.storagearea.domain.Batch;
import eapli.base.storagearea.repositories.BatchRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaBatchRepository extends BasepaRepositoryBase<Batch,Long,Long>
        implements BatchRepository {

    JpaBatchRepository() {
        super("pkBatch");
    }

    /**
     * Method to list all batches in the database
     * @return a List of all batches in the database, or an empty list if none were found
     */
    public List<Batch> listAllBatches(){
        final TypedQuery<Batch> query = entityManager().createQuery(
                "SELECT b FROM Batch b", Batch.class
                );
        try{
            List<Batch> batches = query.getResultList();
            return batches;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    public Optional<Batch> getBatchByCode(String code){
        final TypedQuery<Batch> query = entityManager().createQuery(
                "SELECT b FROM Batch b WHERE b.batchCode= :code",Batch.class
        );
        query.setParameter("code",code);
        try{
            Batch b = query.getSingleResult();
            return Optional.of(b);
        }catch(Exception e){
            return Optional.empty();
        }
    }





}

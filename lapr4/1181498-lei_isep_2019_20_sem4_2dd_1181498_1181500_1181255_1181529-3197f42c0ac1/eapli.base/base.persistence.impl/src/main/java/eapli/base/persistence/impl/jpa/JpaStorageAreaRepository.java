package eapli.base.persistence.impl.jpa;

import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.storagearea.repositories.StorageAreaRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JpaStorageAreaRepository extends BasepaRepositoryBase<StorageArea, Long, Long>
        implements StorageAreaRepository {

    JpaStorageAreaRepository(){
        super("pkStorageArea");
    }

    @Override
    public List<StorageArea> listAllStorageAreas(){
        List<StorageArea> result = new LinkedList<>();
        try{
            Iterable<StorageArea> storageAreas = findAll();
            for(StorageArea sa : storageAreas){
                result.add(sa);
            }

            return result;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<StorageArea> findStorageAreaByDescription(String description) {
        final TypedQuery<StorageArea> query = entityManager().createQuery(
                "SELECT s FROM StorageArea s  WHERE s.storageDescription =: description",StorageArea.class
        );
        query.setParameter("description",description);

        try{
            StorageArea p = query.getSingleResult();
            return Optional.of(p);
        }catch(Exception e){
            return Optional.empty();
        }
    }
}

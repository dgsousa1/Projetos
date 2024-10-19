package eapli.base.persistence.impl.jpa;

import eapli.base.product.domain.Product;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.rawmaterial.repositories.RawMaterialRepository;
import eapli.base.services.domain.RawMessage;
import eapli.base.services.repositories.RawMessageRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaRawMessageRepository extends BasepaRepositoryBase<RawMessage,Long,Long>
        implements RawMessageRepository {

    JpaRawMessageRepository() {
        super("pkRawMessage");
    }

    @Override
    public Optional<RawMessage> findMessageInRepository(String message) {
        final TypedQuery<RawMessage> query = entityManager().createQuery(
                "SELECT m FROM RawMessage m WHERE m.message=:message", RawMessage.class
        );
        query.setParameter("message", message);
        try {
            RawMessage m  = query.getSingleResult();
            return Optional.of(m);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public List<RawMessage> findMessagesByMachineSerialNumber(Long serialNumber){
        final TypedQuery<RawMessage> query = entityManager().createQuery(
                "SELECT m FROM RawMessage m WHERE m.serialNumber=:number",RawMessage.class
        );
        query.setParameter("number",serialNumber);
        try{
            List<RawMessage> messages = query.getResultList();
            return messages;
        } catch(Exception e){
            return new ArrayList<>();
        }

    }
    
    

    }

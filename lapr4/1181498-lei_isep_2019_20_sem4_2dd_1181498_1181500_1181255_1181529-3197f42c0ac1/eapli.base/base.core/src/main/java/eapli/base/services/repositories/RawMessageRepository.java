package eapli.base.services.repositories;

import eapli.base.services.domain.RawMessage;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface RawMessageRepository extends DomainRepository<Long, RawMessage> {


    public Optional<RawMessage> findMessageInRepository(String message);

    public List<RawMessage> findMessagesByMachineSerialNumber(Long serialNumber);

}

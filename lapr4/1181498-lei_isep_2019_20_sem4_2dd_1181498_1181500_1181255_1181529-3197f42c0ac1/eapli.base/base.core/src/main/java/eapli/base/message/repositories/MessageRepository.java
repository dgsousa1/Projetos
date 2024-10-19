package eapli.base.message.repositories;

import eapli.base.message.domain.Message;
import eapli.framework.domain.repositories.DomainRepository;

public interface MessageRepository extends DomainRepository<Long, Message> {
}

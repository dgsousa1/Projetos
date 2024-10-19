package eapli.base.persistence.impl.jpa;

import eapli.base.message.domain.Message;
import eapli.base.message.repositories.MessageRepository;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionline.repositories.ProductionLineRepository;

public class JpaMessageRepository extends BasepaRepositoryBase<Message, Long, Long>
        implements MessageRepository {

    JpaMessageRepository() {
        super("pkMessage");
    }
}

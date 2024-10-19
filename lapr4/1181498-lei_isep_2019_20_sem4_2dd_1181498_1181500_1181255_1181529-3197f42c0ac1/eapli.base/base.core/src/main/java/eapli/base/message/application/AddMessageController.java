package eapli.base.message.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.message.domain.Message;
import eapli.base.message.repositories.MessageRepository;

public class AddMessageController {

    private final MessageRepository messageRepository = PersistenceContext.repositories().message();

    /**
     * Adds a message to the system.
     * @param message
     * @return message
     */
    public Message addMessage(Message message){
        return messageRepository.save(message);
    }
}

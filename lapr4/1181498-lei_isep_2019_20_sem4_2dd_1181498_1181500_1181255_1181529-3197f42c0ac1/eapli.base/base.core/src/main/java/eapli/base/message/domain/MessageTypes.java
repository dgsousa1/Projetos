package eapli.base.message.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class MessageTypes implements ValueObject {

    private MessageTypeEnum messageType;

    /**
     * Constructor responsible for getting the message types being used.
     * @param messageType
     */
    public MessageTypes(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    protected MessageTypes(){
        //for JPA
    }

    public String findMessageType() {
        return messageType.name();
    }
}

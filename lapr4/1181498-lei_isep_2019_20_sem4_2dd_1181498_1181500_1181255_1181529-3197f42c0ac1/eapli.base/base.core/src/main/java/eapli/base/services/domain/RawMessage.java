package eapli.base.services.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RawMessage implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMessage;

    private Long serialNumber;

    private String message;

    protected RawMessage(){
        //for JPA
    }

    public RawMessage(Long serialNumber, String message) {
        this.serialNumber = serialNumber;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkMessage;
    }
}

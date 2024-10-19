package eapli.base.storagearea.domain;

import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.DomainEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class StockMovement implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkSM;

    @Version
    private Long version;

    @OneToOne
    private StorageArea storage;

    @Embedded
    private StockMovementDirection movement;

    @OneToMany
    private Set<Batch> batches;

    private LocalDateTime dateOfMovement;

    /**
     * Constructor responsible for getting the stock movement being used.
     * @param storage
     * @param batches
     * @param dateOfMovement
     */
    public StockMovement(StorageArea storage, Set<Batch> batches, LocalDateTime dateOfMovement) {
        this.storage=storage;
        this.batches=batches;
        this.dateOfMovement=dateOfMovement;
    }

    protected StockMovement() {
        //for JPA
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return this.pkSM;
    }

}

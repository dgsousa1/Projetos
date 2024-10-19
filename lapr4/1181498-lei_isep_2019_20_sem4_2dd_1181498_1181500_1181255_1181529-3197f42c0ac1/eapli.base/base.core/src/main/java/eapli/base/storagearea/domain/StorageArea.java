package eapli.base.storagearea.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class StorageArea implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Long pk;

    @Version
    private Long version;

    @Column(length = 50)
    private String storageDescription;

    @OneToMany
    private Set<Batch> batches;


    /**
     * Constructor responsible for getting the storage description being used.
     * @param storageDescription
     */
    public StorageArea(String storageDescription) {
        this.storageDescription = storageDescription;
    }

    protected  StorageArea(){
        //for JPA
    }

    /**
     * Returns the primary key for storage area.
     * @return pk
     */
    public Long getPk() { return pk; }

    /**
     * Modifies the
     * @param pk
     */
    public void setPk(Long pk) {this.pk = pk; }

    /**
     * Returns the storage area description.
     * @return storageDescription
     */
    public String getStorageDescription() {return storageDescription; }

    /**
     * Modifies the storage description.
     * @param storageDescription
     */
    public void setStorageDescription(String storageDescription) { this.storageDescription = storageDescription; }

    /**
     * Returns the batches.
     * @return batches
     */
    public Set<Batch> getBatches() { return batches; }

    /**
     * Modifies the batches.
     * @param batches
     */
    public void setBatches(Set<Batch> batches) { this.batches = batches; }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return null;
    }
}

package eapli.base.storagearea.domain;

import eapli.base.utils.MeasuredRawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Batch implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkBatch;

    @Version
    private Long version;

    private String batchCode;

    @OneToOne
    MeasuredRawMaterial material;

    private LocalDateTime batchCreationDate;

    /**
     * Constructor responsible for getting the batch being used.
     * @param pkBatch
     * @param material
     * @param batchCreationDate
     * @param code
     */
    public Batch(Long pkBatch,MeasuredRawMaterial material, LocalDateTime batchCreationDate, String code) {
        this.pkBatch=pkBatch;
        this.material=material;
        this.batchCreationDate=batchCreationDate;
        this.batchCode = code;
    }

    protected Batch() {
        //for JPA
    }

    /**
     * Returns the material.
     * @return material
     */
    public MeasuredRawMaterial getMaterial() {
        return material;
    }

    /**
     * Returns the batch creation date.
     * @return batchCreationDate
     */
    public LocalDateTime getBatchCreationDate() {
        return batchCreationDate;
    }

    /**
     * Returns the batch code.
     * @return batchCode
     */
    public String getBatchCode() {
        return batchCode;
    }

    @Override
    public boolean sameAs(Object other) {
        return  DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return this.pkBatch;
    }
}

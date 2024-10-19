package eapli.base.utils;

import eapli.base.material.domain.Material;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;

@Entity
public class MeasuredRawMaterial implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMRM;

    @OneToOne
    private RawMaterial rawMaterial;

    private float quantity;

    protected MeasuredRawMaterial(){
        //for JPA
    }

    public MeasuredRawMaterial(RawMaterial rawMaterial, float quantity) {
        this.rawMaterial = rawMaterial;
        this.quantity = quantity;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof MeasuredRawMaterial)) {
            return false;
        }
        final MeasuredRawMaterial that = (MeasuredRawMaterial) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public float getQuantity() {
        return quantity;
    }


    @Override
    public Long identity() {
        return this.pkMRM;
    }
}

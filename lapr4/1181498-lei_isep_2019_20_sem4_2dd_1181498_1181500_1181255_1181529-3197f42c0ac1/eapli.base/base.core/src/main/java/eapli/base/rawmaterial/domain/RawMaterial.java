package eapli.base.rawmaterial.domain;

import eapli.base.utils.Unit;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;

@Entity
public class RawMaterial implements AggregateRoot<Long>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkRawMaterial;

    @Version
    private Long version;

    private String rawMaterialName;

    @OneToOne
    private Unit productUnit;


    protected RawMaterial() {
        //for JPA
    }

    /**
     * Constructor responsible for getting the raw material being used.
     * @param rawMaterialName
     * @param productUnit
     */
    public RawMaterial(String rawMaterialName, Unit productUnit) {
        this.rawMaterialName = rawMaterialName;
        this.productUnit = productUnit;
    }

    /**
     * Returns the product unit.
     * @return productUnit
     */
    public Unit getProductUnit() {
        return productUnit;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkRawMaterial;
    }

    @Override
    public String toString() {
        return "RawMaterial: " +
                " Name: " + rawMaterialName +
                " Unit: " + productUnit;
    }

    /**
     * Returns the raw material name.
     * @return rawMaterialName
     */
    public String getRawMaterialName() {
        return rawMaterialName;
    }
}

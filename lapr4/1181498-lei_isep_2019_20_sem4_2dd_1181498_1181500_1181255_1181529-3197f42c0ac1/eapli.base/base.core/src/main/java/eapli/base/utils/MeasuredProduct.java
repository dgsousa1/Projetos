package eapli.base.utils;

import eapli.base.product.domain.Product;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;

@Entity
public class MeasuredProduct implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMP;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    private float quantity;

    protected MeasuredProduct(){
        //for JPA
    }
    public MeasuredProduct(Product product, float quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof MeasuredProduct)) {
            return false;
        }
        final MeasuredProduct that = (MeasuredProduct) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public Long identity() {
        return this.pkMP;
    }

    public Product getProduct() {
        return product;
    }

    public float getQuantity() {
        return quantity;
    }
}

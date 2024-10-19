/*
package eapli.base.productionorder.domain;


import eapli.base.utils.MeasuredProduct;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commission implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkCommission;

    @Column(unique = true)
    private String commissionCode;

    @OneToOne(cascade=CascadeType.ALL)
    private MeasuredProduct  requestedProducts;

    public Commission(String commissionCode, MeasuredProduct requestedProducts) {
        this.commissionCode = commissionCode;
        this.requestedProducts = requestedProducts;
    }

    protected Commission() {
        //for JPA
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return this.pkCommission;
    }

    public String getCommissionCode() {
        return commissionCode;
    }

    public MeasuredProduct getRequestedProducts() {
        return requestedProducts;
    }


}
*/
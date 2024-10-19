package eapli.base.productionorder.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductionOrderCode implements ValueObject {

    @Column(length = 50,unique=true)
    private String orderCode;

    protected ProductionOrderCode(){
        //for JPA
    }

    /**
     * Returns the order code.
     * @return orderCode
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * Constructor responsible for getting the production order code being used.
     * @param orderCode
     */
    public ProductionOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}

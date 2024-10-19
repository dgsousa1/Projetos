package eapli.base.productionorder.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class ProductionOrderStatus implements ValueObject {

    private ProductionOrderStatusEnum productionOrderStatusEnum;

    protected ProductionOrderStatus(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the production order status being used.
     * @param productionOrderStatusEnum
     */
    public ProductionOrderStatus(ProductionOrderStatusEnum productionOrderStatusEnum) {
        this.productionOrderStatusEnum = productionOrderStatusEnum;
    }

    /**
     * Returns the production order status enum.
     * @return productionOrderStatusEnum
     */
    public ProductionOrderStatusEnum getProductionOrderStatusEnum() {
        return productionOrderStatusEnum;
    }

    /**
     * Modifies the production order status enum.
     * @param productionOrderStatusEnum
     */
    public void setProductionOrderStatusEnum(ProductionOrderStatusEnum productionOrderStatusEnum) {
        this.productionOrderStatusEnum = productionOrderStatusEnum;
    }
    @Override
    public String toString() {
        return productionOrderStatusEnum.name();
    }
}


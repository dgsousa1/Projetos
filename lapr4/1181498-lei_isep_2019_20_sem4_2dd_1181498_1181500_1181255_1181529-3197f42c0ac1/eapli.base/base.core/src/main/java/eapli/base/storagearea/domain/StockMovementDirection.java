package eapli.base.storagearea.domain;

import javax.persistence.Embeddable;

@Embeddable
public class StockMovementDirection {
    StockMovementEnum stockMovementEnum;

    protected StockMovementDirection(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the stock movement direction being used.
     * @param stockMovementEnum
     */
    public StockMovementDirection(StockMovementEnum stockMovementEnum) {
        this.stockMovementEnum = stockMovementEnum;
    }
}

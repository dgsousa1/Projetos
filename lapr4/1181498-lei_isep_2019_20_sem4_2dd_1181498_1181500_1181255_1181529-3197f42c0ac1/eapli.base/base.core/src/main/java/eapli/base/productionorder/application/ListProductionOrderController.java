package eapli.base.productionorder.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderStatusEnum;
import eapli.base.productionorder.repositories.ProductionOrderRepository;

import java.util.List;

public class ListProductionOrderController {
    ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();

    /**
     * Finds production orders by a certain commission.
     * @param commission
     * @return production order
     */
    public List<ProductionOrder> findProductionOrderByCommission(String commission){
        return this.productionOrderRepository.findProductionOrderByCommission(commission);
    }

    /**
     * Finds production orders by a certain state.
     * @param status
     * @return production order
     */
    public Iterable<ProductionOrder> findProductionOrderByState(ProductionOrderStatusEnum status){
        return this.productionOrderRepository.findProductionOrderByState(status);
    }
}

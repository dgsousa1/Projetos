package eapli.base.productionorder.repositories;

import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderStatusEnum;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionOrderRepository extends DomainRepository<Long, ProductionOrder> {

    public Optional<ProductionOrder> findProductionOrderByCode(String code);

    public Iterable<ProductionOrder> findProductionOrderByState(ProductionOrderStatusEnum status);

    public List<ProductionOrder> findProductionOrderByCommission(String associatedCommissions);

    public List<ProductionOrder> listAllProductionOrders();


}

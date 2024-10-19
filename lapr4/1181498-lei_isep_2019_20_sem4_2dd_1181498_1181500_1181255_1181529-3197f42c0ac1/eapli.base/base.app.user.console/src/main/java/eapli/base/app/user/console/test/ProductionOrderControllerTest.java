package eapli.base.app.user.console.test;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionorder.application.AddProductionOrderController;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.repositories.ProductionOrderRepository;

import java.util.List;

public class ProductionOrderControllerTest {
    public static void main(String[] args) {
        AddProductionOrderController productionOrderController = new AddProductionOrderController();

        /* productionOrderController.addProductionOrder("B123", "2020-05-10 10:00",
                "1234", "12", "ProductOrder", "Product from Order",
                "UN", "CAT1", 25);

        ProductionOrderRepository productionOrdersRepository = PersistenceContext.repositories().productionOrders()
        productionOrdersRepository.findProductionOrderByCode()*/

        ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
        List<ProductionOrder> list = productionOrderRepository.findProductionOrderByCommission("E300");
        for (ProductionOrder productionOrder : list) {
            System.out.println(productionOrder.getProductionOrderCode());
        }


    }
}

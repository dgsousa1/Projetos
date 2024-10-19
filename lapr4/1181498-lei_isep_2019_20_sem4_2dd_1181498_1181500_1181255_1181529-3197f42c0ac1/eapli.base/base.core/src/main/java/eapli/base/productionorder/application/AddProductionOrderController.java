package eapli.base.productionorder.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderCode;
import eapli.base.productionorder.domain.ProductionOrderSchedule;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.base.utils.MeasuredProduct;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AddProductionOrderController {
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();
    /**
     * Method creates a new Production Order from the given parameters, and saves it to the database.
     * @param orderCode String object that is the code of the Production Order
     * @param startExecutionS String object of the expected execution date for the Production Order
     * @param issueDate LocalDatetime object that represents the date and time the Production Order was issued.
     * @param product Product object that is to be associated to the Production Order
     * @param productQuantity Float value of the quantity of the product
     * @param associatedCommissions List of Strings that represent the commissions associated to the Production Order
     * @return the ProductionOrder object saved to the database
     */
    public ProductionOrder addProductionOrder(String orderCode, String startExecutionS, LocalDateTime issueDate,
                                              Product product,
                                              float productQuantity, List<String> associatedCommissions){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startExecution = LocalDateTime.parse(startExecutionS, formatter);
        return productionOrderRepository.save(new ProductionOrder(new ProductionOrderCode(orderCode),
                new ProductionOrderSchedule(startExecution), new MeasuredProduct(product, productQuantity),issueDate,associatedCommissions));
    }

    /**
     * Method that saves a ProductionOrder object to the databse
     * @param newProductionOrder ProductionOrder object to be added
     * @return the saved ProductionOrder object
     */
    public ProductionOrder addProductionOrder(ProductionOrder newProductionOrder){
        productionOrderRepository.save(newProductionOrder);
        return newProductionOrder;
    }


    /**
     * Lists the products that have no production order.
     * @return product
     */
    public List<Product> productsWithNoProductionOrder(){
        return this.productRepository.productsWithNoProductionOrder();
    }

    /**
     * Finds all production orders by a certain code.
     * @param code
     * @return productionorder
     */
    public Optional<ProductionOrder> findProductionOrderByCode(String code){
        return this.productionOrderRepository.findProductionOrderByCode(code);
    }

}

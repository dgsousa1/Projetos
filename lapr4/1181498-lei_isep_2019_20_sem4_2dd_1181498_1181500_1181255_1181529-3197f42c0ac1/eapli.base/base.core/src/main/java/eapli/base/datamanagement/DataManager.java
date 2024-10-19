package eapli.base.datamanagement;

import eapli.base.datamanagement.dto.ProductLineDTO;
import eapli.base.datamanagement.dto.ProductionOrderDTO;
import eapli.base.product.domain.Product;
import eapli.base.productionorder.domain.ProductionOrder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public interface DataManager {

    Set<Product> importProductCatalog(String filename) throws  IOException;

    boolean exportProductsWithErrors(String filename, Set<ProductLineDTO> products) throws IOException;

    Set<ProductionOrder> importProductionOrders(String filename) throws IOException;

    boolean exportProductionsOrdersWithErrors(String filename, Set<ProductionOrderDTO> orders) throws IOException;
}

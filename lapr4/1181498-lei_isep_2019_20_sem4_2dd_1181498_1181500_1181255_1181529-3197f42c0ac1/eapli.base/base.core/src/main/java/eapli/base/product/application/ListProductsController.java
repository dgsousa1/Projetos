package eapli.base.product.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;

import java.util.List;

public class ListProductsController {

    ProductRepository productRepository = PersistenceContext.repositories().product();

    /**
     * Lists all products that have no production order.
     * @return product
     */
    public List<Product> listProductsWithNoProductionOrder(){
        return this.productRepository.productsWithNoProductionOrder();
    }

}



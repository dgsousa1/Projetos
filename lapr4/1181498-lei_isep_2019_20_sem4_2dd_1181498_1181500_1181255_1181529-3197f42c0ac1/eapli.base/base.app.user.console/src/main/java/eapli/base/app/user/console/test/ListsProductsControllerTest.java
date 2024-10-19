package eapli.base.app.user.console.test;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListsProductsControllerTest {

    public static void main(String[] args) {
        final ProductRepository productRepository = PersistenceContext.repositories().product();

        /*Iterable<Product> result = listProductsController.productsWithNoProductionOrder();
        for(Product p : result){
            System.out.println(p.getCodes());
        } */
        Optional<Product> p = productRepository.findByCommercialCode("2222");
        System.out.println(p.get().toString());

       /* p= listProductsController.productByManufacturingCode("2222");
        System.out.println(p.get().toString());
       /* p=listProductsController.productByCommercialCode("77777");
        System.out.println(p.toString()); */

    }
}

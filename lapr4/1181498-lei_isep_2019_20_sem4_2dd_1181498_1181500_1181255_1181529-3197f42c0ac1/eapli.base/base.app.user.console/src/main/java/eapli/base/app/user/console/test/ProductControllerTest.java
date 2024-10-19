package eapli.base.app.user.console.test;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;

import java.util.List;

public class ProductControllerTest {

    private final String FILEPATH = "Produtos.csv";

    public static void main(String[] args) {
       /* AddProductController productController = new AddProductController();
        productController.addProduct(69, 420,
        "Prato", "Um prato lmao", "UN", "AEX12");*/

    // ImportProductCatalogController impcController= new ImportProductCatalogController();
       // impcController.importProductCatalog("zzzz.csv",0);

        ProductRepository productRepository = PersistenceContext.repositories().product();
        List<Product> products = productRepository.listAllProducts();
        for (Product product : products) {
            System.out.println(product.toDTO().writeForUI());
        }




    }
}

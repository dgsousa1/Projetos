package eapli.base.infrastructure.smoketests;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.AddProductController;
import eapli.base.product.application.ImportProductCatalogController;

import eapli.base.product.domain.Product;

import eapli.base.product.repositories.ProductRepository;
import eapli.base.utils.application.UnitController;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ProductSmokeTester implements Action {
    private final static Logger LOGGER = LogManager.getLogger(ProductSmokeTester.class);
    private final static String FILENAME = "teste.csv";

    AddProductController addProductController = new AddProductController();
    ImportProductCatalogController importProductCatalogController = new ImportProductCatalogController();
    UnitController unitController = new UnitController();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();


    public boolean addProduct(){
        final UnitRepository unitRepository = PersistenceContext.repositories().units();
        LOGGER.info("Adding Product:"+"\n"+ "Manufacturing Code: 33333 Commercial Code: 45678 Brief Description: Product3 " +
                "Complete Description: Third Product Unit: UN Product Category: CAT2");
        addProductController.addProduct("33333", "45678",
                "Product3", "Third Product",  unitRepository.findUnit("UN").get(), "CAT2");
        return true;
    }

    public boolean listAllProducts(){
        LOGGER.info("Listing all products:");
        Iterable<Product> products = productRepository.listAllProducts();
        for(Product p : products)
            System.out.println(p.toString());

        return true;
    }

    public boolean searchByManufacturingCode(){
        LOGGER.info("Searching by Manufacturing Code: 33333");
        Optional<Product> p =productRepository.findByManufacturingCode("33333");
        if(p.isPresent()){
            System.out.println(p.get().toString());
        }else{
            System.out.println("Product not found");
        }
        return true;
    }

    public boolean searchByCommercialCode(){
        LOGGER.info("Searching by Commercial Code: 45678");
        Optional<Product> p =productRepository.findByCommercialCode("45678");
        if(p.isPresent()){
            System.out.println(p.get().toString());
        }else{
            System.out.println("Product not found");
        }
        return true;
    }

    public boolean listAllProductsWithNoProductionOrder(){
        LOGGER.info("Listing all products with no production order:");
        Iterable<Product> products = productRepository.productsWithNoProductionOrder();
        for(Product p : products)
            System.out.println(p.toString());

        return true;
    }

    public boolean importProductCatalog(){
        LOGGER.info("Import product Catalog:");
        importProductCatalogController.importProductCatalog(FILENAME,1);
        LOGGER.info("Imported successfully");
        return true;
    }


    @Override
    public boolean execute() {
        addProduct();
        listAllProducts();
        searchByCommercialCode();
        searchByManufacturingCode();
        listAllProductsWithNoProductionOrder();
        importProductCatalog();
        return true;
    }
}

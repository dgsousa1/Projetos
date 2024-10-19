package eapli.base.product.application;

import eapli.base.datamanagement.csv.CSVDataManager;
import eapli.base.datamanagement.DataManager;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.utils.Unit;
import eapli.base.utils.application.UnitController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class ImportProductCatalogController {

    private final DataManager dataManager = new CSVDataManager();
    private final AddProductController controller = new AddProductController();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();

    /**
     * Imports a Set of Products from a provided file
     * @param filename path to the file to be imported
     * @return number of duplicate products found, or -1 if an error ocurred
     */
    public int importProductCatalog(String filename, int userOption) {
        Set<Product> products = null;
        try {
            products = dataManager.importProductCatalog(filename);
        } catch (IOException e) {
            return -1;
        }
        int duplicate = 0;
        //TODO verify products already in DB and ask user to replace or ignore
        for(Product p : products){
            if(!productRepository.findByManufacturingCode(p.getProductManufacturingCode()).isPresent()) {
                controller.addProduct(p);
            }else duplicate++;
        }
        return duplicate;
    }


}
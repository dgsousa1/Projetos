package eapli.base.product.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.domain.ProductCategory;
import eapli.base.product.domain.ProductCode;
import eapli.base.product.domain.ProductDescription;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.application.UseCaseController;

import java.util.List;
import java.util.Optional;

@UseCaseController
public class AddProductController{
    UnitRepository unitRepository = PersistenceContext.repositories().units();
    private final ProductRepository productRepository = PersistenceContext.repositories().product();

    /**
     * Add products to the system.
     * @param manufacturingCode
     * @param commercialCode
     * @param briefDescription
     * @param completeDescription
     * @param productUnit
     * @param productCategory
     * @return newProduct
     */
    public Product addProduct(String manufacturingCode, String commercialCode,
                              String briefDescription, String completeDescription,
                              Unit productUnit,
                              String productCategory){
        ProductCode code = new ProductCode(manufacturingCode,commercialCode);
        ProductDescription description = new ProductDescription(briefDescription,completeDescription);
        ProductCategory category = new ProductCategory(productCategory);
        Product newProduct = new Product(code,description,category, new RawMaterial(briefDescription,productUnit));
        productRepository.save(newProduct);
        return newProduct;
    }

    /**
     * Adds a product.
     * @param newProduct
     * @return newProduct
     */
    public Product addProduct(Product newProduct){
        productRepository.save(newProduct);
        return newProduct;
    }

    /**
     * Lists all units.
     * @return unit
     */
    public List<Unit> listAllUnits(){
        return this.unitRepository.listAllUnits();
    }

    /**
     * Finds all products by a certain commercial code.
     * @param commercialCode
     * @return product
     */
    public Optional<Product> findByCommercialCode (String commercialCode){
       return this.productRepository.findByCommercialCode(commercialCode);
    }

    /**
     * Finds all products by a certain manufacturing code.
     * @param manufacturingCode
     * @return product
     */
    public Optional<Product> findByManufacturingCode (String manufacturingCode){
        return this.productRepository.findByManufacturingCode(manufacturingCode);
    }



}

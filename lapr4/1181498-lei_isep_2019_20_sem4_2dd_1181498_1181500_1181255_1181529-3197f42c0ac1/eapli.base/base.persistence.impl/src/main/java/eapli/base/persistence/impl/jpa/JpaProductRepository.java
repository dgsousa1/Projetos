package eapli.base.persistence.impl.jpa;


import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import javax.persistence.TypedQuery;
import java.util.*;

public class JpaProductRepository extends BasepaRepositoryBase<Product, Long, Long>
        implements ProductRepository {

    JpaProductRepository() {
        super("pkProduct");
    }

    /**
     * Method to list all products in the database
     * @return a List of all products in the database, or an empty list if none were found
     */
    @Override
    public List<Product> listAllProducts() {
        final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p from Product p",
                Product.class
        );
        try{
            List<Product> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * Method to find a Product via its commercial code
     * @param code commercial code of the Product to be found
     * @return Optional object containing the found Product, or empty if not found
     */
    @Override
    public Optional<Product> findByCommercialCode(String code){
        final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p FROM Product p  WHERE p.codes.comercialCode= :code",Product.class
        );
        query.setParameter("code",code);
        try{
            Product p = query.getSingleResult();
            return Optional.of(p);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    /**
     * Method to find a Product via its manufacturing code
     * @param code manufacturing code of the Product to be found
     * @return Optional object containing the found Product, or empty if not found
     */
    @Override
    public Optional<Product> findByManufacturingCode(String code) {
        final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p FROM Product p  WHERE p.codes.manufacturingCode= :code",Product.class
        );
        query.setParameter("code",code);
        try{
            Product p = query.getSingleResult();
            return Optional.of(p);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    /**
     * Method that returns all the Products that aren't associated to a Production Order
     * @return List with all products not associated, or an empty list if none were found.
     */
    @Override
    public List<Product> productsWithNoProductionOrder() {
      final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p from Product p WHERE p.pkProduct NOT IN (SELECT DISTINCT(product.pkMP) FROM ProductionOrder z JOIN z.productResult product )",
              Product.class
        );
        try{
            List<Product> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * Method that returns all the Categories of the Products found in the database
     * @return List with all categories not associated, or an empty list if none were found.
     */
    @Override
    public List<String> listAllProductCategories(){
        List<String> categories = new ArrayList<>();
        Iterable<Product> products = this.findAll();
        for(Product p :products){
            categories.add(p.getProductCategory());
        }
        return categories;
    }



}

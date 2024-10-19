package eapli.base.product.repositories;

import eapli.base.product.domain.Product;
import eapli.base.product.domain.ProductCategory;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.domain.repositories.DomainRepository;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends DomainRepository<Long, Product> {

    public Optional<Product> findByCommercialCode(String code);

    public Optional<Product> findByManufacturingCode(String code);

    public List<Product> productsWithNoProductionOrder();

    public List<String> listAllProductCategories();

    public List<Product> listAllProducts();


}

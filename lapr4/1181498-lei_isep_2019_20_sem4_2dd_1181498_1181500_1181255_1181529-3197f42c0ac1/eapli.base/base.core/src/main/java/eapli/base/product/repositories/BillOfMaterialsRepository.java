package eapli.base.product.repositories;

import eapli.base.product.domain.BillOfMaterials;
import eapli.base.product.domain.Product;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface BillOfMaterialsRepository extends DomainRepository<Long, BillOfMaterials> {

    public Optional<BillOfMaterials> findProductBillOfMaterials(String code);

    public List<Product> productsWithNoBillOfMaterials();


}

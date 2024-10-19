package eapli.base.material.repositories;

import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.product.domain.Product;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends DomainRepository<Long, Material> {

    public Optional<Material> findByMaterialCode(String code);

    public Optional<Material> findByMaterialName(String name);

    public List<MaterialCategory> findCategory();

    public List<Material> listAllMaterials();

}

package eapli.base.material.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.material.repositories.MaterialRepository;

import java.util.List;

public class ListMaterialCategoriesController {
    private final MaterialRepository materialRepository = PersistenceContext.repositories().materials();


    /**
     * Finds all material categories.
     * @return category
     */
    public List<MaterialCategory> findAllMaterialCategories(){
        return this.materialRepository.findCategory();
    }

}

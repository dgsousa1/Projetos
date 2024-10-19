package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.application.ListMaterialCategoriesController;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.material.repositories.MaterialRepository;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class ListMaterialCategoriesUI extends AbstractUI {

    ListMaterialCategoriesController listMaterialCategoriesController = new ListMaterialCategoriesController();

    /**
     * Menu that displays to the user a list of all material categories.
     * @return boolean true when the method has finished
     */
    @Override
    protected boolean doShow() {
        System.out.println("Listing all the categories of materials: ");
        List<MaterialCategory> categories = listMaterialCategoriesController.findAllMaterialCategories();
        for(MaterialCategory c : categories){
            System.out.println(c.getCategoryDescription());
        }
        return true;
    }

    @Override
    public String headline() {
        return "List of available material categories:";
    }
}

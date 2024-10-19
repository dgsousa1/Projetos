package eapli.base.app.user.console.presentation.production;

import eapli.base.product.domain.Product;
import eapli.base.product.application.ListProductsController;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class SearchProductsUI extends AbstractUI {

    ListProductsController listProductsController = new ListProductsController();

    /**
     * Menu that displays to the user all the Products that aren't associated to a Production Order
     * @return boolean true when the method has finished
     */
    @Override
    protected boolean doShow() {
        System.out.println("Listing all the products with no production Order: ");
        List<Product> products = listProductsController.listProductsWithNoProductionOrder();
        for(Product p : products){
            System.out.println(p.toDTO().writeForUI());
        }
        return true;
    }

    @Override
    public String headline() {
        return "Search Products with no Production Order";
    }
}

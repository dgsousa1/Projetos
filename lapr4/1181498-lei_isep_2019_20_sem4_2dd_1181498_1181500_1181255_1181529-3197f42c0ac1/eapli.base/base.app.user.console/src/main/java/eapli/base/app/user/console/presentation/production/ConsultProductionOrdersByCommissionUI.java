package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionorder.application.ListProductionOrderController;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.List;

public class ConsultProductionOrdersByCommissionUI extends AbstractUI {

    ListProductionOrderController listProductionOrderController = new ListProductionOrderController();

    /**
     * Consult Production Orders By Commission UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        System.out.println("Listing all Productions Orders by Commission: ");
        String chosenCommmission;
        do{
            chosenCommmission = Console.readLine("Insert a commission: ");
        }while(chosenCommmission.isEmpty()|| chosenCommmission ==null);

        List<ProductionOrder>  orders = listProductionOrderController.findProductionOrderByCommission(chosenCommmission);
        if(orders.size()==0) {
            System.out.println("No commissions");
        }else{
            for(ProductionOrder o : orders){
                System.out.println("Production Order: "+o.getProductionOrderCode()+" Issued in: "+o.getIssueDate());
            }
        }
        return true;

    }

    @Override
    public String headline() {
        return "Consulting Production Orders by Commission:";
    }
}

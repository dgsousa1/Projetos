package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionorder.application.ListProductionOrderController;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderStatusEnum;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class ConsultProductionOrdersByStateUI extends AbstractUI {

    ListProductionOrderController listProductionOrderController = new ListProductionOrderController();

    /**
     * Consult Production Orders By State UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow(){
        int response;
        int statusNumber = 0;

        System.out.println("Listing all Productions Orders by State:");
        List<ProductionOrderStatusEnum> orderStatus = new ArrayList<ProductionOrderStatusEnum>(EnumSet.allOf(ProductionOrderStatusEnum.class));
        for(ProductionOrderStatusEnum pos : orderStatus){
            System.out.println(statusNumber + ": " + pos );
            statusNumber++;
        }
        System.out.println();
        do{
            response = Console.readInteger("What state would you like?");
        }while(!(response>-1 && response<=orderStatus.size()));
        Iterable<ProductionOrder> orders = listProductionOrderController.findProductionOrderByState(orderStatus.get(response));
        for(ProductionOrder po : orders ){
            System.out.println("Production Order: "+po.getProductionOrderCode());
        }
        return true;
    }

    @Override
    public String headline() {
        return "Consulting Production Orders by State:";
    }
}

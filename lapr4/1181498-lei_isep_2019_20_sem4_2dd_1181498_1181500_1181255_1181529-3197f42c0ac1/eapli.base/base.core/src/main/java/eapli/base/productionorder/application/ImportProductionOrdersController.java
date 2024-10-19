package eapli.base.productionorder.application;

import eapli.base.datamanagement.DataManager;
import eapli.base.datamanagement.csv.CSVDataManager;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.repositories.ProductionOrderRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ImportProductionOrdersController {

    private final DataManager dataManager = new CSVDataManager();
    private final AddProductionOrderController controller = new AddProductionOrderController();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();

    /**
     * Imports a Set of Production Orders from a provided file.
     * @param filename path to the file to be imported
     * @param userOption
     * @return number of duplicate production orders found, or -1 if an error ocurred
     */
    public int importProductionOrders(String filename, int userOption){

        Set<ProductionOrder> orders = null;
        try{
            orders =  dataManager.importProductionOrders(filename);
        }catch (IOException e) {
            return -1;
        }
        int duplicate = 0;
        //TODO verify products already in DB and ask user to replace or ignore
        for (ProductionOrder po: orders){
            if (!productionOrderRepository.findProductionOrderByCode(po.getProductionOrderCode()).isPresent()){
                controller.addProductionOrder(po);
            }else duplicate++;
        }

        return duplicate;
    }
}

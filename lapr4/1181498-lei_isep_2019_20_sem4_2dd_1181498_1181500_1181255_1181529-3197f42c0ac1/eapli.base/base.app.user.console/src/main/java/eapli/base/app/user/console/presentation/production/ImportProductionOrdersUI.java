package eapli.base.app.user.console.presentation.production;

import eapli.base.productionorder.application.ImportProductionOrdersController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportProductionOrdersUI  extends AbstractUI {
    ImportProductionOrdersController controller = new ImportProductionOrdersController();
    private final String DEFAULT_IMPORT_FILE = "OrdensFabrico.csv";
    private final String EXPORT_FILE = "ProductionOrdersWErrors";

    /**
     * Method that displays the menu that allows the user to import a CSV file containing production orders information.
     * @return boolean true, after the operation has ended
     */
    @Override
    protected boolean doShow() {
        final String import_file = Console.readLine("CSV File to be imported from(example.csv):");
        System.out.println("\nErrors will be exported to:"+EXPORT_FILE);
        //imported file with no duplicates
        int duplicates = controller.importProductionOrders(import_file,0);

        if(duplicates==0){
            System.out.println(import_file + " file successful imported!");
        }else if(controller.importProductionOrders(import_file,0)>=0) {//found duplicates
            System.out.println("Error! " + duplicates + " Production Orders already exist in the DB\n=====================================");
        }else if(duplicates==-1){
            System.out.println("File: "+import_file+" not found!\n=====================================");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Import Production Orders";
    }
}

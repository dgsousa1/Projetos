package eapli.base.app.user.console.presentation.production;

import eapli.base.product.application.ImportProductCatalogController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportProductCatalogUI extends AbstractUI {

    ImportProductCatalogController controller = new ImportProductCatalogController();
    private final String DEFAULT_IMPORT_FILE = "Produtos.csv";
    private final String EXPORT_FILE = "ProductWErrors.csv";

    /**
     * Method that displays the menu that allows the user to import a CSV file containing product information
     * @return boolean true, after the operation has ended
     */
    @Override
    protected boolean doShow() {
        boolean flag=true;
        while(flag){
            final String import_file = Console.readLine("CSV File to be imported from(example.csv):");
            System.out.println("\nErrors will be exported to:"+EXPORT_FILE);
            //imported file with no duplicates
            int duplicates = controller.importProductCatalog(import_file,0);

            if(duplicates==0){
                flag = false;
                System.out.println(import_file + " file successful imported!");
            }else if(controller.importProductCatalog(import_file,0)>=0) {//found duplicates
                System.out.println("Error! " + duplicates + " Products already exist in the DB\n=====================================");
                flag=false;
            }else if(duplicates==-1){
                flag=false;
                System.out.println("File: "+import_file+" not found!\n=====================================");
            }
        }
        return true;
    }

    @Override
    public String headline() {
        return "Import Product Catalog";
    }
}

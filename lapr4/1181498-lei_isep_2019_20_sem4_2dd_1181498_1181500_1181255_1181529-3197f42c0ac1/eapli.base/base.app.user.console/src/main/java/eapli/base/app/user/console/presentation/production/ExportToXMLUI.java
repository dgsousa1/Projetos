package eapli.base.app.user.console.presentation.production;

import eapli.base.datamanagement.xml.XMLExportController;
import eapli.base.datamanagement.xml.XMLValidation;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.time.LocalDateTime;

public class ExportToXMLUI extends AbstractUI {
    XMLExportController xmlExportController = new XMLExportController();
    private static final String XSD_LOCATION = "xml/sffm.xsd";

    /**
     * Method that displays a menu that allows the user to configure aspects of the XML file that's going to be exported
     * @return boolean true or false depending on the success of the method
     */
    @Override
    protected boolean doShow() {

        int userChoice = 0;
        String exportFile;
           /* the user will insert a filename, with no extension, which will be added by the program itself*/
            do {
                exportFile = Console.readLine("XML File Name:(No extension)");
            }while(exportFile.isEmpty() || exportFile.contains("."));

            exportFile = "xml/"+exportFile+".xml";

            /*User can choose one of the temporal filters, or exit the menu*/
            System.out.println("Temporal Filtering: ");
            System.out.println("1.From a date forwards");
            System.out.println("2.Until a date");
            System.out.println("3.Between two dates");
            System.out.println("0.Exit");

            do{
                userChoice = Console.readInteger("Choose an option:");
            }while(userChoice<0 || userChoice>3);

            if(userChoice==0){
                return true;
            }

            LocalDateTime from = null;
            LocalDateTime until = null;
            switch(userChoice){
                case 1:
                    System.out.println("From:");
                    from=UIMethods.askForDate();
                    break;
                case 2:
                    System.out.println("Until:");
                    until=UIMethods.askForDate();
                    break;
                case 3:
                    System.out.println("From:");
                    from=UIMethods.askForDate();
                    System.out.println("Until:");
                    until=UIMethods.askForDate();
                    break;
            }
            /*User will be asked what parts of the factory floor are to be exported to the XML file*/
            boolean showMaterialCategories=UIMethods.askYN("Show Material Categories?");
            boolean showProductCategories=UIMethods.askYN("Show Product Categories?");
            boolean showMaterialCatalog=UIMethods.askYN("Show Material Catalog?");
            boolean showProductCatalog=UIMethods.askYN("Show Product Catalog?");
            boolean showBillOfMaterials=UIMethods.askYN("Show Bill Of Materials?");
            boolean showProductionOrders=UIMethods.askYN("Show Production Orders?");
            boolean showProductionLines=UIMethods.askYN("Show Production Lines?");
            boolean showStorageAreas= UIMethods.askYN("Show Storage Areas?");

            /*Call to the controller to export to XML */
            if(!xmlExportController.exportIntoXML(exportFile,from,until,showMaterialCategories,showProductCategories,showMaterialCatalog,
                    showProductCatalog,showBillOfMaterials,showProductionOrders,showProductionLines,showStorageAreas)){
                System.out.println("Error in exporting");
                return false;
            }else{
                System.out.println("Done creating XML File");
            }

            /*Validate the created xml with the xsd schema*/
           if(XMLValidation.validateXMLSchema(XSD_LOCATION,exportFile)){
               System.out.println("Validated with XSD");
               return true;
           }else{
               System.out.println("Error in validation");
               return false;
           }
    }

    @Override
    public String headline() {
        return "Export to XML";
    }
}

package eapli.base.app.user.console.presentation.menu;

import eapli.base.app.user.console.presentation.production.*;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;

public class ProductionUI extends Menu {

    private static final int EXIT_OPTION = 0;
    //PRODUCTION MENU
    private static final int IMPORT_CATALOG = 1;
    private static final int IMPORT_PRODUCTION_ORDERS = 2;
    private static final int INSERT_NEW_PRODUCT = 3;
    private static final int DEFINE_MATERIAL_CATEGORY = 4;
    private static final int INSERT_NEW_MATERIAL = 5;
    private static final int SEARCH_PRODUCTS = 6;
    private static final int ADD_UNIT = 7;
    private static final int INSERT_NEW_BILL_OF_MATERIALS = 8;
    private static final int SPECIFY_PRODUCTION_ORDER = 9;
    private static final int CONSULT_PRODUCTION_ORDER_BY_STATE = 10;
    private static final int CONSULT_PRODUCTION_ORDER_BY_COMMISSION = 11;
    private static final int EXPORT_TO_XML = 12;
    private static final int XML_TRANSFORMATIONS = 13;

    public ProductionUI()  {
        super("Production Manager >");
        buildProductionMenu();
    }

    private void buildProductionMenu() {
        addItem(MenuItem.of(IMPORT_CATALOG,"Import Product Catalog", new ImportProductCatalogUI()::show));
        addItem(MenuItem.of(IMPORT_PRODUCTION_ORDERS, "Import Production Orders", new ImportProductionOrdersUI()::show));
        addItem(MenuItem.of(INSERT_NEW_PRODUCT,"Insert new Product", new AddProductUI()::show));
        addItem(MenuItem.of(DEFINE_MATERIAL_CATEGORY,"List Material Categories", new ListMaterialCategoriesUI()::show));
        addItem(MenuItem.of(INSERT_NEW_MATERIAL,"Insert new Material", new AddMaterialUI()::show));
        addItem(MenuItem.of(SEARCH_PRODUCTS,"Search products with no production order", new SearchProductsUI()::show));
        addItem(MenuItem.of(ADD_UNIT,"Add a new Unit", new AddUnitUI()::show));
        addItem(MenuItem.of(INSERT_NEW_BILL_OF_MATERIALS,"Specify Bill of Materials",new SpecifyBillOfMaterialsUI()::show));
        addItem(MenuItem.of(SPECIFY_PRODUCTION_ORDER,"Specify Production Order",new SpecifyProductionOrderUI()::show));
        addItem(MenuItem.of(CONSULT_PRODUCTION_ORDER_BY_STATE, "Consult Production Order by state", new ConsultProductionOrdersByStateUI() ::show));
        addItem(MenuItem.of(CONSULT_PRODUCTION_ORDER_BY_COMMISSION, "Consult Production Order by commission", new ConsultProductionOrdersByCommissionUI() ::show));
        addItem(MenuItem.of(EXPORT_TO_XML,"Export to a XML file",new ExportToXMLUI()::show));
        addItem(MenuItem.of(XML_TRANSFORMATIONS,"Transform a XML file",new XMLTransformationUI()::show));
        addItem(MenuItem.of(EXIT_OPTION,"Return", Actions.SUCCESS));
    }
}

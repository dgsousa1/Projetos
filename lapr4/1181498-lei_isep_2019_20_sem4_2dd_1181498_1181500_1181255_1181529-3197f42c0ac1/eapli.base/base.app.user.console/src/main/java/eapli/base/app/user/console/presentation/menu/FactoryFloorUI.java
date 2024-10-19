package eapli.base.app.user.console.presentation.menu;

import eapli.base.app.user.console.presentation.floormanagement.*;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;

public class FactoryFloorUI extends Menu {

    private static final int EXIT_OPTION = 0;
    //FACTORY FLOOR MENU
    private static final int SPECIFY_PRODUCTION_LINE = 1;
    private static final int DEFINE_NEW_MACHINE = 2;
    private static final int SPECIFY_STORAGE_AREA =3;
    private static final int ASSOCIATE_CONFIGURATION_FILE = 4;
    private static final int PROCESS_MESSAGE = 5;
    private static final int CONSULT_ACTIVE_ERROR_NOTIFICATIONS = 6;
    private static final int ARCHIVE_ERROR_NOTIFICATIONS = 7;
    private static final int CONSULT_ARCHIVED_ERROR_NOTIFICATIONS = 8;

    public FactoryFloorUI() {
        super("Factory Floor Manager >");
        buildFactoryFloorMenu();
    }

    private void buildFactoryFloorMenu() {
        addItem(MenuItem.of(SPECIFY_PRODUCTION_LINE,"Specify Production Line", new AddProductionLineUI()::show));
        addItem(MenuItem.of(DEFINE_NEW_MACHINE,"Define new Machine", new AddMachineUI()::show));
        addItem(MenuItem.of(SPECIFY_STORAGE_AREA,"Specify Storage Area", new AddStorageAreaUI()::show));
        addItem(MenuItem.of(ASSOCIATE_CONFIGURATION_FILE,"Associate Configuration File to Machine", new AssociateConfigurationFileUI()::show));
        addItem(MenuItem.of(PROCESS_MESSAGE,"Process Message", new ProcessMessageUI()::show));
        addItem(MenuItem.of(CONSULT_ACTIVE_ERROR_NOTIFICATIONS, "Consult Active Error Notifications", new ConsultActiveErrorNotificationUI()::show));
        addItem(MenuItem.of(ARCHIVE_ERROR_NOTIFICATIONS, "Archived Error Notifications", new ArchiveErrorNotificationUI()::show));
        addItem(MenuItem.of(CONSULT_ARCHIVED_ERROR_NOTIFICATIONS, "Consult Archived Error Notifications", new ConsultArchivedErrorNotificationUI()::show));
        addItem(MenuItem.of(EXIT_OPTION,"Return", Actions.SUCCESS));
    }
}

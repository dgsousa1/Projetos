package eapli.base.app.user.console.presentation.menu;

import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.ExitWithMessageAction;

public class LoginUI extends Menu {

    private static final int EXIT_OPTION = 0;
    //1ST MENU
    private static final int LOGIN_OPTION = 1;
    private static final int REGISTER_OPTION = 2;
    //LOGIN MENU
    private static final int CREATEUSERMENU = 1;
    //private static final int PRODUCTION_MENU = 1;
    //private static final int FACTORYFLOOR_MENU  = 2;
    //private static final String MENU_SEPARATOR = "-----------";


    public LoginUI() {
        super("Login");
        buildLoginMenu();
    }

    private Menu buildLoginMenu() {
        Menu loginMenu = new Menu("Login");






        /*final Menu productionMenu = new ProductionUI();
        final Menu factoryFloorMenu =new FactoryFloorUI();

        addItem(MenuItem.separator(MENU_SEPARATOR));
        addSubMenu(PRODUCTION_MENU, productionMenu);
        addSubMenu(FACTORYFLOOR_MENU, factoryFloorMenu);*/
        addItem(MenuItem.of(EXIT_OPTION,"Return", Actions.SUCCESS));
        return loginMenu;
    }
}

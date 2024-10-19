package eapli.base.app.user.console.presentation.menu;


import eapli.base.Application;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.app.user.console.presentation.floormanagement.AddMachineUI;
import eapli.base.app.user.console.presentation.floormanagement.AddProductionLineUI;
import eapli.base.app.user.console.presentation.floormanagement.AddStorageAreaUI;
import eapli.base.app.user.console.presentation.production.*;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import java.util.Optional;


public class MainMenu extends AbstractUI {
    //MAIN MENU OPTIONS
    private static final int USER_OPTION = 1;
    private static final int EXIT_OPTION = 0;
    private static final int PRODUCTION_OPTION = 2;
    private static final int FACTORY_OPTION = 2;


    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    @Override
    protected boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    @Override
    public String headline() {
        return authz.session().map(s -> "Base User App - DASHBOARD [@" + s.authenticatedUser().identity() +"]")
                .orElse("Base User App - DASHBOARD [@Anonymous]");
    }

    public Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(USER_OPTION,myUserMenu);

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.PRODUCTION_MANAGER)) {
            final ProductionUI productionMenu = new ProductionUI();
            mainMenu.addSubMenu(PRODUCTION_OPTION, productionMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.FACTORY_MANAGER)) {
            final FactoryFloorUI factoryFloorMenu = new FactoryFloorUI();
            mainMenu.addSubMenu(FACTORY_OPTION, factoryFloorMenu);
        }
        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));
        return mainMenu;
    }
}

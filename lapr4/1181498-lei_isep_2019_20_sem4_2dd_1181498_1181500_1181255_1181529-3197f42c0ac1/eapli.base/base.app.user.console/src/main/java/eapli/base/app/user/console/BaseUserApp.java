package eapli.base.app.user.console;

import eapli.base.app.common.console.presentation.authz.LoginUI;
import eapli.base.app.user.console.presentation.menu.MainMenu;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.message.application.MessageProcessingRepository;
import eapli.base.services.application.scm.TCP;
import eapli.base.services.application.smm.UDP;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

/**
 * Base User App.
 */
@SuppressWarnings("squid:S106")
public final class BaseUserApp {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseUserApp() {
    }

    public static void main(final String[] args) {
        System.out.println("=====================================");
        System.out.println("Base User App");
        System.out.println("SMART SHOP FLOOR MANAGEMENT (C) 2020");
        System.out.println("=====================================");
        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());

        new Thread(new TCP()).start();
        new Thread(new UDP()).start();
        new MessageProcessingRepository();

        if(new LoginUI().show()){
            //go to the main menu
            final MainMenu menu = new MainMenu();
            menu.mainLoop();
        }
        // exiting the application, closing all threads
        System.exit(0);
    }
}

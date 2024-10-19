/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.processementerrornotification.application.ConsultArchivedErrorNotificationsController;
import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.framework.presentation.console.AbstractUI;
import java.util.List;

/**
 *
 * @author luisdematos
 */
public class ConsultArchivedErrorNotificationUI extends AbstractUI{
    
    private final ConsultArchivedErrorNotificationsController controller = new ConsultArchivedErrorNotificationsController();
    
    @Override
    protected boolean doShow(){
         System.out.println("Listing all archived error notifications: ");
        List<ErrorNotification> errorNotification = controller.consultArchivedErrorNotifications();
    for(ErrorNotification en : errorNotification){
            System.out.println(en.toString());
        }
        return true;
    }
     @Override
    public String headline(){
        return "Consult Archived Error Notifications";
    }
}

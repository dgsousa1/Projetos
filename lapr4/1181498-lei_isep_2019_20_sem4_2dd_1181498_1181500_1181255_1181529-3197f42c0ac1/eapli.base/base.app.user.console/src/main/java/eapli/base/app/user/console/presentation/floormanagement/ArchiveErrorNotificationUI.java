/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.user.console.presentation.floormanagement;

import eapli.base.processementerrornotification.application.ArchiveErrorNotificationController;
import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import java.util.List;

/**
 *
 * @author luisdematos
 */
public class ArchiveErrorNotificationUI extends AbstractUI {
    
    private final ArchiveErrorNotificationController controller = new ArchiveErrorNotificationController();
    
    @Override
    protected boolean doShow(){
        
        final List<ErrorNotification> errorNotification = this.controller.allActiveErrorNotifications();
        
        int selection = 1;
        
        do{
            final SelectWidget<ErrorNotification> selector = new SelectWidget<>("\n Active Error Notifications: ", errorNotification);
            
            selector.show();
            final ErrorNotification en = selector.selectedElement();
            errorNotification.remove(en);
            
            try{
                if(en != null){
                    this.controller.archiveErrorNotification(en);
                    System.out.println("\nSuccess! Error notification archived!\n ");
                }
            }catch (final IntegrityViolationException ex){
                System.out.println("\nError! Notification not archived!\n ");
                selection = 0;
            }
            
        }while(selection != 0);
        
        return true;
    }
    
    @Override
    public String headline(){
        return "Archive Error Notifications";
    }
}

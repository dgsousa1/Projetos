/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.processementerrornotification.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.base.processementerrornotification.domain.ErrorNotificationState;
import eapli.base.processementerrornotification.repositories.ErrorNotificationRepository;
import java.util.List;

/**
 *
 * @author luisdematos
 */
public class ConsultArchivedErrorNotificationsController {
    
    private final ErrorNotificationRepository enr = PersistenceContext.repositories().errorNotification();
   private final ListErrorNotificationService ensList = new ListErrorNotificationService();
    
    
    public Iterable<ErrorNotification> findArchivedErrorNotifications(ErrorNotificationState state){
        return this.enr.findErrorNotificationByState(state);}
    
   public List<ErrorNotification> consultArchivedErrorNotifications(){
       return this.ensList.findAllArchivedErrorNotifications();
   }
    
        
}

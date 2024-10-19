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
import eapli.framework.application.UseCaseController;
import java.util.List;

/**
 *
 * @author luisdematos
 */

@UseCaseController

public class ConsultActiveErrorNotificationsController {
    
    private final ErrorNotificationRepository enr = PersistenceContext.repositories().errorNotification();
    private final ListErrorNotificationService ensList = new ListErrorNotificationService();
    
    public Iterable<ErrorNotification> findErrorNotificationsByState(ErrorNotificationState active) {return this.enr.findErrorNotificationByState(active);}
    
    public List<ErrorNotification> consultActiveErrorNotifications(){
        return this.ensList.findAllActiveErrorNotifications();
    }
    
    
    
    
    

}

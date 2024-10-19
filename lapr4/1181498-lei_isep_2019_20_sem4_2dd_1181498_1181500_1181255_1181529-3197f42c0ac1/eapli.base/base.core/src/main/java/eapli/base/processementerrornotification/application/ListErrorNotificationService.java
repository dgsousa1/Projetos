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
public class ListErrorNotificationService {
    
    private final ErrorNotificationRepository enr = PersistenceContext.repositories().errorNotification();
    
    public List<ErrorNotification> findAllActiveErrorNotifications(){
        final List<ErrorNotification> notifications = this.enr.findErrorNotificationByState(ErrorNotificationState.ACTIVE);
        return notifications;
    }
    
    public List<ErrorNotification> findAllArchivedErrorNotifications(){
        final List<ErrorNotification> notifications = this.enr.findErrorNotificationByState(ErrorNotificationState.ARCHIVED);
        return notifications;
    }
}

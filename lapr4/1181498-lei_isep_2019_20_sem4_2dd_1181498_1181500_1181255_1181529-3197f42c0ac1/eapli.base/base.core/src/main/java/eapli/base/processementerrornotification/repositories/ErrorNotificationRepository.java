/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.processementerrornotification.repositories;

import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.base.processementerrornotification.domain.ErrorNotificationState;
import eapli.framework.domain.repositories.DomainRepository;
import java.util.List;

/**
 *
 * @author luisdematos
 */
public interface ErrorNotificationRepository extends DomainRepository<Long, ErrorNotification>{
    
    List<ErrorNotification> findErrorNotificationByState(ErrorNotificationState state);
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.base.processementerrornotification.domain.ErrorNotificationState;
import eapli.base.processementerrornotification.repositories.ErrorNotificationRepository;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author luisdematos
 */
public class JpaErrorNotificationRepository extends BasepaRepositoryBase<ErrorNotification, Long, Long> implements ErrorNotificationRepository {
    
    JpaErrorNotificationRepository() {
        super("errorNotificationId");
    }
    
    @Override
    public List<ErrorNotification> findErrorNotificationByState(ErrorNotificationState state){
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "SELECT e FROM ErrorNotification WHERE e.errorState = :state", ErrorNotification.class).setParameter("state", state);
        return query.getResultList();
    }
    
 
   
    
}

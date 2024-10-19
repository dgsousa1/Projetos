package eapli.base.message.domain;

import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.DomainEntity;

import javax.persistence.*;

@Entity
public class Notification implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkNotification;

    @Embedded
    private NotificationType notificationType;

    @Embedded
    private NotificationErrorType notificationErrorType;

    /**
     * Constructor responsible for getting the notification being used.
     * @param pkNotification
     * @param notificationType
     * @param notificationErrorType
     */
    public Notification(Long pkNotification, NotificationType notificationType, NotificationErrorType notificationErrorType) {
        this.pkNotification = pkNotification;
        this.notificationType = notificationType;
        this.notificationErrorType = notificationErrorType;
    }

    protected Notification(){
        //for JPA
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkNotification;
    }
}

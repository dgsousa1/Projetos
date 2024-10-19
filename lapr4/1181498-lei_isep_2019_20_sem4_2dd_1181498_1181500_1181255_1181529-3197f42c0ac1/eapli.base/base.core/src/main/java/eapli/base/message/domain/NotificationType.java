package eapli.base.message.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class NotificationType implements ValueObject {
    private NotificationTypeEnum notificationType;

    /**
     * Constructor responsible for getting the notification types being used.
     * @param notificationType
     */
    public NotificationType(NotificationTypeEnum notificationType) {
        this.notificationType = notificationType;
    }

    protected NotificationType(){
    }
}

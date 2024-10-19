/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.processementerrornotification.domain;

import eapli.base.productionline.domain.ProductionLine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author luisdematos
 */
@Entity
public class ErrorNotification implements AggregateRoot<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkErrorNotification;
    
    @Enumerated(EnumType.STRING)
    private ErrorNotificationState errorState;
    
    @ManyToOne
    private ProductionLine productionLine;
    
    
    protected ErrorNotification(){
        //for JPA
    }
    
    
    public ErrorNotification(ErrorNotificationState eSt){
        this.errorState = ErrorNotificationState.ACTIVE;
    }
    
    public ErrorNotification(Long id, ErrorNotificationState eSt){
        this.pkErrorNotification = id;
        this.errorState = ErrorNotificationState.ACTIVE;
    }
    
    public ErrorNotification(Long id, ErrorNotificationState eSt, ProductionLine productionLine){
        this.pkErrorNotification = id;
        this.errorState = ErrorNotificationState.ACTIVE;
        this.productionLine = productionLine;
    }
    
      @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkErrorNotification;
    }
    
    public ErrorNotificationState state(){
        return this.errorState;
    }
    
    public ProductionLine pl(){
        return this.productionLine;
    }
    
    public void changeStateToArchived(){
        if(errorState == ErrorNotificationState.ACTIVE){
            this.errorState = ErrorNotificationState.ARCHIVED;
        }
    }
    
    
}

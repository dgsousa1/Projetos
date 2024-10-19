package eapli.base.message.domain;

import eapli.base.machine.domain.Machine;
import eapli.base.processementerrornotification.domain.ErrorNotification;
import eapli.base.product.domain.Product;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.storagearea.domain.Batch;
import eapli.base.storagearea.domain.StorageArea;
import eapli.base.utils.MeasuredProduct;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Message implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMessage;

    @OneToOne
    private Machine machine;

    @Embedded
    private MessageTypes type;

    private LocalDateTime issueDate;

    @OneToOne
    private Product product;

    private int quantity;

    @OneToOne
    private StorageArea storageArea;

    @OneToOne
    private Batch batch;

    @OneToOne
    private ProductionOrder productionOrder;

    /* ERROR ON THE ONE TO ONE
    @OneToOne(cascade = CascadeType.ALL)
    private ErrorNotification processingError;
    */

    protected Message(){
        //for JPA
    }

    //FOR MESSAGE TYPE: S8
    public Message(Machine machine, MessageTypes type, LocalDateTime issueDate) {
        this.machine = machine;
        this.type = type;
        this.issueDate = issueDate;
    }

    //FOR MESSAGE TYPE: S1
    public Message(Machine machine, MessageTypes type, LocalDateTime issueDate, ErrorNotification processingError) {
        this.machine = machine;
        this.type = type;
        this.issueDate = issueDate;
        //this.processingError = processingError;
    }

    //FOR MESSAGE TYPE: S0, S9
    public Message(Machine machine, MessageTypes type, LocalDateTime issueDate, ProductionOrder productionOrder) {
        this.machine = machine;
        this.type = type;
        this.issueDate = issueDate;
        this.productionOrder = productionOrder;
    }

    //FOR MESSAGE TYPE: C0, C9, P2
    public Message(Machine machine, MessageTypes type, LocalDateTime issueDate, Product product, int quantity, StorageArea storageArea) {
        this.machine = machine;
        this.type = type;
        this.issueDate = issueDate;
        this.product = product;
        this.quantity = quantity;
        this.storageArea = storageArea;
    }

    //FOR MESSAGE TYPE: P1
    public Message(Machine machine, MessageTypes type, LocalDateTime issueDate, Product product, int quantity, Batch batch) {
        this.machine = machine;
        this.type = type;
        this.issueDate = issueDate;
        this.product = product;
        this.quantity = quantity;
        this.batch = batch;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkMessage;
    }
}

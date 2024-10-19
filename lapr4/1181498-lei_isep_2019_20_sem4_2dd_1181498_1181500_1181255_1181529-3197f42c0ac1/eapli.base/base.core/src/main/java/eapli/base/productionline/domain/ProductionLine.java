package eapli.base.productionline.domain;


import eapli.base.machine.domain.Machine;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class ProductionLine implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkProductionLine;

    @Version
    private Long version;

    @OneToOne
    private ProductionOrder productionOrder;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Machine> machines;

    /**
     * Constructor responsible for getting the production line code being used.
     * @param machines
     */
    public ProductionLine(List<Machine> machines) {
        Preconditions.noneNull(machines);

        this.machines = machines;
    }

    protected ProductionLine(){
        //for JPA
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkProductionLine;
    }

    /**
     * Returns the production orders.
     * @return productionOrder
     */
    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    /**
     * Returns the machines.
     * @return machines
     */
    public List<Machine> getMachines() {
        return machines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionLine that = (ProductionLine) o;
        return Objects.equals(pkProductionLine, that.pkProductionLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkProductionLine);
    }
}

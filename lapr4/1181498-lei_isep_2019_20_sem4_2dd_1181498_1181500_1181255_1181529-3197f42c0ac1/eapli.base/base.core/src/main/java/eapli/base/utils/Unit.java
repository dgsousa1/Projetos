package eapli.base.utils;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Unit implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkUnit;

    private String unit;

    protected Unit(){
        //For JPA;
    }

    public Unit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.unit;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit1 = (Unit) o;
        return pkUnit.equals(unit1.pkUnit) &&
                unit.equals(unit1.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkUnit, unit);
    }

    @Override
    public Long identity() {
        return this.pkUnit;
    }
}

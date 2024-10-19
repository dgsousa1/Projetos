package eapli.base.utils.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitController {
    private final UnitRepository unitRepository = PersistenceContext.repositories().units();

    public Unit addUnit(String unit){
        return this.unitRepository.save(new Unit(unit));
    }

    public boolean isUnitPresent(String name){
        Optional<Unit> unit = this.unitRepository.findUnit(name);
        if(unit.isPresent()){return true;}
        else{
            return false;
        }

    }

}

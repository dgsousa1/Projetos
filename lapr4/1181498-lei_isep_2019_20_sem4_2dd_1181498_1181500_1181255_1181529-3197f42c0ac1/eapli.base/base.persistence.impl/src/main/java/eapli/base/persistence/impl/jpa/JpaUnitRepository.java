package eapli.base.persistence.impl.jpa;

import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaUnitRepository extends BasepaRepositoryBase<Unit, Long, Long>
        implements UnitRepository {

    JpaUnitRepository() {
        super("pkUnit");
    }

    @Override
    public List<Unit> listAllUnits() {
        final TypedQuery<Unit> query = entityManager().createQuery(
                "SELECT u from Unit u",
                Unit.class
        );
        try{
            List<Unit> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<Unit> findUnit(String name) {
        final TypedQuery<Unit> query = entityManager().createQuery(
                "SELECT u FROM Unit u  WHERE u.unit = :name",Unit.class
        );
        query.setParameter("name",name);
        try{
            Unit u = query.getSingleResult();
            return Optional.of(u);
        }catch(Exception e){
            return Optional.empty();
        }
    }
}

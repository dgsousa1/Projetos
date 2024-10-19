package eapli.base.utils.repositories;

import eapli.base.product.domain.Product;
import eapli.base.utils.Unit;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends DomainRepository<Long, Unit> {

     Optional<Unit> findUnit(String name);

     List<Unit> listAllUnits();

}

package eapli.base.productionline.repositories;

import eapli.base.machine.domain.Machine;
import eapli.base.productionline.domain.ProductionLine;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface ProductionLineRepository extends DomainRepository<Long, ProductionLine> {

    /**
     *
     * @return machines that are not associated with a production line
     */
    public Iterable<Machine> findMachinesWithoutProductionLine();

    public List<ProductionLine> listAllProductionLines();

}

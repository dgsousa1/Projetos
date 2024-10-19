package eapli.base.persistence.impl.jpa;

import eapli.base.machine.domain.Machine;
import eapli.base.product.domain.Product;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionline.repositories.ProductionLineRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JpaProductionLineRepository extends BasepaRepositoryBase<ProductionLine, Long, Long>
        implements ProductionLineRepository {

    JpaProductionLineRepository() {
        super("pkProductionLine");
    }


    /**
     *
     * @return machines that are not associated with a production line
     */
    @Override
    public Iterable<Machine> findMachinesWithoutProductionLine() {
        final TypedQuery<Machine> query = entityManager().createQuery(
                "SELECT m FROM Machine m WHERE m NOT IN (SELECT DISTINCT(machine) FROM ProductionLine p JOIN p.machines machine)",
                Machine.class
        );
        return query.getResultList();
    }

    @Override
    public List<ProductionLine> listAllProductionLines() {
        final TypedQuery<ProductionLine> query = entityManager().createQuery(
                "SELECT p from ProductionLine p",
                ProductionLine.class
        );
        try{
            List<ProductionLine> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }


}

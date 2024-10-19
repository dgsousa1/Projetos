package eapli.base.productionline.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.Machine;
import eapli.base.productionline.domain.ProductionLine;
import eapli.base.productionline.repositories.ProductionLineRepository;

import java.util.ArrayList;
import java.util.List;

public class AddProductionLineController {
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();

    /**
     * Creates and saves a new production line on the data base.
     * @param machines machines that constitute the production line.
     * @return the new production line.
     */
    public ProductionLine addProductionLine(List<Machine> machines){
        return productionLineRepository.save(new ProductionLine(machines));
    }

    /**
     * Gets all machines that are available (are not in a production line).
     * @return list of machines without production line.
     */
    public List<Machine> getMachinesWithoutProductionLine(){
        Iterable<Machine> it = productionLineRepository.findMachinesWithoutProductionLine();

        List<Machine> machines = new ArrayList<>();

        for(Machine m : it){
            machines.add(m);
        }

        return machines;
    }
}

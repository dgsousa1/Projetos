package eapli.base.persistence.impl.jpa;

import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.material.domain.Material;
import eapli.base.product.domain.Product;

import javax.crypto.Mac;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaMachineRepository extends BasepaRepositoryBase<Machine, Long, Long>
        implements MachineRepository {

    JpaMachineRepository() {
        super("pkMachine");
    }

    @Override
    public Optional<Machine> findBySerialNumber(Long number) {
        final TypedQuery<Machine> q = createQuery(
                "SELECT m FROM Machine m WHERE serialNumber = :key",Machine.class
        );
        q.setParameter("key",number);
        try {
            Machine m = q.getSingleResult();
            return Optional.of(m);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Machine updateMachine(Machine machine, ConfigurationFile configurationFile){
        this.context().beginTransaction();
        machine.addConfigurationFile(configurationFile);
        this.context().commit();
        return machine;
    }

    @Override
    public Optional<Machine> findByModelName(String name){
        final TypedQuery<Machine> q = entityManager().createQuery(
                "SELECT m FROM Machine m WHERE m.machineModel.modelName=:name",Machine.class
        );
        q.setParameter("name",name);
        try {
            Machine m = q.getSingleResult();
            return Optional.of(m);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    /**
     * Method to list all products in the database
     * @return a List of all products in the database, or an empty list if none were found
     */
    @Override
    public List<Machine> listAllMachines() {
        final TypedQuery<Machine> query = entityManager().createQuery(
                "SELECT m from Machine m",
                Machine.class
        );
        try{
            List<Machine> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
}

package eapli.base.machine.repositories;

import eapli.base.machine.domain.ConfigurationFile;
import eapli.base.machine.domain.Machine;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends DomainRepository<Long, Machine> {

    public Optional<Machine> findBySerialNumber(Long number);

    public Machine updateMachine(Machine machine, ConfigurationFile configurationFile);

    public Optional<Machine> findByModelName(String name);

    public List<Machine> listAllMachines();
}

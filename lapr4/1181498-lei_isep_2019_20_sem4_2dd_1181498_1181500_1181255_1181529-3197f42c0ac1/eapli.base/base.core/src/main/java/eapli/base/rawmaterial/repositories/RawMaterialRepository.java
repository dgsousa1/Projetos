package eapli.base.rawmaterial.repositories;

import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface RawMaterialRepository extends DomainRepository<Long, RawMaterial> {

    public Optional<RawMaterial> findByRawMaterialName(String name);

    public List<RawMaterial> listAllRawMaterials();

}

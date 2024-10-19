package eapli.base.infrastructure.smoketests;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.application.AddMaterialController;
import eapli.base.material.domain.Material;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.utils.application.UnitController;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.actions.Action;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class MaterialSmokeTester implements Action {
    private static final Logger LOGGER = LogManager.getLogger(MaterialSmokeTester.class);
    final AddMaterialController materialController = new AddMaterialController();
    final UnitController unitController = new UnitController();
    private final MaterialRepository materialRepository = PersistenceContext.repositories().materials();





    @Override
    public boolean execute() {
        addMaterialSmokeTester();
        findAllMaterialsCodeSmokeTester();
        findMaterialByCodeSmokeTest();
        findMaterialByNameSmokeTest();
        return true;
    }

    private void addMaterialSmokeTester(){
        final UnitRepository unitRepository = PersistenceContext.repositories().units();
        final Material material = materialController.addMaterial("AB33", "Material3",
                "CAT3", "Category3", "File3", "FilePath3", unitRepository.findUnit("UN").get());
        Invariants.noneNull(material);
        LOGGER.info("»»» add material");
    }

    private void findAllMaterialsCodeSmokeTester(){
        final Iterable<Material> allMaterials = materialRepository.listAllMaterials();
        Invariants.nonNull(allMaterials);
        Invariants.nonNull(allMaterials.iterator());
        Invariants.ensure(allMaterials.iterator().hasNext());
        LOGGER.info("»»» find all materials");
    }

    private void findMaterialByCodeSmokeTest(){
        final Optional<Material> material = materialRepository.findByMaterialCode("AB33");
        Invariants.nonNull(material);
        LOGGER.info("»»» find material by code");
    }

    private void findMaterialByNameSmokeTest(){
        final Optional<Material> material = materialRepository.findByMaterialName("Material3");
        Invariants.nonNull(material);
        LOGGER.info("»»» find material by name");
    }
}

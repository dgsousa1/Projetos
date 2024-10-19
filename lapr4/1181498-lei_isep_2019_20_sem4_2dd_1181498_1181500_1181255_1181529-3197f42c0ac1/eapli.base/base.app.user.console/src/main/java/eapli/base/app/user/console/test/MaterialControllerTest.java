package eapli.base.app.user.console.test;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.application.AddMaterialController;
import eapli.base.utils.repositories.UnitRepository;

public class MaterialControllerTest {
    public static void main(String[] args) {
        final UnitRepository unitRepository = PersistenceContext.repositories().units();
        AddMaterialController materialController = new AddMaterialController();
        materialController.addMaterial("A007", "Banana", "Fruta",
                "É fruta bro", "File", "Path",unitRepository.findUnit("UN").get());
    }
}

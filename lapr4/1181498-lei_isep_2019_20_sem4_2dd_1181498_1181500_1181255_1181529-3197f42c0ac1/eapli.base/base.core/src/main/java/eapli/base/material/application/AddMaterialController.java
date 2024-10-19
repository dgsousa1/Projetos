package eapli.base.material.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.material.domain.MaterialCode;
import eapli.base.material.domain.TechnicalFile;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.application.UseCaseController;

import java.util.List;
import java.util.Optional;

@UseCaseController
public class AddMaterialController {
    private final MaterialRepository materialRepository = PersistenceContext.repositories().materials();
    private final UnitRepository unitRepository = PersistenceContext.repositories().units();


    /**
     * Add materials to the system.
     * @param materialCode
     * @param materialName
     * @param categoryCode
     * @param categoryDescription
     * @param fileName
     * @param filePath
     * @param unit
     * @return material
     */
    public Material addMaterial(final String materialCode, final String materialName, final String categoryCode,
                                final String categoryDescription, final String fileName, final String filePath,final Unit unit){
        return materialRepository.save(new Material(new MaterialCode(materialCode, materialName),
                new MaterialCategory(categoryCode, categoryDescription), new TechnicalFile(fileName, filePath),
                new RawMaterial(materialName,unit)));
    }

    /**
     * Finds all materials by a certain code.
     * @param code
     * @return material
     */
    public Optional<Material> findByMaterialCode(String code){
        return this.materialRepository.findByMaterialCode(code);
    }

    /**
     * List all units available.
     * @return units
     */
    public List<Unit> listAllUnits(){
       return this.unitRepository.listAllUnits();
    }



}

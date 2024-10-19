package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.material.application.AddMaterialController;
import eapli.base.material.domain.Material;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;
import java.util.Optional;

public class AddMaterialUI extends AbstractUI {

    AddMaterialController materialController = new AddMaterialController();

    /**
     * Add material UI
     * @return true if the operation ran successfully and false the opposite occurs.
     */
    @Override
    protected boolean doShow() {
        boolean flag=true;
        String materialCode = null;
        while(flag){
            materialCode = Console.readLine("Material Code:  ");
            Optional<Material> m = materialController.findByMaterialCode(materialCode);
            if(!m.isPresent()){
                flag=false;
            }else if(m.isPresent()){
                System.out.println("Material already exists!\n-------------------------------------");
            }

        }

        final String materialName = Console.readLine("Material Name:");
        final String categoryCode = Console.readLine("Category Code:");
        final String categoryDescription = Console.readLine("Category Description:");
        final String fileName = Console.readLine("File Name:");
        final String filePath = Console.readLine("File Path:");


        int index, chosenUnitIndex;
        String valString;
        boolean val=true;
        Unit chosenUnit = null;
        List<Unit> allUnits = materialController.listAllUnits();
        if (allUnits.isEmpty()) {
            System.out.println("There are no units in the database");
        } else {
            while (val) {
                if (allUnits.isEmpty()) {
                    System.out.println("There are no more products available");
                    val = false;
                } else {
                    index = 1;
                    System.out.println("Choose a Unit:");
                    for (Unit u : allUnits) {
                        System.out.println(index + ": "+u.toString());
                        index++;
                    }
                    chosenUnitIndex = Console.readInteger("Write the number of the unit: ");
                    try {
                        chosenUnit = allUnits.get(chosenUnitIndex - 1);
                    } catch (Exception e) {
                        System.out.println("Unit is not available");
                    }
                    do {
                        valString = Console.readLine("Is this the Unit you want?Y/N");
                    } while (!(valString.trim().equals("Y") || valString.trim().equals("N")));
                    if (valString.equals("Y")) {
                        val = false;
                    } else {
                        val = true;
                    }
                }
            }
        }

        materialController.addMaterial(materialCode,materialName,categoryCode,categoryDescription,fileName,filePath,chosenUnit);
        System.out.println("Success!");
        return true;
    }

    @Override
    public String headline() {
        return "Add Material";
    }
}

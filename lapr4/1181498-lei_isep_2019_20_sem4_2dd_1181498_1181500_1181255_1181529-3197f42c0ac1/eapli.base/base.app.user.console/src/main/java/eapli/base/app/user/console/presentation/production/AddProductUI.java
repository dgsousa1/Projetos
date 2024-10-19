package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.AddProductController;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.utils.UIMethods;
import eapli.base.utils.Unit;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;
import java.util.Optional;

public class AddProductUI extends AbstractUI {

    AddProductController addProductController = new AddProductController();


    /**
     * Add Product UI.
     * @return true if success and false if failure.
     */
    @Override
    protected boolean doShow() {
        boolean mainFlag = true;

        while(mainFlag) {
            boolean flag = true;
            String commercialCode = null;
            while (flag) {
                do {
                    commercialCode = Console.readLine("Commercial Code(Max. 15 char): ");
                    if (commercialCode.length() > 15) {
                        System.out.println("Invalid code, please insert again\n-------------------------------------");
                    }
                } while (commercialCode.length() > 15);

                Optional<Product> p = addProductController.findByCommercialCode(commercialCode);
                if (!p.isPresent()) {
                    flag = false;
                } else if (p.isPresent()) {
                    System.out.println("Commercial code already exists!\n-------------------------------------");
                }
            }

            flag = true;
            String manufacturingCode = null;
            while (flag) {
                do {
                    manufacturingCode = Console.readLine("Manufacturing Code(Max. 15 char): ");
                    if (manufacturingCode.length() > 15) {
                        System.out.println("Invalid Code, please insert again\n-------------------------------------");
                    }
                } while (manufacturingCode.length() > 15);
                Optional<Product> p = addProductController.findByManufacturingCode(manufacturingCode);
                if (!p.isPresent()) {
                    flag = false;
                } else if (p.isPresent()) {
                    System.out.println("Product with Manufacturing code already exists!\n-------------------------------------");
                }
            }
            final String briefDescription = Console.readLine("Brief Description: ");
            final String completeDescription = Console.readLine("Complete Description: ");


            int index, chosenUnitIndex;
            String valString;
            boolean val=true;
            Unit chosenUnit = null;
            List<Unit> allUnits = addProductController.listAllUnits();
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
                        val=!UIMethods.askYN("Is this the Unit you want?");
                    }
                }
            }

            final String productCategory = Console.readLine("Product Category: ");

            addProductController.addProduct(manufacturingCode, commercialCode, briefDescription,
                    completeDescription, chosenUnit, productCategory);
            System.out.println("Product Successful Added!");

            mainFlag = UIMethods.askYN("Do you want to add another Product?");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Insert new Product";
    }
}

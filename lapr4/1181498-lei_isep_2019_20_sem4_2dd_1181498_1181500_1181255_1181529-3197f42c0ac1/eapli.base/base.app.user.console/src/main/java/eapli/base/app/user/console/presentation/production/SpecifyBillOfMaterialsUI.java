package eapli.base.app.user.console.presentation.production;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.application.BillOfMaterialsController;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.BillOfMaterialsRepository;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.rawmaterial.repositories.RawMaterialRepository;
import eapli.base.utils.MeasuredRawMaterial;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.LinkedList;
import java.util.List;

public class SpecifyBillOfMaterialsUI extends AbstractUI {
    BillOfMaterialsController billOfMaterialsController = new BillOfMaterialsController();
    Product p;

    /**
     * Method that displays the menu to the user that allows the creation of a new Bill Of Materials
     * @return a boolean True or False depending on the success of the operation.
     */
    @Override
    protected boolean doShow() {
        boolean val = true;
        String valString;
        int index, chosenProduct,chosenRawMaterial;
        List<Product> productsWithoutBOM = billOfMaterialsController.productsWithNoBillOfMaterials();
        if (productsWithoutBOM.isEmpty()) {
            System.out.println("There are no products available");
            return false;
        } else {
            while (val) {
                if (productsWithoutBOM.isEmpty()) {
                    System.out.println("There are no more products available");
                    val = false;
                } else {
                    index = 1;
                    System.out.println("Associate product with the bill of materials:");
                    for (Product m : productsWithoutBOM) {
                        System.out.println(index+": "+m.toDTO().writeForUI());
                        index++;
                    }
                    chosenProduct = Console.readInteger("Choose a product to be added: ");
                    try {
                        p = productsWithoutBOM.get(chosenProduct - 1);
                        productsWithoutBOM.remove(chosenProduct - 1);
                    } catch (Exception e) {
                        System.out.println("Product is not available");
                    }

                    val = !UIMethods.askYN("Is this the product you want to add?");

                    /* do {
                        valString = Console.readLine("Is this the product you want to add?Y/N");
                    } while (!(valString.trim().equals("Y") || valString.trim().equals("N")));
                    if (valString.equals("Y")) {
                        val = false;
                    } else {
                        val = true;
                    }*/
                }
            }
        }


        List<RawMaterial> rawMaterialList = billOfMaterialsController.listAllRawMaterials();
        List<RawMaterial> chosenRawMaterials = new LinkedList<>();
        List<MeasuredRawMaterial> measuredRawMaterials = new LinkedList<>();
        double chosenQuantity;

        if (rawMaterialList.isEmpty()) {
            System.out.println("There are no raw materials available");
        } else {
            val =true;
            while (val) {
                if (rawMaterialList.isEmpty()) {
                    System.out.println("There are no more raw materials available");
                    val = false;
                } else {
                    index = 1;
                    System.out.println("Associate raw materials with the bill of materials:");
                    for (RawMaterial m : rawMaterialList) {
                        System.out.println(index + ": " + m.getRawMaterialName());
                        index++;
                    }
                    chosenRawMaterial = Console.readInteger("Choose a raw material to be added: ");
                    try {
                        RawMaterial temp = rawMaterialList.remove(chosenRawMaterial-1);
                        chosenQuantity = Console.readDouble("Choose the quantity: ");
                        boolean loop=true;
                        while(loop){
                            if(chosenQuantity<=0){
                                System.out.println("Quantity has to be bigger than zero");
                            }else{
                                loop=false;
                            }

                        }
                        measuredRawMaterials.add(new MeasuredRawMaterial(temp,(float)chosenQuantity));
                    } catch (Exception e) {
                        System.out.println("Raw Material is not available");
                    }
                    val = UIMethods.askYN("Want to add more raw materials?");
                    /*
                    do {
                        valString = Console.readLine("Want to add more raw materials?Y/N");
                    } while (!(valString.trim().equals("Y") || valString.trim().equals("N")));
                    if (valString.equals("Y")) {
                        val = true;
                    } else {
                        val = false;
                    }*/
                }

            }


        }


        System.out.println(p.toDTO().writeForUI());
        for(MeasuredRawMaterial mr : measuredRawMaterials){
            System.out.println("Quantity: "+mr.getQuantity() +" "+mr.getRawMaterial().toString());
        }

        valString = Console.readLine("Add this bill Of Materials? Y/N");
        if (valString.equals("Y")||valString.equals("y")) {
            try {
                billOfMaterialsController.addBillOfMaterials(p,measuredRawMaterials);
                System.out.println("Success");
            } catch (Exception e) {
                System.out.println("Failure");
                return false;
            }
        }
        return true;
    }

    @Override
    public String headline() { return "Specify Bill Of Materials"; }
}

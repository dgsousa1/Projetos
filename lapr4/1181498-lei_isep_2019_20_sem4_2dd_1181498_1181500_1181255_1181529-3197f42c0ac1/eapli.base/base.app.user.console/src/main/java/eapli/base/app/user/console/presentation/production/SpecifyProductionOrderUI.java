package eapli.base.app.user.console.presentation.production;

import eapli.base.product.domain.Product;
import eapli.base.productionorder.application.AddProductionOrderController;
import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SpecifyProductionOrderUI extends AbstractUI {


    AddProductionOrderController addProductionOrderController = new AddProductionOrderController();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Product p;

    /**
     * Method that displays the menu that allows the user to create a new Production Order
     * @return a boolean True or False depending on the success of the operation.
     */
    @Override
    protected boolean doShow() {
        boolean val = true;
        String valString;
        int index, chosenProduct;
        List<Product> productsWithoutPO = addProductionOrderController.productsWithNoProductionOrder();
        if (productsWithoutPO.isEmpty()) {
            System.out.println("There are no products available");
            return false;
        } else {
            while (val) {
                if (productsWithoutPO.isEmpty()) {
                    System.out.println("There are no more products available");
                    val = false;
                } else {
                    index = 1;
                    System.out.println("Associate product with the production order:");
                    for (Product m : productsWithoutPO) {
                        System.out.println(index +": "+m.toDTO().writeForUI());
                        index++;
                    }
                    chosenProduct = Console.readInteger("Choose a product to be added: ");
                    try {
                        p = productsWithoutPO.get(chosenProduct - 1);
                        productsWithoutPO.remove(chosenProduct - 1);
                    } catch (Exception e) {
                        System.out.println("Product is not available");
                    }
                    do {
                        valString = Console.readLine("Is this the product you want to add?");
                    } while (!(valString.trim().equals("Y") || valString.trim().equals("N")));
                    if (valString.equals("Y")) {
                        val = false;
                    } else {
                        val = true;
                    }
                }
            }
        }

        boolean loop = true;
        String chosenCommission;
        List<String> chosenCommissions = new ArrayList<>();

        while(loop) {
            chosenCommission = Console.readLine("Associate Commission: ");
            if(chosenCommission.isEmpty() || chosenCommission == null){
                loop = true;
            }else {
                loop=UIMethods.askYN("Associate more commissions?");
            }
        }

        loop=true;
        double quantity=0;
        while(loop) {
                quantity = Console.readDouble("Insert Product Quantity: ");
                if(quantity>0){
                    loop=false;
                }else{
                    System.out.println("Quantity has to be bigger than zero.");
                }
        }



        loop = true;
        String orderCode = null;

            while (loop) {
                orderCode = Console.readLine("Insert Order Code: ");
                if (orderCode.isEmpty() || orderCode == null) {
                    System.out.println("Order Code null or empty");
                    loop = true;
                } else {
                    Optional<ProductionOrder> p = addProductionOrderController.findProductionOrderByCode(orderCode);
                    if (p.isPresent()) {
                        System.out.println("Production Order with that code already exists");
                        loop = true;
                    } else {
                        loop = false;
                    }
                }
            }


        System.out.println("Insert issuing date: ");
        LocalDateTime issueDate = UIMethods.askForDate();

        String issDate = issueDate.format(formatter);

        String executionDate = null;
            System.out.println("Insert execution date: ");
            loop = true;
            while (loop) {
                executionDate = Console.readLine("Date format: YYYY-MM-DD HH:mm");
                if (executionDate.length() != 16) {
                    System.out.println("Date provided is invalid");
                    loop = true;
                } else {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime startExecution = LocalDateTime.parse(executionDate, formatter);
                        if (startExecution.isBefore(LocalDateTime.now())) {
                            System.out.println("Date provided is invalid");
                            loop = true;
                        } else if(startExecution.isBefore(issueDate)) {
                            System.out.println("Date provided is invalid");
                            loop = true;
                        } else {
                            loop = false;
                        }
                    } catch (DateTimeParseException e) {
                        loop = true;
                        System.out.println("Date provided is invalid");
                    }
                }
            }



            System.out.println("Production Order: ");
            System.out.println("Order Code: " + orderCode);
            System.out.println("Execution Date: " + executionDate);
            System.out.println("Issue Date: " + issDate);
            System.out.println(p.toDTO().writeForUI());
            System.out.println("Quantity: " + quantity);
            System.out.println("Associated Commission:");
            for(String s : chosenCommissions){
                System.out.println(s);
            }


            if (UIMethods.askYN("Add this production order?")) {
                try {
                    addProductionOrderController.addProductionOrder(orderCode,executionDate,issueDate,p,(float)quantity,chosenCommissions);
                    System.out.println("Success");
                } catch (Exception e) {
                    System.out.println("Failure");
                    return false;
                }
            }
            return true;
        }

    @Override
    public String headline() {
        return "Specify Production Order";
    }





}

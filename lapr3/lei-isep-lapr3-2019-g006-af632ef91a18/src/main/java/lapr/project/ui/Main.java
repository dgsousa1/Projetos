package lapr.project.ui;

//import lapr.project.data.DataHandler;
//import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lapr.project.controller.POIController;
import lapr.project.controller.ParkController;
import lapr.project.controller.VehicleController;
import lapr.project.data.DataHandler;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.Properties;
//import java.util.logging.Logger;
import lapr.project.data.InitializeDataHandler;
import lapr.project.data.POIRegistration;
import lapr.project.data.ParkRegistration;
import lapr.project.data.VehicleRegistration;
//import lapr.project.data.UserRegistration;

/**
 * @author Nuno Bettencourt <nmb@isep.ipp.pt> on 24/05/16.
 */
class Main {

    /**
     * Logger class.
     */
    //private static final Logger LOGGER = Logger.getLogger("MainLog");
    /**
     * Private constructor to hide implicit public one.
     */
    private Main() {

    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        /*   CalculatorExample calculatorExample = new CalculatorExample();
        int value = calculatorExample.sum(3, 5);

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.log(Level.INFO, String.valueOf(value)); */

        //load database properties
//        try {
//            Properties properties =
//                    new Properties(System.getProperties());
//            InputStream input = new FileInputStream("target/classes/application.properties");
//            properties.load(input);
//            input.close();
//            System.setProperties(properties);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Initial Database Setup
//        DataHandler dh = new DataHandler();
        /*   dh.scriptRunner("target/test-classes/demo_jdbc.sql");

        System.out.println("\nVerificar se existe Sailor 100...");
        try {
            Sailor.getSailor(100);
            System.out.println("Nunca deve aparecer esta mensagem");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("\nAdicionar Sailor ..."); 


        long sailorID = 100;
        String sailorName = "Popeye";
        long sailorRating = 11;
        int sailorAge = 85;

        Sailor sailor = new Sailor(sailorID, sailorName);
        sailor.setAge(sailorAge);
        sailor.setRating(sailorRating);
        sailor.save();

        System.out.println("\t... Sailor Adicionado.");

        System.out.println("\nVerificar se existe Sailor 100...");
        try {
            sailor = Sailor.getSailor(100);
            System.out.println("\nSailor 100 existe...: " + sailor.getName());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
         */
//        InitializeDataHandler.initializeDH();
//        ParkController controller = new ParkController(new ParkRegistration());
//        controller.addPark("parks.csv");
//        
//        controller.getNearestParks(41.148415, -8.610815, "NearestParksTest", 1000);
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }
        DataHandler dh = new DataHandler();
//        VehicleController controller = new VehicleController(new VehicleRegistration());
//        controller.addBicycle("BicycleTest");
        POIController controller = new POIController(new POIRegistration());
        controller.addPOI("POITest");
    }
}

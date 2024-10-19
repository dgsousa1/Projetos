package lapr.project.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Location;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import lapr.project.utils.Printer;

public class VehiclesAtGivenParkController {

    /**
     * Vehicle manager.
     */
    private final VehicleRegistration vehicleRegistration;
    
    /**
     * Park manager.
     */
    private final ParkRegistration parkRegistration;

    /**
     * Builder responsible for getting the vehicle at a park being used.
     * @param vehicleRegistration
     * @param parkRegistration 
     */
    public VehiclesAtGivenParkController(VehicleRegistration vehicleRegistration, ParkRegistration parkRegistration) {
        this.vehicleRegistration = vehicleRegistration;
        this.parkRegistration = parkRegistration;
    }

//    public int bicyclesAtGivenPark(double latitude, double longitude) throws SQLException {
//        try {
//            return vehicleRegistration.getBicyclesAtGivenLocation(latitude, longitude);
//        } catch (SQLException e) {
//            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return 0;
//
//    }
    
     /**
     * Get the list of escooters parked at a given park.
     *
     * @param latitude
     * @param longitude
     * @param outputFileName Path to file where output should be written,
     *                       according to file output/escooters.csv. Sort in
     *                       ascending order by bike description.
     * @return The number of escooters at a given park.
     */
    public int scootersAtGivenPark(double latitude, double longitude, String outputFileName) {
        List<Scooter> list;
        try {
            list = vehicleRegistration.getAllScootersOfPark(new Location(latitude, longitude));
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            Printer.printScooters(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return list.size();
    }

  /**
     * Get the list of escooters parked at a given park.
     *
     * @param parkIdentification The Park Identification.
     * @param outputFileName Path to file where output should be written,
     *                       according to file output/escooters.csv. Sort in
     *                       ascending order by bike description.
     * @return The number of escooters at a given park.
     */
    public int scootersAtGivenPark(String parkIdentification, String outputFileName) {
        List<Scooter> list;
        Park park;
        try {
            park = parkRegistration.getPark(parkIdentification);
            list = vehicleRegistration.getAllScootersOfPark(park.getLocation());

        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            Printer.printScooters(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return list.size();
    }

    /**
     * Get the list of bicycles parked at a given park.
     *
     * @param latitude
     * @param longitude
     * @param outputFileName Path to file where output should be written,
     *                       according to file output/bicycles.csv. Sort in
     *                       ascending order by bike description.
     * @return The number of bicycles at a given park.
     */
    public int bicyclesAtGivenPark(double latitude, double longitude, String outputFileName) {
        List<Bicycle> list = vehicleRegistration.getAllBicyclesOfPark(new Location(latitude, longitude));
        try {
            Printer.printBicycles(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return list.size();
    }

   /**
     * Get the list of bicycles parked at a given park.
     *
     * @param parkIdentification The Park Identification.
     * @param outputFileName Path to file where output should be written,
     *                       according to file output/bicycles.csv. Sort in
     *                       ascending order by bike description.
     * @return The number of bicycles at a given park.
     */
    public int bicyclesAtGivenPark(String parkIdentification, String outputFileName) {
        List<Bicycle> list;
        Park park;
        try {
            park = parkRegistration.getPark(parkIdentification);
            list = vehicleRegistration.getAllBicyclesOfPark(park.getLocation());
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            Printer.printBicycles(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(VehiclesAtGivenParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return list.size();
    }
}

package lapr.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.model.Location;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.utils.Calculator;
import lapr.project.utils.Graph;
import lapr.project.utils.GraphAlgorithms;
import lapr.project.utils.GraphArithmetic;
import lapr.project.utils.Printer;
import lapr.project.utils.Reader;

/**
 *
 *
 */
public class ParkController {

    /**
     * Park manager.
     */
    ParkRegistration parkRegistration;

    /**
     * Builder responsible for getting the park being used.
     * @param parkRegistration 
     */
    public ParkController(ParkRegistration parkRegistration) {
        this.parkRegistration = parkRegistration;
    }

    /**
     * Updates the information about a park so it can be manageabel.
     * @param s
     * @return 0 if zero things were changed in the park and so nothing happens,
     * or 1 if something was changed and so the park is updated.
     */
    public int updatePark(String s) {
        Park park;
        try {
            String[] param = s.split(";");
            String idPark = param[0].trim();

            double latitude = Double.parseDouble(param[1].trim());
            double longitude = Double.parseDouble(param[2].trim());
            int elevation = Integer.parseInt(param[3].trim());
            Location location = new Location(latitude, longitude, elevation);
            String description = param[4].trim();
            int maxBike = Integer.parseInt(param[5].trim());
            int maxScooter = Integer.parseInt(param[6].trim());
            float inputVoltage = Float.parseFloat(param[7].trim());
            float inputCurrent = Float.parseFloat(param[8].trim());
            park = new Park(idPark, location, description, maxBike, maxScooter, inputVoltage, inputCurrent);
        
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        try {
            parkRegistration.updatePark(park);
        } catch (Exception ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return 1;
    }

  /**
     * Add Parks to the system.
     *
     * Basic: Add one Park.
     * Intermediate: Add several Parks.
     * Advanced: Add several Parks transactionally.
     *
     * @param s
     * @return The number of added parks.
     */
    public int addPark(String s) {
        List<Park> parks = new LinkedList<>();
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {

            String str;
            try {
                while ((str = br.readLine()) != null) {
                    if (!(str.startsWith("#") || str.contains("park identification"))) {

                        String[] param = str.split(";");
                        String idPark = param[0].trim();

                        double latitude = Double.parseDouble(param[1].trim());
                        double longitude = Double.parseDouble(param[2].trim());
                        int elevation = Integer.parseInt(param[3].trim());
                        Location location = new Location(latitude, longitude, elevation);
                        String description = param[4].trim();
                        int maxBike = Integer.parseInt(param[5].trim());
                        int maxScooter = Integer.parseInt(param[6].trim());
                        float inputVoltage = Float.parseFloat(param[7].trim());
                        float inputCurrent = Float.parseFloat(param[8].trim());
                        Park park = new Park(idPark, location, description, maxBike, maxScooter, inputVoltage, inputCurrent);
                        parks.add(park);
                    }
                }
            } catch (NumberFormatException e) {
                Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, e);
                return 0;
            }
        } catch (IOException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            parkRegistration.addParkBatch(parks);
        } catch (Exception ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return parks.size();
    }

    /**
     * Get a list of the nearest parks to the user.
     *
     * @param v
     * @param v1
     * @param s
     * @param i
     * @return 0 if the user can't find any park near him,
     * or 1 if he finds and so returns the list of them.
     */
    public int getNearestParks(double v, double v1, String s, int i) {
        Map<Double, Park> nearestParks = new HashMap<>();
        try {
            nearestParks = parkRegistration.getNearestParks(v, v1, i);
        } catch (Exception ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            Printer.printPark(nearestParks, s);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 1;
    }

  /**
     * Remove a park from the system.
     *
     * @param id
     * @return The number of removed parks.
     */
    public int removePark(String id) {
        try {
            parkRegistration.removePark(id);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * Get the linear distance from one location to another.
     *
     * @param d
     * @param d1
     * @param d2
     * @param d3
     * @return Returns the distance in meters from one location to another.
     */
    public int linearDistanceTo(double d, double d1, double d2, double d3) {
        return Calculator.calcularDistancia(d, d1, d2, d3).intValue();
    }

 /**
     * Get the number of free bicyle parking places at a given park for the
     * loaned bicycle.
     *
     * @param parkId
     * @param username The username that has an unlocked it.
     *
     * @return The number of free slots at a given park for the user's
     * bicycle type.
     */
    public int getFreeBicycleSlotsAtPark(String parkId, String username) {
        int res;
        try {
            res = parkRegistration.getFreeBicycleSlotsAtPark(parkId);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }

  /**
     * Get the number of free escooters parking places at a given park for the
     * loaned scooter.
     *
     * @param parkId
     * @param username The username that has an unlocked it.
     *
     * @return The number of free slots at a given park for the user's vehicle.
     *
     */
    public int getFreeEscooterSlotsAtPark(String parkId, String username) {
        int res;
        try {
            res = parkRegistration.getFreeEscooterSlotsAtPark(parkId);
        } catch (Exception e) {
            return 0;
        }
        return res;
    }
    
    public int getFreeSlotsAtParkForMyLoanedVehicle(String username, String parkIdentification){
        int res;
        try{
            res = parkRegistration.getFreeSlotsAtParkForMyLoanedVehicle(username, parkIdentification);
        }catch(Exception e){
            return 0;
        }
        return res;
    }

        /**
     * Get the shortest path distance from one location to another.
     *
     * @param d
     * @param d1
     * @param d2
     * @param d3
     * @return Returns the distance in meters from one location to another.
     */
    public int pathDistanceTo(double d, double d1, double d2, double d3) {
        Graph<Location, Path> graph = new Graph<>(true);
        LinkedList<Location> shortPath = new LinkedList<>();
        try {
            graph = parkRegistration.getGraph();
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
        }
        Location begin = parkRegistration.getLocation(graph.vertices().iterator(), d, d1);
        Location end = parkRegistration.getLocation(graph.vertices().iterator(), d2, d3);
        double distance = GraphAlgorithms.shortestPath(graph, begin, end, shortPath);
        return (int) distance;
    }

    /**
     * Calculate the shortest Route from one park to another.
     *
     * Basic: Only one shortest Route between two Parks is available.
     * Advanced: More than one Route between two parks are available with
     * different number of points inbetween and different evelations difference.
     *
     * @param idParkOrg
     * @param idParkDest
     * @param inputPOIs Path to file that contains the POIs that the route
     *                  must go through, according to file input/pois.csv.
     * @param outFile
     * @return The distance in meters for the shortest path.
     */
    public long shortestRoutePassingInPOIs(String idParkOrg, String idParkDest, String inputPOIs, String outFile) {
        Graph<Location, Path> graph = new Graph<>(true);
        Park parkOrg = null;
        Park parkDest = null;
        List<POI> pois = Reader.getPOIsFromFile(inputPOIs);
        try {
            graph = parkRegistration.getGraph();
            parkOrg = parkRegistration.getPark(idParkOrg);
            parkDest = parkRegistration.getPark(idParkDest);
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        List<Location> shortPath = new LinkedList<>();
        double finalDistance = GraphArithmetic.getShortestPathPassingInPOIs(pois, graph, parkOrg, parkDest, shortPath);
        try {
            Printer.printPathShortestDistance(shortPath, outFile, finalDistance);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return Math.round(finalDistance);
    }

   /**
     * Calculate the shortest Route from one park to another.
     *
     * Basic: Only one shortest Route between two Parks is available.
     * Advanced: More than one Route between two parks are available with
     * different number of points inbetween and different evelations difference.
     *
     * @param latOrig
     * @param lonOrg
     * @param latDest
     * @param inputPOIs Path to file that contains the POIs that the route
     *                  must go through, according to file input/pois.csv.
     * @param lonDest
     * @return The distance in meters for the shortest path.
     */
    public long shortestRoutePassingInPOIs(double latOrig, double lonOrg, double latDest, double lonDest,
            String inputPOIs, String outFile) {
        Graph<Location, Path> graph = new Graph<>(true);
        Park parkOrg = null;
        Park parkDest = null;
        List<POI> pois = Reader.getPOIsFromFile(inputPOIs);
        try {
            graph = parkRegistration.getGraph();
            String idParkOrg = parkRegistration.getParkByCoordsWithoutElevation(latOrig, lonOrg);
            String idParkDest = parkRegistration.getParkByCoordsWithoutElevation(latDest, lonDest);
            parkOrg = parkRegistration.getPark(idParkOrg);
            parkDest = parkRegistration.getPark(idParkDest);
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }

        List<Location> shortPath = new LinkedList<>();
        double finalDistance = GraphArithmetic.getShortestPathPassingInPOIs(pois, graph, parkOrg, parkDest, shortPath);
        try {
            Printer.printPathShortestDistance(shortPath, outFile, finalDistance);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return Math.round(finalDistance);
    }
}

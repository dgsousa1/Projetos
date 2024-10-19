package lapr.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
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
public class EnergyUsageController {

    /**
     * Vehicle manager.
     */
    VehicleRegistration vehicleRegistration;
    
    /**
     * Park manager.
     */
    ParkRegistration parkRegistration;
    
    /**
     * User manager.
     */
    UserRegistration userRegistration;

    /**
     * Builder responsible for getting the energy usage being used.
     * @param vehicleRegistration
     * @param parkRegistration
     * @param userRegistrtion 
     */
    public EnergyUsageController(VehicleRegistration vehicleRegistration, ParkRegistration parkRegistration, UserRegistration userRegistrtion) {
        this.vehicleRegistration = vehicleRegistration;
        this.parkRegistration = parkRegistration;
        this.userRegistration = userRegistrtion;
    }

    /**
     * It gets the projection of calories that a user has spent during his trip.
     * @param idParkOrg
     * @param username
     * @param idBike
     * @param lat
     * @param lon
     * @return energy
     */
    public int projectionOfCalories(String idParkOrg, String username, String idBike, double lat, double lon) {
        Graph<Location, Path> graph;
        Client client;
        Park parkOrg;
        Park parkDest;
        Bicycle bike;
        try {
            graph = parkRegistration.getGraph();
            client = userRegistration.getClient(username);
            String idPark = parkRegistration.getParkByCoordsWithoutElevation(lat, lon);
            parkOrg = parkRegistration.getPark(idParkOrg);
            parkDest = parkRegistration.getPark(idPark);
            bike = vehicleRegistration.getBicycle(idBike);
        } catch (Exception ex) {
            return 0;
        }
        LinkedList<Location> shortPath = new LinkedList<>();
        GraphAlgorithms.shortestPath(graph, parkOrg.getLocation(), parkDest.getLocation(), shortPath);
        List<Path> paths = getPaths(shortPath, graph);
        double energy = 0;
        for (Path p : paths) {
            energy += Calculator.calculateEnergyForPath(client, p, bike);
        }
        //kcal
        return (int) (energy * 860.42065);
    }

 
    /**
     * Suggest escooters with enough energy + 10% to go from one
     * Park to another.
     *
     * @param string
     * @param string1
     * @param d
     * @param d1
     * @param string2
     * @return The number of suggested vehicles.
     */
    public int suggestEscootersToGoFromOneParkToAnother(String string, String string1, double d, double d1, String string2) {
        List<Scooter> scooters;
        Graph<Location, Path> graph;
        Client client;
        Park parkOrg;
        Park parkDest;
        try {
            graph = parkRegistration.getGraph();
            client = userRegistration.getClient(string1);
            String idPark = parkRegistration.getParkByCoordsWithoutElevation(d, d1);
            parkOrg = parkRegistration.getPark(string);
            parkDest = parkRegistration.getPark(idPark);
            scooters = vehicleRegistration.getAllScootersOfPark(parkOrg.getLocation());
        } catch (Exception ex) {
            return 0;
        }
        LinkedList<Location> shortPath = new LinkedList<>();
        GraphAlgorithms.shortestPath(graph, parkOrg.getLocation(), parkDest.getLocation(), shortPath);
        List<Path> paths = getPaths(shortPath, graph);
        List<Scooter> usableScooters = new ArrayList<>();
        for (Scooter scooter : scooters) {
            double energy = 0;
            for (Path p : paths) {
                energy += Calculator.calculateEnergyForPath(client, p, scooter);
            }
            if (energy <= (scooter.getActualBatteryCapacity() / 100) * scooter.getMaxBatteryCapacity() + 0.10 * energy) {
                usableScooters.add(scooter);
            }
        }
        try {
            Printer.printScooters(usableScooters, string2);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return usableScooters.size();
    }

    /**
     * It gets all the paths available.
     * @param list
     * @param graph
     * @return paths
     */
    private List<Path> getPaths(List<Location> list, Graph<Location, Path> graph) {
        List<Path> paths = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            paths.add(graph.getEdge(list.get(i), list.get(i + 1)).getElement());
        }
        return paths;
    }

  /**
     * Calculate the most energy efficient route from one park to
     * another.
     *
     * Basic: Does not consider wind.
     * Intermediate: Considers wind.
     * Advanced: Considers the different mechanical and aerodynamic
     * coefficients.
     *
     * @param idParkOrg
     * @param idParkDest
     * @param vehicleType
     * @param outputFile
     * @param vehicleSpecs The specs for the vehicle e.g. "16", "19",
     *                    "27" or any other number for bicyles and "city" or
     *                     "off-road" for any escooter.
     * @param username The username.
     * @return The distance in meters for the most energy efficient path.
     */
    public long mostEnergeticallyEffecientRoute(String idParkOrg, String idParkDest, String vehicleType, String vehicleSpecs,
            String username, String outputFile) {
        Graph<Location, Path> graph;
        LinkedList<Location> shortPath = new LinkedList<>();
        Client client;
        Vehicle vehicle;
        Park parkOrg;
        Park parkDest;
        try {
            client = userRegistration.getClient(username);
            vehicle = getVehicle(vehicleType, vehicleSpecs);
            graph = parkRegistration.getGraphForEnergy(client, vehicle);
            parkOrg = parkRegistration.getPark(idParkOrg);
            parkDest = parkRegistration.getPark(idParkDest);
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
        double energy = GraphAlgorithms.shortestPath(graph, parkOrg.getLocation(), parkDest.getLocation(), shortPath);
        //List<List<Location>> shortPaths = GraphArithmetic.getPathWithMinDistance(energy, graph, parkOrg, parkDest, client, vehicle);
        try {
            Printer.printPathEletric(shortPath, outputFile, client, vehicle, energy);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return Math.round(Calculator.calculatePathDistance(shortPath));
    }

    /**
     * It gets the vehicle.
     * @param vehicleType
     * @param vehicleSpecs
     * @return vehicle
     */
    private Vehicle getVehicle(String vehicleType, String vehicleSpecs) {
        Vehicle vehicle = null;
        if (vehicleType.equals("escooter")) {
            try {
                vehicle = vehicleRegistration.getScooterFromSpecs(vehicleSpecs);
            } catch (Exception ex) {
                Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (vehicleType.equals("bicycle")) {
            try {
                vehicle = vehicleRegistration.getBicycleFromSpecs(vehicleSpecs);
            } catch (Exception ex) {
                Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vehicle;
    }

   /**
     * Calculate the most efficient Route passing in POIs.
     *
     * Basic: Only one shortest Route between two Parks is available.
     * Advanced: More than one Route between two parks are available with
     * different number of points inbetween and different evelations difference.
     *
     * @param originParkIdentification
     * @param destinationParkIdentification
     * @param typeOfVehicle
     * @param vehicleSpecs
     * @param username
     * @param maxNumberOfSuggestions
     * @param ascendingOrder
     * @param sortingCriteria
     * @param inputPOIs Path to file that contains the POIs that the route
     *                  must go through, according to file input/pois.csv.
     * @param outputFileName Write to the file the Route between two parks
     *                   according to file output/paths.csv. More than one
     *                   path may exist. If so, sort routes by the ascending
     *                   number of points between the parks and by ascending
     *                   order of elevation difference.
     * @return The distance in meters for the most efficent route.
     */
    public int mostEnergeticallyEffecientRoutePassingInPOIs(String originParkIdentification,
            String destinationParkIdentification,
            String typeOfVehicle,
            String vehicleSpecs,
            String username,
            int maxNumberOfSuggestions,
            boolean ascendingOrder,
            String sortingCriteria,
            String inputPOIs,
            String outputFileName) {
        Graph<Location, Path> graph = new Graph<>(true);
        Park parkOrg = null;
        Park parkDest = null;
        Client client = null;
        Vehicle vehicle = null;
        List<POI> pois = Reader.getPOIsFromFile(inputPOIs);
        try {
            parkOrg = parkRegistration.getPark(originParkIdentification);
            parkDest = parkRegistration.getPark(destinationParkIdentification);
            client = userRegistration.getClient(username);
            vehicle = getVehicle(typeOfVehicle, vehicleSpecs);
            graph = parkRegistration.getGraphForEnergy(client, vehicle);
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
        List<Location> shortPath = new LinkedList<>();

        double finalDistance = GraphArithmetic.getShortestPathPassingInPOIs(pois, graph, parkOrg, parkDest, shortPath);

        try {
            Printer.printPathEletric(shortPath, outputFileName, client, vehicle, finalDistance);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return (int) Math.round(Calculator.calculatePathDistance(shortPath));
    }

   /**
     * Get a report for the escooter charging status at a given park.
     *
     * @param parkIdentification Park Identification.
     * @param outputFileName Path to file where vehicles information should be
     *                       written, according to file output/chargingReport
     *                       .csv.
     *                       Sort items by descending order of time to finish
     *                       charge in seconds and secondly by ascending
     *                       escooter description order.
     * @return The number of escooters charging at the moment that are not
     * 100% fully charged.
     */
    public long getParkChargingReport(String parkIdentification, String outputFileName) {
        Park park = null;
        List<Scooter> scooters = null;
        Map<String, Double> unSortedMap = new LinkedHashMap<>();

        try {
            park = parkRegistration.getPark(parkIdentification);
            scooters = vehicleRegistration.getAllScootersOfPark(park.getLocation());
        } catch (Exception ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        double potencyPark = 3000;

        double divisionCalc = potencyPark * 1 / scooters.size();
        int notFullyCharged = 0;
        Scooter s;

        for (int i = 0; i < scooters.size(); i++) {
            s = scooters.get(i);
            if (s.getActualBatteryCapacity() < 100) {
                notFullyCharged++;
                double scooterChargingTime = ((s.getMaxBatteryCapacity() - (s.getMaxBatteryCapacity()
                        * s.getActualBatteryCapacity()) / 100) / divisionCalc) * 3600;
                unSortedMap.put(s.getIdVehicle(), scooterChargingTime);
            }
        }
        Map<String, Double> reverseSortedMapByValue = new LinkedHashMap<>();

        unSortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMapByValue.put(x.getKey(), x.getValue()));

        Map<String, Double> sortedMap = new LinkedHashMap<>(reverseSortedMapByValue);
        Map<Scooter, Double> scooterMap = new LinkedHashMap<>();

        for (Map.Entry<String, Double> i : sortedMap.entrySet()) {
            Scooter scooter = null;
            try {
                scooter = vehicleRegistration.getScooter(i.getKey());
            } catch (Exception ex) {
                Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
            double chargingTime = i.getValue();
            scooterMap.put(scooter, chargingTime);
        }

        try {
            Printer.printChargingTimeScooters(scooterMap, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(EnergyUsageController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return notFullyCharged;

    }

   /**
     * Calculate the amount of electrical energy required to travel from one
     * park to another.
     *
     * @param originLatitudeInDegrees Origin latitude in Decimal degrees.
     * @param originLongitudeInDegrees Origin Longitude in Decimal degrees.
     * @param destinationLatitudeInDegrees Destination Park latitude in
     *                                     Decimal degrees.
     * @param destinationLongitudeInDegrees Destination Park Longitude in
     *                                      Decimal degrees.
     * @param username Username.
     * @return The electrical energy required in kWh, rounded to two decimal
     * places.
     */
    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(
            double originLatitudeInDegrees,
            double originLongitudeInDegrees,
            double destinationLatitudeInDegrees,
            double destinationLongitudeInDegrees,
            String username) {
        Park parkOrg;
        Park parkDest;
        Client client;
        Vehicle vehicle;
        Graph<Location, Path> graph;
        try {
            String idParkOrg = parkRegistration.getParkByCoordsWithoutElevation(originLatitudeInDegrees, originLongitudeInDegrees);
            String idParkDest = parkRegistration.getParkByCoordsWithoutElevation(destinationLatitudeInDegrees, destinationLongitudeInDegrees);
            parkOrg = parkRegistration.getPark(idParkOrg);
            parkDest = parkRegistration.getPark(idParkDest);
            client = userRegistration.getClient(username);
            vehicle = vehicleRegistration.getAllScootersOfPark(parkOrg.getLocation()).get(0);
            graph = parkRegistration.getGraphForEnergy(client, vehicle);
        } catch (Exception e) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
        LinkedList<Location> shortPath = new LinkedList<>();
        double energy = GraphAlgorithms.shortestPath(graph, parkOrg.getLocation(), parkDest.getLocation(), shortPath);

        return Math.round(energy * 100.0) / 100.0;
    }
}

package lapr.project.assessment;

import java.math.BigDecimal;
import lapr.project.controller.EnergyUsageController;
import lapr.project.controller.InvoiceController;
import lapr.project.controller.LockVehicleController;
import lapr.project.controller.POIController;
import lapr.project.controller.ParkController;
import lapr.project.controller.PathController;
import lapr.project.controller.UnlockVehicleController;
import lapr.project.controller.UserController;
import lapr.project.controller.VehicleController;
import lapr.project.controller.VehiclesAtGivenParkController;
import lapr.project.data.InitializeDataHandler;
import lapr.project.data.POIRegistration;
import lapr.project.data.ParkRegistration;
import lapr.project.data.PathRegistration;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.data.VehicleRegistration;

public class Facade implements Serviceable {

    @Override
    public int addBicycles(String s) {
        VehicleController controller = new VehicleController(new VehicleRegistration());
        return controller.addBicycle(s);
    }

    @Override
    public int addEscooters(String s) {
        VehicleController controller = new VehicleController(new VehicleRegistration());
        return controller.addScooter(s);
    }

    @Override
    public int addParks(String s) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.addPark(s);
    }

    @Override
    public int removePark(String string) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.removePark(string);
    }

    @Override
    public int addPOIs(String s) {
        POIController controller = new POIController(new POIRegistration());
        return controller.addPOI(s);
    }

    @Override
    public int addUsers(String s) {
        UserController controller = new UserController(new UserRegistration());
        return controller.addClient(s);
    }

    @Override
    public int addPaths(String s) {
        PathController controller = new PathController(new PathRegistration());
        return controller.addPath(s);
    }

    @Override
    public int getNumberOfBicyclesAtPark(double v, double v1, String s) {
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(new VehicleRegistration(), new ParkRegistration());
        return controller.bicyclesAtGivenPark(v, v1, s);
    }

    @Override
    public int getNumberOfBicyclesAtPark(String string, String string1) {
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(new VehicleRegistration(), new ParkRegistration());
        return controller.bicyclesAtGivenPark(string, string1);
    }

    @Override
    public int getNumberOfEscootersAtPark(double v, double v1, String s) {
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(new VehicleRegistration(), new ParkRegistration());
        return controller.scootersAtGivenPark(v, v1, s);
    }

    @Override
    public int getNumberOfEScootersAtPark(String string, String string1) {
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(new VehicleRegistration(), new ParkRegistration());
        return controller.scootersAtGivenPark(string, string1);
    }

    /**
     * Distance is returns in metres, rounded to the unit e.g. (281,58 rounds to
     * 282);
     *
     * @param v Latitude in degrees.
     * @param v1 Longitude in degrees.
     * @param s Filename for output.
     */
    @Override
    public void getNearestParks(double v, double v1, String s) {
        ParkController controller = new ParkController(new ParkRegistration());
        controller.getNearestParks(v, v1, s, 1000);
    }

    @Override
    public void getNearestParks(double d, double d1, String string, int i) {
        ParkController controller = new ParkController(new ParkRegistration());
        controller.getNearestParks(d, d1, string, i);
    }

    @Override
    public int getFreeBicycleSlotsAtPark(String parkIdentification, String username) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.getFreeBicycleSlotsAtPark(parkIdentification, username);
    }

    @Override
    public int getFreeEscooterSlotsAtPark(String parkIdentification, String username) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.getFreeEscooterSlotsAtPark(parkIdentification, username);
    }

    @Override
    public int getFreeSlotsAtParkForMyLoanedVehicle(String username, String parkIdentification) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.getFreeSlotsAtParkForMyLoanedVehicle(username, parkIdentification);
    }

    @Override
    public int linearDistanceTo(double d, double d1, double d2, double d3) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.linearDistanceTo(d, d1, d2, d3);
    }

    @Override
    public int pathDistanceTo(double d, double d1, double d2, double d3) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.pathDistanceTo(d, d1, d2, d3);
    }

    @Override
    public long unlockBicycle(String string, String string1) {
        UnlockVehicleController controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
        return controller.unlockBicycle(string, string1);
    }

    @Override
    public long unlockEscooter(String string, String string1) {
        UnlockVehicleController controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
        return controller.unlockScooter(string, string1);
    }

    @Override
    public long lockBicycle(String string, double d, double d1, String string1) {
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        return controller.lockBicycle(string, d, d1, string1);
    }

    @Override
    public long lockBicycle(String string, String string1, String string2) {
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        return controller.lockBicycle(string, string1, string2);
    }

    @Override
    public long lockEscooter(String string, double d, double d1, String string1) {
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        return controller.lockEscooter(string, d, d1, string1);
    }

    @Override
    public long lockEscooter(String string, String string1, String string2) {
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        return controller.lockEscooter(string, string1, string2);
    }

    @Override
    public int registerUser(String string, String string1, String string2, String string3, int i, int i1, BigDecimal bd, String string4) {
        UserController controller = new UserController(new UserRegistration());
        return controller.registerUser(string, string1, string2, string3, i, i1, string4, bd.floatValue());
    }

    @Override
    public long unlockAnyEscooterAtPark(String string, String string1, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long unlockAnyEscooterAtParkForDestination(String string, String string1, double d, double d1, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int suggestEscootersToGoFromOneParkToAnother(String string, String string1, double d, double d1, String string2) {
        EnergyUsageController controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        return controller.suggestEscootersToGoFromOneParkToAnother(string, string1, d, d1, string2);
    }

    @Override
    public long mostEnergyEfficientRouteBetweenTwoParks(String string, String string1, String string2, String string3, String string4, String string5) {
        EnergyUsageController controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        return controller.mostEnergeticallyEffecientRoute(string, string1, string2, string3, string4, string5);
    }

    @Override
    public double getUserCurrentDebt(String string, String string1) {
        InvoiceController ic = new InvoiceController(new UserRegistration(), new TripRegistration());
        return ic.getUserCurrentDebt(string, string1);
    }

    @Override
    public double getUserCurrentPoints(String string, String string1) {
        InvoiceController ic = new InvoiceController(new UserRegistration(), new TripRegistration());
        return ic.getUserCurrentPoints(string, string1);
    }

    @Override
    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(double d, double d1, double d2, double d3, String string) {
        EnergyUsageController controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        return controller.calculateElectricalEnergyToTravelFromOneLocationToAnother(d, d1, d2, d3, string);
    }

    @Override
    public long forHowLongAVehicleIsUnlocked(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(String string, String string1, String string2, String string3) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.shortestRoutePassingInPOIs(string, string1, string2, string3);
    }

    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(double d, double d1, double d2, double d3, String string, String string1) {
        ParkController controller = new ParkController(new ParkRegistration());
        return controller.shortestRoutePassingInPOIs(d, d1, d2, d3, string, string1);
    }

    @Override
    public long getParkChargingReport(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int suggestRoutesBetweenTwoLocations(String string, String string1, String string2, String string3, String string4, int i, boolean bln, String string5, String string6, String string7) {
        EnergyUsageController controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        return controller.mostEnergeticallyEffecientRoutePassingInPOIs(string, string1, string2, string3, string4, i, bln, string5, string6, string7);
    }

    @Override
    public double getInvoiceForMonth(int i, String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long shortestRouteBetweenTwoParks(double originLatitudeInDegrees, double originLongitudeInDegrees, double destinationLatitudeInDegrees, double destinationLongitudeInDegrees, int numberOfPOIs, String outputFileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long shortestRouteBetweenTwoParks(String originParkIdentification, String destinationParkIdentification, int numberOfPOIs, String outputFileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int projectionOfCalories(String idParkOrg, String username, String idBike, double lat, double lon){
        EnergyUsageController controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        return controller.projectionOfCalories(idParkOrg, username, idBike, lat, lon);
    }
}

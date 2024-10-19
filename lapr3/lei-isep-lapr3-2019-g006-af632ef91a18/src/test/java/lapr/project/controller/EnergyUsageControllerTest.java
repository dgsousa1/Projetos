package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 *
 */
public class EnergyUsageControllerTest {

    EnergyUsageController controller;

    @Test
    public void suggestEscootersToGoFromOneParkToAnotherTestFail() {
        int expResult = 0;
        controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        int result = controller.suggestEscootersToGoFromOneParkToAnother("string", "string1", 1.0, 1.0, "string2");
        assertEquals(expResult, result);
    }

    @Test
    public void suggestEscootersToGoFromOneParkToAnotherTestFailFile() {
        int expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(1.01f, 1.f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        graph.insertEdge(l1, l2, p, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), l1.getElevation(), l2.getElevation()));
        when(parkRegistration.getGraph()).thenReturn(graph);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l1, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l1, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient("idUser")).thenReturn(new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12));
            when(parkRegistration.getPark("idPark")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getParkByCoordsWithoutElevation(1.0, 1.0)).thenReturn("idPark2");
            when(parkRegistration.getPark("idPark2")).thenReturn(new Park("P002", l2, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(vehicleRegistration.getAllScootersOfPark(l1)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.suggestEscootersToGoFromOneParkToAnother("idPark", "idUser", 1.0, 1.0, "");
        assertEquals(expResult, result);
    }

    @Test
    public void suggestEscootersToGoFromOneParkToAnotherTest() {
        int expResult = 2;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(1.01f, 1.f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        graph.insertEdge(l1, l2, p, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), l1.getElevation(), l2.getElevation()));
        when(parkRegistration.getGraph()).thenReturn(graph);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l1, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l1, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient("idUser")).thenReturn(new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12));
            when(parkRegistration.getPark("idPark")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getParkByCoordsWithoutElevation(1.0, 1.0)).thenReturn("idPark2");
            when(parkRegistration.getPark("idPark2")).thenReturn(new Park("P002", l2, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(vehicleRegistration.getAllScootersOfPark(l1)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.suggestEscootersToGoFromOneParkToAnother("idPark", "idUser", 1.0, 1.0, "ScooterEnergyTeste");
        assertEquals(expResult, result);
    }

    @Test
    public void projectionOfCaloriesTest() {
        int expResult = 118;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(1.01f, 1.f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        graph.insertEdge(l1, l2, p, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), l1.getElevation(), l2.getElevation()));
        when(parkRegistration.getGraph()).thenReturn(graph);
        try {
            when(userRegistration.getClient("idUser")).thenReturn(new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12));
            when(parkRegistration.getPark("idPark")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getParkByCoordsWithoutElevation(1.0, 1.0)).thenReturn("idPark2");
            when(parkRegistration.getPark("idPark2")).thenReturn(new Park("P002", l2, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(vehicleRegistration.getBicycle("B001")).thenReturn(new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f));
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.projectionOfCalories("idPark", "idUser", "B001", 1.0, 1.0);
        assertEquals(expResult, result);
    }

    @Test
    public void projectionOfCaloriestestFail() {
        int expResult = 0;
        controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        int result = controller.projectionOfCalories("idPark", "idUser", "B001", 1.0, 1.0);
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRouteFails() {
        long expResult = 0;
        controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        long result = controller.mostEnergeticallyEffecientRoute("P001", "P002", "scooter", "city", "username", "MostEnergeticallyEffecientRouteTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoutFileFail() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p, Calculator.calculateEnergyForPath(client, p, vehicle));
        try {
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l2, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getBicycleFromSpecs("11")).thenReturn((Bicycle) vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, vehicle)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        long result = controller.mostEnergeticallyEffecientRoute("P001", "P002", "bicycle", "11", "username", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoute() {
        long expResult = 157225;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p, Calculator.calculateEnergyForPath(client, p, vehicle));
        try {
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l2, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getBicycleFromSpecs("11")).thenReturn((Bicycle) vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, vehicle)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        long result = controller.mostEnergeticallyEffecientRoute("P001", "P002", "bicycle", "11", "username", "MostEnergeticallyEffecientRouteTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoutePassingInPOIsFail() {
        int expResult = 0;
        controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        int result = controller.mostEnergeticallyEffecientRoutePassingInPOIs("P001", "P002", "scooter", "city", "username", 0, true, "energy", "inputPOI", "MostEnergeticallyEffecientRouteTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoutePassingInPOIsFileFail() {
        int expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        List<POI> pois = new ArrayList<>();
        POI poi1 = new POI("poi1", l2);
        POI poi2 = new POI("poi1", l3);
        pois.add(poi1);
        pois.add(poi2);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getBicycleFromSpecs("11")).thenReturn((Bicycle) vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, vehicle)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.mostEnergeticallyEffecientRoutePassingInPOIs("P001", "P002", "bicycle", "11", "username", 0, true, "energy", "inputPOI", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoutePassingInPOIs() {
        int expResult = 291419;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        List<POI> pois = new ArrayList<>();
        POI poi1 = new POI("poi1", l2);
        POI poi2 = new POI("poi1", l3);
        pois.add(poi1);
        pois.add(poi2);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getBicycleFromSpecs("11")).thenReturn((Bicycle) vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, vehicle)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.mostEnergeticallyEffecientRoutePassingInPOIs("P001", "P002", "bicycle", "11", "username", 0, true, "energy", "inputPOI", "MostEnergeticallyEffecientRouteTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testMostEnergeticallyEffecientRoutePassingInPOIsScooter() {
        int expResult = 291419;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Scooter("city", 1, 10.0f, "S001", 30, l1, 1.1f, 0.3f, 1f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        try {
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getScooterFromSpecs("city")).thenReturn((Scooter) vehicle);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, vehicle)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        int result = controller.mostEnergeticallyEffecientRoutePassingInPOIs("P001", "P002", "escooter", "city", "username", 0, true, "energy", "inputPOI", "MostEnergeticallyEffecientRouteTest");
        assertEquals(expResult, result);
    }

    @Test
    public void getParkChargingReportTest1() {
        long expResult = 4;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(park);
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenReturn(scooters);
            for (Scooter s : scooters) {
                when(vehicleRegistration.getScooter(s.getIdVehicle())).thenReturn(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), "ChargingTimeTest"));
    }

    @Test
    public void getParkChargingReportTest2() {
        long expResult = 3;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 100, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(park);
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenReturn(scooters);
            for (Scooter s : scooters) {
                when(vehicleRegistration.getScooter(s.getIdVehicle())).thenReturn(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), "ChargingTimeTest"));
    }

    //FAILS TESTS CHARGING PARK REPORT
    @Test
    public void getParkChargingReportTestFileFailPrinter() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(park);
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenReturn(scooters);
            when(vehicleRegistration.getScooter("ID-001")).thenReturn(s1);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), ""));
    }

    @Test
    public void getParkChargingReportTestFailPark() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        try {
            when(parkRegistration.getPark("P001")).thenThrow(new SQLException());
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenReturn(scooters);
            when(vehicleRegistration.getScooter("ID-001")).thenReturn(s1);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), "ChargingTimeTest"));
    }

    @Test
    public void getParkChargingReportTestFailAllScooters() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(park);
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenThrow(new SQLException());
            when(vehicleRegistration.getScooter("ID-001")).thenReturn(s1);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), "ChargingTimeTest"));
    }

    @Test
    public void getParkChargingReportTestFailScooter() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Location l1 = new Location(30f, 30f, 0);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 220f, 13.63f);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        try {
            when(parkRegistration.getPark("P001")).thenReturn(park);
            when(vehicleRegistration.getAllScootersOfPark(park.getLocation())).thenReturn(scooters);
            when(vehicleRegistration.getScooter("ID-001")).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        assertEquals(expResult, controller.getParkChargingReport(park.getIdPark(), "ChargingTimeTest"));
    }

    @Test
    public void calculateElectricalEnergyToTravelFromOneLocationToAnotherFail() {
        double expResult = 0;
        controller = new EnergyUsageController(new VehicleRegistration(), new ParkRegistration(), new UserRegistration());
        double result = controller.calculateElectricalEnergyToTravelFromOneLocationToAnother(0, 0, 0, 0, "username");
        assertEquals(expResult, result);
    }

    @Test
    public void calculateElectricalEnergyToTravelFromOneLocationToAnother() {
        double expResult = 178.34;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 70, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, s1));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, s1));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, s1));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, s1));
        try {
            when(parkRegistration.getParkByCoordsWithoutElevation(l1.getLatitude(), l1.getLongitude())).thenReturn("P001");
            when(parkRegistration.getParkByCoordsWithoutElevation(l4.getLatitude(), l4.getLongitude())).thenReturn("P002");
            when(parkRegistration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(parkRegistration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(userRegistration.getClient("username")).thenReturn(client);
            when(vehicleRegistration.getAllScootersOfPark(l1)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(parkRegistration.getGraphForEnergy(client, s1)).thenReturn(graph);
        controller = new EnergyUsageController(vehicleRegistration, parkRegistration, userRegistration);
        double result = controller.calculateElectricalEnergyToTravelFromOneLocationToAnother(l1.getLatitude(), l1.getLongitude(), l4.getLatitude(), l4.getLongitude(), "username");
        assertEquals(expResult, result);
    }

}

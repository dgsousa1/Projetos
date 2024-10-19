package lapr.project.utils;

//import static lapr.project.utils.Calculator.degreesToRadians;
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Path;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 */
public class CalculatorTest {

    /**
     * Test of calcularDistancia method, of class Calculator.
     */
    @Test
    public void testCalcularDistancia() {
        //Porto
        double lat1 = 53.478612;
        double lon1 = 6.250578;
        //Porto
        double lat2 = 50.752342;
        double lon2 = 5.916981;
        Double expResult = 304001.0210460888;
        Double result = Calculator.calcularDistancia(lat1, lon1, lat2, lon2);
        assertEquals(expResult, result);
    }

    @Test
    public void testCalcularDistancia2() {
        Double expResult = 0.0;
        Double result = Calculator.calcularDistancia(1, 1, 1, 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testCalcularDistancia3() {
        Double expResult = 314498.76254388795;
        Double result = Calculator.calcularDistancia(-1, -1, 1, 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testCalcularDistancia4() {
        Double expResult = 314498.76254388795;
        Double result = Calculator.calcularDistancia(1, 1, -1, -1);
        assertEquals(expResult, result);
    }

    @Test
    public void calcularDistanciaWithElevationTest() {
        Double expResult = 314498.76254388795;
        Double result = Calculator.calcularDistanciaWithElevation(1, 1, -1, -1, 0, 0);
        assertEquals(expResult, result);
    }

    @Test
    public void calcularDistanciaWithElevationTest2() {
        Double expResult = 314498.7625454778;
        Double result = Calculator.calcularDistanciaWithElevation(1, 1, -1, -1, 1, 2);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathFaillDistance() {
        double expResult = 0;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0), new Location(40.0, 40.0), 2.0f, 4.0f, 10.0f);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathFaillWind() {
        double expResult = 246.95748065188025;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0), new Location(45.0, 40.0), 2.0f, 0f, 0f);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathFaill() {
        double expResult = 0;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0), new Location(40.0, 40.0), 2.0f, 0f, 0f);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathBike() {
        double expResult = 123;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0, 2), new Location(42.0, 42.0, 3), 2.0f, 4.0f, 1.0f);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike).intValue();
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathScooter() {
        double expResult = 123;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0, 2), new Location(42.0, 42.0, 3), 2.0f, 4.0f, 1.0f);
        Scooter scooter = new Scooter("off-road", 1, 1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, scooter).intValue();
        assertEquals(expResult, result);
    }

    @Test
    public void calculateBatteryForGivenDistance() {
        double expResult = 0.5;
        double maxBattery = 30;
        double actualBattery = 75;
        double power = 45;
        double result = Calculator.calculateBatteryForGivenDistance(maxBattery, power, actualBattery);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathClientNull() {
        double expResult = 0;
        Client client = null;
        Path path = new Path(new Location(40.0, 40.0), new Location(40.0, 40.0), 2.0f, 0f, 0f);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(client, null, null);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(client, path, null);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(client, null, bike);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForPathPathNull() {
        double expResult = 0;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = null;
        Bicycle bike = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(null, path, null);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(null, path, bike);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(null, path, null);
        assertEquals(expResult, result);
    }

    @Test
    public void calculateEnergyForVehicleNull() {
        double expResult = 0;
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        Path path = new Path(new Location(40.0, 40.0), new Location(40.0, 40.0), 2.0f, 0f, 0f);
        Bicycle bike = null;
        double result = Calculator.calculateEnergyForPath(client, path, bike);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(null, path, null);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(client, null, null);
        assertEquals(expResult, result);
        result = Calculator.calculateEnergyForPath(null, null, null);
        assertEquals(expResult, result);
    }

    @Test
    public void calculatePathDistanceTest() {
        int expResult = 291419;
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        List<Location> paths = new ArrayList<>();
        paths.add(l1);
        paths.add(l2);
        paths.add(l3);
        paths.add(l4);
        double result = Calculator.calculatePathDistance(paths);
        assertEquals(expResult, Math.round(result));
    }

    @Test
    public void calculatePathEnergyTest() {
        long expResult = 36;
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
        List<Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);
        double result = Calculator.calculatePathEnergy(locations, graph, client, vehicle);
        assertEquals(expResult, Math.round(result));
    }
}

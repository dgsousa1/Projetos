package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Location;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class VehiclesAtGivenParkControllerTest {

    VehiclesAtGivenParkController controller;

    /**
     * Test of scootersAtGivenPark method, of class
     * VehiclesAtGivenParkController.
     */
    @Test
    public void testScootersAtGivenPark_double_double() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "ScootersAtGivenParkTest";
        Location l = new Location(latitude, longitude);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(vehicleRegistration.getAllScootersOfPark(l)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(scooters.size(), controller.scootersAtGivenPark(latitude, longitude, outputFileName));
    }

    @Test
    public void testScootersAtGivenPark_double_double_EmptyFile() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "";
        Location l = new Location(latitude, longitude);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(vehicleRegistration.getAllScootersOfPark(l)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(0, controller.scootersAtGivenPark(latitude, longitude, outputFileName));
    }

    /**
     * Test of scootersAtGivenPark method, of class
     * VehiclesAtGivenParkController.
     */
    @Test
    public void testScootersAtGivenPark_String() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "ScootersAtGivenParkTest";
        String parkIdentification = "P001";
        Location l = new Location(latitude, longitude);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenReturn(new Park(parkIdentification, l, "Parque Test", 10, 10, 200f, 200f));
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            when(vehicleRegistration.getAllScootersOfPark(l)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(scooters.size(), controller.scootersAtGivenPark(parkIdentification, outputFileName));
    }

    @Test
    public void testScootersAtGivenPark_String_EmptyFile() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "";
        String parkIdentification = "P001";
        Location l = new Location(latitude, longitude);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenReturn(new Park(parkIdentification, l, "Parque Test", 10, 10, 200f, 200f));
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            when(vehicleRegistration.getAllScootersOfPark(l)).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(0, controller.scootersAtGivenPark(parkIdentification, outputFileName));
    }

    @Test
    public void testScootersAtGivenPark_String_fails() throws SQLException {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String parkIdentification = "P001";
        String outputFileName = "ScootersAtGivenParkTest";
        Location l = new Location(latitude, longitude);
        List<Scooter> empty = new ArrayList<>();
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(vehicleRegistration.getAllScootersOfPark(l)).thenReturn(scooters);
        assertEquals(empty.size(), controller.scootersAtGivenPark(parkIdentification, outputFileName));
    }

    /**
     * Test of bicyclesAtGivenPark method, of class
     * VehiclesAtGivenParkController.
     */
    @Test
    public void testBicyclesAtGivenPark_double_double() {

        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "BicyclesAtGivenParkTest";

        Location l = new Location(latitude, longitude);
        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(new Bicycle(16, "B001", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(14, "B002", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(15, "B003", 5, l, 1f, 0.5f));

        when(vehicleRegistration.getAllBicyclesOfPark(l)).thenReturn(bikes);
        assertEquals(bikes.size(), controller.bicyclesAtGivenPark(latitude, longitude, outputFileName));

    }

    @Test
    public void testBicyclesAtGivenPark_double_double_EmptyFile() {

        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "";

        Location l = new Location(latitude, longitude);
        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(new Bicycle(16, "B001", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(14, "B002", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(15, "B003", 5, l, 1f, 0.5f));

        when(vehicleRegistration.getAllBicyclesOfPark(l)).thenReturn(bikes);
        assertEquals(0, controller.bicyclesAtGivenPark(latitude, longitude, outputFileName));

    }

    /**
     * Test of bicyclesAtGivenPark method, of class
     * VehiclesAtGivenParkController.
     */
    @Test
    public void testBicyclesAtGivenPark_String() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String parkIdentification = "P001";
        Location l = new Location(latitude, longitude);
        List<Bicycle> bikes = new ArrayList<>();
        String outputFileName = "BicyclesAtGivenParkTest";

        bikes.add(new Bicycle(16, "B001", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(14, "B002", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(15, "B003", 5, l, 1f, 0.5f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenReturn(new Park(parkIdentification, l, "Parque Test", 10, 10, 200f, 200f));
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(vehicleRegistration.getAllBicyclesOfPark(l)).thenReturn(bikes);
        assertEquals(bikes.size(), controller.bicyclesAtGivenPark(parkIdentification, outputFileName));
    }

    @Test
    public void testBicyclesAtGivenPark_String_EmptyFile() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String parkIdentification = "P001";
        Location l = new Location(latitude, longitude);
        List<Bicycle> bikes = new ArrayList<>();
        String outputFileName = "";

        bikes.add(new Bicycle(16, "B001", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(14, "B002", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(15, "B003", 5, l, 1f, 0.5f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenReturn(new Park(parkIdentification, l, "Parque Test", 10, 10, 200f, 200f));
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(vehicleRegistration.getAllBicyclesOfPark(l)).thenReturn(bikes);
        assertEquals(0, controller.bicyclesAtGivenPark(parkIdentification, outputFileName));
    }

    @Test
    public void testBicyclesAtGivenPark_String_fails() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String parkIdentification = "P001";
        String outputFileName = "BicyclesAtGivenParkTest";

        Location l = new Location(latitude, longitude);
        List<Bicycle> empty = new ArrayList<>();
        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(new Bicycle(16, "B001", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(14, "B002", 5, l, 1f, 0.5f));
        bikes.add(new Bicycle(15, "B003", 5, l, 1f, 0.5f));
        try {
            when(parkRegistration.getPark(parkIdentification)).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        when(vehicleRegistration.getAllBicyclesOfPark(l)).thenReturn(bikes);
        assertEquals(empty.size(), controller.bicyclesAtGivenPark(parkIdentification, outputFileName));
    }

    @Test
    public void testScootersAtGivenPark_double_double_fails() {
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        VehiclesAtGivenParkController controller = new VehiclesAtGivenParkController(vehicleRegistration, parkRegistration);
        double latitude = 43.7;
        double longitude = 49.5;
        String outputFileName = "BicyclesAtGivenParkTest";

        Location l = new Location(latitude, longitude);
        List<Scooter> scooters = new ArrayList<>();
        List<Scooter> empty = new ArrayList<>();
        scooters.add(new Scooter("city", 1, 10.0f, "S001", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 100f, "S002", 30, l, 1.1f, 0.3f, 1f));
        scooters.add(new Scooter("city", 1, 16.74793827137404f, "S003", 30, l, 1.1f, 0.3f, 1f));
        try {
            when(vehicleRegistration.getAllScootersOfPark(l)).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(VehiclesAtGivenParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(empty.size(), controller.scootersAtGivenPark(latitude, longitude, outputFileName));
    }

}

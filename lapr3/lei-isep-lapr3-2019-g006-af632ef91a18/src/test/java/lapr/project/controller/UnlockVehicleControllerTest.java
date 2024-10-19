package lapr.project.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnlockVehicleControllerTest {

    UnlockVehicleController controller;

    @Test
    public void testUnlockBicycle() {
        long expResult = 10000;
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        when(userRegistration.unlockVehicleParkID("B001")).thenReturn(10000l);
        when(vehicleRegistration.getBicycle("B001")).thenReturn(new Bicycle(1, "B001", 10, new Location(1, 1, 1), 1.1f, 0.3f));
        try {
            when(userRegistration.getClient("C001")).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockBicycle("B001", "C001");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testUnlockBicycleFail() {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        when(userRegistration.unlockVehicleParkID("B001")).thenReturn(10000l);
        when(vehicleRegistration.getBicycle("B001")).thenReturn(new Bicycle(1, "B001", 10, new Location(1, 1, 1), 1.1f, 0.3f));
        try {
            when(userRegistration.getClient("C001")).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(new TripRegistration(), vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockBicycle("B001", "C001");
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockBicycleFails() {
        String bicycleDescription = "B001";
        String username = "idUser";
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockBicycle(bicycleDescription, username);
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockScooter() throws SQLException {
        long expResult = 10000;
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        when(userRegistration.unlockVehicleParkID("B001")).thenReturn(10000l);
        when(vehicleRegistration.getScooter("B001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, new Location(1, 1, 1), 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient("C001")).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockScooter("B001", "C001");
        assertEquals(expResult, result);
    }
    
        @Test
    public void testUnlockScooterFail() throws SQLException {
        long expResult = 0;
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        when(userRegistration.unlockVehicleParkID("B001")).thenReturn(10000l);
        when(vehicleRegistration.getScooter("B001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, new Location(1, 1, 1), 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient("C001")).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(new TripRegistration(), vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockScooter("B001", "C001");
        assertEquals(expResult, result);
    }


    @Test
    public void testUnlockScooterFails() {
        String escooterDescription = "S001";
        String username = "idUser";
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockScooter(escooterDescription, username);
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockAnyEscooterAtPark() throws SQLException {
        String parkID = "P001";
        String username = "idUser";
        long expResult = System.currentTimeMillis();
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        Location l1 = new Location(1, 1, 1);
        Park park = new Park("P001", new Location(1, 1, 1), "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        when(userRegistration.unlockAnyEscootereAtPark(parkID)).thenReturn("S001");
        when(vehicleRegistration.getScooter("S001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient(username)).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
            when(parkRegistration.getPark(parkID)).thenReturn(park);
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = expResult - controller.unlockAnyEscooterAtPark(parkID, username, "UnluckEscooterTest");
        assertTrue(result < 100);
    }
    
       @Test
    public void testUnlockAnyEscooterAtParkFail() throws SQLException {
        String parkID = "P001";
        String username = "idUser";
        long expResult = 0;
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        Location l1 = new Location(1, 1, 1);
        Park park = new Park("P001", new Location(1, 1, 1), "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        when(userRegistration.unlockAnyEscootereAtPark(parkID)).thenReturn("S001");
        when(vehicleRegistration.getScooter("S001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient(username)).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
            when(parkRegistration.getPark(parkID)).thenReturn(park);
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockAnyEscooterAtPark(parkID, username, "");
        assertEquals(expResult,result);
    }

    @Test
    public void testUnlockAnyEscooterAtParkFails() {
        String parkID = "P001";
        String username = "idUser";
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockAnyEscooterAtPark(parkID, username, "UnluckEscooterTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockAnyEscooterAtParkFileFails() {
        String parkID = "P001";
        String username = "idUser";
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockAnyEscooterAtPark(parkID, username, "");
        assertEquals(expResult, result);
    }
    
        @Test
    public void testUnlockAnyEscootereAtParkForDestination() throws SQLException {
        String parkID = "P001";
        String username = "idUser";
        double latitude = 80;
        double longitude = 70;
        long expResult = System.currentTimeMillis();
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        Location l1 = new Location(1, 1, 1);
        Park park = new Park("P001", new Location(1, 1, 1), "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        when(userRegistration.unlockAnyEscootereAtPark(parkID)).thenReturn("S001");
        when(vehicleRegistration.getScooter("S001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient(username)).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
            when(parkRegistration.getPark(parkID)).thenReturn(park);
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = expResult - controller.unlockAnyEscootereAtParkForDestination(parkID, username, latitude, longitude, "UnluckEscooterTest");
        assertTrue(result < 100);
    }
    
            @Test
    public void testUnlockAnyEscootereAtParkForDestinationFail() throws SQLException {
        String parkID = "P001";
        String username = "idUser";
        double latitude = 80;
        double longitude = 70;
        long expResult = 0;
        TripRegistration tripRegistration = mock(TripRegistration.class);
        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
        UserRegistration userRegistration = mock(UserRegistration.class);
        ParkRegistration parkRegistration = mock(ParkRegistration.class);
        Location l1 = new Location(1, 1, 1);
        Park park = new Park("P001", new Location(1, 1, 1), "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        when(userRegistration.unlockAnyEscootereAtPark(parkID)).thenReturn("S001");
        when(vehicleRegistration.getScooter("S001")).thenReturn(new Scooter("city", 1, 0.1674793827137404f, "S003", 30, l1, 1.1f, 0.3f, 1f));
        try {
            when(userRegistration.getClient(username)).thenReturn(new Client("C001", "pass", "email", "0001", 180, 70, "cão", 12.0f));
            when(parkRegistration.getPark(parkID)).thenReturn(park);
        } catch (SQLException ex) {
            Logger.getLogger(UnlockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new UnlockVehicleController(tripRegistration, vehicleRegistration, userRegistration, parkRegistration);
        long result = controller.unlockAnyEscootereAtParkForDestination(parkID, username, latitude, longitude, "");
        assertEquals(expResult,result);
    }

    @Test
    public void testUnlockAnyEscootereAtParkForDestinationFails() {
        String parkID = "P001";
        String username = "idUser";
        double latitude = 80;
        double longitude = 70;
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockAnyEscootereAtParkForDestination(parkID, username, latitude, longitude, "UnluckEscooterTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockAnyEscootereAtParkForDestinationFileFails() {
        String parkID = "P001";
        String username = "idUser";
        double latitude = 80;
        double longitude = 70;
        long expResult = 0;
        controller = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(),
                new UserRegistration(), new ParkRegistration());
        long result = controller.unlockAnyEscootereAtParkForDestination(parkID, username, latitude, longitude, "");
        assertEquals(expResult, result);
    }

    @Test
    public void testUnlockBicycleEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockBicycle("", "");
        });
    }

    @Test
    public void testUnlockBicycleEmptyString1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockBicycle("B001", "");
        });
    }

    @Test
    public void testUnlockBicycleEmptyString2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockBicycle("", "dummy");
        });
    }

    @Test
    public void testUnlockScooterEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockScooter("", "");
        });
    }

    @Test
    public void testUnlockScooterEmptyString1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockScooter("S001", "");
        });
    }

    @Test
    public void testUnlockScooterEmptyString2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            UnlockVehicleController controler = new UnlockVehicleController(new TripRegistration(), new VehicleRegistration(), new UserRegistration(), new ParkRegistration());
            controler.unlockScooter("", "dummy");
        });
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

//import static org.mockito.Mockito.*;
/**
 *
 * @author Nuno
 */
public class TripRequestTest {

    TripRequest instance;

    @BeforeEach
    public void before() {
        // locMock = mock(Location.class);
        Location locationBike = new Location(1, 1, 1);
        Bicycle bike = new Bicycle(1, "idVehicle", 1, locationBike, 1, 1);
        Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);

        instance = new TripRequest(bike, new Location(50.0, 50.0), client, LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 30));
        instance.setDestination(new Location(25.0, 25.0, 0));
        instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
    }

    /**
     * Test of getVehicle method, of class TripRequest.
     */
    @Test
    public void testGetVehicle() {
        assertNotNull(instance.getVehicle());
    }

    /**
     * Test of getOrigin method, of class TripRequest.
     */
    @Test
    public void testGetOrigin() {
        assertNotNull(instance.getOrigin());
    }

    /**
     * Test of getDestination method, of class TripRequest.
     */
    @Test
    public void testGetDestination() {
        assertNotNull(instance.getDestination());
    }

    /**
     * Test of getClient method, of class TripRequest.
     */
    @Test
    public void testGetClient() {
        assertNotNull(instance.getClient());
    }

    /**
     * Test of getInitialDate method, of class TripRequest.
     */
    @Test
    public void testGetInitialDate() {
        assertNotNull(instance.getInitialDate());
    }

    /**
     * Test of getFinalDate method, of class TripRequest.
     */
    @Test
    public void testGetFinalDate() {
        assertNotNull(instance.getFinalDate());
    }

    /**
     * Test of setDestination method, of class TripRequest.
     */
    @Test
    public void testSetDestination() {
        instance.setDestination(new Location(25.0, 25.0, 0));
        assertNotNull(instance.getDestination());
    }

    @Test
    public void testSetDestinationFails() {
        System.out.println("setDestination");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            instance.setDestination(null);
        });
    }

    /**
     * Test of setFinalDate method, of class TripRequest.
     */
    @Test
    public void testSetFinalDate() {
        instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
        assertNotNull(instance.getFinalDate());
    }

    @Test
    public void testSetFinalDateFails() {
        System.out.println("setFinalDate");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            instance.setFinalDate(null);
        });
    }

    @Test
    public void testInvalidVehicle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
            instance = new TripRequest(null, new Location(50.0, 50.0), client, LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 30));
            instance.setDestination(new Location(25.0, 25.0, 0));
            instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
        });
    }

    @Test
    public void testInvalidPark() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location locationBike = new Location(1, 1, 1);
            Bicycle bike = new Bicycle(1, "idVehicle", 1, locationBike, 1, 1);
            Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
            instance = new TripRequest(bike, null, client, LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 30));
            instance.setDestination(new Location(25.0, 25.0, 0));
            instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
        });
    }

    @Test
    public void testInvalidClient() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location locationBike = new Location(1, 1, 1);
            Bicycle bike = new Bicycle(1, "idVehicle", 1, locationBike, 1, 1);
            Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
            instance = new TripRequest(bike, new Location(50.0, 50.0), null, LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 30));
            instance.setDestination(new Location(25.0, 25.0, 0));
            instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
        });
    }

    @Test
    public void testInvalidInitialDate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location locationBike = new Location(1, 1, 1);
            Bicycle bike = new Bicycle(1, "idVehicle", 1, locationBike, 1, 1);
            Client client = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
            instance = new TripRequest(null, new Location(50.0, 50.0), client, LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 30));
            instance.setDestination(new Location(25.0, 25.0, 0));
            instance.setFinalDate(LocalDateTime.of(2019, Month.DECEMBER, 25, 15, 45));
        });
    }
}

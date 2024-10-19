/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Rafael Crista
 */
public class TripReportTest {

    TripReport instance;

    @BeforeEach
    public void before() {
        instance = new TripReport("S001", "user1", new Location(25.0, 25.0, 5), new Location(42.0, 42.0, 0),
                "25/08/2019 14:30", "25/08/2019 15:10");
    }

    /**
     * Test of getIdVehicle method, of class TripReport.
     */
    @Test
    public void testGetIdVehicle() {
        System.out.println("getIdVehicle");
        String expResult = "S001";
        String result = instance.getIdVehicle();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdClient method, of class TripReport.
     */
    @Test
    public void testGetIdClient() {
        System.out.println("getIdClient");
        String expResult = "user1";
        String result = instance.getIdClient();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrigin method, of class TripReport.
     */
    @Test
    public void testGetOrigin() {
        System.out.println("getOrigin");
        assertNotNull(instance.getOrigin());
    }

    /**
     * Test of getDestination method, of class TripReport.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");
        assertNotNull(instance.getDestination());
    }

    /**
     * Test of getInitialDate method, of class TripReport.
     */
    @Test
    public void testGetInitialDate() {
        System.out.println("getInitialDate");
        String expResult = "25/08/2019 14:30";
        String result = instance.getInitialDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFinalDate method, of class TripReport.
     */
    @Test
    public void testGetFinalDate() {
        System.out.println("getFinalDate");
        String expResult = "25/08/2019 15:10";
        String result = instance.getFinalDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDestination method, of class TripReport.
     */
    @Test
    public void testSetDestination() {
        System.out.println("setDestination");
        instance.setDestination(new Location(15.0, 15.0, 3));
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
     * Test of setFinalDate method, of class TripReport.
     */
    @Test
    public void testSetFinalDate() {
        System.out.println("setFinalDate");
        instance.setFinalDate("15/09/2019 16:15");
        assertNotNull(instance.getFinalDate());
    }

    @Test
    public void testSetFinalDateFails() {
        System.out.println("setFinalDate");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            instance.setFinalDate(null);
        });
    }

}

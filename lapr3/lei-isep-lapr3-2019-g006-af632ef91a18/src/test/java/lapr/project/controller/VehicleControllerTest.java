package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.VehicleRegistration;
import lapr.project.model.Location;
import lapr.project.model.Scooter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import org.junit.jupiter.api.BeforeEach;

/**
 *
 *
 */
public class VehicleControllerTest {

    VehicleController controller;

    /**
     * Test of addBicycle method, of class VehicleController.
     */
    @Test
    public void testAddBicycleFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.addBicycle("BicycleTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testAddBicycleNoFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.addBicycle("fail");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAddBicicyleEmptyFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            int result = controller.addBicycle("EmptyTestFail");
            assertEquals(expResult, result);
        });
    }

    @Test
    public void testAddBicycle() {
        int expResult = 2;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.addBicycle("BicycleTest");
        assertEquals(expResult, result);
    }

    /**
     * Test of addScooter method, of class VehicleController.
     */
    @Test
    public void testAddScooterFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.addScooter("ScooterTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testAddScooter() {
        int expResult = 2;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.addScooter("ScooterTest");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAddScooterEmptyFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            int result = controller.addScooter("EmptyTestFail");
            assertEquals(expResult, result);
        });
    }

    @Test
    public void testAddScooterNoFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.addScooter("fail");
        assertEquals(expResult, result);
    }

    /**
     * Test of removeBicycle method, of class VehicleController.
     */
    @Test
    public void testRemoveBicycleFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.removeBicycle("B004");
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveBicycle() {
        int expResult = 1;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.removeBicycle("B001");
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveBicycleNoFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.removeBicycle("fail");
        assertEquals(expResult, result);
    }

    /**
     * Test of removeScooter method, of class VehicleController.
     */
    @Test
    public void testRemoveScooterFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.removeScooter("ScooterTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveScooter() {
        int expResult = 1;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.removeScooter("ScooterTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveScooterNoFile() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.removeScooter("fail");
        assertEquals(expResult, result);
    }

    /**
     * Test of updateBicycle method, of class VehicleController.
     */
    @Test
    public void testUpdateBicycleFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.updateBicycle("B001;5;40.0;40.0;2;1;15");
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateBicycle() {
        int expResult = 1;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.updateBicycle("B001;5;40.0;40.0;2;1;15");
        assertEquals(expResult, result);
    }

    /**
     * Test of updateBicycle method, of class VehicleController.
     */
    @Test
    public void testUpdateScooterFail() {
        int expResult = 0;
        controller = new VehicleController(new VehicleRegistration());
        int result = controller.updateScooter("S001;5;city;30.0;30.0;10;5;2;1;25.1");
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateScooter() {
        int expResult = 1;
        VehicleRegistration registration = mock(VehicleRegistration.class);
        controller = new VehicleController(registration);
        int result = controller.updateScooter("S001;5;city;30.0;30.0;10;5;2;1;25.1");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testgetReportAboutEletricCapacityEqualPowers() {
        VehicleRegistration registration = mock(VehicleRegistration.class);        
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
            when(registration.getAllScooters()).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        controller = new VehicleController(registration);
        List<Scooter> list = controller.getReportAboutEletricCapacity(4);
        assertEquals(2,controller.getReportAboutEletricCapacity(4).size());
    }
    
    @Test
    public void testgetReportAboutEletricCapacityEqualTimes() {
        VehicleRegistration registration = mock(VehicleRegistration.class);        
        Scooter s1 = new Scooter("off-road", 500.0f, 50, "ID-001", 150, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s2 = new Scooter("city", 500.0f, 50, "ID-002", 200, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s3 = new Scooter("city", 1000.0f, 40, "ID-003", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);
        Scooter s4 = new Scooter("city", 1000.0f, 90, "ID-004", 80, new Location(1, 1, 1), 10.5f, 15.5f, 1000.0f);        
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s1);
        scooters.add(s2);
        scooters.add(s3);
        scooters.add(s4);              
        try {
            when(registration.getAllScooters()).thenReturn(scooters);
        } catch (SQLException ex) {
            Logger.getLogger(EnergyUsageControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        controller = new VehicleController(registration);
        List<Scooter> list = controller.getReportAboutEletricCapacity(4);
        assertEquals(2,controller.getReportAboutEletricCapacity(4).size());
    }

    @Test
    public void testgetReportAboutEletricCapacityFail() {
        controller = new VehicleController(new VehicleRegistration());
        assertEquals(new ArrayList<>(), controller.getReportAboutEletricCapacity(4));
    }
}

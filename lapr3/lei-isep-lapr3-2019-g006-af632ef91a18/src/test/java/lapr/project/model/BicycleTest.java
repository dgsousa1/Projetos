package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.mockito.Mockito.*;

/**
 *
 * @author Nuno
 */
public class BicycleTest {

    Bicycle instance;

    @BeforeEach
    public void before() {
       // locMock = mock(Location.class);
        instance = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
    }
    
    /**
     * Test of getWheelSize method, of class Bicycle.
     */
    @Test
    public void testGetWheelSize() {
        int expectedResult = 1;
        assertEquals(expectedResult, instance.getWheelSize());
    }
    
    @Test
    public void testGetIdVehicle() {
        String expectedResult = "idVehicle";
        assertEquals(expectedResult, instance.getIdVehicle());
    }
    
    @Test
    public void testGetWeight() {
        int expectedResult = 1;
        assertEquals(expectedResult, instance.getWeight());
    }
    
    @Test
    public void testGetLocation() {
        assertNotNull(instance.getLocation());
    }
    
    @Test
    public void testGetAerodynamicCoef() {
        float expectedResult = 1;
        assertEquals(expectedResult, instance.getAerodynamicCoef());
    }
    
    @Test
    public void testGetFrontalArea() {
        float expectedResult = 1;
        assertEquals(expectedResult, instance.getAerodynamicCoef());
    }

    @Test
    public void testInvalidWheelSize() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(0, "idVehicle", 1, new Location(1, 1, 1), 1, 1);
        });
    }
    
    @Test
    public void testInvalidIdVehicle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(1, "", 1, new Location(1, 1, 1), 1, 1);
        });
    }
    
    @Test
    public void testInvalidWeight() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(1, "idVehicle", 0, new Location(1, 1, 1), 1, 1);
        });
    }
    
    @Test
    public void testInvalidLocation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(1, "idVehicle", 1, null, 1, 1);
        });
    }
    
    @Test
    public void testInvalidAerodynamicCoef() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 0, 1);
        });
    }
    
    @Test
    public void testInvalidFrontalArea() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Bicycle b = new Bicycle(1, "idVehicle", 1, new Location(1, 1, 1), 1, 0);
        });
    }
}

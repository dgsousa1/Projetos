package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.mockito.Mockito.*;

/**
 *
 * @author Nuno
 */
public class ScooterTest {

    Scooter instance;

    @BeforeEach
    public void before() {
        // locMock = mock(Location.class);
        instance = new Scooter("off-road", 1, 1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
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
        assertEquals(expectedResult, instance.getFrontalArea());
    }

    /**
     * Test of getType method, of class Scooter.
     */
    @Test
    public void testGetType() {
        String expectedResult = "off-road";
        assertEquals(expectedResult, instance.getType());
    }

    /**
     * Test of getMaxBatteryCapacity method, of class Scooter.
     */
    @Test
    public void testGetMaxBatteryCapacity() {
        float expectedResult = 1;
        assertEquals(expectedResult, instance.getMaxBatteryCapacity());
    }

    /**
     * Test of getActualBatteryCapacity method, of class Scooter.
     */
    @Test
    public void testGetActualBatteryCapacity() {
        float expectedResult = 1;
        assertEquals(expectedResult, instance.getActualBatteryCapacity());
    }

    /**
     * Test of getActualBatteryCapacity method, of class Scooter.
     */
    @Test
    public void testGetPower() {
        float expectedResult = 1;
        assertEquals(expectedResult, instance.getPower());
    }

    @Test
    public void testInvalidType() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("test", 1, 1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
        });
    }

    @Test
    public void testInvalidMaxBatteryCapacity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("city", 0, 1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
        });
    }

    @Test
    public void testInvalidActualBatteryCapacity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("off-road", 1, -1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
        });
    }

    @Test
    public void testInvalidScooter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("off-road", 1, -1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, 1);
        });
    }

    @Test
    public void testInvalidPower() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("off-road", 1, -1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, -1);
        });
    }

    @Test
    public void testInvalidPower2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Scooter s = new Scooter("city", 1, -1, "idVehicle", 1, new Location(1, 1, 1), 1, 1, -1);
        });
    }
}

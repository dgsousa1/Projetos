package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

/**
 *
 *
 */
public class ParkTest {

    //Park parkMock;
    Park instance;

    @BeforeEach
    public void before() {
        // parkMock = mock(Park.class);
        instance = new Park("P001", new Location(50.0, 50.0), "Parque Teste", 10, 10, 4.0f, 4.0f);

    }

    /**
     * Test of getIdPark method, of class Park.
     */
    @Test
    public void testGetIdPark() {
        String exp = "P001";
        assertEquals(exp, instance.getIdPark());
    }

    /**
     * Test of getLocation method, of class Park.
     */
    @Test
    public void testGetLocation() {
        assertNotNull(instance.getLocation());
    }

    /**
     * Test of getDescription method, of class Park.
     */
    @Test
    public void testGetDescription() {
        String exp = "Parque Teste";
        assertEquals(exp, instance.getDescription());
    }

    /**
     * Test of getMaxBike method, of class Park.
     */
    @Test
    public void testGetMaxBike() {
        int exp = 10;
        assertEquals(exp, instance.getMaxBike());
    }

    /**
     * Test of getMaxScooter method, of class Park.
     */
    @Test
    public void testGetMaxScooter() {
        int exp = 10;
        assertEquals(exp, instance.getMaxScooter());
    }

    /**
     * Test of getInputVoltage method, of class Park.
     */
    @Test
    public void testGetInputVoltage() {
        float exp = 4.0f;
        assertEquals(exp, instance.getInputVoltage());
    }

    /**
     * Test of getInputCurrent method, of class Park.
     */
    @Test
    public void testGetInputCurrent() {
        float exp = 4.0f;
        assertEquals(exp, instance.getInputCurrent());

    }

    @Test
    public void testInvalidIdPark() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("", new Location(50.0, 50.0), "Parque Teste", 10, 10, 4.0f, 4.0f);
        });
    }

    @Test
    public void testInvalidLocation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", null, "Parque Teste", 10, 10, 4.0f, 4.0f);
        });
    }

    @Test
    public void testInvalidDescription() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", new Location(50.0, 50.0), "", 10, 10, 4.0f, 4.0f);
        });
    }

    @Test
    public void testInvalidMaxBike() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", new Location(50.0, 50.0), "Parque Teste", 0, 10, 4.0f, 4.0f);
        });
    }

    @Test
    public void testInvalidMaxScooter() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", new Location(50.0, 50.0), "Parque Teste", 10, 0, 4.0f, 4.0f);
        });
    }

    @Test
    public void testInvalidInputVoltage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", new Location(50.0, 50.0), "Parque Teste", 10, 10, 0, 4.0f);
        });
    }

    @Test
    public void testInvalidInputCurrent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Park p = new Park("P001", new Location(50.0, 50.0), "Parque Teste", 10, 10, 4.0f, 0);
        });
    }

}

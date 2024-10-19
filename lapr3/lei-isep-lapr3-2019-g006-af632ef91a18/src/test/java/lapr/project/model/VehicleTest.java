package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
/**
 *
 * @author Nuno
 */
public class VehicleTest {

    /**
     * Test of getIdVehicle method, of class Vehicle.
     */
    @Test
    public void testGetIdVehicle() {
        String expectedResult = "idVehicle";
        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getIdVehicle()).thenReturn("idVehicle");
        assertEquals(expectedResult, vehicleMock.getIdVehicle());
    }

    /**
     * Test of getWeight method, of class Vehicle.
     */
    @Test
    public void testGetWeight() {
        int expectedResult = 1;
        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getWeight()).thenReturn(1);
        assertEquals(expectedResult, vehicleMock.getWeight());
    }

    /**
     * Test of getLocation method, of class Vehicle.
     */
    @Test
    public void testGetLocation() {
        Location expectedResult = new Location(1,1,1);
        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getLocation()).thenReturn(new Location(1,1,1));
        assertNotNull(vehicleMock.getLocation());
    }

    /**
     * Test of getAerodynamicCoef method, of class Vehicle.
     */
    @Test
    public void testGetAerodynamicCoef() {
        float expectedResult = (float) 1.1;
        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getAerodynamicCoef()).thenReturn((float) 1.1);
        assertEquals(expectedResult, vehicleMock.getAerodynamicCoef());
    }

    /**
     * Test of getFrontalArea method, of class Vehicle.
     */
    @Test
    public void testGetFrontalArea() {
        float expectedResult = (float) 1.1;
        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getFrontalArea()).thenReturn((float) 1.1);
        assertEquals(expectedResult, vehicleMock.getFrontalArea());
    }
}

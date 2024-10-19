package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathTest {

    Path aPath;

    @BeforeEach
    public void before() {
        aPath = new Path(new Location(40.0, 40.0), new Location(50.0, 50.0), 2.0f, 4.0f, 10.0f);
    }

    /**
     * Test of getLocationA method, of class Path.
     */
    @Test
    public void testGetLocationA() {
        assertNotNull(aPath.getLocationA());
    }

    /**
     * Test of setLocationA method, of class Path.
     */
    @Test
    public void testSetLocationA() {
        aPath.setLocationA(new Location(30.0, 30.0));
        assertNotNull(aPath.getLocationA());
    }

    /**
     * Test of getLocationB method, of class Path.
     */
    @Test
    public void testGetLocationB() {
        assertNotNull(aPath.getLocationB());
    }

    /**
     * Test of setLocationB method, of class Path.
     */
    @Test
    public void testSetLocationB() {
        aPath.setLocationB(new Location(30.0, 30.0));
        assertNotNull(aPath.getLocationB());
    }

    /**
     * Test of getKineticCoefficient method, of class Path.
     */
    @Test
    public void testGetKineticCoefficient() {
        float expResult = 2.0f;
        assertEquals(expResult, aPath.getKineticCoefficient());
    }

    /**
     * Test of setKineticCoefficient method, of class Path.
     */
    @Test
    public void testSetKineticCoefficient() {
        aPath.setKineticCoefficient(6.0f);
        assertNotNull(aPath.getKineticCoefficient());
    }

    /**
     * Test of getWindDirection method, of class Path.
     */
    @Test
    public void testGetWindDirection() {
        float expResult = 4.0f;
        assertEquals(expResult, aPath.getWindDirection());
    }

    /**
     * Test of setWindDirection method, of class Path.
     */
    @Test
    public void testSetWindDirection() {
        aPath.setWindDirection(6.0f);
        assertNotNull(aPath.getWindDirection());
    }

    /**
     * Test of getWindSpeed method, of class Path.
     */
    @Test
    public void testGetWindSpeed() {
        float expResult = 10.0f;
        assertEquals(expResult,aPath.getWindSpeed());
    }

    /**
     * Test of setWindSpeed method, of class Path.
     */
    @Test
    public void testSetWindSpeed() {
        aPath.setWindSpeed(6.0f);
        assertNotNull(aPath.getWindSpeed());

    }

    @Test
    public void testInvalidLocationA() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Path p = new Path(null, new Location(50.0, 50.0), 2.0f, 4.0f, 10.0f);
        });
    }

    @Test
    public void testInvalidLocationB() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Path p = new Path(new Location(40.0, 40.0), null, 2.0f, 4.0f, 10.0f);
        });
    }
}

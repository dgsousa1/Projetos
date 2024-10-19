package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class POITest {

    POI instance;

    @BeforeEach
    public void before() {
        instance = new POI("String teste", new Location(40.0, 40.0));
    }

    /**
     * Test of getDescription method, of class POI.
     */
    @Test
    public void testGetDescription() {
        String expResult = "String teste";
        assertEquals(expResult, instance.getDescription());

    }

    /**
     * Test of setDescription method, of class POI.
     */
    @Test
    public void testSetDescription() {
        String expResult = "T1";
        instance.setDescription("T1");
        assertEquals(expResult, instance.getDescription());
    }

    /**
     * Test of getLocation method, of class POI.
     */
    @Test
    public void testGetLocation() {
        assertNotNull(instance.getLocation());
    }

    /**
     * Test of setLocation method, of class POI.
     */
    @Test
    public void testSetLocation() {
        Location location = new Location(50.0, 50.0);
        instance.setLocation(location);
        assertEquals(location,instance.getLocation());
    }

    @Test
    public void testInvalidLocation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            POI p = new POI("String teste", null);
        });
    }

}

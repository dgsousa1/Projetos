package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

public class LocationTest {

    Location instance;

    @BeforeEach
    public void before() {
        instance = new Location(40.0, 40.0);
    }

    /**
     * Test of getLongitude method, of class Location.
     */
    @Test
    public void testGetLongitude() {
        double expResult = 40.0;
        assertEquals(expResult, instance.getLongitude());

    }

    /**
     * Test of getLatitude method, of class Location.
     */
    @Test
    public void testGetLatitude() {
        double expResult = 40.0;
        assertEquals(expResult, instance.getLongitude());
    }

    /**
     * Test of getElevation method, of class Location.
     */
    @Test
    public void testGetElevation() {
        int expResult = 0;
        assertEquals(expResult, instance.getElevation());

    }

    @Test
    public void testLocation() {
        Location l = new Location(40, 40);
        assertNotNull(l);
    }

    @Test
    public void testLocationE() {
        Location l = new Location(40, 40, 2);
        assertNotNull(l);
    }

    @Test
    public void testLocation2() {
        Location l = new Location(90, 20);
        assertNotNull(l);
    }

    @Test
    public void testLocation2E() {
        Location l = new Location(90, 20, 0);
        assertNotNull(l);
    }

    @Test
    public void testLocation3() {
        Location l = new Location(-90, 20);
        assertNotNull(l);
    }

    @Test
    public void testLocation3E() {
        Location l = new Location(-90, 20, 0);
        assertNotNull(l);
    }

    @Test
    public void testLocation4() {
        Location l = new Location(90, 180);
        assertNotNull(l);
    }

    @Test
    public void testLocation4E() {
        Location l = new Location(90, 180, 0);
        assertNotNull(l);
    }

    @Test
    public void testLocation5() {
        Location l = new Location(-90, -180);
        assertNotNull(l);
    }

    @Test
    public void testLocation5E() {
        Location l = new Location(-90, -180, 0);
        assertNotNull(l);
    }

    @Test
    public void testEquals() {
        Location l = new Location(20, 20);
        Location l2 = new Location(20, 20);
        Location l3 = new Location(30, 40);
        assertTrue(!l.equals(null));
        assertTrue(!l.equals(new Integer(3)));
        assertEquals(l, l2);
        assertNotEquals(l, l3);
    }

    @Test
    public void testEqualsE() {
        Location l = new Location(20, 20, 3);
        Location l2 = new Location(20, 20, 3);
        Location l3 = new Location(30, 40, 4);
        Location l4 = new Location(20, 20, 10);
        assertTrue(!l.equals(null));
        assertTrue(!l.equals(new Integer(3)));
        assertEquals(l, l2);
        assertNotEquals(l, l3);
    }

    @Test
    public void testHashCode() {
        Location l = new Location(20, 20);
        Location l2 = new Location(20, 20);
        Location l3 = new Location(30, 40);
        String x = "test";
        assertEquals(l.hashCode(), l2.hashCode());
        assertNotEquals(l.hashCode(), l3.hashCode());
        assertNotEquals(l.hashCode(), x.hashCode());
    }

    @Test
    public void testHashCodeE() {
        Location l = new Location(20, 20, 3);
        Location l2 = new Location(20, 20, 3);
        Location l3 = new Location(30, 40, 4);
        Location l4 = new Location(20, 20, 10);
        String x = "test";
        assertEquals(l.hashCode(), l2.hashCode());
        assertNotEquals(l.hashCode(), l3.hashCode());
        assertNotEquals(l.hashCode(), l4.hashCode());
        assertNotEquals(l.hashCode(), x.hashCode());

    }

    @Test
    public void testIfFailsWithInvalidLatitude() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(91.0, 50.0);
        });
    }

    @Test
    public void testIfFailsWithInvalidLatitude2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-95.0, 50.0);
        });
    }

    @Test
    public void testIfFailsWithInvalidLongitude() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(50.0, -190.0);
        });
    }

    @Test
    public void testIfFailsWithInvalidLongitude2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(50.0, 181.0);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(200.0, 200.0);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-200.0, -200.0);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(100.0, -200.0);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-200.0, 200.0);
        });
    }

    @Test
    public void testIfFailsWithInvalidLatitudeE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(91.0, 50.0, 2);
        });
    }

    @Test
    public void testIfFailsWithInvalidLatitude2E() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-95.0, 50.0, 5);
        });
    }

    @Test
    public void testIfFailsWithInvalidLongitudeE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(50.0, -190.0, 1);
        });
    }

    @Test
    public void testIfFailsWithInvalidLongitude2E() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(50.0, 181.0, 15);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitudeE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(200.0, 200.0, 20);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude2E() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-200.0, -200.0, 30);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude3E() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(100.0, -200.0, 40);
        });
    }

    @Test
    public void testIfFailsWithLatitudeAndLongitude4E() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Location l = new Location(-200.0, 200.0, 30);
        });
    }

}

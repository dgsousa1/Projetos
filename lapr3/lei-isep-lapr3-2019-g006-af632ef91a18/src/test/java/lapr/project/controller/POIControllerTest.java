package lapr.project.controller;

//import org.junit.jupiter.api.Assertions;
//import javax.activation.DataHandler;
import lapr.project.data.POIRegistration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

/**
 *
 *
 */
public class POIControllerTest {

    POIController controller;

    /**
     * Test of addPOI method, of class POIController.
     */
    @Test
    public void testAddPOIFail() {
        int expectedResult = 0;
        controller = new POIController(new POIRegistration());
        int result = controller.addPOI("POITest");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddPOINoFile() {
        int expectedResult = 0;
        controller = new POIController(new POIRegistration());
        int result = controller.addPOI("noFile");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddPOI() {
        int expectedResult = 2;
        POIRegistration registration = mock(POIRegistration.class);
        controller = new POIController(registration);
        int result = controller.addPOI("POITest");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddPOIFail2() {
        int expectedResult = 0;
        controller = new POIController(new POIRegistration());
        int result = controller.addPOI("POITestNoEle");
        assertEquals(expectedResult, result);
    }
}

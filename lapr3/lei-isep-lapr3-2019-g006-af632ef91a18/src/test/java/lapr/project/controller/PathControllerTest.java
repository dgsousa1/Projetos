package lapr.project.controller;

import lapr.project.data.PathRegistration;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Nuno
 */
public class PathControllerTest {

    PathController controller;

    @Test
    public void testAddPathFail() {
        controller = new PathController(new PathRegistration());
        int expectedResult = 0;
        int result = controller.addPath("PathTest");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddPathNoFile() {
        controller = new PathController(new PathRegistration());
        int expectedResult = 0;
        int result = controller.addPath("noFile");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddPathEmptyFile() {
        int expResult = 0;
        controller = new PathController(new PathRegistration());
        int result = controller.addPath("EmptyTestFail");
        assertEquals(expResult, result);
    }

    @Test
    public void testAddPathError() {
        controller = new PathController(new PathRegistration());
        int expectedResult = 0;
        int result = controller.addPath("PathError");
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testAddPathFileFail() {
        int expResult = 0;
        controller = new PathController(new PathRegistration());
        int result = controller.addPath("PathTest");
        assertEquals(expResult, result);
        result = controller.addPath("PathTestError1");
        assertEquals(expResult, result);
        result = controller.addPath("PathTestError2");
        assertEquals(expResult, result);
        result = controller.addPath("PathTestError3");
        assertEquals(expResult, result);
        result = controller.addPath("");
        assertEquals(expResult, result);
    }

    /**
     * Test of addPath method, of class PathController.
     */
    @Test
    public void testAddPath() {
        PathRegistration registration = mock(PathRegistration.class);
        PathController controller = new PathController(registration);
        int expectedResult = 2;
        int result = controller.addPath("PathTest");
        assertEquals(expectedResult, result);
    }

}

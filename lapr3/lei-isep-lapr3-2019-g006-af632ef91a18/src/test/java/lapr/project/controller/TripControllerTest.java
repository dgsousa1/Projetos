package lapr.project.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import lapr.project.data.TripRegistration;
import lapr.project.model.Location;
import lapr.project.model.TripReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Rafael Crista
 */
public class TripControllerTest {

    TripController controller;
    /**
     * Test of getUserTripReport method, of class TripController.
     */
    @Test
    public void testGetUserTripReportFail() {
        controller = new TripController(new TripRegistration());
        assertEquals(new ArrayList<>(), controller.getUserTripReport("1234"));
    }

    @Test
    public void testGetUserTripReport(){
        TripReport report = new TripReport("S001", "user1", new Location(25.0,25.0,5), new Location(42.0,42.0,0), 
                "25/08/2019 14:30", "25/08/2019 15:10");        
        TripRegistration registration = mock(TripRegistration.class);        
        when(registration.getUserTripReport("user1")).thenReturn(asList(report));        
        controller = new TripController(registration);        
        List<TripReport> result = controller.getUserTripReport("user1");
        assertNotNull(result);
    }
    
}

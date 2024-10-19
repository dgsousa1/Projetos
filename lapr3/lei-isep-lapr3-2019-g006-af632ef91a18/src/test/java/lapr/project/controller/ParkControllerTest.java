package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.model.Location;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.utils.Calculator;
import lapr.project.utils.Graph;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 *
 */
public class ParkControllerTest {

    ParkController controller;

    /**
     * Test of addBicycle method, of class VehicleController.
     */
    @Test
    public void testAddParkFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.addPark("ParkTest");
        assertEquals(expResult, result);
        result = controller.addPark("ParkTestError1");
        assertEquals(expResult, result);
        result = controller.addPark("ParkTestError2");
        assertEquals(expResult, result);
        result = controller.addPark("ParkTestError3");
        assertEquals(expResult, result);
        result = controller.addPark("");
        assertEquals(expResult, result);
    }

    @Test
    public void testAddPark() {
        int expResult = 4;
        ParkRegistration registration = mock(ParkRegistration.class);
        controller = new ParkController(registration);
        int result = controller.addPark("ParkTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateParkFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.updatePark("P001;45.38221;71.963841;5;Parque Noroeste;12;20;3.45;7.25");
        assertEquals(expResult, result);
        ParkRegistration registration = mock(ParkRegistration.class);
        Location l1 = new Location(1, 1, 1);
        Park park = new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        try {
            when(registration.updatePark(park)).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(ParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new ParkController(registration);
        result = controller.updatePark("P001;45.38221;71.963841;5;Parque Noroeste;12;20;3.45;");
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdatePark() {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        controller = new ParkController(registration);
        int result = controller.updatePark("P001;45.38221;71.963841;5;Parque Noroeste;12;20;3.45;7.25");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNearestParksNoFile() {
        int expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        controller = new ParkController(registration);
        int result = controller.getNearestParks(1, 1, "", 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNearestParksFail() {
        int expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        try {
            when(registration.getNearestParks(1.0, 1.0, 1)).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(ParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new ParkController(registration);
        int result = controller.getNearestParks(1.0, 1.0, "NearestParksTest", 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNearestParks() {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        controller = new ParkController(registration);
        int result = controller.getNearestParks(1, 1, "NearestParksTest", 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testRemovePark() {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        controller = new ParkController(registration);
        int result = controller.removePark("P001");
        assertEquals(expResult, result);
    }

    @Test
    public void testRemoveParkFail() {
        int expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        try {
            when(registration.removePark("P001")).thenThrow(new SQLException());
        } catch (SQLException ex) {
            Logger.getLogger(ParkControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller = new ParkController(registration);
        int result = controller.removePark("P001");
        assertEquals(expResult, result);
    }

    @Test
    public void testLinearDistanceTo() {
        controller = new ParkController(new ParkRegistration());
        double lat1 = 53.478612;
        double lon1 = 6.250578;
        double lat2 = 50.752342;
        double lon2 = 5.916981;
        int expResult = 304001;
        int result = controller.linearDistanceTo(lat1, lon1, lat2, lon2);
        assertEquals(expResult, result);
    }

    @Test
    public void testLinearDistanceTo2() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.linearDistanceTo(1, 1, 1, 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testLinearDistanceTo3() {
        int expResult = 314498;
        controller = new ParkController(new ParkRegistration());
        int result = controller.linearDistanceTo(-1, -1, 1, 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testLinearDistanceTo4() {
        int expResult = 314498;
        controller = new ParkController(new ParkRegistration());
        int result = controller.linearDistanceTo(1, 1, -1, -1);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeEscooterSlotsAtPark() throws SQLException {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        when(registration.getFreeEscooterSlotsAtPark("P001")).thenReturn(1);
        controller = new ParkController(registration);
        int result = controller.getFreeEscooterSlotsAtPark("P001", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeEscooterSlotsAtParkFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.getFreeEscooterSlotsAtPark("P002", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeBicycleSlotsAtPark() throws SQLException {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        when(registration.getFreeBicycleSlotsAtPark("P002")).thenReturn(1);
        controller = new ParkController(registration);
        int result = controller.getFreeBicycleSlotsAtPark("P002", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeBicycleSlotsAtParkFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.getFreeBicycleSlotsAtPark("P002", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeSlotsAtParkForMyLoanedVehicle() throws SQLException {
        int expResult = 1;
        ParkRegistration registration = mock(ParkRegistration.class);
        when(registration.getFreeSlotsAtParkForMyLoanedVehicle("user1", "P001")).thenReturn(1);
        controller = new ParkController(registration);
        int result = controller.getFreeSlotsAtParkForMyLoanedVehicle("user1", "P001");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFreeSlotsAtParkForMyLoanedVehicleFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.getFreeSlotsAtParkForMyLoanedVehicle("P002", "user1");
        assertEquals(expResult, result);
    }

    @Test
    public void testpathDistanceToFail() {
        int expResult = 0;
        controller = new ParkController(new ParkRegistration());
        int result = controller.pathDistanceTo(1, 1, 1, 1);
        assertEquals(expResult, result);
    }

    @Test
    public void testpathDistanceTo() {
        int expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p = new Path(l1, l2, 0.5f, 10, 2);
        graph.insertEdge(l1, l2, p, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        int result = controller.pathDistanceTo(1, 1, 2, 2);
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIsFail() {
        long expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1.0, 1.0, 0);
        Location l2 = new Location(2.0, 2.0, 1);
        Location l3 = new Location(2.5, 2.0, 1);
        Location l4 = new Location(2.0, 2.5, 1);
        Location l5 = new Location(2.5, 2.5, 0);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        Path p5 = new Path(l2, l5, 0.5f, 10, 2);
        Path p6 = new Path(l5, l3, 0.5f, 10, 2);
        Path p7 = new Path(l1, l3, 0.5f, 10, 2);
        Path p8 = new Path(l3, l5, 0.5f, 10, 2);
        Path p9 = new Path(l5, l2, 0.5f, 10, 2);
        try {
            when(registration.getPark("P001")).thenThrow(new SQLException());
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p3, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p4, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l5, p5, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l3, p6, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l1, l3, p7, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l5, p8, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l2, p9, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs("P001", "P002", "inputPOI", "ShortestRoutePassingInPOIsTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIsFileFail() {
        long expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        POI poi1 = new POI("POI1", l2);
        POI poi2 = new POI("POI1", l3);
        List<POI> pois = new ArrayList<>();
        pois.add(poi1);
        pois.add(poi2);
        try {
            when(registration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p2, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p2, Calculator.calcularDistanciaWithElevation(l4.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs("P001", "P002", "inputPOI", "");
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIs() {
        long expResult = 291419;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1.0, 1.0, 0);
        Location l2 = new Location(2.0, 2.0, 1);
        Location l3 = new Location(2.5, 2.0, 1);
        Location l4 = new Location(2.0, 2.5, 1);
        Location l5 = new Location(2.5, 2.5, 0);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        Path p5 = new Path(l2, l5, 0.5f, 10, 2);
        Path p6 = new Path(l5, l3, 0.5f, 10, 2);
        Path p7 = new Path(l1, l3, 0.5f, 10, 2);
        Path p8 = new Path(l3, l5, 0.5f, 10, 2);
        Path p9 = new Path(l5, l2, 0.5f, 10, 2);
        try {
            when(registration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p3, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p4, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l5, p5, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l3, p6, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l1, l3, p7, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l5, p8, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l2, p9, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs("P001", "P002", "inputPOI", "ShortestRoutePassingInPOIsTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIsCoordenatesFail() {
        long expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1.0, 1.0, 0);
        Location l2 = new Location(2.0, 2.0, 1);
        Location l3 = new Location(2.5, 2.0, 1);
        Location l4 = new Location(2.0, 2.5, 1);
        Location l5 = new Location(2.5, 2.5, 0);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        Path p5 = new Path(l2, l5, 0.5f, 10, 2);
        Path p6 = new Path(l5, l3, 0.5f, 10, 2);
        Path p7 = new Path(l1, l3, 0.5f, 10, 2);
        Path p8 = new Path(l3, l5, 0.5f, 10, 2);
        Path p9 = new Path(l5, l2, 0.5f, 10, 2);
        try {
            when(registration.getParkByCoordsWithoutElevation(l1.getLatitude(), l1.getLongitude())).thenReturn("P001");
            when(registration.getParkByCoordsWithoutElevation(l4.getLatitude(), l4.getLongitude())).thenReturn("P002");
            when(registration.getPark("P001")).thenThrow(new SQLException());
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p3, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p4, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l5, p5, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l3, p6, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l1, l3, p7, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l5, p8, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l2, p9, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs(l1.getLatitude(), l1.getLongitude(), l4.getLatitude(), l4.getLongitude(),
                "inputPOI", "ShortestRoutePassingInPOIsTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIsCoordenatesFileFail() {
        long expResult = 0;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        POI poi1 = new POI("POI1", l2);
        POI poi2 = new POI("POI1", l3);
        List<POI> pois = new ArrayList<>();
        pois.add(poi1);
        pois.add(poi2);
        try {
            when(registration.getParkByCoordsWithoutElevation(l1.getLatitude(), l1.getLongitude())).thenReturn("P001");
            when(registration.getParkByCoordsWithoutElevation(l4.getLatitude(), l4.getLongitude())).thenReturn("P002");
            when(registration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p2, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p2, Calculator.calcularDistanciaWithElevation(l4.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs(l1.getLatitude(), l1.getLongitude(), l4.getLatitude(), l4.getLongitude(),
                "inputPOI", "");
        assertEquals(expResult, result);
        result = controller.shortestRoutePassingInPOIs(l1.getLatitude(), l1.getLongitude(), l4.getLatitude(), l4.getLongitude(),
                "", "ShortestRoutePassingInPOIsTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testShortestRoutePassingInPOIsCoordenates() {
        long expResult = 291419;
        ParkRegistration registration = mock(ParkRegistration.class);
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1.0, 1.0, 0);
        Location l2 = new Location(2.0, 2.0, 1);
        Location l3 = new Location(2.5, 2.0, 1);
        Location l4 = new Location(2.0, 2.5, 1);
        Location l5 = new Location(2.5, 2.5, 0);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        graph.insertVertex(l3);
        graph.insertVertex(l4);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l2, l3, 0.5f, 10, 2);
        Path p3 = new Path(l3, l4, 0.5f, 10, 2);
        Path p4 = new Path(l2, l4, 0.5f, 10, 2);
        Path p5 = new Path(l2, l5, 0.5f, 10, 2);
        Path p6 = new Path(l5, l3, 0.5f, 10, 2);
        Path p7 = new Path(l1, l3, 0.5f, 10, 2);
        Path p8 = new Path(l3, l5, 0.5f, 10, 2);
        Path p9 = new Path(l5, l2, 0.5f, 10, 2);
        try {
            when(registration.getParkByCoordsWithoutElevation(l1.getLatitude(), l1.getLongitude())).thenReturn("P001");
            when(registration.getParkByCoordsWithoutElevation(l4.getLatitude(), l4.getLongitude())).thenReturn("P002");
            when(registration.getPark("P001")).thenReturn(new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
            when(registration.getPark("P002")).thenReturn(new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f));
        } catch (SQLException ex) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.insertEdge(l1, l2, p1, Calculator.calcularDistanciaWithElevation(1, 1, 2, 2, 0, 1));
        graph.insertEdge(l2, l3, p2, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l4, p3, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l4, p4, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l4.getLatitude(), l4.getLongitude(), 0, 1));
        graph.insertEdge(l2, l5, p5, Calculator.calcularDistanciaWithElevation(l2.getLatitude(), l2.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l3, p6, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l1, l3, p7, Calculator.calcularDistanciaWithElevation(l1.getLatitude(), l1.getLongitude(),
                l3.getLatitude(), l3.getLongitude(), 0, 1));
        graph.insertEdge(l3, l5, p8, Calculator.calcularDistanciaWithElevation(l3.getLatitude(), l3.getLongitude(),
                l5.getLatitude(), l5.getLongitude(), 0, 1));
        graph.insertEdge(l5, l2, p9, Calculator.calcularDistanciaWithElevation(l5.getLatitude(), l5.getLongitude(),
                l2.getLatitude(), l2.getLongitude(), 0, 1));
        when(registration.getGraph()).thenReturn(graph);
        controller = new ParkController(registration);
        long result = controller.shortestRoutePassingInPOIs(l1.getLatitude(), l1.getLongitude(), l4.getLatitude(), l4.getLongitude(),
                "inputPOI", "ShortestRoutePassingInPOIsTest");
        assertEquals(expResult, result);
    }
}

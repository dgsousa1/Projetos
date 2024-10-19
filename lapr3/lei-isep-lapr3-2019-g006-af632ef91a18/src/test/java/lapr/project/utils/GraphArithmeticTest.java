package lapr.project.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import static lapr.project.utils.GraphArithmetic.getPaths;
import static lapr.project.utils.GraphArithmetic.getShortestPathPassingInPOIs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class GraphArithmeticTest {

    @Test
    public void testGetShortestPathPassingInPOIs() {
        long expResult = 36;
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        List<POI> pois = new ArrayList<>();
        POI poi1 = new POI("poi1", l2);
        POI poi2 = new POI("poi1", l3);
        pois.add(poi1);
        pois.add(poi2);
        Park park1 = new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        Park park2 = new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        List<Location> shortPath = new LinkedList<>();
        long result = Math.round(getShortestPathPassingInPOIs(pois, graph, park1, park2, shortPath));
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPaths() {
        List<Path> expResult = new ArrayList<>();
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        expResult.add(p1);
        expResult.add(p3);
        expResult.add(p2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        List<POI> pois = new ArrayList<>();
        POI poi1 = new POI("poi1", l2);
        POI poi2 = new POI("poi1", l3);
        pois.add(poi1);
        pois.add(poi2);
        Park park1 = new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        Park park2 = new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        List<Location> shortPath = new LinkedList<>();
        getShortestPathPassingInPOIs(pois, graph, park1, park2, shortPath);
        List<Path> result = getPaths(shortPath, graph);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetPathWithMinDistanceFail(){
        List<List<Location>> expResult = new ArrayList<>();
        Graph<Location, Path> graph = new Graph<>(true);
        Location l1 = new Location(1f, 1f, 0);
        Location l2 = new Location(2f, 2f, 1);
        Location l3 = new Location(2.5f, 2f, 1);
        Location l4 = new Location(2.0f, 2.5f, 1);
        graph.insertVertex(l1);
        graph.insertVertex(l2);
        Path p1 = new Path(l1, l2, 0.5f, 10, 2);
        Path p2 = new Path(l3, l4, 0.5f, 10, 2);
        Path p3 = new Path(l2, l3, 0.5f, 10, 2);
        Path p4 = new Path(l1, l4, 0.5f, 10, 2);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Scooter("city", 1, 10.0f, "S001", 30, l1, 1.1f, 0.3f, 1f);
        graph.insertEdge(l1, l2, p1, Calculator.calculateEnergyForPath(client, p1, vehicle));
        graph.insertEdge(l3, l4, p2, Calculator.calculateEnergyForPath(client, p2, vehicle));
        graph.insertEdge(l2, l3, p3, Calculator.calculateEnergyForPath(client, p3, vehicle));
        graph.insertEdge(l1, l4, p4, Calculator.calculateEnergyForPath(client, p4, vehicle));
        Park parkOrg = new Park("P001", l1, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        Park ParkDest = new Park("P002", l4, "Parque Noroeste", 5, 20, 3.45f, 7.25f);
        List<List<Location>> result = GraphArithmetic.getPathWithMinDistance(0, graph, parkOrg, ParkDest, client, vehicle);
        assertEquals(expResult, result);
        result = GraphArithmetic.getPathWithMinDistance(43.97246556341183, graph, parkOrg, ParkDest, client, vehicle);
        List<Location> list = new ArrayList<>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);
        expResult.add(list);
        assertEquals(expResult, result);
    }
}

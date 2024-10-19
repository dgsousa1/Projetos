package lapr.project.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;

/**
 *
 *
 */
public class GraphAlgorithmsTest {

    Graph<String, String> completeMap = new Graph<>(false);
    Graph<String, String> incompleteMap = new Graph<>(false);

    public GraphAlgorithmsTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");

        completeMap.insertEdge("Porto", "Aveiro", "A1", 75);
        completeMap.insertEdge("Porto", "Braga", "A3", 60);
        completeMap.insertEdge("Porto", "Vila Real", "A4", 100);
        completeMap.insertEdge("Viseu", "Guarda", "A25", 75);
        completeMap.insertEdge("Guarda", "Castelo Branco", "A23", 100);
        completeMap.insertEdge("Aveiro", "Coimbra", "A1", 60);
        completeMap.insertEdge("Coimbra", "Lisboa", "A1", 200);
        completeMap.insertEdge("Coimbra", "Leiria", "A34", 80);
        completeMap.insertEdge("Aveiro", "Leiria", "A17", 120);
        completeMap.insertEdge("Leiria", "Lisboa", "A8", 150);

        completeMap.insertEdge("Aveiro", "Viseu", "A25", 85);
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        completeMap.insertEdge("Lisboa", "Faro", "A2", 280);

        incompleteMap = completeMap.clone();

        incompleteMap.removeEdge("Aveiro", "Viseu");
        incompleteMap.removeEdge("Leiria", "Castelo Branco");
        incompleteMap.removeEdge("Lisboa", "Faro");
    }

    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test of BreadthFirstSearch method, of class GraphAlgorithms.
     */
    @Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        assertTrue(GraphAlgorithms.BreadthFirstSearch(completeMap, "LX") == null, "Should be null if vertex does not exist");

        LinkedList<String> path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Faro");

        assertTrue(path.size() == 1, "Should be just one");

        Iterator<String> it = path.iterator();
        assertTrue(it.next().compareTo("Faro") == 0, "it should be Faro");

        path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertTrue(path.size() == 7, "Should give seven vertices ");

        path = GraphAlgorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        assertTrue(path.size() == 3, "Should give 3 vertices");
    }

    /**
     * Test of DepthFirstSearch method, of class GraphAlgorithms.
     */
    @Test
    public void testDepthFirstSearch() {
        System.out.println("Test of DepthFirstSearch");

        LinkedList<String> path;

        assertTrue(GraphAlgorithms.DepthFirstSearch(completeMap, "LX") == null, "Should be null if vertex does not exist");

        path = GraphAlgorithms.DepthFirstSearch(incompleteMap, "Faro");
        assertTrue(path.size() == 1, "Should be just one");

        Iterator<String> it = path.iterator();
        assertTrue(it.next().compareTo("Faro") == 0, "it should be Faro");

        path = GraphAlgorithms.DepthFirstSearch(incompleteMap, "Porto");
        assertTrue(path.size() == 7, "Should give seven vertices ");

        path = GraphAlgorithms.DepthFirstSearch(incompleteMap, "Viseu");
        assertTrue(path.size() == 3, "Should give 3 vertices");

        it = path.iterator();
        assertTrue(it.next().compareTo("Viseu") == 0, "First in visit should be Viseu");
        assertTrue(it.next().compareTo("Guarda") == 0, "then Guarda");
        assertTrue(it.next().compareTo("Castelo Branco") == 0, "then Castelo Branco");
    }

    /**
     * Test of allPaths method, of class GraphAlgorithms.
     */
    @Test
    public void testAllPaths() {
        System.out.println("Test of all paths");

        ArrayList<LinkedList<String>> paths = new ArrayList<LinkedList<String>>();

        paths = GraphAlgorithms.allPaths(completeMap, "Porto", "LX");
        assertTrue(paths.size() == 0, "There should not be paths if vertex does not exist");

        paths = GraphAlgorithms.allPaths(incompleteMap, "Porto", "Lisboa");
        assertTrue(paths.size() == 4, "There should be 4 paths");

        paths = GraphAlgorithms.allPaths(incompleteMap, "Porto", "Faro");
        assertTrue(paths.size() == 0, "There should not be paths between Porto and Faro in the incomplete map");
    }

    /**
     * Test of shortestPath method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<String>();
        double lenpath = 0;
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "LX", shortPath);
        assertTrue(lenpath == 0, "Length path should be 0 if vertex does not exist");

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Faro", shortPath);
        assertTrue(lenpath == 0, "Length path should be 0 if there is no path");

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Porto", shortPath);
        assertTrue(shortPath.size() == 1, "Number of nodes should be 1 if source and vertex are the same");

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Lisboa", shortPath);
        assertTrue(lenpath == 335, "Path between Porto and Lisboa should be 335 Km");

        Iterator<String> it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0, "First in path should be Porto");
        assertTrue(it.next().compareTo("Aveiro") == 0, "then Aveiro");
        assertTrue(it.next().compareTo("Coimbra") == 0, "then Coimbra");
        assertTrue(it.next().compareTo("Lisboa") == 0, "then Lisboa");

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Braga", "Leiria", shortPath);
        assertTrue(lenpath == 255, "Path between Braga and Leiria should be 255 Km");

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Braga") == 0, "First in path should be Braga");
        assertTrue(it.next().compareTo("Porto") == 0, "then Porto");
        assertTrue(it.next().compareTo("Aveiro") == 0, "then Aveiro");
        assertTrue(it.next().compareTo("Leiria") == 0, "then Leiria");

        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue(lenpath == 335, "Path between Porto and Castelo Branco should be 335 Km");
        assertTrue(shortPath.size() == 5, "N. cities between Porto and Castelo Branco should be 5 ");

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0, "First in path should be Porto");
        assertTrue(it.next().compareTo("Aveiro") == 0, "then Aveiro");
        assertTrue(it.next().compareTo("Viseu") == 0, "then Viseu");
        assertTrue(it.next().compareTo("Guarda") == 0, "then Guarda");
        assertTrue(it.next().compareTo("Castelo Branco") == 0, "then Castelo Branco");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue(lenpath == 365, "Path between Porto and Castelo Branco should now be 365 Km");
        assertTrue(shortPath.size() == 4, "Path between Porto and Castelo Branco should be 4 cities");

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0, "First in path should be Porto");
        assertTrue(it.next().compareTo("Aveiro") == 0, "then Aveiro");
        assertTrue(it.next().compareTo("Leiria") == 0, "then Leiria");
        assertTrue(it.next().compareTo("Castelo Branco") == 0, "then Castelo Branco");

    }

    /**
     * Test of shortestPaths method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPaths() {
        System.out.println("Test of shortest path");

        ArrayList<LinkedList<String>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();

        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);

        assertEquals(paths.size(), dists.size(), "There should be as many paths as sizes");
        assertEquals(completeMap.numVertices(), paths.size(), "There should be a path to every vertex");
        assertEquals(1, paths.get(completeMap.getKey("Porto")).size(), "Number of nodes should be 1 if source and vertex are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")), "Path to Lisbon");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")), "Path to Castelo Branco");
        assertEquals(335, dists.get(completeMap.getKey("Castelo Branco")), 0.01, "Path between Porto and Castelo Branco should be 335 Km");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco        
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);
        assertEquals(365, dists.get(completeMap.getKey("Castelo Branco")), 0.01, "Path between Porto and Castelo Branco should now be 365 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));

        GraphAlgorithms.shortestPaths(incompleteMap, "Porto", paths, dists);
        assertEquals(Double.MAX_VALUE, dists.get(completeMap.getKey("Faro")), 0.01, "Length path should be Double.MAX_VALUE if there is no path");
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01, "Path between Porto and Lisboa should be 335 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")), "Path to Lisboa");
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01, "Path between Porto and Lisboa should be 335 Km");

        GraphAlgorithms.shortestPaths(incompleteMap, "Braga", paths, dists);
        assertEquals(255, dists.get(completeMap.getKey("Leiria")), 0.01, "Path between Braga and Leiria should be 255 Km");
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.getKey("Leiria")), "Path to Leiria");
    }
}

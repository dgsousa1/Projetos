package lapr.project.utils;

import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 *
 */
public class GraphTest {

    Graph<String, String> instance = new Graph<>(true);

    public GraphTest() {
    }

    @BeforeEach
    public void setUp() {

    }

    /**
     * Test of numVertices method, of class Graph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("Test numVertices");

        assertTrue((instance.numVertices() == 0), "result should be zero");

        instance.insertVertex("A");
        assertTrue((instance.numVertices() == 1), "result should be one");

        instance.insertVertex("B");
        assertTrue((instance.numVertices() == 2), "result should be two");

        instance.removeVertex("A");
        assertTrue((instance.numVertices() == 1), "result should be one");

        instance.removeVertex("B");
        assertTrue((instance.numVertices() == 0), "result should be zero");
    }

    /**
     * Test of vertices method, of class Graph.
     */
    @Test
    public void testVertices() {
        System.out.println("Test vertices");

        Iterator<String> itVerts = instance.vertices().iterator();

        assertTrue(itVerts.hasNext() == false, "vertices should be empty");

        instance.insertVertex("A");
        instance.insertVertex("B");

        itVerts = instance.vertices().iterator();

        assertTrue((itVerts.next().compareTo("A") == 0), "first vertice should be A");
        assertTrue((itVerts.next().compareTo("B") == 0), "second vertice should be B");

        instance.removeVertex("A");

        itVerts = instance.vertices().iterator();
        assertTrue((itVerts.next().compareTo("B")) == 0, "first vertice should now be B");

        instance.removeVertex("B");

        itVerts = instance.vertices().iterator();
        assertTrue(itVerts.hasNext() == false, "vertices should now be empty");
    }

    /**
     * Test of numEdges method, of class Graph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("Test numEdges");

        assertTrue((instance.numEdges() == 0), "result should be zero");

        instance.insertEdge("A", "B", "Edge1", 6);
        assertTrue((instance.numEdges() == 1), "result should be one");

        instance.insertEdge("A", "C", "Edge2", 1);
        assertTrue((instance.numEdges() == 2), "result should be two");

        instance.removeEdge("A", "B");
        assertTrue((instance.numEdges() == 1), "result should be one");

        instance.removeEdge("A", "C");
        assertTrue((instance.numEdges() == 0), "result should be zero");
    }

    /**
     * Test of edges method, of class Graph.
     */
    @Test
    public void testEdges() {
        System.out.println("Test Edges");

        Iterator<Edge<String, String>> itEdge = instance.edges().iterator();

        assertTrue((itEdge.hasNext() == false), "edges should be empty");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        itEdge = instance.edges().iterator();

        itEdge.next();
        itEdge.next();
        assertTrue(itEdge.next().getElement().equals("Edge3") == true, "third edge should be B-D");

        itEdge.next();
        itEdge.next();
        assertTrue(itEdge.next().getElement().equals("Edge6") == true, "sixth edge should be D-A");

        instance.removeEdge("A", "B");

        itEdge = instance.edges().iterator();
        assertTrue(itEdge.next().getElement().equals("Edge2") == true, "first edge should now be A-C");

        instance.removeEdge("A", "C");
        instance.removeEdge("B", "D");
        instance.removeEdge("C", "D");
        instance.removeEdge("C", "E");
        instance.removeEdge("D", "A");
        instance.removeEdge("E", "D");
        instance.removeEdge("E", "E");
        itEdge = instance.edges().iterator();
        assertTrue((itEdge.hasNext() == false), "edges should now be empty");
    }

    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("Test getEdge");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        assertTrue(instance.getEdge("A", "E") == null, "edge should be null");

        assertTrue(instance.getEdge("B", "D").getElement().equals("Edge3") == true, "edge between B-D");
        assertTrue(instance.getEdge("D", "B") == null, "edge should be null");

        instance.removeEdge("D", "A");
        assertTrue(instance.getEdge("D", "A") == null, "edge should be null");

        assertTrue(instance.getEdge("E", "E").getElement().equals("Edge8") == true, "edge should be edge8");
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVertices() {
        System.out.println("Test endVertices");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Edge<String, String> edge0 = new Edge<>();

        String[] vertices = new String[2];

        //assertTrue("endVertices should be null", instance.endVertices(edge0)==null);
        Edge<String, String> edge1 = instance.getEdge("A", "B");
        //vertices = instance.endVertices(edge1);
        assertTrue(instance.endVertices(edge1)[0].equals("A"), "first vertex should be A");
        assertTrue(instance.endVertices(edge1)[1].equals("B"), "second vertex should be B");
    }

    /**
     * Test of opposite method, of class Graph.
     */
    @Test
    public void testOpposite() {
        System.out.println("Test opposite");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Edge<String, String> edge5 = instance.getEdge("C", "E");
        String vert = instance.opposite("A", edge5);
        assertTrue(vert == null, "opposite should be null");

        Edge<String, String> edge1 = instance.getEdge("A", "B");
        vert = instance.opposite("A", edge1);
        assertTrue(vert.equals("B") == true, "opposite should be B");

        Edge<String, String> edge8 = instance.getEdge("E", "E");
        vert = instance.opposite("E", edge8);
        assertTrue(vert.equals("E") == true, "opposite should be E");
    }

    /**
     * Test of outDegree method, of class Graph.
     */
    @Test
    public void testOutDegree() {
        System.out.println("Test outDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        int outdeg = instance.outDegree("G");
        assertTrue(outdeg == -1, "degree should be -1");

        outdeg = instance.outDegree("A");
        assertTrue(outdeg == 2, "degree should be 2");

        outdeg = instance.outDegree("B");
        assertTrue(outdeg == 1, "degree should be 1");

        outdeg = instance.outDegree("E");
        assertTrue(outdeg == 2, "degree should be 2");
    }

    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
    public void testInDegree() {
        System.out.println("Test inDegree");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        int indeg = instance.inDegree("G");
        assertTrue(indeg == -1, "in degree should be -1");

        indeg = instance.inDegree("A");
        assertTrue(indeg == 1, "in degree should be 1");

        indeg = instance.inDegree("D");
        assertTrue(indeg == 3, "in degree should be 3");

        indeg = instance.inDegree("E");
        assertTrue(indeg == 2, "in degree should be 2");
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges() {
        System.out.println(" Test outgoingEdges");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Iterator<Edge<String, String>> itEdge = instance.outgoingEdges("C").iterator();
        Edge<String, String> first = itEdge.next();
        Edge<String, String> second = itEdge.next();
        assertTrue(((first.getElement().equals("Edge4") == true && second.getElement().equals("Edge5") == true)
                || (first.getElement().equals("Edge5") == true && second.getElement().equals("Edge4") == true)),
                "Outgoing Edges of vert C should be Edge4 and Edge5");

        instance.removeEdge("E", "E");

        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue((itEdge.next().getElement().equals("Edge7") == true), "first edge should be Edge7");

        instance.removeEdge("E", "D");

        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue((itEdge.hasNext() == false), "edges should be empty");
    }

    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges() {

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Iterator<Edge<String, String>> itEdge = instance.incomingEdges("D").iterator();

        assertTrue((itEdge.next().getElement().equals("Edge3") == true), "first edge should be edge3");
        assertTrue((itEdge.next().getElement().equals("Edge4") == true), "second edge should be edge4");
        assertTrue((itEdge.next().getElement().equals("Edge7") == true), "third edge should be edge7");

        itEdge = instance.incomingEdges("E").iterator();

        assertTrue((itEdge.next().getElement().equals("Edge5") == true), "first edge should be Edge5");
        assertTrue((itEdge.next().getElement().equals("Edge8") == true), "second edge should be Edge8");

        instance.removeEdge("E", "E");

        itEdge = instance.incomingEdges("E").iterator();

        assertTrue((itEdge.next().getElement().equals("Edge5") == true), "first edge should be Edge5");

        instance.removeEdge("C", "E");

        itEdge = instance.incomingEdges("E").iterator();
        assertTrue((itEdge.hasNext() == false), "edges should be empty");
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("Test insertVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        Iterator<String> itVert = instance.vertices().iterator();

        assertTrue((itVert.next().equals("A") == true), "first vertex should be A");
        assertTrue((itVert.next().equals("B") == true), "second vertex should be B");
        assertTrue((itVert.next().equals("C") == true), "third vertex should be C");
        assertTrue((itVert.next().equals("D") == true), "fourth vertex should be D");
        assertTrue((itVert.next().equals("E") == true), "fifth vertex should be E");
    }

    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("Test insertEdge");

        assertTrue((instance.numEdges() == 0), "num. edges should be zero");

        instance.insertEdge("A", "B", "Edge1", 6);
        assertTrue((instance.numEdges() == 1), "num. edges should be 1");

        instance.insertEdge("A", "C", "Edge2", 1);
        assertTrue((instance.numEdges() == 2), "num. edges should be 2");

        instance.insertEdge("B", "D", "Edge3", 3);
        assertTrue((instance.numEdges() == 3), "num. edges should be 3");

        instance.insertEdge("C", "D", "Edge4", 4);
        assertTrue((instance.numEdges() == 4), "num. edges should be 4");

        instance.insertEdge("C", "E", "Edge5", 1);
        assertTrue((instance.numEdges() == 5), "num. edges should be 5");

        instance.insertEdge("D", "A", "Edge6", 2);
        assertTrue((instance.numEdges() == 6), "num. edges should be 6");

        instance.insertEdge("E", "D", "Edge7", 1);
        assertTrue((instance.numEdges() == 7), "num. edges should be 7");

        instance.insertEdge("E", "E", "Edge8", 1);
        assertTrue((instance.numEdges() == 8), "num. edges should be 8");

        Iterator<Edge<String, String>> itEd = instance.edges().iterator();

        itEd.next();
        itEd.next();
        assertTrue((itEd.next().getElement().equals("Edge3") == true), "third edge should be Edge3");
        itEd.next();
        itEd.next();
        assertTrue((itEd.next().getElement().equals("Edge6") == true), "sixth edge should be Edge6");
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("Test removeVertex");

        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");

        instance.removeVertex("C");
        assertTrue((instance.numVertices() == 4), "Num vertices should be 4");

        Iterator<String> itVert = instance.vertices().iterator();
        assertTrue((itVert.next().equals("A") == true), "first vertex should be A");
        assertTrue((itVert.next().equals("B") == true), "second vertex should be B");
        assertTrue((itVert.next().equals("D") == true), "third vertex should be D");
        assertTrue((itVert.next().equals("E") == true), "fourth vertex should be E");

        instance.removeVertex("A");
        assertTrue((instance.numVertices() == 3), "Num vertices should be 3");

        itVert = instance.vertices().iterator();
        assertTrue((itVert.next().equals("B") == true), "first vertex should be B");
        assertTrue((itVert.next().equals("D") == true), "second vertex should be D");
        assertTrue((itVert.next().equals("E") == true), "third vertex should be E");

        instance.removeVertex("E");
        assertTrue((instance.numVertices() == 2), "Num vertices should be 2");

        itVert = instance.vertices().iterator();

        assertTrue(itVert.next().equals("B") == true, "first vertex should be B");
        assertTrue(itVert.next().equals("D") == true, "second vertex should be D");

        instance.removeVertex("B");
        instance.removeVertex("D");
        assertTrue((instance.numVertices() == 0), "Num vertices should be 4");
    }

    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("Test removeEdge");

        assertTrue((instance.numEdges() == 0), "num. edges should be zero");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        assertTrue((instance.numEdges() == 8), "Num. edges should be 8");

        instance.removeEdge("E", "E");
        assertTrue((instance.numEdges() == 7), "Num. edges should be 7");

        Iterator<Edge<String, String>> itEd = instance.edges().iterator();

        itEd.next();
        itEd.next();
        assertTrue((itEd.next().getElement().equals("Edge3") == true), "third edge should be Edge3");
        itEd.next();
        itEd.next();
        assertTrue((itEd.next().getElement().equals("Edge6") == true), "sixth edge should be Edge6");

        instance.removeEdge("C", "D");
        assertTrue((instance.numEdges() == 6), "Num. edges should be 6");

        itEd = instance.edges().iterator();
        itEd.next();
        itEd.next();
        assertTrue((itEd.next().getElement().equals("Edge3") == true), "third edge should be Edge3");
        assertTrue((itEd.next().getElement().equals("Edge5") == true), "fourth edge should be Edge5");
        assertTrue((itEd.next().getElement().equals("Edge6") == true), "fifth edge should be Edge6");
        assertTrue((itEd.next().getElement().equals("Edge7") == true), "...last edge should be Edge7");
    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testClone() {
        System.out.println("Test Clone");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        Graph<String, String> instClone = instance.clone();

        assertTrue(instance.numVertices() == instClone.numVertices(), "number of vertices should be equal");
        assertTrue(instance.numEdges() == instClone.numEdges(), "number of edges should be equal");

        //vertices should be equal
        Iterator<String> itvertClone = instClone.vertices().iterator();
        Iterator<String> itvertSource = instance.vertices().iterator();
        while (itvertSource.hasNext()) {
            assertTrue((itvertSource.next().equals(itvertClone.next()) == true), "vertices should be equal ");
        }
    }

    @Test
    public void testEquals() {
        System.out.println("Test Equals");

        instance.insertEdge("A", "B", "Edge1", 6);
        instance.insertEdge("A", "C", "Edge2", 1);
        instance.insertEdge("B", "D", "Edge3", 3);
        instance.insertEdge("C", "D", "Edge4", 4);
        instance.insertEdge("C", "E", "Edge5", 1);
        instance.insertEdge("D", "A", "Edge6", 2);
        instance.insertEdge("E", "D", "Edge7", 1);
        instance.insertEdge("E", "E", "Edge8", 1);

        assertFalse(instance.equals(null), "should not be equal to null");

        assertTrue(instance.equals(instance), "should be equal to itself");

        assertTrue(instance.equals(instance.clone()), "should be equal to a clone");

        Graph<String, String> other = instance.clone();

        other.removeEdge("E", "E");
        assertFalse(instance.equals(other), "instance should not be equal to other");

        other.insertEdge("E", "E", "Edge8", 1);
        assertTrue(instance.equals(other), "instance should be equal to other");

        other.removeVertex("D");
        assertFalse(instance.equals(other), "instance should not be equal to other");

    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testToString() {

        System.out.println(instance);
    }

    @Test
    public void testInvalidVertexEdges() {
        assertEquals(instance.adjVertices(null), null);
        assertEquals(instance.endVertices(null), null);
        Edge<String, String> edge = new Edge<>();
        edge.setVDest(null);
        edge.setVOrig(null);
        assertEquals(instance.endVertices(edge), null);
        assertEquals(instance.opposite(null, edge), null);
        assertEquals(instance.outgoingEdges(null), null);
        assertEquals(instance.incomingEdges(null), null);
        assertEquals(instance.insertVertex(null), true);
        assertEquals(instance.insertEdge(null, null, null, 0), true);
    }
}

/*
* A collection of graph algorithms.
 */
package lapr.project.utils;

import java.util.ArrayList;
import static java.util.Collections.reverse;
import java.util.LinkedList;

/**
 *
 * @author DEI-ESINF
 */
public class GraphAlgorithms {

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param <V>
     * @param <E>
     * @param g Graph instance
     * @param vert
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        V[] keys = g.allkeyVerts();

        if (g.validVertex(vert) == false) {
            return null;
        }

        int index = g.getKey(vert);

        LinkedList<V> resultQueue = new LinkedList<>();
        boolean[] knownVertices = new boolean[g.numVertices()];

        LinkedList<V> auxQueue = new LinkedList<>();

        resultQueue.add(vert);
        auxQueue.add(vert);

        knownVertices[index] = true;

        while (!auxQueue.isEmpty()) {
            vert = auxQueue.removeFirst();
            for (int i = 0; i < g.numVertices(); i++) {
                if (g.getEdge(vert, keys[i]) != null) {
                    int dest = i;
                    if (knownVertices[dest] == false) {
                        knownVertices[dest] = true;
                        resultQueue.add(keys[dest]);
                        auxQueue.add(keys[dest]);
                    }
                }
            }
        }
        return resultQueue;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g Graph instance
     * @param vOrig Vertex of graph g that will be the source of the search
     * @param visited set of discovered vertices
     * @param qdfs queue with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, LinkedList<V> qdfs) {
        qdfs.push(vOrig);
        for (V v : g.adjVertices(vOrig)) {
            if (!qdfs.contains(v)) {
                DepthFirstSearch(g, v, qdfs);
            }
        }
    }

    /**
     * @param <V>
     * @param <E>
     * @param g Graph instance
     * @param vert
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (g.validVertex(vert) == false) {
            return null;
        }
        LinkedList<V> qdfs = new LinkedList<>();
        Graph<V, E> clone = g.clone();
        DepthFirstSearch(clone, vert, qdfs);
        return revPath(qdfs);
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vDest Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path stack with vertices of the current path (the path is in
     * reverse order)
     * @param paths ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
            LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        visited[g.getKey(vOrig)] = true;
        path.add(vOrig);
        LinkedList<V> reversedStack;
        for (V v : g.adjVertices(vOrig)) {
            if (v == vDest) {
                path.add(vDest);
                reversedStack = (LinkedList) path.clone();
                reverse(reversedStack);
                paths.add(reversedStack);
                path.pollLast();
            } else if (visited[g.getKey(v)] == false) {
                allPaths(g, v, vDest, visited, path, paths);
            }

        }
        visited[g.getKey(vOrig)] = false;
        path.pollLast();
    }

    /**
     * @param <V>
     * @param <E>
     * @param g Graph instance
     * @param vOrig
     * @param vDest
     * @return paths ArrayList with all paths from voInf to vdInf
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> auxStack = new LinkedList<>();
        boolean[] knownVertices = new boolean[g.numVertices()];
        paths.clear();

        if (g.validVertex(vDest) == false || g.validVertex(vOrig) == false) {
            return paths;
        }

        allPaths(g, vOrig, vDest, knownVertices, auxStack, paths);
        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights This implementation
     * uses Dijkstra's algorithm
     *
     * @param <V>
     * @param <E>
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vertices
     * @param visited set of discovered vertices
     * @param pathKeys
     * @param dist minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
            boolean[] visited, int[] pathKeys, double[] dist) {

        dist[g.getKey(vOrig)] = 0;
        while (vOrig != null) {
            visited[g.getKey(vOrig)] = true;
            for (V v : g.adjVertices(vOrig)) {
                Edge e = g.getEdge(vOrig, v);
                if (!visited[g.getKey(v)] && dist[g.getKey(v)] > dist[g.getKey(vOrig)] + e.getWeight()) {
                    dist[g.getKey(v)] = dist[g.getKey(vOrig)] + e.getWeight();
                    pathKeys[g.getKey(v)] = g.getKey(vOrig);
                }
            }
            int indice = getVertMinDist(dist, visited);
            if (indice == -1) {
                vOrig = null;
            } else {
                vOrig = vertices[indice];
            }
        }
    }

    private static <V, E> int getVertMinDist(double[] dist, boolean[] visited) {
        int vertMinDist = -1;
        double distance = Double.MAX_VALUE;

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i] && dist[i] < distance) {
                distance = dist[i];
                vertMinDist = i;
            }
        }

        return vertMinDist;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf The path
     * is constructed from the end to the beginning
     *
     * @param <V>
     * @param <E>
     * @param g Graph instance
     * @param vOrig
     * @param vDest
     * @param verts
     * @param pathKeys
     * @param path stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {
        path.addFirst(vDest);
        if (!vOrig.equals(vDest)) {
            vDest = verts[pathKeys[g.getKey(vDest)]];
            getPath(g, vOrig, vDest, verts, pathKeys, path);
        }
    }

    //shortest-path between vOrig and vDest
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        if (g.validVertex(vDest) == false || g.validVertex(vOrig) == false) {
            return 0;
        }

        int sourceIdx = g.getKey(vOrig);
        int dest_idx = g.getKey(vDest);
        V[] vertices = g.allkeyVerts();
        int[] pathKeys = new int[g.numVertices()];
        boolean[] visited = new boolean[g.numVertices()];
        double[] dist = new double[g.numVertices()];

        for (int i = 0; i < g.numVertices(); i++) {
            visited[i] = false;
            pathKeys[i] = -1;
            dist[i] = Double.MAX_VALUE;
        }

        if (vOrig.equals(vDest)) {
            shortPath.add(vDest);
            return 0;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        if (dist[g.getKey(vDest)] == Double.MAX_VALUE) {
            return 0;
        }

        getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);

        if (shortPath.isEmpty() == true) {
            return 0;
        }

        return dist[g.getKey(vDest)];
    }

    //shortest-path between voInf and all other
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Double.MAX_VALUE) {
                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */
    private static <V, E> LinkedList<V> revPath(LinkedList<V> path) {

        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty()) {
            pathrev.push(pathcopy.pop());
        }

        return pathrev;
    }
}

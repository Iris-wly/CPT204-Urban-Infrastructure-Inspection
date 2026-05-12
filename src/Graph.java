import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an undirected weighted graph using an adjacency list.
 *
 * Internal structure:
 *   adjacencyList maps each node ID (String) to a list of outgoing Edge objects.
 *   Because the graph is undirected, addEdge() always inserts two Edge entries:
 *   one for from -> to and one for to -> from.
 *
 * This representation is chosen because:
 *   - Most location nodes are sparsely connected (far fewer edges than n^2).
 *   - Neighbour lookup during Dijkstra is O(degree), which is efficient.
 *   - HashMap gives O(1) average-case node lookup.
 */
public class Graph {

    private HashMap<String, ArrayList<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds an undirected edge between fromId and toId with the given weight.
     * Both nodes are created in the adjacency list if they do not already exist.
     */
    public void addEdge(String fromId, String toId, int weight) {
        if (!adjacencyList.containsKey(fromId)) {
            adjacencyList.put(fromId, new ArrayList<>());
        }
        if (!adjacencyList.containsKey(toId)) {
            adjacencyList.put(toId, new ArrayList<>());
        }
        adjacencyList.get(fromId).add(new Edge(toId, weight));
        adjacencyList.get(toId).add(new Edge(fromId, weight));
    }

    /**
     * Returns all edges leaving nodeId.
     * Returns an empty list if the node does not exist.
     */
    public ArrayList<Edge> getNeighbors(String nodeId) {
        ArrayList<Edge> neighbors = adjacencyList.get(nodeId);
        if (neighbors == null) {
            return new ArrayList<>();
        }
        return neighbors;
    }

    /**
     * Returns true if the graph contains a node with the given ID.
     */
    public boolean containsNode(String nodeId) {
        return adjacencyList.containsKey(nodeId);
    }

    /**
     * Returns the total number of nodes in the graph.
     */
    public int getNodeCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the total number of undirected edges in the graph.
     * Each undirected edge is stored as two directed entries, so the raw
     * adjacency-list size is divided by 2.
     */
    public int getEdgeCount() {
        int directedCount = 0;
        for (ArrayList<Edge> edges : adjacencyList.values()) {
            directedCount += edges.size();
        }
        return directedCount / 2;
    }
}

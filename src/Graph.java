import java.util.ArrayList;
import java.util.HashMap;

// Undirected weighted graph stored as an adjacency list.
// Each node ID maps to the edges leaving that node.
public class Graph {

    private HashMap<String, ArrayList<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Add the edge in both directions because the graph is undirected.
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
    
    // Return an empty list for unknown nodes so callers do not get null.
    public ArrayList<Edge> getNeighbors(String nodeId) {
        ArrayList<Edge> neighbors = adjacencyList.get(nodeId);
        if (neighbors == null) {
            return new ArrayList<>();
        }
        return neighbors;
    }

    public boolean containsNode(String nodeId) {
        return adjacencyList.containsKey(nodeId);
    }

    public int getNodeCount() {
        return adjacencyList.size();
    }

    public ArrayList<String> getAllNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    // Each undirected edge is stored twice, so divide the stored edge count by 2.
    public int getEdgeCount() {
        int directedCount = 0;
        for (ArrayList<Edge> edges : adjacencyList.values()) {
            directedCount += edges.size();
        }
        return directedCount / 2;
    }
}

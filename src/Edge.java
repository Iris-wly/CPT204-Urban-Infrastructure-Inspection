/**
 * Represents one directed edge in the graph.
 * The graph is undirected, so each undirected edge is stored as two Edge objects:
 * one from A to B and one from B to A.
 *
 * Fields:
 *   to     - the ID of the destination node
 *   weight - the travel distance/cost of this edge
 */
public class Edge {

    private String to;
    private int weight;

    public Edge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    public String getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "-> " + to + " (weight=" + weight + ")";
    }
}

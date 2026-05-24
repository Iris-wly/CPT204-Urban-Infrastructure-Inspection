// One edge from the current node to another node.
// In the graph, an undirected road is stored using two of these edges.
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

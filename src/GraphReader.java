import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Reads the paths CSV file and builds the graph used by Dijkstra.
// The first row is a header, so it is skipped before reading the edges.
public class GraphReader {

    // Reads each row as from node, to node, and edge weight.
    public static Graph readGraph(String filePath) throws IOException {
        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the column names.

            String line;
            while ((line = br.readLine()) != null) {
                // CSV fields are simple here: from,to,weight.
                String[] parts = line.split(",");
                String from   = parts[0].trim();
                String to     = parts[1].trim();
                int    weight = Integer.parseInt(parts[2].trim());
                graph.addEdge(from, to, weight);
            }
        }

        return graph;
    }
}

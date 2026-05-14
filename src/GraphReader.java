import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads a paths CSV file and builds a {@link Graph}.
 *
 * Expected format (header row is skipped):
 * <pre>
 *   from_location,to_location,weight
 *   L0001,L0002,2
 * </pre>
 * Each row adds one undirected edge to the graph.
 */
public class GraphReader {

    /**
     * @param filePath path to the paths CSV file
     * @return fully constructed Graph
     * @throws IOException if the file cannot be read
     */
    public static Graph readGraph(String filePath) throws IOException {
        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
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

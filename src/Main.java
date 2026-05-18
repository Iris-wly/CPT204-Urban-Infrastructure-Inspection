import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Main program for the coursework.
// It runs the sorting benchmark first, then uses the selected locations for
// the shortest path part.
public class Main {

    public static void main(String[] args) throws IOException {

        new File("output").mkdirs();

        // Task A: run sorting on the three candidate datasets and save the results.
        ArrayList<Location> top10A;
        ArrayList<Location> top10B;
        ArrayList<Location> top10C;
        try (PrintWriter sortingWriter = new PrintWriter(new FileWriter("output/sorting_results.txt"))) {
            sortingWriter.println("Task A Sorting Benchmark Results");
            sortingWriter.println();
            sortingWriter.println("Report note: The final report table, console screenshot, and output/sorting_results.txt should use the same final run results.");
            sortingWriter.println();

            top10A = SortBenchmark.runForDataset("Dataset A", "data/candidates_A.csv", sortingWriter);
            top10B = SortBenchmark.runForDataset("Dataset B", "data/candidates_B.csv", sortingWriter);
            top10C = SortBenchmark.runForDataset("Dataset C", "data/candidates_C.csv", sortingWriter);
        }
        System.out.println("Sorting results saved to output/sorting_results.txt");

        // Pick the required ranked locations for the path search cases.
        String a1  = top10A.get(0).getLocationId();
        String a10 = top10A.get(9).getLocationId();
        String b1  = top10B.get(0).getLocationId();
        String b5  = top10B.get(4).getLocationId();
        String c1  = top10C.get(0).getLocationId();
        String c5  = top10C.get(4).getLocationId();

        System.out.println("========================================");
        System.out.println("Key Nodes for Task B");
        System.out.println("========================================");
        System.out.printf("A1=%-8s A10=%-8s%n", a1, a10);
        System.out.printf("B1=%-8s B5=%-8s%n",  b1, b5);
        System.out.printf("C1=%-8s C5=%-8s%n",  c1, c5);

        // Task B: load the road/path graph and run the required shortest-path cases.
        System.out.println();
        System.out.println("==============================");
        System.out.println("Task B Shortest Path Benchmark Results");
        System.out.println("==============================");
        System.out.println();

        Graph graph = GraphReader.readGraph("data/paths.csv");
        System.out.println("Graph loaded: " + graph.getNodeCount()
                + " nodes, " + graph.getEdgeCount() + " edges\n");

        try (PrintWriter pw = new PrintWriter(new FileWriter("output/path_results.txt"))) {
            pw.println("Task B Shortest Path Benchmark Results");
            pw.println();
            pw.println("Report note: The final report table, console screenshot, and output/path_results.txt should use the same final run results.");
            pw.println();

            PathBenchmark.runForCase(1, graph, new String[]{a1, a1},         pw);
            PathBenchmark.runForCase(2, graph, new String[]{a1, a10},        pw);
            PathBenchmark.runForCase(3, graph, new String[]{a1, b5, b1},     pw);
            PathBenchmark.runForCase(4, graph, new String[]{a1, b5, c5, c1}, pw);
        }
        System.out.println("Path results saved to output/path_results.txt");
    }
}

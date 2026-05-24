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
        System.out.println("Shortest Path Results");
        System.out.println("==============================");
        System.out.println();

        Graph graph = GraphReader.readGraph("data/paths.csv");
        System.out.println("Graph loaded: " + graph.getNodeCount()
                + " nodes, " + graph.getEdgeCount() + " edges\n");

        PathResult case1 = Dijkstra.findPathWithWaypoints(graph, new String[]{a1, a1});
        PathResult case2 = Dijkstra.findPathWithWaypoints(graph, new String[]{a1, a10});
        PathResult case3 = Dijkstra.findPathWithWaypoints(graph, new String[]{a1, b5, b1});
        PathResult case4 = Dijkstra.findPathWithWaypoints(graph, new String[]{a1, b5, c5, c1});

        printPathCase(1, a1, a1,  new String[]{},       case1);
        printPathCase(2, a1, a10, new String[]{},       case2);
        printPathCase(3, a1, b1,  new String[]{b5},     case3);
        printPathCase(4, a1, c1,  new String[]{b5, c5}, case4);

        try (PrintWriter pw = new PrintWriter(new FileWriter("output/path_results.txt"))) {
            pw.println("Shortest Path Results\n");
            writePathCase(pw, 1, a1, a1,  new String[]{},       case1);
            writePathCase(pw, 2, a1, a10, new String[]{},       case2);
            writePathCase(pw, 3, a1, b1,  new String[]{b5},     case3);
            writePathCase(pw, 4, a1, c1,  new String[]{b5, c5}, case4);
        }
        System.out.println("Path results saved to output/path_results.txt");

    }

    // Format one case so console output and file output stay the same.

    private static String formatPathCase(int caseNum, String start, String dest,
                                          String[] waypoints, PathResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Case ").append(caseNum).append(":\n");
        sb.append("  Start:       ").append(start).append("\n");
        sb.append("  Destination: ").append(dest).append("\n");
        sb.append("  Waypoints:   ");
        if (waypoints.length == 0) {
            sb.append("None");
        } else {
            for (int i = 0; i < waypoints.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(waypoints[i]);
            }
        }
        sb.append("\n");
        if (result.getTotalDistance() < 0) {
            sb.append("  Path:        No path found\n");
            sb.append("  Total Cost:  N/A\n");
        } else {
            sb.append("  Path:        ");
            ArrayList<String> path = result.getPath();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(path.get(i));
            }
            sb.append("\n");
            sb.append("  Total Cost:  ").append(result.getTotalDistance()).append("\n");
        }
        return sb.append("\n").toString();
    }

    private static void printPathCase(int n, String s, String d, String[] w, PathResult r) {
        System.out.print(formatPathCase(n, s, d, w, r));
    }

    private static void writePathCase(PrintWriter pw, int n, String s, String d,
                                       String[] w, PathResult r) {
        pw.print(formatPathCase(n, s, d, w, r));
    }
}

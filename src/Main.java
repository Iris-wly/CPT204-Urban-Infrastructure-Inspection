import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String pathA = "data/candidates_A.csv";
        String pathB = "data/candidates_B.csv";
        String pathC = "data/candidates_C.csv";

        // Run the full sorting experiment for all three datasets.
        ArrayList<Location> top10A = SortingExperiment.runExperimentForDataset("Dataset A", pathA);
        ArrayList<Location> top10B = SortingExperiment.runExperimentForDataset("Dataset B", pathB);
        ArrayList<Location> top10C = SortingExperiment.runExperimentForDataset("Dataset C", pathC);

        // Print the six key nodes that WLY hands to XXY for Task B.
        // A1  = rank 1  of Dataset A top 10
        // A10 = rank 10 of Dataset A top 10
        // B1  = rank 1  of Dataset B top 10
        // B5  = rank 5  of Dataset B top 10
        // C1  = rank 1  of Dataset C top 10
        // C5  = rank 5  of Dataset C top 10
        //
        // XXY builds the graph from the FULL paths.csv (all nodes and edges).
        // Dijkstra runs on the complete graph; shortest paths may pass through
        // non-selected locations.
        System.out.println("========================================");
        System.out.println("Key Nodes for Task B (Dijkstra)");
        System.out.println("========================================");
        System.out.println("A1  = " + top10A.get(0).getLocationId());
        System.out.println("A10 = " + top10A.get(9).getLocationId());
        System.out.println("B1  = " + top10B.get(0).getLocationId());
        System.out.println("B5  = " + top10B.get(4).getLocationId());
        System.out.println("C1  = " + top10C.get(0).getLocationId());
        System.out.println("C5  = " + top10C.get(4).getLocationId());

        // ----------------------------------------------------------------
        // Task B: build the graph and run the four required shortest-path cases.
        //
        // Case 1: A1 -> A1          (trivial, distance = 0)
        // Case 2: A1 -> A10         (direct, no waypoints)
        // Case 3: A1 -> B5 -> B1    (one required waypoint B5)
        // Case 4: A1 -> B5 -> C5 -> C1  (two required waypoints B5 then C5)
        //
        // Cases 3 and 4 are solved by running Dijkstra on each sub-segment and
        // stitching the results together (removing duplicate waypoint nodes).
        // ----------------------------------------------------------------
        System.out.println();
        System.out.println("==============================");
        System.out.println("Shortest Path Results");
        System.out.println("==============================");
        System.out.println();

        Graph graph = GraphCSVReader.readGraph("data/paths.csv");
        System.out.println("Graph loaded: " + graph.getNodeCount()
                + " nodes, " + graph.getEdgeCount() + " edges");
        System.out.println();

        String a1  = top10A.get(0).getLocationId();
        String a10 = top10A.get(9).getLocationId();
        String b1  = top10B.get(0).getLocationId();
        String b5  = top10B.get(4).getLocationId();
        String c1  = top10C.get(0).getLocationId();
        String c5  = top10C.get(4).getLocationId();

        PathResult case1 = DijkstraPathFinder.findPathWithWaypoints(
                graph, new String[]{a1, a1});
        PathResult case2 = DijkstraPathFinder.findPathWithWaypoints(
                graph, new String[]{a1, a10});
        PathResult case3 = DijkstraPathFinder.findPathWithWaypoints(
                graph, new String[]{a1, b5, b1});
        PathResult case4 = DijkstraPathFinder.findPathWithWaypoints(
                graph, new String[]{a1, b5, c5, c1});

        printPathCase(1, a1, a1,  new String[]{},       case1);
        printPathCase(2, a1, a10, new String[]{},       case2);
        printPathCase(3, a1, b1,  new String[]{b5},     case3);
        printPathCase(4, a1, c1,  new String[]{b5, c5}, case4);

        // Save path results to output/path_results.txt for report use.
        new File("output").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter("output/path_results.txt"))) {
            pw.println("==============================");
            pw.println("Shortest Path Results");
            pw.println("==============================");
            pw.println();
            writePathCase(pw, 1, a1, a1,  new String[]{},       case1);
            writePathCase(pw, 2, a1, a10, new String[]{},       case2);
            writePathCase(pw, 3, a1, b1,  new String[]{b5},     case3);
            writePathCase(pw, 4, a1, c1,  new String[]{b5, c5}, case4);
        }
        System.out.println("Path results saved to output/path_results.txt");
    }

    // Formats one path case as a multi-line string matching the required output format.
    private static String formatPathCase(int caseNum, String start, String destination,
                                          String[] waypoints, PathResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Case ").append(caseNum).append(":\n");
        sb.append("Start: ").append(start).append("\n");
        sb.append("Destination: ").append(destination).append("\n");
        sb.append("Waypoints: ");
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
            sb.append("Path: No path found\n");
            sb.append("Total Cost: N/A\n");
        } else {
            ArrayList<String> path = result.getPath();
            sb.append("Path: ");
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" -> ");
                sb.append(path.get(i));
            }
            sb.append("\n");
            sb.append("Total Cost: ").append(result.getTotalDistance()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    private static void printPathCase(int caseNum, String start, String destination,
                                       String[] waypoints, PathResult result) {
        System.out.print(formatPathCase(caseNum, start, destination, waypoints, result));
    }

    private static void writePathCase(PrintWriter pw, int caseNum, String start,
                                       String destination, String[] waypoints,
                                       PathResult result) {
        pw.print(formatPathCase(caseNum, start, destination, waypoints, result));
    }

    // --- Development tests (kept for reference, not part of final submission output) ---

    static void devTests() throws IOException {
        String pathA = "data/candidates_A.csv";

        // Stage 1: verify CSV reading
        ArrayList<Location> datasetA = CandidateCSVReader.readCandidates(pathA);
        System.out.println("Dataset A size: " + datasetA.size());
        System.out.println("First 5 records:");
        for (int i = 0; i < 5; i++) System.out.println(datasetA.get(i));

        // Stage 1: verify compare() tie-breaking
        Location x = new Location("L0002", 98.5);
        Location y = new Location("L0001", 98.5);
        System.out.println("compare(L0002, L0001) equal scores: " + Location.compare(x, y));
        System.out.println("Expected: positive");

        // Stages 2-4: small manual list - all three algorithms must produce the same order
        ArrayList<Location> ref = buildSmallList();
        new BubbleSort().sort(ref);
        System.out.println("Bubble: " + ref);

        ArrayList<Location> ref2 = buildSmallList();
        new QuickSort().sort(ref2);
        System.out.println("Quick:  " + ref2);

        ArrayList<Location> ref3 = buildSmallList();
        new MergeSort().sort(ref3);
        System.out.println("Merge:  " + ref3);
    }

    private static ArrayList<Location> buildSmallList() {
        ArrayList<Location> list = new ArrayList<>();
        list.add(new Location("L0003", 50.0));
        list.add(new Location("L0001", 99.0));
        list.add(new Location("L0002", 99.0));
        list.add(new Location("L0005", 10.0));
        list.add(new Location("L0004", 75.0));
        return list;
    }
}

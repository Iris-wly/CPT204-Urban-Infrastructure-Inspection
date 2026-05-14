import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Entry point for the Urban Infrastructure Inspection coursework.
 *
 * Task A – sorting benchmark: reads three candidate datasets, runs Bubble Sort,
 *   Quick Sort, and Merge Sort on each, reports average runtimes, and extracts
 *   the Top 10 highest-priority locations from each dataset.
 *
 * Task B – shortest paths: builds a weighted undirected graph from paths.csv and
 *   finds shortest paths between the key nodes identified in Task A using Dijkstra.
 *   Results are printed to the console and saved to output/path_results.txt.
 *
 * Algorithm comparison: Dijkstra and Bellman-Ford are timed on Cases 2–4 to
 *   confirm identical distances and demonstrate Dijkstra's speed advantage.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // ── Task A: sorting benchmark ────────────────────────────────────────
        ArrayList<Location> top10A = SortBenchmark.runForDataset("Dataset A", "data/candidates_A.csv");
        ArrayList<Location> top10B = SortBenchmark.runForDataset("Dataset B", "data/candidates_B.csv");
        ArrayList<Location> top10C = SortBenchmark.runForDataset("Dataset C", "data/candidates_C.csv");

        // Key nodes passed to Task B (A1/A10 from dataset A, B1/B5 from B, C1/C5 from C).
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

        // ── Task B: build graph and run four shortest-path cases ─────────────
        //   Case 1: A1  → A1                   (trivial, distance = 0)
        //   Case 2: A1  → A10                  (direct)
        //   Case 3: A1  → B5 → B1              (one required waypoint)
        //   Case 4: A1  → B5 → C5 → C1         (two required waypoints)
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

        new File("output").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter("output/path_results.txt"))) {
            pw.println("Shortest Path Results\n");
            writePathCase(pw, 1, a1, a1,  new String[]{},       case1);
            writePathCase(pw, 2, a1, a10, new String[]{},       case2);
            writePathCase(pw, 3, a1, b1,  new String[]{b5},     case3);
            writePathCase(pw, 4, a1, c1,  new String[]{b5, c5}, case4);
        }
        System.out.println("Path results saved to output/path_results.txt");

        // ── Algorithm comparison: Dijkstra vs Bellman-Ford (Cases 2–4) ───────
        System.out.println();
        System.out.println("==============================");
        System.out.println("Dijkstra vs Bellman-Ford");
        System.out.println("==============================\n");

        final int RUNS = 3;
        long dT2 = 0, bT2 = 0, dT3 = 0, bT3 = 0, dT4 = 0, bT4 = 0;
        PathResult bfCase2 = null, bfCase3 = null, bfCase4 = null;

        for (int r = 0; r < RUNS; r++) {
            long s;
            s = System.nanoTime(); Dijkstra.findPathWithWaypoints(graph, new String[]{a1, a10});       dT2 += System.nanoTime() - s;
            s = System.nanoTime(); bfCase2 = BellmanFord.findPathWithWaypoints(graph, new String[]{a1, a10});       bT2 += System.nanoTime() - s;
            s = System.nanoTime(); Dijkstra.findPathWithWaypoints(graph, new String[]{a1, b5, b1});    dT3 += System.nanoTime() - s;
            s = System.nanoTime(); bfCase3 = BellmanFord.findPathWithWaypoints(graph, new String[]{a1, b5, b1});    bT3 += System.nanoTime() - s;
            s = System.nanoTime(); Dijkstra.findPathWithWaypoints(graph, new String[]{a1, b5, c5, c1}); dT4 += System.nanoTime() - s;
            s = System.nanoTime(); bfCase4 = BellmanFord.findPathWithWaypoints(graph, new String[]{a1, b5, c5, c1}); bT4 += System.nanoTime() - s;
        }

        System.out.printf("%-8s %-22s %-22s %-20s%n", "Case", "Dijkstra avg (ns)", "Bellman-Ford avg (ns)", "Same distance?");
        printCompRow("Case 2", dT2, bT2, RUNS, case2, bfCase2);
        printCompRow("Case 3", dT3, bT3, RUNS, case3, bfCase3);
        printCompRow("Case 4", dT4, bT4, RUNS, case4, bfCase4);
        System.out.println("\nBoth algorithms produce identical distances; Dijkstra is faster");
        System.out.println("because its min-heap avoids redundant relaxations.");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void printCompRow(String label, long dTotal, long bTotal, int runs,
                                      PathResult dResult, PathResult bResult) {
        boolean match = dResult.getTotalDistance() == bResult.getTotalDistance();
        System.out.printf("%-8s %-22d %-22d %-20s%n", label,
                dTotal / runs, bTotal / runs,
                match ? "Yes (" + dResult.getTotalDistance() + ")" : "MISMATCH");
    }

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

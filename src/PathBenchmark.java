import java.io.PrintWriter;
import java.util.ArrayList;

// Runs the Dijkstra path search repeatedly to measure execution time.
// The structure mirrors SortBenchmark: 50 warmup runs followed by 1000 timed runs.
public class PathBenchmark {

    private static final int WARMUP_RUNS = 50;
    private static final int MEASUREMENT_RUNS = 1000;

    // Benchmarks one path case and returns the final PathResult.
    // nodeIds[0] is the start, nodeIds[last] is the destination,
    // any nodes in between are required waypoints visited in order.
    public static PathResult runForCase(int caseNum, Graph graph, String[] nodeIds,
                                        PrintWriter outputWriter) {
        for (int run = 0; run < WARMUP_RUNS; run++) {
            Dijkstra.findPathWithWaypoints(graph, nodeIds);
        }

        long totalTime = 0;
        long[] times = new long[MEASUREMENT_RUNS];
        PathResult lastResult = null;
        PathResult referenceResult = null;
        boolean resultConsistent = true;

        for (int run = 0; run < MEASUREMENT_RUNS; run++) {
            long start = System.nanoTime();
            lastResult = Dijkstra.findPathWithWaypoints(graph, nodeIds);
            times[run] = System.nanoTime() - start;
            totalTime += times[run];

            if (referenceResult == null) {
                referenceResult = lastResult;
            } else if (!sameResult(referenceResult, lastResult)) {
                resultConsistent = false;
            }
        }

        double avgNs    = totalTime / (double) MEASUREMENT_RUNS;
        double stdDevNs = calculateStandardDeviation(times, avgNs);

        String start = nodeIds[0];
        String dest  = nodeIds[nodeIds.length - 1];

        printLine(outputWriter, "Case " + caseNum + ":");
        printLine(outputWriter, "  Start:       " + start);
        printLine(outputWriter, "  Destination: " + dest);

        if (nodeIds.length <= 2) {
            printLine(outputWriter, "  Waypoints:   None");
        } else {
            StringBuilder wp = new StringBuilder();
            for (int i = 1; i < nodeIds.length - 1; i++) {
                if (i > 1) wp.append(", ");
                wp.append(nodeIds[i]);
            }
            printLine(outputWriter, "  Waypoints:   " + wp);
        }

        if (lastResult.getTotalDistance() < 0) {
            printLine(outputWriter, "  Path:        No path found");
            printLine(outputWriter, "  Total Cost:  N/A");
        } else {
            ArrayList<String> path = lastResult.getPath();
            StringBuilder pathSb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) pathSb.append(" -> ");
                pathSb.append(path.get(i));
            }
            printLine(outputWriter, "  Path:        " + pathSb);
            printLine(outputWriter, "  Total Cost:  " + lastResult.getTotalDistance());
        }

        printLine(outputWriter, "  Average Time: "
                + String.format("%.0f", avgNs) + " ns"
                + "  (" + String.format("%.3f", avgNs / 1_000_000.0) + " ms)"
                + "  Std Dev: " + String.format("%.0f", stdDevNs) + " ns"
                + "  (" + String.format("%.3f", stdDevNs / 1_000_000.0) + " ms)");
        printLine(outputWriter, "  Path Consistency Check: "
                + (resultConsistent ? "PASS" : "FAILED"));
        printBlankLine(outputWriter);

        return lastResult;
    }

    private static boolean sameResult(PathResult a, PathResult b) {
        if (a.getTotalDistance() != b.getTotalDistance()) return false;
        ArrayList<String> pa = a.getPath(), pb = b.getPath();
        if (pa.size() != pb.size()) return false;
        for (int i = 0; i < pa.size(); i++) {
            if (!pa.get(i).equals(pb.get(i))) return false;
        }
        return true;
    }

    private static double calculateStandardDeviation(long[] times, double mean) {
        double squaredDiffSum = 0;
        for (long time : times) {
            double diff = time - mean;
            squaredDiffSum += diff * diff;
        }
        return Math.sqrt(squaredDiffSum / times.length);
    }

    private static void printLine(PrintWriter outputWriter, String text) {
        System.out.println(text);
        if (outputWriter != null) outputWriter.println(text);
    }

    private static void printBlankLine(PrintWriter outputWriter) {
        System.out.println();
        if (outputWriter != null) outputWriter.println();
    }
}

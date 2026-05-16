import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Runs all three sorting algorithms on one dataset, measures average runtime
 * and standard deviation, and extracts the Top 10 highest-priority locations.
 *
 * Each algorithm is warmed up first and then timed multiple times on fresh
 * copies of the original list so that every measured run starts from the same
 * unsorted state. {@code System.nanoTime()} is used for timing.
 */
public class SortBenchmark {

    private static final int WARMUP_RUNS = 50;
    private static final int MEASUREMENT_RUNS = 1000;

    /**
     * Runs the sorting benchmark for one dataset file.
     * Prints per-algorithm average runtimes and the Top 10 locations.
     *
     * @param datasetName label printed in the output header
     * @param filePath    path to the candidates CSV file
     * @return Top 10 locations sorted by descending priority
     */
    public static ArrayList<Location> runForDataset(String datasetName,
                                                     String filePath) throws IOException {
        return runForDataset(datasetName, filePath, null);
    }

    public static ArrayList<Location> runForDataset(String datasetName,
                                                     String filePath,
                                                     PrintWriter outputWriter) throws IOException {
        printLine(outputWriter, "==============================");
        printLine(outputWriter, datasetName + " Sorting Results");
        printLine(outputWriter, "==============================");

        ArrayList<Location> original = CandidateReader.readCandidates(filePath);

        ArrayList<Sorter> sorters = new ArrayList<>();
        sorters.add(new BubbleSort());
        sorters.add(new QuickSort());
        sorters.add(new MergeSort());

        ArrayList<Location> lastSortedCopy = null;
        ArrayList<Location> referenceTop10 = null;
        boolean top10Consistent = true;

        for (Sorter sorter : sorters) {
            for (int run = 0; run < WARMUP_RUNS; run++) {
                ArrayList<Location> warmupCopy = copyLocations(original);
                sorter.sort(warmupCopy);
            }

            long totalTime = 0;
            long[] times = new long[MEASUREMENT_RUNS];
            ArrayList<Location> sortedCopy = null;

            for (int run = 0; run < MEASUREMENT_RUNS; run++) {
                sortedCopy = copyLocations(original);
                long start = System.nanoTime();
                sorter.sort(sortedCopy);
                times[run] = System.nanoTime() - start;
                totalTime += times[run];
            }

            double avgNs = totalTime / (double) MEASUREMENT_RUNS;
            double stdDevNs = calculateStandardDeviation(times, avgNs);
            printLine(outputWriter, sorter.getName()
                    + " Average Time: " + String.format("%.0f", avgNs) + " ns"
                    + "  (" + String.format("%.3f", avgNs / 1_000_000.0) + " ms)"
                    + "  Std Dev: " + String.format("%.0f", stdDevNs) + " ns"
                    + "  (" + String.format("%.3f", stdDevNs / 1_000_000.0) + " ms)");

            ArrayList<Location> currentTop10 = getTop10(sortedCopy);
            if (referenceTop10 == null) {
                referenceTop10 = currentTop10;
            } else if (!sameLocationIds(referenceTop10, currentTop10)) {
                top10Consistent = false;
            }

            lastSortedCopy = sortedCopy;
        }

        printLine(outputWriter, "Top 10 Consistency Check: "
                + (top10Consistent ? "PASS" : "FAILED"));
        printBlankLine(outputWriter);
        printTop10(lastSortedCopy, outputWriter);
        return getTop10(lastSortedCopy);
    }

    private static double calculateStandardDeviation(long[] times, double mean) {
        double squaredDiffSum = 0;
        for (long time : times) {
            double diff = time - mean;
            squaredDiffSum += diff * diff;
        }
        return Math.sqrt(squaredDiffSum / times.length);
    }
    
    private static ArrayList<Location> copyLocations(ArrayList<Location> original) {
        ArrayList<Location> copy = new ArrayList<>();
        for (Location loc : original) copy.add(loc);
        return copy;
    }

    private static void printTop10(ArrayList<Location> sorted, PrintWriter outputWriter) {
        printLine(outputWriter, "Top 10 Selected Locations:");
        for (int i = 0; i < 10; i++) {
            printLine(outputWriter, (i + 1) + ". " + sorted.get(i));
        }
        printBlankLine(outputWriter);
    }

    private static ArrayList<Location> getTop10(ArrayList<Location> sorted) {
        ArrayList<Location> top10 = new ArrayList<>();
        for (int i = 0; i < 10; i++) top10.add(sorted.get(i));
        return top10;
    }

    private static boolean sameLocationIds(ArrayList<Location> first,
                                           ArrayList<Location> second) {
        for (int i = 0; i < first.size(); i++) {
            if (!first.get(i).getLocationId().equals(second.get(i).getLocationId())) {
                return false;
            }
        }
        return true;
    }

    private static void printLine(PrintWriter outputWriter, String text) {
        System.out.println(text);
        if (outputWriter != null) {
            outputWriter.println(text);
        }
    }

    private static void printBlankLine(PrintWriter outputWriter) {
        System.out.println();
        if (outputWriter != null) {
            outputWriter.println();
        }
    }
}

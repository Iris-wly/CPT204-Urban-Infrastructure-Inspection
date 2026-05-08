import java.io.IOException;
import java.util.ArrayList;

/**
 * SortingExperiment runs all three sorting algorithms on one dataset,
 * measures their runtime, and extracts the Top 10 selected locations.
 *
 * Design decisions:
 *   - Each algorithm receives a fresh copy of the original list so that
 *     the input state is identical for every run. Without this, the second
 *     algorithm would receive an already-sorted list, making timing unfair.
 *   - Each algorithm is run RUNS_PER_ALGORITHM times and the average is reported
 *     to reduce the effect of JVM warm-up and OS scheduling noise.
 *   - System.nanoTime() is used because it has higher resolution than
 *     System.currentTimeMillis() and is not affected by wall-clock adjustments.
 *   - Top 10 is extracted from the sorted copy produced by the last run
 *     of Merge Sort (any algorithm would give the same order).
 */
public class SortingExperiment {

    // Number of timed runs per algorithm. Average is reported.
    private static final int RUNS_PER_ALGORITHM = 3;

    /**
     * Runs the full experiment for one dataset file.
     *
     * Steps:
     *   1. Read the dataset from the CSV file.
     *   2. For each sorter: run RUNS_PER_ALGORITHM timed runs on fresh copies.
     *   3. Print average runtime for each sorter.
     *   4. Extract and print the Top 10 locations.
     *   5. Return the Top 10 list so Main can hand the IDs to XXY for Task B.
     *      XXY uses these IDs to identify source/destination nodes (A1, A10, B1, B5, C1, C5).
     *      XXY builds the graph from the full paths.csv - shortest paths may pass through
     *      non-selected locations.
     */
    public static ArrayList<Location> runExperimentForDataset(String datasetName,
                                                               String filePath) throws IOException {
        System.out.println("==============================");
        System.out.println(datasetName + " Sorting Results");
        System.out.println("==============================");

        // Step 1: read the original dataset once.
        ArrayList<Location> original = CandidateCSVReader.readCandidates(filePath);

        // Step 2: build the list of sorters to test.
        ArrayList<Sorter> sorters = new ArrayList<>();
        sorters.add(new BubbleSort());
        sorters.add(new QuickSort());
        sorters.add(new MergeSort());

        // This will hold the sorted result from the last algorithm (used for Top 10).
        ArrayList<Location> lastSortedCopy = null;

        // Step 3: for each sorter, run RUNS_PER_ALGORITHM timed runs.
        for (Sorter sorter : sorters) {
            long totalTime = 0;
            ArrayList<Location> sortedCopy = null;

            for (int run = 0; run < RUNS_PER_ALGORITHM; run++) {
                // Make a fresh copy of the original list for each run.
                // This ensures every run starts from the same unsorted state.
                sortedCopy = copyLocations(original);

                // Record start time in nanoseconds.
                long start = System.nanoTime();

                // Sort the copy.
                sorter.sort(sortedCopy);

                // Record end time and accumulate.
                long end = System.nanoTime();
                totalTime += (end - start);
            }

            // Calculate average runtime across all runs.
            long averageTime = totalTime / RUNS_PER_ALGORITHM;

            // averageTime / 1_000_000.0 converts ns to ms as a decimal value.
            // String.format("%.3f", ...) shows three decimal places (e.g. 0.081 ms).
            double averageMs = averageTime / 1_000_000.0;
            System.out.println(sorter.getName() + " Average Time: " + averageTime + " ns"
                    + "  (" + String.format("%.3f", averageMs) + " ms)");

            // Keep the last sorted copy for Top 10 extraction.
            // All algorithms produce the same order, so any of them would work.
            lastSortedCopy = sortedCopy;
        }

        // Step 4: extract and print Top 10.
        System.out.println();
        printTop10(lastSortedCopy);

        // Step 5: return the Top 10 list for use in Task B.
        return getTop10(lastSortedCopy);
    }

    /**
     * Creates a new ArrayList containing the same Location objects as the original.
     * This is a shallow copy: the Location objects themselves are not duplicated,
     * but since sort only reorders references (never modifies a Location's fields),
     * a shallow copy is sufficient.
     */
    private static ArrayList<Location> copyLocations(ArrayList<Location> original) {
        ArrayList<Location> copy = new ArrayList<>();
        for (Location loc : original) {
            copy.add(loc);
        }
        return copy;
    }

    /**
     * Prints the Top 10 locations from an already-sorted list.
     * The list must be sorted in descending priority order before calling this.
     */
    private static void printTop10(ArrayList<Location> sorted) {
        System.out.println("Top 10 Selected Locations:");
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
        System.out.println();
    }

    /**
     * Returns a new list containing only the first 10 elements of a sorted list.
     */
    private static ArrayList<Location> getTop10(ArrayList<Location> sorted) {
        ArrayList<Location> top10 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            top10.add(sorted.get(i));
        }
        return top10;
    }
}

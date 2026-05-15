import java.io.IOException;
import java.util.ArrayList;

/**
 * Runs all three sorting algorithms on one dataset, measures average runtime,
 * and extracts the Top 10 highest-priority locations.
 *
 * Each algorithm is run {@code RUNS_PER_ALGORITHM} times on a fresh copy of the
 * original list so that every run starts from the same unsorted state and JVM
 * warm-up effects are averaged out. {@code System.nanoTime()} is used for timing.
 */
public class SortBenchmark {

    private static final int RUNS_PER_ALGORITHM = 3;

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
        System.out.println("==============================");
        System.out.println(datasetName + " Sorting Results");
        System.out.println("==============================");

        ArrayList<Location> original = CandidateReader.readCandidates(filePath);

        ArrayList<Sorter> sorters = new ArrayList<>();
        sorters.add(new BubbleSort());
        sorters.add(new QuickSort());
        sorters.add(new MergeSort());

        ArrayList<Location> lastSortedCopy = null;

        for (Sorter sorter : sorters) {
            long totalTime = 0;
            ArrayList<Location> sortedCopy = null;

            for (int run = 0; run < RUNS_PER_ALGORITHM; run++) {
                sortedCopy = copyLocations(original);
                long start = System.nanoTime();
                sorter.sort(sortedCopy);
                totalTime += System.nanoTime() - start;
            }

            long avgNs = totalTime / RUNS_PER_ALGORITHM;
            System.out.println(sorter.getName() + " Average Time: " + avgNs + " ns"
                    + "  (" + String.format("%.3f", avgNs / 1_000_000.0) + " ms)");

            lastSortedCopy = sortedCopy;
        }

        System.out.println();
        printTop10(lastSortedCopy);
        return getTop10(lastSortedCopy);
    }
    
    private static ArrayList<Location> copyLocations(ArrayList<Location> original) {
        ArrayList<Location> copy = new ArrayList<>();
        for (Location loc : original) copy.add(loc);
        return copy;
    }

    private static void printTop10(ArrayList<Location> sorted) {
        System.out.println("Top 10 Selected Locations:");
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
        System.out.println();
    }

    private static ArrayList<Location> getTop10(ArrayList<Location> sorted) {
        ArrayList<Location> top10 = new ArrayList<>();
        for (int i = 0; i < 10; i++) top10.add(sorted.get(i));
        return top10;
    }
}

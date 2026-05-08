import java.io.IOException;
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

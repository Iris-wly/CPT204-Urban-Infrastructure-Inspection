import java.util.ArrayList;

// Common interface for the sorting algorithms used in the benchmark.
public interface Sorter {
    void sort(ArrayList<Location> locations);
    String getName();
}

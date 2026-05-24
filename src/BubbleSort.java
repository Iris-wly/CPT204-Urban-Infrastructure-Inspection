import java.util.ArrayList;

// Sorts locations with Bubble Sort.
// It repeatedly compares neighboring locations and stops early if a pass makes no swaps.

public class BubbleSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        int n = locations.size();

        // After each pass, one low-ranked item settles at the end.
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            // Only compare the part that is not fixed yet.
            for (int j = 0; j < n - 1 - i; j++) {

                Location left  = locations.get(j);
                Location right = locations.get(j + 1);

                // Positive means left should come after right, so swap them.
                if (Location.compare(left, right) > 0) {
                    locations.set(j,     right);
                    locations.set(j + 1, left);
                    swapped = true;
                }
            }

            // If no swaps happened, the list is already sorted.
            if (!swapped) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}

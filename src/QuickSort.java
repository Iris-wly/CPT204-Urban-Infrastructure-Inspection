import java.util.ArrayList;

// Sorts locations with Quick Sort.
// This version uses the last item as the pivot and sorts the list in place.
public class QuickSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        // Start with the whole list.
        quickSort(locations, 0, locations.size() - 1);
    }

    // Sort the part of the list between low and high.
    private void quickSort(ArrayList<Location> list, int low, int high) {
        if (low >= high) {
            return;
        }

        // Put the pivot in its final place, then sort both sides.
        int pivotIndex = partition(list, low, high);

        quickSort(list, low, pivotIndex - 1);
        quickSort(list, pivotIndex + 1, high);
    }

    // Move smaller-ranked items before the pivot and return the pivot position.
    private int partition(ArrayList<Location> list, int low, int high) {
        // The last element is used as the pivot.
        Location pivot = list.get(high);

        // i marks the end of the "before pivot" area.
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Location current = list.get(j);

            // Keep items that should rank before the pivot on the left.
            if (Location.compare(current, pivot) <= 0) {
                i++;
                Location temp = list.get(i);
                list.set(i, current);
                list.set(j, temp);
            }
        }

        // Put the pivot between the left and right parts.
        Location temp = list.get(i + 1);
        list.set(i + 1, pivot);
        list.set(high, temp);

        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}

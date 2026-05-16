import java.util.ArrayList;

/**
 * Quick Sort implementation of the Sorter interface.
 *
 * How Quick Sort works:
 *   1. Choose one element as the "pivot".
 *   2. Rearrange (partition) the list so that:
 *        - every element that should come BEFORE the pivot is on its left,
 *        - every element that should come AFTER  the pivot is on its right.
 *   3. The pivot is now in its final sorted position.
 *   4. Recursively apply the same process to the left sub-list and the right sub-list.
 *   Recursion stops when a sub-list has 0 or 1 element (already sorted by definition).
 *
 * Pivot strategy used here: this implementation uses the last element as the pivot.
 *   It is simple, but on already sorted or reverse-sorted input it may degrade to O(n^2).
 *
 * Time complexity:
 *   Average case: O(n log n) - each partition splits the list roughly in half.
 *   Worst case  : O(n^2)    - pivot is always the smallest or largest element
 *                             (e.g. already-sorted input with last-element pivot).
 *   Best case   : O(n log n)
 *
 * Space complexity: O(log n) average, O(n) worst case - due to the recursion call stack.
 *   No extra list is allocated; sorting is done in-place.
 */
public class QuickSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        // Start the recursive sort on the full list: index 0 to index n-1.
        quickSort(locations, 0, locations.size() - 1);
    }

    //Recursively sorts the sub-list from index low to index high (inclusive).
    private void quickSort(ArrayList<Location> list, int low, int high) {
        // Base case: if low >= high, the sub-list has 0 or 1 element and is already sorted.
        if (low >= high) {
            return;
        }

        // Partition the sub-list and get the final index of the pivot.
        int pivotIndex = partition(list, low, high);

        // Recursively sort the left part (elements before the pivot).
        quickSort(list, low, pivotIndex - 1);

        // Recursively sort the right part (elements after the pivot).
        quickSort(list, pivotIndex + 1, high);
    }

    //Partitions the sub-list list[low..high] around the pivot (last element).
    private int partition(ArrayList<Location> list, int low, int high) {
        // Choose the last element as the pivot.
        Location pivot = list.get(high);

        // i is the index of the last element confirmed to belong before the pivot.
        // It starts one position before the sub-list.
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Location current = list.get(j);

            // If current should come before or equal to the pivot, it belongs on the left side.
            // Location.compare(current, pivot) <= 0 means current ranks before or equal to pivot.
            if (Location.compare(current, pivot) <= 0) {
                i++;
                // Swap list[i] and list[j] to move current into the "before pivot" region.
                Location temp = list.get(i);
                list.set(i, current);
                list.set(j, temp);
            }
        }

        // Place the pivot immediately after the last "before" element.
        // Swap list[i + 1] with list[high] (the pivot).
        Location temp = list.get(i + 1);
        list.set(i + 1, pivot);
        list.set(high, temp);

        // Return the pivot's final sorted position.
        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}

import java.util.ArrayList;

/**
 * Merge Sort implementation of the Sorter interface.
 *
 * How Merge Sort works:
 *   Merge Sort is a divide-and-conquer algorithm.
 *   It works in two phases:
 *
 *   Phase 1 - Divide:
 *     Split the list in half repeatedly until every sub-list has only 1 element.
 *     A list of 1 element is already sorted by definition.
 *
 *   Phase 2 - Merge (conquer):
 *     Combine two adjacent sorted sub-lists into one sorted sub-list.
 *     Repeat this merging upward until the whole list is merged back together.
 *
 *   Key difference from Quick Sort:
 *     Quick Sort splits by value (pivot) and sorts in place.
 *     Merge Sort splits by index (midpoint) and uses a temporary list during merging.
 *
 * Time complexity:
 *   All cases: O(n log n) - the list is always split into log n levels,
 *              and each level does O(n) work during merging.
 *   Merge Sort is more consistent than Quick Sort because it does not depend
 *   on pivot choice. It always runs in O(n log n) regardless of input order.
 *
 * Space complexity: O(n) - the merge step needs a temporary ArrayList
 *   to hold the merged result before writing it back.
 *   This is the main trade-off compared to Quick Sort (which is O(1) extra space).
 */
public class MergeSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        // Start the recursive sort on the full list: index 0 to index n-1.
        mergeSort(locations, 0, locations.size() - 1);
    }

    /**
     * Recursively divides the list and merges the sorted halves.
     *
     * How the division works:
     *   Find the midpoint: mid = (left + right) / 2
     *   Recursively sort the left half:  list[left .. mid]
     *   Recursively sort the right half: list[mid+1 .. right]
     *   Then merge the two sorted halves back together.
     *
     * Why recursion?
     *   Each call works on a strictly smaller sub-list (half the size).
     *   The base case (left >= right) stops the recursion when the sub-list
     *   has 0 or 1 element, which is already sorted.
     */
    private void mergeSort(ArrayList<Location> list, int left, int right) {
        // Base case: sub-list of 0 or 1 element is already sorted.
        if (left >= right) {
            return;
        }

        // Find the midpoint index to split the sub-list into two halves.
        // Integer division automatically floors the result.
        int mid = (left + right) / 2;

        // Recursively sort the left half: list[left .. mid]
        mergeSort(list, left, mid);

        // Recursively sort the right half: list[mid+1 .. right]
        mergeSort(list, mid + 1, right);

        // Merge the two now-sorted halves back into list[left .. right].
        merge(list, left, mid, right);
    }

    /**
     * Merges two adjacent sorted sub-lists into one sorted sub-list.
     *
     * The two sorted halves are:
     *   Left half:  list[left .. mid]
     *   Right half: list[mid+1 .. right]
     *
     * How the merge works:
     *   1. Copy both halves into a temporary ArrayList.
     *   2. Use two pointers (i for the left half, j for the right half).
     *   3. Compare the front elements of each half using Location.compare().
     *   4. Take the smaller (higher-priority) element and write it back to list.
     *   5. Advance the pointer of whichever half we just took from.
     *   6. When one half is exhausted, copy the remaining elements from the other half.
     *
     * Why a temporary list is needed:
     *   We cannot merge in-place without overwriting elements we still need to compare.
     *   The temporary list preserves the original values during the merge.
     *
     * Why Location.compare() is used:
     *   All three sorting algorithms must follow the same ranking rule.
     *   Using the shared compare() method ensures consistent ordering.
     */
    private void merge(ArrayList<Location> list, int left, int mid, int right) {
        // Copy the sub-list list[left .. right] into a temporary ArrayList.
        // temp index 0 corresponds to list index left.
        ArrayList<Location> temp = new ArrayList<>();
        for (int k = left; k <= right; k++) {
            temp.add(list.get(k));
        }

        // leftSize is the number of elements in the left half of temp.
        int leftSize = mid - left + 1;

        // i scans the left half of temp: indices 0 .. leftSize-1
        // j scans the right half of temp: indices leftSize .. (right-left)
        int i = 0;
        int j = leftSize;

        // k is the write position back into the original list.
        int k = left;

        // Compare front elements of each half and write the correct one back.
        while (i < leftSize && j <= right - left) {
            Location fromLeft  = temp.get(i);
            Location fromRight = temp.get(j);

            // If fromLeft should come before or equal to fromRight, take from the left half.
            // Location.compare(fromLeft, fromRight) <= 0 means fromLeft ranks first.
            if (Location.compare(fromLeft, fromRight) <= 0) {
                list.set(k, fromLeft);
                i++;
            } else {
                // fromRight should come before fromLeft, so take from the right half.
                list.set(k, fromRight);
                j++;
            }
            k++;
        }

        // If any elements remain in the left half, copy them.
        while (i < leftSize) {
            list.set(k, temp.get(i));
            i++;
            k++;
        }

        // If any elements remain in the right half, copy them.
        while (j <= right - left) {
            list.set(k, temp.get(j));
            j++;
            k++;
        }
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}

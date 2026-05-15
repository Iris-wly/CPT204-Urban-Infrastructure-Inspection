import java.util.ArrayList;

/**
 * Bubble Sort implementation of the Sorter interface.
 *
 * How Bubble Sort works:
 *   The algorithm makes repeated passes through the list.
 *   On each pass it compares every pair of adjacent elements.
 *   If the left element should come AFTER the right element, they are swapped.
 *   After each full pass, the largest unsorted element has "bubbled up"
 *   to its correct position at the end of the unsorted region.
 *   The unsorted region shrinks by one after every pass.
 *
 * Time complexity:
 *   Worst case  : O(n^2) - every pair must be compared and swapped (reverse-sorted input).
 *   Best case   : O(n)   - one pass with no swaps confirms the list is already sorted.
 *   Average case: O(n^2)
 *
 * Space complexity: O(1) - sorting is done in-place; no extra list is needed.
 */
public class BubbleSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        int n = locations.size();

        // Outer loop: each pass i guarantees that the i largest elements are already in their final positions at the end of the list.
        // So the unsorted region is locations[0 .. n-1-i].
        for (int i = 0; i < n - 1; i++) {

            // Inner loop: walk through every adjacent pair in the unsorted region.
            // After this loop finishes, the largest element in [0..n-1-i] has moved to position n-1-i.
            for (int j = 0; j < n - 1 - i; j++) {

                Location left  = locations.get(j);
                Location right = locations.get(j + 1);

                // Location.compare(left, right) returns:
                //   negative -> left should come before right (correct order, no swap)
                //   zero     -> equal under the ranking rule  (no swap needed)
                //   positive -> left should come AFTER right  (wrong order, swap!)
                if (Location.compare(left, right) > 0) {
                    // Swap: put right before left
                    locations.set(j,     right);
                    locations.set(j + 1, left);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}

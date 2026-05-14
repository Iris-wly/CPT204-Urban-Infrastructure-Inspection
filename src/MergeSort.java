import java.util.ArrayList;

/**
 * Merge Sort implementation of {@link Sorter}.
 *
 * Divide-and-conquer: recursively splits the list at its midpoint until each
 * sub-list has one element, then merges adjacent sorted sub-lists upward.
 *
 * Time:  O(n log n) in all cases
 * Space: O(n) — a temporary ArrayList is required during each merge step
 */
public class MergeSort implements Sorter {

    @Override
    public void sort(ArrayList<Location> locations) {
        mergeSort(locations, 0, locations.size() - 1);
    }

    private void mergeSort(ArrayList<Location> list, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(list, left,    mid);
        mergeSort(list, mid + 1, right);
        merge(list, left, mid, right);
    }

    /**
     * Merges list[left..mid] and list[mid+1..right] into one sorted range.
     * A temporary copy is required to avoid overwriting elements still needed for comparison.
     */
    private void merge(ArrayList<Location> list, int left, int mid, int right) {
        ArrayList<Location> temp = new ArrayList<>();
        for (int k = left; k <= right; k++) temp.add(list.get(k));

        int leftSize = mid - left + 1;
        int i = 0, j = leftSize, k = left;

        while (i < leftSize && j <= right - left) {
            if (Location.compare(temp.get(i), temp.get(j)) <= 0) {
                list.set(k++, temp.get(i++));
            } else {
                list.set(k++, temp.get(j++));
            }
        }
        while (i < leftSize)      list.set(k++, temp.get(i++));
        while (j <= right - left) list.set(k++, temp.get(j++));
    }

    @Override
    public String getName() { return "Merge Sort"; }
}

import java.util.ArrayList;

// Sorts locations with Merge Sort.
// It keeps splitting the list into smaller parts, then merges them back in order.
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

    // Merges two already sorted parts into one sorted range.
    private void merge(ArrayList<Location> list, int left, int mid, int right) {
        // Copy this range first so values are not overwritten while merging.
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

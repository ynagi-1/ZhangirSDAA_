package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

public final class HeapSort {

    private HeapSort() {  }


    public static void sort(int[] a, PerformanceTracker tracker) {
        Objects.requireNonNull(a, "Input array must not be null");
        if (tracker == null) tracker = PerformanceTracker.disabled();

        final int n = a.length;
        tracker.recordAllocation(0);

        if (n <= 1) {
            return;
        }


        for (int i = (n / 2) - 1; i >= 0; i--) {
            siftDown(a, i, n - 1, tracker);
        }


        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end, tracker);
            siftDown(a, 0, end - 1, tracker);
        }
    }


    private static void siftDown(int[] a, int lo, int hi, PerformanceTracker tracker) {
        int root = lo;
        tracker.recordArrayAccess();
        while (true) {
            int left = 2 * root + 1;
            if (left > hi) break;

            int right = left + 1;
            int swapIndex = left;

            tracker.incrementComparisons();
            tracker.recordArrayAccess();
            if (right <= hi) {
                tracker.recordArrayAccess();

                tracker.incrementComparisons();
                if (a[right] > a[left]) {
                    swapIndex = right;
                }
            }


            tracker.recordArrayAccess();
            tracker.recordArrayAccess();
            tracker.incrementComparisons();
            if (a[root] < a[swapIndex]) {
                swap(a, root, swapIndex, tracker);
                root = swapIndex;
            } else {
                break;
            }
        }
    }

    private static void swap(int[] a, int i, int j, PerformanceTracker tracker) {
        if (i == j) return;
        tracker.recordArrayAccess();
        tracker.recordArrayAccess();
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        tracker.recordArrayAccess();
        tracker.recordArrayAccess();
        tracker.incrementSwaps();
    }
}

package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class        HeapSortTest {

    @Test
    public void testEmptyArray() {
        int[] a = new int[0];
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(new int[0], a);
    }

    @Test
    public void testSingleElement() {
        int[] a = {42};
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    public void testDuplicates() {
        int[] a = {5, 1, 5, 3, 5, 1};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(expected, a);
    }

    @Test
    public void testAlreadySorted() {
        int[] a = {1,2,3,4,5,6};
        int[] expected = Arrays.copyOf(a, a.length);
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(expected, a);
    }

    @Test
    public void testReverseSorted() {
        int[] a = {6,5,4,3,2,1};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        HeapSort.sort(a, new PerformanceTracker());
        assertArrayEquals(expected, a);
    }

    @Test
    public void propertyBasedRandom() {
        Random r = new Random(123);
        for (int trial = 0; trial < 50; trial++) {
            int n = r.nextInt(50);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(1000) - 500;
            int[] expected = Arrays.copyOf(a, a.length);
            Arrays.sort(expected);
            HeapSort.sort(a, new PerformanceTracker());
            assertArrayEquals(expected, a);
        }
    }
}

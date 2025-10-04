package cli;

import algorithms.HeapSort;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 10000;
        int trials = args.length > 1 ? Integer.parseInt(args[1]) : 5;
        String dist = args.length > 2 ? args[2] : "random";
        String csvFile = args.length > 3 ? args[3] : "heap_sort_results.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile, false))) {

            writer.println("n,trial,time_ms,comparisons,swaps,array_accesses,allocations");
            for (int t = 1; t <= trials; t++) {
                int[] arr = generateArray(n, dist, t);
                PerformanceTracker tracker = new PerformanceTracker();
                long start = System.nanoTime();
                HeapSort.sort(arr, tracker);
                long end = System.nanoTime();
                long timeMs = (end - start) / 1_000_000L;


                if (!isSorted(arr)) {
                    System.err.println("ERROR: output not sorted for trial " + t);
                }

                writer.printf("%d,%d,%d,%s%n",
                        n, t, timeMs, tracker.toCsvLine());
                writer.flush();
                System.out.printf("n=%d trial=%d time=%dms %s%n", n, t, timeMs, tracker.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Unable to write CSV: " + e.getMessage());
            throw e;
        }
    }

    private static int[] generateArray(int n, String distribution, int seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        switch (distribution) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly-sorted":
                for (int i = 0; i < n; i++) a[i] = i;

                for (int k = 0; k < Math.max(1, n/100); k++) {
                    int i = rnd.nextInt(n);
                    int j = rnd.nextInt(n);
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
                break;
            case "random":
            default:
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
                break;
        }
        return a;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}

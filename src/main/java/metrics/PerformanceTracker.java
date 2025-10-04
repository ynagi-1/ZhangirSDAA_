package metrics;

import java.io.PrintWriter;
import java.util.concurrent.atomic.LongAdder;


public class PerformanceTracker {
    private final LongAdder comparisons = new LongAdder();
    private final LongAdder swaps = new LongAdder();
    private final LongAdder arrayAccesses = new LongAdder();
    private final LongAdder allocations = new LongAdder(); // conceptual (e.g., new arrays)

    public void incrementComparisons() { comparisons.increment(); }
    public void incrementSwaps() { swaps.increment(); }
    public void recordArrayAccess() { arrayAccesses.increment(); }
    public void recordAllocation(long count) { allocations.add(count); }

    public long getComparisons() { return comparisons.longValue(); }
    public long getSwaps() { return swaps.longValue(); }
    public long getArrayAccesses() { return arrayAccesses.longValue(); }
    public long getAllocations() { return allocations.longValue(); }

    public void reset() {

        throw new UnsupportedOperationException("create a fresh tracker per run");
    }

    public String toCsvLine() {
        return String.format("%d,%d,%d,%d", getComparisons(), getSwaps(), getArrayAccesses(), getAllocations());
    }

    public static PerformanceTracker disabled() {
        return DisabledHolder.INSTANCE;
    }

    private static final class DisabledHolder {
        static final PerformanceTracker INSTANCE = new PerformanceTracker() {
            @Override public void incrementComparisons() {}
            @Override public void incrementSwaps() {}
            @Override public void recordArrayAccess() {}
            @Override public void recordAllocation(long c) {}
            @Override public long getComparisons() { return 0; }
            @Override public long getSwaps() { return 0; }
            @Override public long getArrayAccesses() { return 0; }
            @Override public long getAllocations() { return 0; }
            @Override public String toCsvLine() { return "0,0,0,0"; }
        };
    }


    public static String csvHeader() {
        return "comparisons,swaps,array_accesses,allocations";
    }


    public void printSummary(PrintWriter pw) {
        pw.printf("comparisons=%d, swaps=%d, array_accesses=%d, allocations=%d%n",
                getComparisons(), getSwaps(), getArrayAccesses(), getAllocations());
    }
}

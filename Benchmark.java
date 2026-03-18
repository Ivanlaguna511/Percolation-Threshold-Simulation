import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Performance Benchmark Suite.
 * Pits the Legacy Recursive DFS approach against the Optimized Union-Find approach.
 * Tests multiple grid sizes to demonstrate Time Complexity scaling and Stack Overflow limits.
 */
public class Benchmark {

    public static void main(String[] args) {
        // We test increasingly large grids. 
        // Warning: Legacy DFS will likely StackOverflow at N=200 or N=500.
        int[] gridSizes = {10, 50, 100, 250, 500, 1000};

        System.out.println("=========================================================================");
        System.out.println("   PERCOLATION ALGORITHM BENCHMARK: LEGACY (DFS) vs OPTIMIZED (UF)   ");
        System.out.println("=========================================================================");
        System.out.printf("%-10s | %-15s | %-20s | %-20s%n", "Grid (NxN)", "Strikes Needed", "Legacy Time (ms)", "Union-Find Time (ms)");
        System.out.println("-------------------------------------------------------------------------");

        for (int n : gridSizes) {
            // 1. Generate a deterministic list of random strikes so both algorithms face the exact same scenario
            List<int[]> strikes = generateRandomStrikes(n);

            // 2. Setup instances
            LegacyPercolation legacyGrid = new LegacyPercolation(n);
            Percolation optimizedGrid = new Percolation(n);

            long legacyTime = -1;
            long optimizedTime = -1;
            boolean legacyCrashed = false;
            int strikesToPercolate = 0;

            // 3. Test Optimized Union-Find
            long startTimeOpt = System.currentTimeMillis();
            for (int[] strike : strikes) {
                optimizedGrid.strike(strike[0], strike[1]);
                if (optimizedGrid.percolates()) {
                    strikesToPercolate = strikes.indexOf(strike) + 1;
                    break;
                }
            }
            optimizedTime = System.currentTimeMillis() - startTimeOpt;

            // 4. Test Legacy Recursive DFS
            long startTimeLeg = System.currentTimeMillis();
            try {
                for (int[] strike : strikes) {
                    legacyGrid.strike(strike[0], strike[1]);
                    if (legacyGrid.percolates()) break;
                }
                legacyTime = System.currentTimeMillis() - startTimeLeg;
            } catch (StackOverflowError e) {
                // DFS goes too deep on large grids and crashes the JVM stack
                legacyCrashed = true;
            }

            // 5. Print Results Formatting
            String legacyStr = legacyCrashed ? "STACK_OVERFLOW ☠️" : String.format("%d ms", legacyTime);
            String optStr = String.format("%d ms", optimizedTime);
            
            System.out.printf("%-10s | %-15d | %-20s | %-20s%n", 
                n + "x" + n, strikesToPercolate, legacyStr, optStr);
        }
        
        System.out.println("=========================================================================");
        System.out.println("* NOTE: Legacy approach uses deep recursion, causing memory limit crashes on large grids.");
        System.out.println("* NOTE: Optimized approach scales linearly thanks to Path Compression in the Disjoint-Set.");
    }

    /**
     * Generates a fully shuffled list of all possible coordinates in an NxN grid.
     */
    private static List<int[]> generateRandomStrikes(int n) {
        List<int[]> strikes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                strikes.add(new int[]{i, j});
            }
        }
        Collections.shuffle(strikes);
        return strikes;
    }
}
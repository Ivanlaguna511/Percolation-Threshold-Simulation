import java.util.Random;

/**
 * Monte Carlo Simulation to test the Percolation threshold.
 */
public class Simulation {
    public static void main(String[] args) {
        int dimension = 10; // 10x10 Grid
        Percolation grid = new Percolation(dimension);
        Random random = new Random();
        int strikes = 0;

        System.out.println("Starting Cosmic Ray Percolation Simulation...\n");

        // Continuously strike random cells until the system percolates
        while (!grid.percolates()) {
            int row = random.nextInt(dimension);
            int col = random.nextInt(dimension);
            
            // The strike method safely handles already-open cells
            grid.strike(row, col);
            strikes++;
        }

        System.out.println("SHORT CIRCUIT ACHIEVED (SYSTEM PERCOLATES)");
        System.out.println("Total cosmic ray strikes required: " + strikes);
        System.out.println("\nFinal Grid State:");
        System.out.println(grid.toString());
        System.out.println("Legend: [.] Insulator | [X] Conductor | [*] Triggering Strike");
    }
}
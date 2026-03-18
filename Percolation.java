/**
 * Models a percolation system using an N-by-N grid.
 * Utilizes the Union-Find data structure to efficiently determine if the system percolates.
 */
public class Percolation {
    private boolean[][] isOpen;
    private int gridSize;
    private UnionFind uf;
    
    // Virtual nodes for instant O(1) percolation checking
    private int virtualTop;
    private int virtualBottom;
    
    private boolean percolatesFlag;
    private int lastStrikeRow = -1;
    private int lastStrikeCol = -1;

    /**
     * Creates an N-by-N grid, with all sites initially blocked (insulators).
     * @param n the size of the grid
     */
    public Percolation(int n) {
        this.gridSize = n;
        this.isOpen = new boolean[n][n];
        
        // Array size = N*N + 2 (Virtual Top + Virtual Bottom)
        this.uf = new UnionFind(n * n + 2);
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        this.percolatesFlag = false;
    }

    /**
     * Maps 2D grid coordinates to a 1D Union-Find array index.
     */
    private int get1DIndex(int row, int col) {
        return row * gridSize + col;
    }

    /**
     * Opens the site (row, col) simulating a cosmic ray strike.
     * Connects the newly opened site to its open neighbors.
     * @param row the row index
     * @param col the column index
     */
    public void strike(int row, int col) {
        // Ignore if system already percolates or site is already open
        if (percolatesFlag || isOpen[row][col]) return;

        isOpen[row][col] = true;
        lastStrikeRow = row;
        lastStrikeCol = col;
        int currentIndex = get1DIndex(row, col);

        // Connect to Virtual Top if in the first row
        if (row == 0) {
            uf.union(currentIndex, virtualTop);
        }
        
        // Connect to Virtual Bottom if in the last row
        if (row == gridSize - 1) {
            uf.union(currentIndex, virtualBottom);
        }

        // 8-Way Directional Check (Cardinals & Diagonals)
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        
        for (int[] dir : directions) {
            int neighborRow = row + dir[0];
            int neighborCol = col + dir[1];

            // If neighbor is within bounds and is open, connect them
            if (neighborRow >= 0 && neighborRow < gridSize && neighborCol >= 0 && neighborCol < gridSize && isOpen[neighborRow][neighborCol]) {
                uf.union(currentIndex, get1DIndex(neighborRow, neighborCol));
            }
        }

        // Check if the system percolates after this strike
        if (uf.connected(virtualTop, virtualBottom)) {
            percolatesFlag = true;
        }
    }

    /**
     * Returns true if the system percolates (top row is connected to bottom row).
     */
    public boolean percolates() {
        return percolatesFlag;
    }

    /**
     * Generates a visual string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (i == lastStrikeRow && j == lastStrikeCol && percolatesFlag) {
                    sb.append("* "); // The exact strike that caused the short circuit
                } else if (isOpen[i][j]) {
                    sb.append("X "); // Open site (Conductor)
                } else {
                    sb.append(". "); // Blocked site (Insulator)
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
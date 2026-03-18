/**
 * Legacy implementation of the Percolation problem using Recursive DFS.
 * This class accurately represents the original, unoptimized approach.
 * It is kept strictly for benchmarking and educational purposes to demonstrate
 * the devastating performance impact of O(N^2) recursive operations and StackOverflow limits.
 */
public class LegacyPercolation {
    private boolean[][] isOpen;
    private boolean[][] visited;
    private int gridSize;
    private boolean percolatesFlag;

    public LegacyPercolation(int n) {
        this.gridSize = n;
        this.isOpen = new boolean[n][n];
        this.visited = new boolean[n][n];
        this.percolatesFlag = false;
    }

    /**
     * Clears the visited array. Required before every DFS traversal.
     */
    private void clearVisited() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                visited[i][j] = false;
            }
        }
    }

    /**
     * Recursive Depth-First Search to find a path to the target boundary.
     * @param row current row
     * @param col current col
     * @param searchingTop true if searching for row 0, false for row N-1
     * @return true if the boundary is reached
     */
    private boolean dfs(int row, int col, boolean searchingTop) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) return false;
        if (!isOpen[row][col] || visited[row][col]) return false;

        visited[row][col] = true;

        // Base case: Reached the target boundary
        if (searchingTop && row == 0) return true;
        if (!searchingTop && row == gridSize - 1) return true;

        // 8-Way Recursive Search
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] dir : directions) {
            if (dfs(row + dir[0], col + dir[1], searchingTop)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Simulates a cosmic ray strike using the legacy double-DFS approach.
     */
    public void strike(int row, int col) {
        if (percolatesFlag || isOpen[row][col]) return;
        isOpen[row][col] = true;

        clearVisited();
        boolean reachesTop = dfs(row, col, true);
        
        clearVisited();
        boolean reachesBottom = dfs(row, col, false);

        if (reachesTop && reachesBottom) {
            percolatesFlag = true;
        }
    }

    public boolean percolates() {
        return percolatesFlag;
    }
}
/**
 * Disjoint-Set (Union-Find) data structure.
 * Implements Path Compression and Union by Size for near O(1) amortized time complexity.
 */
public class UnionFind {
    private int[] parent;
    private int[] size;

    /**
     * Initializes an empty union-find data structure with n isolated sets.
     * @param n the number of elements
     */
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the canonical element (root) of the set containing element p.
     * Includes Path Compression to flatten the tree.
     * @param p an element
     * @return the root of the set containing p
     */
    public int find(int p) {
        int root = p;
        while (root != parent[root]) {
            root = parent[root];
        }
        
        // Path Compression
        int current = p;
        while (current != root) {
            int next = parent[current];
            parent[current] = root;
            current = next;
        }
        return root;
    }

    /**
     * Merges the set containing element p with the set containing element q.
     * Uses Union by Size to maintain a flat tree structure.
     * @param p one element
     * @param q the other element
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // Union by Size
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    /**
     * Returns true if the two elements are in the same set.
     * @param p one element
     * @param q the other element
     * @return true if connected, false otherwise
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
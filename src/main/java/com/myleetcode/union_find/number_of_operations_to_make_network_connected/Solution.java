package com.myleetcode.union_find.number_of_operations_to_make_network_connected;

class Solution {
    public int makeConnected(int n, int[][] connections) {
        return makeConnectedByUnionFind(n, connections);
    }

    /*
    Union Find
    Hardest part:
    get all necessary edges
    caculate edges number still needed

    Be aware, there're some solutions like this: https://leetcode.com/problems/number-of-operations-to-make-network-connected/discuss/477660/Java-Count-number-of-connected-components-Clean-code
    The tricky part is they do a check before all: if (connections.length < n - 1) return -1; // To connect all nodes need at least n-1 edges
    If given parameters pass the check, after Union Find, we only need to return count-1 because this is the extra edges number we needed. This is smarter than the solution below which caculate all necessary edges and all needed edges, then compare them with the total given edges. But both correct and no big-O difference.

    TC: O(M), M is connections length. Actually is O(M * (log*(N))) and log*(N) is approxmately O(1).
    SC: O(N), N is total computer number.
    */

    class UnionFind {
        private int count;
        private int[] roots;
        private int[] ranks;

        public UnionFind(int n) {
            this.count = n;
            this.roots = new int[n];
            this.ranks = new int[n];

            for (int i = 0; i < n; i++) {
                this.roots[i] = i;
            }
        }

        public int find(int node) {
            if (node != this.roots[node]) {
                this.roots[node] = find(this.roots[node]);
            }

            return this.roots[node];
        }

        public void union(int u, int v) {
            int uRoot = find(u);
            int vRoot = find(v);

            if (uRoot == vRoot) {
                return;
            }

            if (this.ranks[uRoot] > this.ranks[vRoot]) {
                this.roots[vRoot] = uRoot;
            } else if (this.ranks[vRoot] > this.ranks[uRoot]) {
                this.roots[uRoot] = vRoot;
            } else {
                this.roots[vRoot] = uRoot;
                this.ranks[uRoot]++;
            }
            this.count--;
        }

        public int[] getRoots() {
            return this.roots;
        }

        public int getCount() {
            return this.count;
        }
    }

    private int makeConnectedByUnionFind(int n, int[][] connections) {
        if (n <= 0 || connections == null || connections.length == 0 || connections[0] == null || connections[0].length == 0) {
            return 0;
        }

        // 0. Smart check part.
        if (connections.length < n - 1) {
            return -1;
        }

        // 1. Union Find.
        // Have n computers.
        UnionFind uf = new UnionFind(n);
        // Have len connections.
        int len = connections.length;
        for (int i = 0; i < len; i++) {
            uf.union(connections[i][0], connections[i][1]);
        }

        // If we do the 0. Smart check part. then we could directly return uf.getCount() - 1, no need for the 2 and 3 step.
        return uf.getCount() - 1;

        /*
        // 2. Find all necessary edges. For node i, if its final root is not itself, then it must need a edge to connect to its root.
        int edges = 0;
        int[] roots = uf.getRoots();
        for(int i = 0; i < n; i++){
            if(uf.find(i) != i){
                edges++;
            }
        }

        // 3. Since have count components, so we need another count-1 cabls. Since we have len cables, so if len>(edges + count-1) then we could connect all computers.
        int edgeNeed = uf.getCount() - 1;
        if(len >= edgeNeed + edges){
            return edgeNeed;
        }
        return -1;
        */
    }
}

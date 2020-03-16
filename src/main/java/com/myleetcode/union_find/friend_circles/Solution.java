package com.myleetcode.union_find.friend_circles;

class Solution {
    public int findCircleNum(int[][] M) {
        // return findCircleNumByDFSAll(M);

        return findCircleNumByUnionFind(M);
    }

    /*
    Union Find
    https://leetcode.com/problems/friend-circles/discuss/101336/Java-solution-Union-Find

    */
    // UnionFind template.
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

        // Find root of given node.
        public int find(int node) {
            if (node != this.roots[node]) {
                this.roots[node] = find(this.roots[node]);
            }

            return this.roots[node];
        }

        // Union the given two nodes' trees.
        public void union(int nodeI, int nodeJ) {
            int nodeIRoot = find(nodeI);
            int nodeJRoot = find(nodeJ);

            if (nodeIRoot == nodeJRoot) {
                return;
            }

            if (this.ranks[nodeIRoot] > this.ranks[nodeJRoot]) {
                this.roots[nodeJRoot] = nodeIRoot;
            } else if (this.ranks[nodeIRoot] < this.ranks[nodeJRoot]) {
                this.roots[nodeIRoot] = nodeJRoot;
            } else {
                this.roots[nodeJRoot] = nodeIRoot;
                this.ranks[nodeJRoot]++;
            }

            this.count--;
        }

        // Get connected components number.
        public int getCount() {
            return this.count;
        }
    }

    private int findCircleNumByUnionFind(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        UnionFind uf = new UnionFind(rowLen);
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (matrix[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.getCount();
    }

    /*
    https://leetcode.com/problems/friend-circles/discuss/101338/Neat-DFS-java-solution

    kepp digging the firend relationship. For a given person i, we do dfs to search all its friends and everytime we meet a frind we do recursion. At last, we could exhaust all the friends of person i, then count 1.

    TC: O(R * C)
    SC: O(R * C)
    */
    private int findCircleNumByDFSAll(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int count = 0;
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        boolean[] visited = new boolean[rowLen];

        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < rowLen; i++) {
            if (!visited[i]) {
                dfs(matrix, i, visited);
                count++;
            }
        }

        return count;
    }

    private void dfs(int[][] matrix, int rowIdx, boolean[] visited) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        for (int i = 0; i < colLen; i++) {
            if (matrix[rowIdx][i] == 1 && !visited[i]) {
                visited[i] = true;
                dfs(matrix, i, visited);
            }
        }
    }
}
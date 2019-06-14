package com.myleetcode.depth_first_search.longest_increasing_path_in_a_matrix;

class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        return longestIncreasingPathByDFSWithMemo(matrix);
    }

    // intuition: DFS with Memoization
    // TC: O(V + E)
    // SC: O(M * N), memo use O(M*N) space, recursion use O(E) space.
    // first, we know this is a Graph problem
    // second, if given a vertex U in graph, we know how to get it's LIP, just do DFS and visit all paths and get the Max one
    // so, for this problem, we need do the second step on every vertex
    // BUT, if we use Naive DFS on every nodes, that will give us expenational TC, and there're a lot of duplicate caculations, so we could use a memo array to record the vertex that we already have its result(if we have visited the node's 4 direcs, then we have known all paths of it, so we remember it)
    // HERE we do not use the visited array for help, because we are use the matrix[x][y] > matrix[i][j] to guarantee we will not visit the same cell again: https://leetcode.com/problems/longest-increasing-path-in-a-matrix/discuss/78308/15ms-Concise-Java-Solution
    private int longestIncreasingPathByDFSWithMemo(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        // remember the cells we visited
        // boolean[][] visited = new boolean[rowLen][colLen];// no need

        // remember the LIP of one vertex
        int[][] memo = new int[rowLen][colLen];

        // directions
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // max
        int max = 0;

        // dfs all
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // dfs to find the LIP of this vertex
                max = Math.max(max, dfs(matrix, direcs, i, j, memo));
            }
        }

        return max;
    }

    private int dfs(int[][] matrix, int[][] direcs, int rowIdx, int colIdx, int[][] memo ){

        // return memo
        if(memo[rowIdx][colIdx] != 0){
            return memo[rowIdx][colIdx];
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int max = 0;

        // explore children
        for(int[] direc: direcs){
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // invalid coordination, next one
            if(nextRowIdx < 0 || nextRowIdx > (rowLen - 1) || nextColIdx < 0 || nextColIdx > (colLen - 1)){
                continue;
            }
            // not Increasing Path, next one
            if(matrix[rowIdx][colIdx] >= matrix[nextRowIdx][nextColIdx]){
                continue;
            }

            // caculate
            max = Math.max(max, dfs(matrix, direcs, nextRowIdx, nextColIdx, memo));
        }

        // after get the max of children from all 4 directions, put into the memo
        memo[rowIdx][colIdx] = 1 + max;

        return memo[rowIdx][colIdx];
    }

}

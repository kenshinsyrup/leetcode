package com.myleetcode.depth_first_search.number_of_distinct_islands;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int numDistinctIslands(int[][] grid) {
        return numDistinctIslandsByDFS(grid);
    }

    /*
    DFS with Path Signature:
    This problem is a transformation of Number of Islands, just need to keep the path signature for hash key to distinguish each path(island).

    N is number of nodes.
    TC: O(N)
    SC: O(N)
    */

    /**
     * WARNING: DO NOT FORGET to add path for backtracking, otherwise, we may have same result when we count two
     * distinct islands in some cases
     * <p>
     * eg:              1 1 1   and    1 1 0
     * 0 1 0          0 1 1
     * with b:          rdbr           rdr
     * without b:       rdr            rdr
     */
    private int numDistinctIslandsByDFS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        Set<String> distinctPathSet = new HashSet<>();

        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    // Clear sb.
                    sb = new StringBuilder();

                    // When want to start exploring a island, use sign O for Origin to mark the start point of this island.
                    dfs(grid, i, j, visited, direcs, "O", sb);

                    // Record this island.
                    distinctPathSet.add(sb.toString());
                }
            }
        }

        return distinctPathSet.size();

    }

    private void dfs(int[][] grid, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs, String sign, StringBuilder sb) {
        // When enter a cell, append its sign.
        sb.append(sign);

        int rowLen = grid.length;
        int colLen = grid[0].length;

        visited[rowIdx][colIdx] = true;

        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }
            if (grid[nextRowIdx][nextColIdx] == 0) {
                continue;
            }

            // To Left, 1
            String nextSign = "";
            if (direc[0] == 0 && direc[1] == -1) {
                nextSign = "L";
            } else if (direc[0] == 0 && direc[1] == 1) { // To Right, 2
                nextSign = "R";
            } else if (direc[0] == -1 && direc[1] == 0) { // To Up, 3
                nextSign = "U";
            } else if (direc[0] == 1 && direc[1] == 0) { // To Down, 4
                nextSign = "D";
            }

            dfs(grid, nextRowIdx, nextColIdx, visited, direcs, nextSign, sb);
        }

        // When leave a cell, append a B for Back to make path unique.
        sb.append("B");
    }
}

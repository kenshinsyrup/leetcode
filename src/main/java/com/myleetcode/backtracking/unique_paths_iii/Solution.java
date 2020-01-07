package com.myleetcode.backtracking.unique_paths_iii;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int uniquePathsIII(int[][] grid) {
        return uniquePathsIIIByBacktracking(grid);
    }


    /*
    Backtracking find all paths.

    TC: O(4 ^ (R * C))
    SC: O(R * C)
    */
    private int uniquePathsIIIByBacktracking(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        // Find the start point.
        int srcRowIdx = 0;
        int srcColIdx = 0;
        int count = rowLen * colLen;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 1) {
                    srcRowIdx = i;
                    srcColIdx = j;
                } else if (grid[i][j] == -1) {
                    count--;
                }
            }
        }

        // Help mark which cell is visited in one exploration.
        boolean[][] visited = new boolean[rowLen][colLen];
        // Store all paths to end.
        List<List<Integer>> paths = new ArrayList<>();
        // Help record one path to end.
        List<Integer> snippet = new ArrayList<>();
        snippet.add(grid[srcRowIdx][srcColIdx]);

        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        backtracking(srcRowIdx, srcColIdx, grid, direcs, visited, paths, snippet, count);

        return paths.size();
    }

    private void backtracking(int rowIdx, int colIdx, int[][] grid, int[][] direcs, boolean[][] visited, List<List<Integer>> paths, List<Integer> snippet, int count) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        if (grid[rowIdx][colIdx] == 2) {
            if (snippet.size() == count) {
                paths.add(new ArrayList<>(snippet));
            }
            return;
        }

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

            if (grid[nextRowIdx][nextColIdx] == -1) {
                continue;
            }

            snippet.add(grid[nextRowIdx][nextColIdx]);

            backtracking(nextRowIdx, nextColIdx, grid, direcs, visited, paths, snippet, count);

            snippet.remove(snippet.size() - 1);
        }

        visited[rowIdx][colIdx] = false;
    }
}

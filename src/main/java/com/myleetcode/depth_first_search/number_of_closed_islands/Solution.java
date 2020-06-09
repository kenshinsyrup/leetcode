package com.myleetcode.depth_first_search.number_of_closed_islands;

public class Solution {
    public int closedIsland(int[][] grid) {
        return closedIslandByDFS(grid);
    }

    /*
    Number of Islands transformation problem.

    Closed islands are silands that has at least one cell connecting to edge.

    TC: O(R*C)
    SC: O(R*C)
    */
    private int closedIslandByDFS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int count = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    Arg arg = new Arg(true);
                    findClosedIsland(grid, i, j, visited, direcs, arg);
                    if (arg.isClosed) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private void findClosedIsland(int[][] grid, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs, Arg arg) {
        visited[rowIdx][colIdx] = true;

        int rowLen = grid.length;
        int colLen = grid[0].length;
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                arg.isClosed = false; // Land connects bound, not closed.
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }
            if (grid[nextRowIdx][nextColIdx] == 0) {
                findClosedIsland(grid, nextRowIdx, nextColIdx, visited, direcs, arg);
            }
        }
    }

    class Arg {
        boolean isClosed;

        public Arg(boolean isClosed) {
            this.isClosed = isClosed;
        }
    }
}

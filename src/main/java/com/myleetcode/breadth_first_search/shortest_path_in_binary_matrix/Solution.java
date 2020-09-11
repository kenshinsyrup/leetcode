package com.myleetcode.breadth_first_search.shortest_path_in_binary_matrix;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        return shortestPathBinaryMatrixByBFS(grid);
    }

    /*
    BFS

    TC: O(R * C)
    SC: O(R * C)
    */
    private int shortestPathBinaryMatrixByBFS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return -1;
        }
        if (grid[0][0] == 1) {
            return -1;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];

        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        Deque<int[]> cellQueue = new ArrayDeque<>();
        cellQueue.offer(new int[]{0, 0});
        int steps = 0;
        while (!cellQueue.isEmpty()) {
            int size = cellQueue.size();

            for (int i = 0; i < size; i++) {
                int[] curCell = cellQueue.poll();
                int curX = curCell[0];
                int curY = curCell[1];
                if (curX == rowLen - 1 && curY == colLen - 1) {
                    return steps + 1;
                }

                for (int[] direc : direcs) {
                    int nextX = curX + direc[0];
                    int nextY = curY + direc[1];

                    if (nextX < 0 || nextX >= rowLen || nextY < 0 || nextY >= colLen) {
                        continue;
                    }
                    if (grid[nextX][nextY] == 1 || visited[nextX][nextY]) {
                        continue;
                    }

                    visited[nextX][nextY] = true;
                    cellQueue.offer(new int[]{nextX, nextY});
                }
            }

            steps++;
        }

        return -1;
    }
}

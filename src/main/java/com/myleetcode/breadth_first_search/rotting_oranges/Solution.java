package com.myleetcode.breadth_first_search.rotting_oranges;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    public int orangesRotting(int[][] grid) {
        return orangesRottingByBFSAll(grid);
    }


    /*
    BFS from all roots. Transfomation of BFS from two ends.

    TC: O(R * C)
    SC: O(R * C)
    */
    private int orangesRottingByBFSAll(int[][] grid) {
        // Special case.
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        // Find all rotten oranges at start.
        List<int[]> rottenList = new ArrayList<>();
        int rowLen = grid.length;
        int colLen = grid[0].length;
        boolean noFresh = true;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 2) {
                    rottenList.add(new int[]{i, j});
                }
                if (grid[i][j] == 1) {
                    noFresh = false;
                }
            }
        }
        // No rotten: if no fresh either, return 0; otherwise, return -1.
        if (rottenList.size() == 0) {
            if (noFresh) {
                return 0;
            }
            return -1;
        }

        // Do the BFS from all roots. Root is a rotten orange at start.
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int atLeast = bfs(rottenList, grid, direcs);

        // Check if there's any oragne that is not rotten, if there is, return -1.
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }

        return atLeast;

    }

    // BFS from all roots.
    private int bfs(List<int[]> rottenList, int[][] grid, int[][] direcs) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        int time = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        for (int[] rottenRoot : rottenList) {
            queue.offer(rottenRoot);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Level by level.
            for (int i = 0; i < size; i++) {
                int[] curNode = queue.poll();
                int rowIdx = curNode[0];
                int colIdx = curNode[1];

                for (int[] direc : direcs) {
                    int nextRowIdx = rowIdx + direc[0];
                    int nextColIdx = colIdx + direc[1];

                    // Out of boundary.
                    if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                        continue;
                    }

                    // 0, no orange, continue; 2, already rotten, continue.
                    if (grid[nextRowIdx][nextColIdx] == 0 || grid[nextRowIdx][nextColIdx] == 2) {
                        continue;
                    }

                    // 1, rot it and offer to queue.
                    if (grid[nextRowIdx][nextColIdx] == 1) {
                        grid[nextRowIdx][nextColIdx] = 2;
                        queue.offer(new int[]{nextRowIdx, nextColIdx});
                    }
                }
            }

            // Time elapse.
            time++;
        }

        // The rotten orange at start is not count in time elapse.
        return time - 1;
    }
}
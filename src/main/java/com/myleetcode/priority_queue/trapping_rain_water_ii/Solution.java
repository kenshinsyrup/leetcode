package com.myleetcode.priority_queue.trapping_rain_water_ii;

import java.util.PriorityQueue;

public class Solution {
    public int trapRainWater(int[][] heightMap) {
        return trapRainWaterByPQ(heightMap);
    }

    /*
    Priority Queue
    https://leetcode.com/problems/trapping-rain-water-ii/discuss/89461/Java-solution-using-PriorityQueue
    https://leetcode.com/problems/trapping-rain-water-ii/discuss/89472/Visualization-No-Code

    */
    class Cell {
        int rowIdx;
        int colIdx;
        int height;

        public Cell(int rowIdx, int colIdx, int height) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
            this.height = height;
        }
    }

    private int trapRainWaterByPQ(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        PriorityQueue<Cell> cellPQ = new PriorityQueue<>((Cell a, Cell b) -> a.height - b.height);

        int rowLen = grid.length;
        int colLen = grid[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        // Put outest cells into PQ
        for (int i = 0; i < rowLen; i++) {
            cellPQ.offer(new Cell(i, 0, grid[i][0]));
            cellPQ.offer(new Cell(i, colLen - 1, grid[i][colLen - 1]));

            visited[i][0] = true;
            visited[i][colLen - 1] = true;
        }
        for (int j = 0; j < colLen; j++) {
            if (j == 0 || j == colLen - 1) {// Already processed.
                continue;
            }

            cellPQ.offer(new Cell(0, j, grid[0][j]));
            cellPQ.offer(new Cell(rowLen - 1, j, grid[rowLen - 1][j]));

            visited[0][j] = true;
            visited[rowLen - 1][j] = true;
        }

        // From the borders, pick the lowest cell visited and check its neighbors:
        // If the neighbor is lower, collect the water it can trap and update its height as its height plus the water trapped.
        // Add all its neighbors to the queue and marked as visited.
        int ret = 0;
        int[][] direcs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!cellPQ.isEmpty()) {
            Cell cell = cellPQ.poll();

            for (int[] direc : direcs) {
                int nextRowIdx = cell.rowIdx + direc[0];
                int nextColIdx = cell.colIdx + direc[1];

                if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen || visited[nextRowIdx][nextColIdx]) {
                    continue;
                }

                // If we are heading down, means trap water. And we update next cell's height to cell.height means it's already trapped water so new height is cell.height.
                int nextCellHeight = grid[nextRowIdx][nextColIdx];
                if (grid[nextRowIdx][nextColIdx] < cell.height) {
                    ret += cell.height - grid[nextRowIdx][nextColIdx];
                    nextCellHeight = cell.height;
                }

                // Offer nextCell to PQ.
                cellPQ.offer(new Cell(nextRowIdx, nextColIdx, nextCellHeight));
                visited[nextRowIdx][nextColIdx] = true;
            }
        }

        return ret;

    }
}

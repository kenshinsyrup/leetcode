package com.myleetcode.graph.dijkstra.swim_in_rising_water;

import java.util.PriorityQueue;

public class Solution {
    public int swimInWater(int[][] grid) {
        return swimInWaterByDijkstra(grid);
    }

    /*
    Dijkstra's Algo
    https://leetcode.com/problems/swim-in-rising-water/discuss/115696/Simple-Java-solution-using-priority-queue

    TC: O(R * C * log(R * C))
    SC: O(R * C)
    */
    private int swimInWaterByDijkstra(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int len = grid.length;
        boolean[][] visited = new boolean[len][len];
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int level = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (grid[a[0]][a[1]] - grid[b[0]][b[1]]));
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] curNode = pq.poll();
            int rowIdx = curNode[0];
            int colIdx = curNode[1];

            // Level update.
            level = Math.max(level, grid[rowIdx][colIdx]);

            // Visited.
            visited[rowIdx][colIdx] = true;

            // Reach destination.
            if (rowIdx == len - 1 && colIdx == len - 1) {
                break;
            }

            // Explore neighbors and offer to pq.
            for (int[] direc : direcs) {
                int nextRowIdx = rowIdx + direc[0];
                int nextColIdx = colIdx + direc[1];

                if (nextRowIdx < 0 || nextRowIdx >= len || nextColIdx < 0 || nextColIdx >= len) {
                    continue;
                }
                if (visited[nextRowIdx][nextColIdx]) {
                    continue;
                }

                pq.offer(new int[]{nextRowIdx, nextColIdx});
            }
        }

        return level;

    }
}

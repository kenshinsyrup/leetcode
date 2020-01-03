package com.myleetcode.breadth_first_search.island_perimeter;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int islandPerimeter(int[][] grid) {
        return islandPerimeterByBFS(grid);
    }

    /*
    Do BFS, when process a island if it has a water neighbor, count 1 in perimeter.

    TC: O(R * C).
    SC: O(R * C).

    bfsByIsland only cost O(NumOfIslands) but worst case the islands number is R*C.
    bfsByWater cost O(R*C) because it literally visited all cells, so could just use a nested for loop to solve it, no need with bfs.
    */

    private int islandPerimeterByBFS(int[][] grid) {
        // Special case.
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }


        int rowLen = grid.length;
        int colLen = grid[0].length;
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[rowLen][colLen];

        // Find a cell belongs to the island as island root.
        int[] islandRoot = new int[]{-1, -1};
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == 1) {
                    islandRoot[0] = i;
                    islandRoot[1] = j;
                    break;
                }
            }
        }

        return bfsByIsland(islandRoot, grid, direcs, visited);

        /*
        // Find a cell belongs to the water as water root.
        int[] waterRoot = new int[]{-1, -1};
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(grid[i][j] == 0){
                    waterRoot[0] = i;
                    waterRoot[1] = j;
                    break;
                }
            }
        }

        // Perimeter of islands adjcent to water.
        int waterAdjLen = 0;
        if(waterRoot[0] != -1 && waterRoot[1] != -1){
            waterAdjLen = bfsByWater(waterRoot, grid, direcs, visited);
        }

        // Perimeter should count the edge.
        int perimeter = waterAdjLen;
        for(int i = 0; i < rowLen; i++){
            if(grid[i][0] == 1){
                perimeter += 1;
            }
            if(grid[i][colLen - 1] == 1){
                perimeter += 1;
            }
        }
        for(int j = 0; j < colLen; j++){
            if(grid[0][j] == 1){
                perimeter += 1;
            }
            if(grid[rowLen - 1][j] == 1){
                perimeter += 1;
            }
        }

        return perimeter;
        */

    }

    // BFS by process island. Better than by water. Because there's only exactly one whole island, so only visited all island cells, though the worst case is still R*C.
    private int bfsByIsland(int[] root, int[][] grid, int[][] direcs, boolean[][] visited) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        int perimeter = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int rowIdx = curNode[0];
            int colIdx = curNode[1];

            // !!! Visited check here because we may push a unvisited island cell multiple times into the queue.
            if (visited[rowIdx][colIdx]) {
                continue;
            }

            // Process the node.
            for (int[] direc : direcs) {
                int nextRowIdx = rowIdx + direc[0];
                int nextColIdx = colIdx + direc[1];

                // Out of boundary, perimeter count 1.
                if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                    perimeter += 1;
                    continue;
                }

                // Water, perimeter count 1.
                if (grid[nextRowIdx][nextColIdx] == 0) {
                    perimeter += 1;
                } else {
                    // Island, enqueue it. Here this unvisied island may already existed in the queue but it's time consuming to check this, so we just push in and check visited when a node is polled.
                    queue.offer(new int[]{nextRowIdx, nextColIdx});
                }
            }

            // Mark current node as visited.
            visited[rowIdx][colIdx] = true;
        }

        return perimeter;

    }

    // BFS by process water.
    private int bfsByWater(int[] root, int[][] grid, int[][] direcs, boolean[][] visited) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        int perimeter = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int[] curNode = queue.poll();
            int rowIdx = curNode[0];
            int colIdx = curNode[1];

            // If visited, continue.
            if (visited[rowIdx][colIdx]) {
                continue;
            }

            for (int[] direc : direcs) {
                int nextRowIdx = rowIdx + direc[0];
                int nextColIdx = colIdx + direc[1];

                if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                    continue;
                }

                // If current node is water and find a adjcent node which is island, perimeter count 1.
                if (grid[rowIdx][colIdx] == 0 && grid[nextRowIdx][nextColIdx] == 1) {
                    perimeter += 1;
                }

                // Offer this adjcent unvisited node(no mater water or island) to queue.
                queue.offer(new int[]{nextRowIdx, nextColIdx});
            }

            // Mark current node as visited.
            visited[rowIdx][colIdx] = true;
        }

        return perimeter;
    }
}
package com.myleetcode.breadth_first_search.pacific_atlantic_water_flow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        // return pacificAtlanticByDFSAll(matrix);
        return pacificAtlanticByBFS(matrix);
    }

    /*
    Improve:
    Think in the other way, we don't check whether a cell could flood into Pacific and Atlantic, we check whether Pacific and Atlantic could flood into the matrix and reach at same cell.
    https://leetcode.com/problems/pacific-atlantic-water-flow/discuss/90733/Java-BFS-and-DFS-from-Ocean

    TC: O(R * C)
    SC: O(R * C)
    */
    public List<List<Integer>> pacificAtlanticByBFS(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        // 1. Init.
        //One visited map for each ocean
        boolean[][] pacific = new boolean[rowLen][colLen];
        boolean[][] atlantic = new boolean[rowLen][colLen];
        Deque<int[]> pQueue = new ArrayDeque<>();
        Deque<int[]> aQueue = new ArrayDeque<>();
        for (int i = 0; i < rowLen; i++) { //Vertical border
            pacific[i][0] = true;
            atlantic[i][colLen - 1] = true;
            pQueue.offer(new int[]{i, 0});
            aQueue.offer(new int[]{i, colLen - 1});
        }
        for (int i = 0; i < colLen; i++) { //Horizontal border
            pacific[0][i] = true;
            atlantic[rowLen - 1][i] = true;
            pQueue.offer(new int[]{0, i});
            aQueue.offer(new int[]{rowLen - 1, i});
        }

        // 2. BFS flood from Pacific/Atlantic to cells.
        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        bfs(matrix, pQueue, pacific, direcs);
        bfs(matrix, aQueue, atlantic, direcs);

        // 3. Get res.
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    List<Integer> innerList = new ArrayList<>();
                    innerList.add(i);
                    innerList.add(j);
                    res.add(innerList);
                }
            }
        }

        return res;
    }

    public void bfs(int[][] matrix, Deque<int[]> queue, boolean[][] visited, int[][] direcs) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] direc : direcs) {
                int x = cur[0] + direc[0];
                int y = cur[1] + direc[1];
                if (x < 0 || x >= rowLen || y < 0 || y >= colLen || visited[x][y] || matrix[x][y] < matrix[cur[0]][cur[1]]) {
                    continue;
                }

                visited[x][y] = true;

                queue.offer(new int[]{x, y});
            }
        }
    }

    /*
    DFSAll

    TC: O((R * C) ^ 2)
    SC: O(R * C)
    */
    private List<List<Integer>> pacificAtlanticByDFSAll(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[rowLen][colLen];
        Arg arg = new Arg();
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                visited = new boolean[rowLen][colLen];
                arg = new Arg();
                if (dfs(matrix, i, j, visited, direcs, res, arg)) {
                    List<Integer> snippet = new ArrayList<>();
                    snippet.add(i);
                    snippet.add(j);
                    res.add(snippet);
                }
            }
        }

        return res;
    }

    private boolean dfs(int[][] matrix, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs, List<List<Integer>> res, Arg arg) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        visited[rowIdx][colIdx] = true;

        // Check ocean.
        if (rowIdx == 0 || colIdx == 0) {
            arg.isPacific = true;
        }
        if (rowIdx == rowLen - 1 || colIdx == colLen - 1) {
            arg.isAtlantic = true;
        }
        if (arg.isPacific && arg.isAtlantic) {
            return true;
        }

        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }
            if (matrix[nextRowIdx][nextColIdx] > matrix[rowIdx][colIdx]) {
                continue;
            }

            if (dfs(matrix, nextRowIdx, nextColIdx, visited, direcs, res, arg)) {
                return true;
            }
        }

        return false;
    }

    private class Arg {
        boolean isPacific;
        boolean isAtlantic;

        public Arg() {
            this.isPacific = false;
            this.isAtlantic = false;
        }
    }
}

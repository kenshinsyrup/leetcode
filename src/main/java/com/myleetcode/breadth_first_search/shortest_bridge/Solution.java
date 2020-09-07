package com.myleetcode.breadth_first_search.shortest_bridge;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int shortestBridge(int[][] A) {
        return shortestBridgeByDFSAndBFS(A);
    }

    /*
    ONLY TWO islands here.
    DFS to find the two islands, mark with islandID 1 or 2.
    BFS from island 1 to try to reach island 2, and record the shortest bridge.
    */
    private int shortestBridgeByDFSAndBFS(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        // 1. Mark cells with their corresponding islandID.
        int[][] islands = new int[rowLen][colLen];
        boolean[][] visited = new boolean[rowLen][colLen];
        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int islandID = 1;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (matrix[i][j] == 1 && !visited[i][j]) {
                    dfs(matrix, i, j, visited, islands, direcs, islandID);
                    islandID++;
                }
            }
        }

        // 2. Try to build bridge.
        return buildShortestBridge(islands);
    }

    private void dfs(int[][] matrix, int rowIdx, int colIdx, boolean[][] visited, int[][] islands, int[][] direcs, int islandID) {
        islands[rowIdx][colIdx] = islandID;
        visited[rowIdx][colIdx] = true;

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx] || matrix[nextRowIdx][nextColIdx] != 1) {
                continue;
            }

            dfs(matrix, nextRowIdx, nextColIdx, visited, islands, direcs, islandID);
        }
    }

    private int buildShortestBridge(int[][] islands) {
        int rowLen = islands.length;
        int colLen = islands[0].length;

        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (islands[i][j] == 1) {
                    ret = Math.min(ret, bfs(islands, i, j));
                }
            }
        }

        return ret;
    }

    private int bfs(int[][] islands, int rowIdx, int colIdx) {
        int rowLen = islands.length;
        int colLen = islands[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Deque<int[]> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(new int[]{rowIdx, colIdx});
        int len = 0;
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();

            for (int i = 0; i < size; i++) {
                int[] curNode = nodeQueue.poll();
                int curRowIdx = curNode[0];
                int curColIdx = curNode[1];
                if (islands[curRowIdx][curColIdx] == 2) {
                    return len - 1;
                }

                for (int[] direc : direcs) {
                    int nextRowIdx = curRowIdx + direc[0];
                    int nextColIdx = curColIdx + direc[1];
                    if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                        continue;
                    }
                    if (visited[nextRowIdx][nextColIdx] || islands[nextRowIdx][nextColIdx] == 1) {
                        continue;
                    }

                    visited[nextRowIdx][nextColIdx] = true;
                    nodeQueue.offer(new int[]{nextRowIdx, nextColIdx});
                }
            }

            len++;

        }

        return Integer.MAX_VALUE;
    }
}

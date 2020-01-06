package com.myleetcode.graph.path_with_maximum_minimum_value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int maximumMinimumPath(int[][] A) {
        // return maximumMinimumPathByDFS(A); // TLE
        return maximumMinimumPathByDijkstra(A);
    }

    /*
    https://leetcode.com/problems/path-with-maximum-minimum-value/discuss/324923/C%2B%2BJavaGo-Clear-Code-Dijkstra-Algorithm

    Dijkstra transformation:
    To find the max of min value in every path: BFS Dijkstra algorithm, use a priority queue to choose the next step with the maximum value. Keep track of the mininum value along the path.
    In every path, we visited some cells from src to dest node. The min value of this path is the min value of all theses cells.
    With the Dijkstra algo, the "shortest" path is the path has the min value that larger than all min values in other paths.
    For every cell, if we could guarantee we could reach next neighbor cell by max neighbor value. When we reach the graph end, since every cell we choose the max neighbor cell, and we has the same start node src, so we could guarantee this path has the max min value.

    TC: (R * C * log(R * C))
    SC: O(R * C)
    */
    private int maximumMinimumPathByDijkstra(int[][] graph) {
        // Special case.
        if (graph == null || graph.length == 0 || graph[0] == null || graph[0].length == 0) {
            return 0;
        }

        int rowLen = graph.length;
        int colLen = graph[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];

        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int maxMin = Integer.MAX_VALUE;

        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] cell1, int[] cell2) {
                return graph[cell2[0]][cell2[1]] - graph[cell1[0]][cell1[1]];
            }
        });
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] curNode = pq.poll();
            int rowIdx = curNode[0];
            int colIdx = curNode[1];

            visited[rowIdx][colIdx] = true;

            maxMin = Math.min(graph[rowIdx][colIdx], maxMin);
            if (rowIdx == rowLen - 1 && colIdx == colLen - 1) {
                return maxMin;
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

                pq.offer(new int[]{nextRowIdx, nextColIdx});
            }
        }

        return -1;
    }


    /*
    DFS backtracking:
    Find all the paths and get their score, at last we choose the max one.

    TLE.

    TC: O(4 ^ (R * C))
    SC: O(4 ^ (R * C))
    */
    private int maximumMinimumPathByDFS(int[][] graph) {
        // Special case.
        if (graph == null || graph.length == 0 || graph[0] == null || graph[0].length == 0) {
            return 0;
        }

        int rowLen = graph.length;
        int colLen = graph[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];

        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        List<List<Integer>> paths = new ArrayList<>();
        int[] srcNode = new int[]{0, 0};
        List<Integer> snippet = new ArrayList<>();
        snippet.add(graph[0][0]);
        dfs(srcNode, paths, graph, direcs, visited, snippet);

        // Find the min value in every path and choose the max one as answer.
        int max = Integer.MIN_VALUE;
        for (List<Integer> path : paths) {
            int min = Integer.MAX_VALUE;
            for (int v : path) {
                min = Math.min(min, v);
            }

            max = Math.max(max, min);
        }

        return max;
    }

    private void dfs(int[] curNode, List<List<Integer>> paths, int[][] graph, int[][] direcs, boolean[][] visited, List<Integer> snippet) {
        int rowLen = graph.length;
        int colLen = graph[0].length;

        int rowIdx = curNode[0];
        int colIdx = curNode[1];

        // If reach final destination, add path snippet to paths list.
        if (rowIdx == rowLen - 1 && colIdx == colLen - 1) {
            paths.add(new ArrayList<>(snippet));
            return;
        }

        // Current node mark as visited, means in this path snippet.
        visited[rowIdx][colIdx] = true;

        // Use DFS to try to find next node in the path to destination.
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // Out of range, continue.
            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }

            // Visited, avoid it, continue.
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }

            // Add this node to path snippet.
            snippet.add(graph[nextRowIdx][nextColIdx]);

            // DFS explore.
            dfs(new int[]{nextRowIdx, nextColIdx}, paths, graph, direcs, visited, snippet);

            // Back.
            snippet.remove(snippet.size() - 1);
        }

        // Current node in this path snippet has been totally processed, so unmark it.
        visited[rowIdx][colIdx] = false;
    }


}
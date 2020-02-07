package com.myleetcode.depth_first_search.regions_cut_by_slashes;

public class Solution {
    public int regionsBySlashes(String[] grid) {
        return regionsBySlashesByDFS(grid);
    }

    /*
    DFS transformation, similar with 200. Number of Islands
    https://leetcode.com/problems/regions-cut-by-slashes/discuss/205719/Mark-the-boundary-and-then-the-problem-become-Number-of-Islands-(dfs-or-bfs)
    https://leetcode.com/problems/regions-cut-by-slashes/discuss/205674/C%2B%2B-with-picture-DFS-on-upscaled-grid

    TC: O(N^2)
    SC: O(N^2)
    */
    /*
    About why use 3*N new graph.
    1st, to solve this problem, one must know he should convert the given N length grid(each item is a string length N) to a matrix.
    2nd, now, we have 3 different status for each char in grid string, they are: '/', '\\', ''. Since we need use a square to represent the 3 status, so the smallest one is 2*2.
    if we use 2*2 square to represent these 3 status, then based on the status shape, we khave:
        '/' is:
        01
        10
        '\\'(acutally represnet \) is:
        10
        01
        '' is:
        00
        00

        then, we'll encounter a problem at example5:{'//', '/ '}
        if we use a matrix with 2*2 square to represnet this grid, then we have:
        { 01  01  01  00
          10, 10, 10, 00}
        For better understanding, we don't put these squares together for now, only keep their shape and order. Now we put them to the matrix:
        0101
        1010
        0100
        1000
        Now we could find, if use the number of islands DFS algo here, there're 5 parts 0s are seperateed by 1s, instead of correct answer 3. the 0s on cell {0,2}, {1,1}, {2,0} should be together in one part but now is 3 parts, because we could only go exploring with 4 directions:up,down,left,right.
        So, 2*2 square acts as basic representing block is wrong.

    3rd, use 3*3 is correct, but has not found a way to testify it. It just works for all 5 examples, and people in leetcode just said 2*2 is incorrect for example5 and 3*3 is correct so let's use it.
    */
    private int regionsBySlashesByDFS(String[] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int len = grid.length * 3;
        int[][] graph = new int[len][len];
        buildGraph(graph, grid);

        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[len][len];
        int ret = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (!visited[i][j] && graph[i][j] == 0) {
                    dfs(graph, i, j, visited, direcs);

                    ret++;
                }
            }
        }

        return ret;

    }

    private void dfs(int[][] graph, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs) {
        int rowLen = graph.length;
        int colLen = graph[0].length;

        // Mark current cell as visited.
        visited[rowIdx][colIdx] = true;

        // Traverse children.
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }
            if (graph[nextRowIdx][nextColIdx] != 0) {
                continue;
            }

            dfs(graph, nextRowIdx, nextColIdx, visited, direcs);
        }

    }

    private void buildGraph(int[][] graph, String[] grid) {
        int rowLen = graph.length;
        int colLen = graph[0].length;

        int gridRowLen = grid.length;
        for (int i = 0; i < gridRowLen; i++) {
            String rowInfo = grid[i];

            for (int j = 0; j < rowInfo.length(); j++) {
                char cellInfo = rowInfo.charAt(j);

                if (cellInfo == '/') {
                    graph[3 * i + 0][3 * j + 2] = 1;
                    graph[3 * i + 1][3 * j + 1] = 1;
                    graph[3 * i + 2][3 * j + 0] = 1;
                } else if (cellInfo == '\\') {
                    graph[3 * i + 0][3 * j + 0] = 1;
                    graph[3 * i + 1][3 * j + 1] = 1;
                    graph[3 * i + 2][3 * j + 2] = 1;
                }
                // else all 0 in the 3*3 subgraph, it's satisfied by default.
            }
        }
    }
}

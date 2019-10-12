package com.myleetcode.depth_first_search.max_area_of_island;

class Solution {

  public int maxAreaOfIsland(int[][] grid) {
    return maxAreaOfIslandByDFS(grid);
  }

  /*
  Intuition:
  count area of islands, return the max one
  DFS

  TC: O(R*C)
  SC: O(R*C)
  */
  private int maxAreaOfIslandByDFS(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
      return 0;
    }

    int rowLen = grid.length;
    int colLen = grid[0].length;
    boolean[][] visited = new boolean[rowLen][colLen];

    int[][] direcs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    int max = 0;
    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        if (grid[i][j] == 1 && !visited[i][j]) {
          Result ret = new Result();
          areaByDFS(grid, i, j, visited, direcs, ret);
          max = Math.max(max, ret.val);
        }
      }
    }

    return max;
  }

  private void areaByDFS(int[][] grid, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs,
      Result ret) {
    visited[rowIdx][colIdx] = true;

    ret.val++;

    int rowLen = grid.length;
    int colLen = grid[0].length;
    for (int[] direc : direcs) {
      int nextRowIdx = rowIdx + direc[0];
      int nextColIdx = colIdx + direc[1];

      // valid check
      if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
        continue;
      }
      if (grid[nextRowIdx][nextColIdx] == 0) {
        continue;
      }
      if (visited[nextRowIdx][nextColIdx]) {
        continue;
      }

      areaByDFS(grid, nextRowIdx, nextColIdx, visited, direcs, ret);
    }

  }

  class Result {

    int val;

    public Result() {
      this.val = 0;
    }
  }
}
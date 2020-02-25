package com.myleetcode.binary_search.count_negative_numbers_in_a_sorted_matrix;

public class Solution {
    public int countNegatives(int[][] grid) {
        return countNegatiesByBS(grid);
    }

    /*
    BinarySearch

    The find first negative row idx part is unnecessary, it's could not speed up the total big-O running time.

    TC: O(R * logC)
    SC: O(1)
    */
    private int countNegatiesByBS(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        // find first negative row idx
        int boundryRowIdx = firstNegativeRowIdx(grid, rowLen);
        if (boundryRowIdx == -1) {
            boundryRowIdx = rowLen - 1;
        }

        int count = 0;
        count += colLen * (rowLen - 1 - boundryRowIdx);
        for (int i = 0; i <= boundryRowIdx; i++) {
            int firstNegativeColIdx = firstNegativeColIdx(grid, i, colLen);
            if (firstNegativeColIdx == -1) {
                continue;
            }

            count += colLen - 1 - firstNegativeColIdx + 1;
        }

        return count;

    }

    private int firstNegativeRowIdx(int[][] grid, int rowLen) {
        int left = 0;
        int right = rowLen - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (grid[mid][0] < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left == rowLen) {
            return -1;
        }

        // left is first idx makes non(gird[idx][0]>=0)true
        return left;

    }

    private int firstNegativeColIdx(int[][] grid, int rowIdx, int colLen) {
        int left = 0;
        int right = colLen - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (grid[rowIdx][mid] < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left == colLen) {
            return -1;
        }

        return left;

    }
}
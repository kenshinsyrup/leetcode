package com.myleetcode.dynamic_program.count_square_submatrices_with_all_ones;

public class Solution {
    public int countSquares(int[][] matrix) {
        return countSquaresByDP(matrix);
    }

    /*
    DP
    https://leetcode.com/problems/count-square-submatrices-with-all-ones/discuss/643429/Python-DP-Solution-%2B-Thinking-Process-Diagrams-(O(mn)-runtime-O(1)-space)
    */
    private int countSquaresByDP(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] dp = new int[rowLen][colLen];

        // Base case.
        for (int i = 0; i < rowLen; i++) {
            if (matrix[i][0] == 1) {
                dp[i][0] = 1;
            }
        }
        for (int j = 0; j < colLen; j++) {
            if (matrix[0][j] == 1) {
                dp[0][j] = 1;
            }
        }

        // Explore.
        for (int i = 1; i < rowLen; i++) {
            for (int j = 1; j < colLen; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        // Get all squares number.
        int res = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                res += dp[i][j];
            }
        }

        return res;
    }
}

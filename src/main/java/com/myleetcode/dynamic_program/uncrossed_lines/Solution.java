package com.myleetcode.dynamic_program.uncrossed_lines;

class Solution {
    public int maxUncrossedLines(int[] A, int[] B) {
        return maxUncrossedLinesByDP(A, B);
    }

    /*
    DP
    LCS
    */
    private int maxUncrossedLinesByDP(int[] A, int[] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return 0;
        }

        int lenA = A.length;
        int lenB = B.length;
        int[][] dp = new int[lenA + 1][lenB + 1];

        for (int i = 0; i < lenA; i++) {
            for (int j = 0; j < lenB; j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        return dp[lenA][lenB];
    }
}

package com.myleetcode.greedy.score_after_flipping_matrix;

public class Solution {
    public int matrixScore(int[][] A) {
        return matrixScoreByGreedyAndSimulate(A);
    }

    /*
    https://leetcode.com/problems/score-after-flipping-matrix/discuss/143812/C%2B%2BJava-From-Intuition-Un-optimized-code-to-Optimized-Code-with-Detailed-Explanation

    TC: O(R * C)
    SC: O(R * C)
    */
    private int matrixScoreByGreedyAndSimulate(int[][] A) {
        if (A == null || A.length == 0 || A[0] == null || A[0].length == 0) {
            return 0;
        }

        int rowLen = A.length;
        int colLen = A[0].length;

        // 1. Make most significant position be 1.
        for (int i = 0; i < rowLen; i++) {
            if (A[i][0] != 1) {
                flipRow(A, i);
            }
        }

        // 2. Count the rows in every col that is 1, if less than rowLen/2, flip, to get more 1 in every cols.
        int[] oneCols = new int[colLen];
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (A[i][j] == 1) {
                    oneCols[j]++;
                }
            }
        }
        for (int j = 0; j < colLen; j++) {
            if (oneCols[j] * 2 < rowLen) {
                flipCol(A, j);
            }
        }

        // 3. Get scores.
        int res = 0;
        for (int i = 0; i < rowLen; i++) {
            int rowScore = 0;
            for (int j = 0; j < colLen; j++) {
                if (A[i][j] == 1) {
                    rowScore += Math.pow(2, colLen - 1 - j);
                }
            }

            res += rowScore;
        }

        return res;

    }

    private void flipRow(int[][] A, int rowIdx) {
        int colLen = A[0].length;
        for (int i = 0; i < colLen; i++) {
            A[rowIdx][i] = A[rowIdx][i] == 1 ? 0 : 1;
        }
    }

    private void flipCol(int[][] A, int colIdx) {
        int rowLen = A.length;
        for (int i = 0; i < rowLen; i++) {
            A[i][colIdx] = A[i][colIdx] == 1 ? 0 : 1;
        }
    }
}
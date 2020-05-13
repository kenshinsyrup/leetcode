package com.myleetcode.array.lucky_numbers_in_a_matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public List<Integer> luckyNumbers(int[][] matrix) {
        // return luckyNumbersBySimulate(matrix);
        return luckyNumbersBySet(matrix);
    }

    /*
    Since distinct, so find the numbers satisfy both conditions

    https://leetcode.com/problems/lucky-numbers-in-a-matrix/discuss/539731/JavaPython-3-Two-23-pass-codes-w-brief-explanation-and-analysis.
    */
    private List<Integer> luckyNumbersBySet(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return res;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        // Find the max in each row.
        Set<Integer> minSet = new HashSet<>();
        for (int i = 0; i < rowLen; i++) {
            int min = matrix[i][0];

            for (int j = 0; j < colLen; j++) {
                min = Math.min(min, matrix[i][j]);
            }
            minSet.add(min);
        }

        // Find the max in each col, if it's also in minSet, it's one candidate.
        for (int j = 0; j < colLen; j++) {
            int max = matrix[0][j];

            for (int i = 0; i < rowLen; i++) {
                max = Math.max(max, matrix[i][j]);
            }
            if (minSet.contains(max)) {
                res.add(max);
            }
        }

        return res;
    }

    /*
    Simulate

    Naive will take TC O((R * C) * (R + C))

    */
    private List<Integer> luckyNumbersBySimulate(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return res;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                int curNum = matrix[i][j];

                // Check it's the min in this row.
                boolean isMin = true;
                for (int jj = 0; jj < colLen; jj++) {
                    if (curNum > matrix[i][jj]) {
                        isMin = false;
                        break;
                    }
                }

                // Check it's the max in the col.
                boolean isMax = true;
                for (int ii = 0; ii < rowLen; ii++) {
                    if (curNum < matrix[ii][j]) {
                        isMax = false;
                        break;
                    }
                }

                // Find one.
                if (isMin && isMax) {
                    res.add(curNum);
                }
            }
        }

        return res;
    }
}

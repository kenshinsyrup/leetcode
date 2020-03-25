package com.myleetcode.trick.two_dimension_matrix_submatrix_sum.max_sum_of_rectangle_no_larger_than_l;

import java.util.Arrays;

public class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        return maxSumSubmatrixByConvertAndPrefixSum(matrix, k);
    }

    /*
    2D matrix convert to 1D array for convience to check every submatrix sum. This also shows up in 1074. Number of Submatrices That Sum to Target

    */
    private int maxSumSubmatrixByConvertAndPrefixSum(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int[] rowSum = new int[rowLen];
        int ret = Integer.MIN_VALUE;
        for (int i = 0; i < colLen; i++) {
            Arrays.fill(rowSum, 0);

            for (int j = i; j < colLen; j++) {
                for (int k = 0; k < rowLen; k++) {
                    rowSum[k] += matrix[k][j];
                }

                ret = Math.max(ret, subarraySum(rowSum, target));
            }
        }

        return ret;
    }

    private int subarraySum(int[] arr, int target) {
        int len = arr.length;
        int[] prefixSumArr = new int[len + 1];
        for (int i = 0; i < len; i++) {
            prefixSumArr[i + 1] = prefixSumArr[i] + arr[i];
        }

        int ret = Integer.MIN_VALUE;
        for (int i = 0; i < prefixSumArr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (prefixSumArr[i] - prefixSumArr[j] <= target) {
                    ret = Math.max(ret, prefixSumArr[i] - prefixSumArr[j]);
                }
            }
        }

        return ret;

    }
}



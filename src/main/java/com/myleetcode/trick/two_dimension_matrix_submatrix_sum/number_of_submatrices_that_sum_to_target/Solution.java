package com.myleetcode.trick.two_dimension_matrix_submatrix_sum.number_of_submatrices_that_sum_to_target;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        return numSubmatrixSumTargetByConvert(matrix, target);
    }

    /*
    https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/303755/(Java)-Simple-solution-with-explanation.
    https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/303773/C%2B%2B-O(n3)-Simple-1D-Subarray-target-sum-applied-to-2D-array

    for matrix col from i to j, row from 0 to rowLen, we get the sum of each row and store the sum value into the rowSum.
    Then, in this col from i to j area, we could get every submatrix sum, that's the prefix sum of the rowSum.
    Then the question becomes to find the num of subarray whose sum equals to target.

    TC: O(R*R*C)
    SC: O(R)
    */
    private int numSubmatrixSumTargetByConvert(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int[] rowSum = new int[rowLen];
        int ret = 0;
        for (int i = 0; i < colLen; i++) { // Starting col of current submatrix.
            Arrays.fill(rowSum, 0); // Clear the storing array.

            for (int j = i; j < colLen; j++) { // Ending col of current submatrix.
                for (int k = 0; k < rowLen; k++) {
                    rowSum[k] += matrix[k][j];
                }

                ret += subarraySumTarget(rowSum, target);
            }
        }

        return ret;
    }

    // Return the number of subarry whose sum is target
    private int subarraySumTarget(int[] arr, int target) {
        int len = arr.length;
        Map<Integer, Integer> complementTimeMap = new HashMap<>();
        complementTimeMap.put(0, 1); // For the case we get a sum equals to target, then the complement for the sum is 0, since there's no such num in the map initially, so we need put this 0->1 here.
        int prefixSum = 0;
        int ret = 0;
        for (int i = 0; i < len; i++) {
            prefixSum += arr[i];

            int complement = prefixSum - target; // !!! subarraySum == target, and subarraySum == prefixSum - excludedArraySum, so compelment is the excludedArraySum is prefixSum - target.
            if (complementTimeMap.containsKey(complement)) {
                ret += complementTimeMap.get(complement);
            }

            complementTimeMap.put(prefixSum, complementTimeMap.getOrDefault(prefixSum, 0) + 1);
        }

        return ret;
    }
}

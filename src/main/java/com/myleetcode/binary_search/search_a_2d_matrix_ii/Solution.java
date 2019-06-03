package com.myleetcode.binary_search.search_a_2d_matrix_ii;

import java.util.Arrays;

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrixBySearchRwo(matrix, target);
    }

    // TC: O(R*C) if no BS, O(R * log(C)) with BS
    // SC: O(1)
    // intuition:
    // first find the row, in a row, if we find first num is smaller than or equals to target and its last num is larger or equals to target, then if we could find target, it's must in this row
    // then, try to find the target in the row
    private boolean searchMatrixBySearchRwo(int[][] matrix, int target){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return false;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        // special case check
        if(matrix[rowLen - 1][colLen - 1] < target || matrix[0][0] > target){
            return false;
        }

        // search
        for(int i = 0; i < rowLen; i++){
            // must in this row
            if(matrix[i][0] <= target && matrix[i][colLen - 1] >= target){
                // actually here we could use BS to reduce the RT from O(C) to O(log(C))
                // for(int j = 0; j < colLen; j++){
                //     if(matrix[i][j] == target){
                //         return true;
                //     }
                // }
                // I am too lazy to really write a BS here, so just call the built in BS
                if(Arrays.binarySearch(matrix[i], target) >= 0){
                    return true;
                }
            }
        }

        return false;

    }
}

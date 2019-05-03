package com.myleetcode.binary_search.search_a_2d_matrix;

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrixBySearch(matrix, target);
    }

    // there's also a o(log(R + C)) solution to treat the matrix as a sorted list then Binary Search:https://leetcode.com/problems/search-a-2d-matrix/discuss/26201/A-Python-binary-search-solution-O(logn)

    // TC: O(min(R, log(C))), R is the # of rows, C is the # of cols
    // SC: O(1)
    // intuition:
    // first we search the row, we check the last elem of every row from 0 to rowLen-1, if target is larger then we check next row, until target is less than the elem, then we know it must in this row.
    // then we do Traverse or Binary Search to find the target
    private boolean searchMatrixBySearch(int[][] matrix, int target){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return false;
        }

        // find row
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int rowIdx = 0;
        for(int i = 0; i < rowLen; i++){
            if(target <= matrix[i][colLen - 1]){
                rowIdx = i;
                break;
            }
        }
        // !!!Important, actually we could set the initial value of rowIdx -1, then we check here, if rowIdx is -1, means target is larger than the last row's last elem, then return false. this could save the additional check in Binary Search.
        // But must be aware, if we set rowIdx as -1 at start, we must check this otherwise it will out of boundary in the binary search because we will access the matrix[rowIdx][...]
        // if(rowIdx == -1){
        //     return false;
        // }

        // search target with Binary Search
        int start = 0;
        int end = colLen - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(matrix[rowIdx][mid] == target){
                return true;
            }else if(matrix[rowIdx][mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return false;

    }
}

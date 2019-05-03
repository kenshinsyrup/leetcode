package com.myleetcode.array.set_matrix_zeroes;

class Solution {
    public void setZeroes(int[][] matrix) {
        setZeroesByInPlace(matrix);
    }

    // TC: O(R * C)
    // SC: O(1)
    /*
store states of each row in the first of that row, and store states of each column in the first of that column. Because the state of row0 and the state of column0 would occupy the same cell, I let it be the state of row0, and use another variable "col0" for column0. In the first phase, use matrix elements to set states in a top-down way. In the second phase, use states to set matrix elements in a bottom-up way.
    */
    // https://leetcode.com/problems/set-matrix-zeroes/discuss/26008/My-AC-java-O(1)-solution-(easy-to-read)
    // there's a SC O(N) solution in solution section
    private void setZeroesByInPlace(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return;
        }

        boolean col0 = false;
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // mark the first cell of row and first cell of col if we meet 0 at (i,j)
        for(int i = 0; i < rowLen; i++){
            // check if first col has 0
            if(matrix[i][0] == 0){
                col0 = true;
            }
            // other cols
            for(int j = 1; j < colLen; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // set 0s
        // !!! important, here we must first set all 0s in cols except the first col and rows except the first row because they are the marks, then we set the first col and first row seperately. Because for eg if we set the first col with other cols in the same nested for loop, some rows will think they are have a leading 0 cell then set the all row 0.
        for(int i = 1; i < rowLen; i++){
            // cols excludes the first col
            for(int j = 1; j < colLen; j++){
                // check row and check col and set
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }

        // first row
        if(matrix[0][0] == 0){
            for(int j = 0; j < colLen; j++){
                matrix[0][j] = 0;
            }
        }

        // first col
        if(col0){
            for(int i = 0; i < rowLen; i++){
                matrix[i][0] = 0;
            }
        }
    }
}

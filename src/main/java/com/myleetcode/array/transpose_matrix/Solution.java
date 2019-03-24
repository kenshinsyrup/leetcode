package com.myleetcode.array.transpose_matrix;

class Solution {
    public int[][] transpose(int[][] A) {
        return transposeByAnotherMatrix(A);
    }

    // intuition: original row to be new column, original column to be new row
    private int[][] transposeByAnotherMatrix(int[][] A){
        if(A == null || A.length == 0 || A[0].length == 0){
            return A;
        }

        int rowNum = A.length;
        int colNum = A[0].length;
        int newRowNum = colNum;
        int newColNum = rowNum;

        int[][] newA = new int[newRowNum][newColNum];
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                newA[j][i] = A[i][j];
            }
        }

        return newA;
    }
}

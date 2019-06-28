package com.myleetcode.array.toeplitz_matrix;

class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        // return isToeplitzMatrixByTraverse(matrix);
        return isToeplitzMatrixByTopLeftNeighbor(matrix); // more elegant
    }

    // from solution: Compare With Top-Left Neighbor
    // traverse the matrix, if a cell has not the same num with its top-left neighbor, then false. after all processed, true
    // TC: O(R * C)
    // SC: O(1)
    private boolean isToeplitzMatrixByTopLeftNeighbor(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return true;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // top-left cell
                int neiRowIdx = i - 1;
                int neiCloIdx = j - 1;
                if(neiRowIdx < 0 || neiRowIdx >= rowLen || neiCloIdx < 0 || neiCloIdx >= colLen){
                    continue;
                }

                if(matrix[i][j] != matrix[neiRowIdx][neiCloIdx]){
                    return false;
                }
            }
        }

        return true;

    }

    // intuition: do what the problem says. we use the cells in the first row and cells in the first cols as the start cells. Then for given a cell[i,j], we need to check all cells in its diagonal from it to bottom-right.
    // TC: O(R * C)
    // SC: O(1)
    private boolean isToeplitzMatrixByTraverse(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return true;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        for(int i = 0; i < rowLen; i++){
            if(!check(i, 0, matrix)){
                return false;
            }
        }
        for(int j = 0; j < colLen; j++){
            if(!check(0, j, matrix)){
                return false;
            }
        }

        return true;
    }

    // TC: O(max(R, C))
    // SC: O(1)
    private boolean check(int rowIdx, int colIdx, int[][] matrix){
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int num = matrix[rowIdx][colIdx];
        while(rowIdx < rowLen && colIdx < colLen){
            if(num != matrix[rowIdx][colIdx]){
                return false;
            }

            rowIdx += 1;
            colIdx += 1;
        }

        return true;
    }

}

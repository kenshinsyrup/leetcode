package com.myleetcode.dynamic_program.range_sum_query_2d_immutable;

class NumMatrix {

    // intuition: DP. 85, 221, 304
    // the intuition is first get a new matrix with all the sums like prefix-sum problem. then when user call the sumRegion we actually only do a simple lookup operation.
    // matrixSums[row2][col2] - matrixSums[row2][col1 - 1] - matrixSums[row1 - 1][col2] + matrixSums[row1 - 1][col1 - 1];
    int[][] matrixSums;

    // TC: O(R * C)
    // SC: O(R * C)
    public NumMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return;
        }

        // dp[i][j] means the area of the rectangle that the right lower corner cell is the matrix[i][j] and the left upper cell is matrix[0][0]
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // base
        for(int i = 0; i < rows; i++){
            if(i == 0){
                dp[i][0] = matrix[i][0];
            }else{
                dp[i][0] = dp[i - 1][0] + matrix[i][0];
            }
        }
        for(int j = 0; j < cols; j++){
            if(j == 0){
                dp[0][j] = matrix[0][j];
            }else{
                dp[0][j] = dp[0][j - 1] + matrix[0][j];
            }
        }

        // dp
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                dp[i][j] = matrix[i][j] + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
            }
        }

        this.matrixSums = dp;
    }

    // TC: O(1)
    // SC: O(1)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int rows = matrixSums.length;
        int cols = matrixSums[0].length;

        if(row1 < 0 || row1 >= rows || row2 < 0 || row2 >= rows || col1 < 0 || col1 >= cols || col2 < 0 || col2 >= cols){
            return 0;
        }

        // to avoid out of boundary, check these cases
        if(row1 == 0 && col1 == 0){
            return matrixSums[row2][col2];
        }
        if(row1 == 0){
            return matrixSums[row2][col2] - matrixSums[row2][col1 - 1];
        }
        if(col1 == 0){
            return matrixSums[row2][col2] - matrixSums[row1 - 1][col2];
        }

        // here, it's important to make clear, that the row1,col1 pos is in the required rectangle, so we need use caculate the row and col carefully
        return matrixSums[row2][col2] - matrixSums[row2][col1 - 1] - matrixSums[row1 - 1][col2] + matrixSums[row1 - 1][col1 - 1];
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */

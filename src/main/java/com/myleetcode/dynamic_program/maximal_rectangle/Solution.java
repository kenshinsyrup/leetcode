package com.myleetcode.dynamic_program.maximal_rectangle;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        return maximalRectangleByDP(matrix);
    }

    // TC: O(R^2 * C)
    // SC: O(R*C)
    // intuition: DP. 85, 221, 304
    // according to the solution, we could use the dp with histogram to solve this.
    /*
    dp[i][j] represents the width(num of 1s) from last 0(or 0th idx) at ith row and jth col

    dp[i][j] = dp[i][j - 1] + 1, if matrix[i][j] is 1

    base case:
    dp default is 0
    dp[0][0] = 1, if matrix[0][0] is 1
    for the 0th col, dp[i][0] = 1, if matrix[i][0] is 1
    for the 0th row, dp[0][j] = dp[0][j - 1] + 1, if matrix[0][j] is 1
    */
    private int maximalRectangleByDP(char[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // 1. base
        // dp[0][0]
        if(matrix[0][0] == '1'){
            dp[0][0] = 1;
        }
        // dp[i][0]
        for(int i = 1; i < rows; i++){
            if(matrix[i][0] == '1'){
                dp[i][0] = 1;
            }
        }
        // dp[0][j]
        for(int j = 1; j < cols; j++){
            if(matrix[0][j] == '1'){
                dp[0][j] = 1 + dp[0][j-1];
            }
        }

        // 2. explore
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][j] == '1'){
                    dp[i][j] = dp[i][j - 1] + 1;
                }
            }
        }

        int maxArea = 0;
        // 3. since we have get all the width for every given i,j
        // we could caculate the areas and choose the max one by try every histogram with given i,j as rightbottom cell
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                // !!! try every cell(i,j) as the rightbotoom of histogram, width is dp[i][j]
                // if this cell(i,j) is 1, we know cell(i,j) is the rightbottom of a valid all 1s histogram
                if(matrix[i][j] == '1'){
                    int width = dp[i][j];

                    // !!! now we try to increase the height the histogram
                    for(int k = i; k >= 0; k--){
                        // keep updating the min width
                        width = Math.min(width, dp[k][j]);
                        int area = width * (i - k + 1);

                        maxArea = Math.max(maxArea, area);
                    }
                }
            }
        }

        return maxArea;

    }

}

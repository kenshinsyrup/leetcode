package com.myleetcode.dynamic_program.maximal_rectangle;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        return maximalRectangleByDP(matrix);
    }

    // TC: O(R^2 * C)
    // SC: O(R*C)
    // intuition: DP. 85, 221, 304
    // according to the solution, we could use the dp with histogram to solve this.
    private int maximalRectangleByDP(char[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }

        // dp[i][j] represents the max width at ith row, 0:jth cols, then the area have relations with the dp
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        // base
        for(int i = 0; i < rows; i++){
            if(matrix[i][0] == '1'){
                dp[i][0] = 1;
            }
        }
        for(int j = 0; j < cols; j++){
            if(matrix[0][j] == '1'){
                if(j == 0){
                    dp[0][j] = 1;
                }else{
                    dp[0][j] = 1 + dp[0][j-1];
                }
            }
        }

        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][j] == '1'){
                    dp[i][j] = dp[i][j - 1] + 1;
                }
            }
        }

        int maxArea = 0;
        // since we have get all the histograms, we could caculate the areas and choose the max one
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                // min width in j col, must keep it out of k loop to record the min
                int width = dp[i][j];
                if(matrix[i][j] == '1'){
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

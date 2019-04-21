package com.myleetcode.dynamic_program.unique_paths_ii;

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        return uniquePathsWithObstaclesByDP(obstacleGrid);
    }

    // TC: O(R * C)
    // SC: O(R * C)
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    // dp[i][j] means how many ways to get here, if obstacleGrid[i][j] == 1 then dp[i][j] = 0; otherwist dp[i][j] = dp[i-1][j] + d[[i][j-1]]
    // base case is the first row and first col, if obstacleGrid is not 1, then itself and cells behind it dp is 1
    private int uniquePathsWithObstaclesByDP(int[][] obstacleGrid){
        if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0] == null || obstacleGrid[0].length == 0){
            return 0;
        }

        int rowLen = obstacleGrid.length;
        int colLen = obstacleGrid[0].length;
        int[][] dp = new int[rowLen][colLen];

        // base case
        for(int i = 0; i < rowLen; i++){
            if(obstacleGrid[i][0] != 1){
                dp[i][0] = 1;
            }else{
                break;
            }
        }
        for(int j = 0; j < colLen; j++){
            if(obstacleGrid[0][j] != 1){
                dp[0][j] = 1;
            }else{
                break;
            }
        }

        for(int i = 1; i < rowLen; i++){
            for(int j = 1; j < colLen; j++){
                if(obstacleGrid[i][j] != 1){
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }else{
                    dp[i][j] = 0;
                }
            }
        }

        return dp[rowLen - 1][colLen - 1];

    }

}

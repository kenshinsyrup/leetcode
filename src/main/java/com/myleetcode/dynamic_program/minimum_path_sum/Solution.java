package com.myleetcode.dynamic_program.minimum_path_sum;

class Solution {
    public int minPathSum(int[][] grid) {
        // dp, 雷同62. Unique Paths
        // dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
        
        // special case
        if(grid == null){
            return 0;
        }
        
        int m = grid.length;
        if(m == 0){
            return 0;
        }
        
        int n = grid[0].length;
        if(n == 0){
            return 0;
        }
        
        
        // dp 初始状态，重点，要考虑清楚dp的初始状态到底是什么
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for(int i = 1; i < m; i++){
            dp[i][0] = grid[i][0] + dp[i - 1][0];  
        }
        for(int j = 1; j < n; j++){
            dp[0][j] = grid[0][j] + dp[0][j - 1];
        }
        
//         重点，如何计算dp[i][j]
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        
        return dp[m - 1][n - 1];
        
    }
}
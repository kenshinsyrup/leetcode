package com.myleetcode.dynamic_program.unique_paths;

class Solution {
    public int uniquePaths(int m, int n) {
        // dp, 看起来很像爬台阶问题.
//         因为只能down和right走
        // dp[i,j] = dp[i-1][j] + dp[i][j-1]
        
        // special case
        if(m == 0 || n == 0){
            return 1;
        }
        
        
        int[][] grid = new int[n][m];
        
        // dp 初始状态
        for(int i = 0; i < n; i++){
            grid[i][0] = 1;
        }
        for(int j = 0; j < m; j++){
            grid[0][j] = 1;
        }
        
        for(int i = 1; i < n; i++){
            for(int j = 1; j < m; j++){
                grid[i][j] = grid[i - 1][j] + grid[i][j-1];
                System.out.println(grid[i][j]);
            }
        }
        
        return grid[n - 1][m - 1];
        
    }
}
package com.myleetcode.dynamic_program.unique_paths;

class Solution {
    public int uniquePaths(int m, int n) {
        return uniquePathsByDP(m, n);
    }

    // TC: O(m * n)
    // SC: O(m * n)
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    // dp[i][j] means the path # to go here. so dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
    // base case is the first row and first col, so we dont need to add extra row and col to dp array.
    private int uniquePathsByDP(int m, int n){
        if(m <= 0 || n <= 0){
            return 0;
        }

        int[][] dp = new int[m][n];

        // base case
        for(int i = 0; i < m; i++){
            dp[i][0] = 1;
        }
        for(int j = 0; j < n; j++){
            dp[0][j] = 1;
        }

        // dp
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}
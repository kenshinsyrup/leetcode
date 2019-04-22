package com.myleetcode.dynamic_program.minimum_falling_path_sum;

class Solution {
    public int minFallingPathSum(int[][] A) {
        return minFallingPathSumByDP(A);
    }

    // TC: O(R * C), or O(N^2) where N is the length of A
    // SC: O(R * C), or O(N^2)
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    // very similar with 120. Triangle, we use dp to caculate all the path sum at the last row, then choose the min one
    // dp[i][j] means at the pos(i, j) the path sum, dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i-1][j+1]) + nums[i][j]
    // the base case are the first row because they are themself
    // there are two special case that are the first col and the last col because the first col has no col before it and the last col has no col after it, so should slightly change the dp fomula
    private int minFallingPathSumByDP(int[][] nums){
        if(nums == null || nums.length == 0 || nums[0] == null || nums[0].length == 0){
            return 0;
        }

        int rowLen = nums.length;
        int colLen = nums[0].length;

        int[][] dp = new int[rowLen][colLen];
        // init
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        // base
        // first row
        for(int j = 0; j < colLen; j++){
            dp[0][j] = nums[0][j];
        }

        // dp
        for(int i = 1; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(j == 0){
                    // special case 1, the first col
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j + 1]) + nums[i][j];
                }else if(j == colLen - 1){
                    // special case 2, the last col
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + nums[i][j];
                }else{
                    // normal cae
                    int lastMin = Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                    lastMin = Math.min(lastMin, dp[i - 1][j + 1]);
                    dp[i][j] = lastMin + nums[i][j];
                }
            }
        }

        // find the min path sum
        int ret = Integer.MAX_VALUE;
        for(int j = 0; j < colLen; j++){
            ret = Math.min(ret, dp[rowLen - 1][j]);
        }

        return ret;
    }


}

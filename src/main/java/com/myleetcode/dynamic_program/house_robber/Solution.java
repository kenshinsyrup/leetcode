package com.myleetcode.dynamic_program.house_robber;

class Solution {
    public int rob(int[] nums) {
        return robByDP(nums);
    }

    // intuition: DP. 198, 213, 309, 740, 790, 801
    // we have only one constraints is the nums, so we need a 1-d array as dp array
    // dp[i] means the max value we could get from the given i houses, then we have two conditions:
    // 1 is we rob the i-1th house, then dp[i] = dp[i-1-1] + nums[i-1],because if we rob current then we must not rob previous house so we are based on the dp[i-1 - 1]
    // 2 is we dont rob the i-1th house, then dp[i] = dp[i-1]
    // we choose the max one
    // base case is dp[0] = 0; dp[1] = nums[0]
    private int robByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len + 1];
        // base
        dp[1] = nums[0];

        for(int i = 2; i<= len; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 1 - 1] + nums[i - 1]);
        }

        return dp[len];
    }
}

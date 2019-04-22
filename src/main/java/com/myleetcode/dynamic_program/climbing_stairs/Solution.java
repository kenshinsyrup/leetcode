package com.myleetcode.dynamic_program.climbing_stairs;

class Solution {
    public int climbStairs(int n) {
        return climbStairsByDP(n);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 70, 746
    // given n means we have n stairs, so the last stair idx is n, so our range is n + 1
    // dp[i] means the ways to reach pos i, so dp[i] = dp[i-1]+dp[i-2]
    // base case is dp[1] == 1, dp[2] == 2
    private int climbStairsByDP(int n){
        // special case
        if(n <= 0){
            return 0;
        }

        // here we return n if n <= 2, because we next will use the dp[1] and dp[2], so the n must >= 3
        if(n <= 2){
            return n;
        }

        int[] dp = new int[n + 1];

        // base case
        dp[1] = 1;
        dp[2] = 2;

        // dp
        for(int i = 3; i < n + 1; i++){
            dp[i] = dp[i -1] + dp[i - 2];
        }

        return dp[n];
    }
}
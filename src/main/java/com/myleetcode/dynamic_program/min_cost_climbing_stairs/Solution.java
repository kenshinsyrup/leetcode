package com.myleetcode.dynamic_program.min_cost_climbing_stairs;

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        return minCostClimbingStairsByDP(cost);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 70, 746
    // here should know, we have cost array, and its length is len, but we need to get the len-th stair, not the (len-1)-th stair
    // dp[i] means the cost to reach the ith stair(not including self). so dp[i] = min(dp[i-1] + cost[i - 1], dp[i-2] + cost[i - 2])
    // base case is dp[0] = 0, dp[1] = 0 because only 0 or 1 stairs we could just jump to skip them. so we dont need extra row
    // and we should consider if the cost has cost[0] and cost[1]
    private int minCostClimbingStairsByDP(int[] cost){
        if(cost == null || cost.length == 0){
            return 0;
        }

        // consider this special case
        if(cost.length == 1){
            return 0;
        }

        int len = cost.length;
        int[] dp = new int[len + 1];
        // base case
        // dp[0] = 0;
        // dp[1] = 0;

        // dp
        for(int i = 2; i < len + 1; i++){
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return dp[len];

    }
}

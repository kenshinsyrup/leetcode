package com.myleetcode.dynamic_program.coin_change;

class Solution {
    public int coinChange(int[] coins, int amount) {
        return coinChangeByDP(coins, amount);
    }

    // TC: O(N * M), N is the amount and M is the length of coins
    // SC: O(N)
    // intuition: DP. 322, 377, 416, 494
    // only the amount of money is limited, so we need a 1D array as dp[].
    // dp[i] means the num needed to change amount i, so for amount i, we have coins to choose, we could change or not, if we change, then we need num dp[i-coins[j]]+1, if not, we need num dp[i], we need the min one.
    // dp[i] = min(dp[i - coins[j]] + 1, dp[i]), dp[i-coins[j]]+1
    // base case is the dp[0] = 0 others are MAX, so the dp length is amount+1
    // remember to check the dp[i - coins[j]] is MAX of not before +1 and assign to dp[i]
    private int coinChangeByDP(int[] coins, int amount){
        if(amount < 0){
            return -1;
        }
        if(coins == null || coins.length == 0){
            return -1;
        }

        int[] dp = new int[amount + 1];
        // base, dp[0] is 0 and others are MAX
        for(int i = 1; i <= amount; i++){
            dp[i] = Integer.MAX_VALUE;
        }

        // dp
        for(int i = 1; i <= amount; i++){
            for(int j = 0; j < coins.length; j++){
                if(i >= coins[j]){
                    // check overflow
                    if(dp[i - coins[j]] != Integer.MAX_VALUE){
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                    }
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE? -1: dp[amount];
    }
}
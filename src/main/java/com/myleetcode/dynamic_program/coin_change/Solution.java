package com.myleetcode.dynamic_program.coin_change;

public class Solution {
    public int coinChange(int[] coins, int amount) {
        return coinChangeByDP(coins, amount);
    }

    /*
    Knapsack Problem with Unlimited Items. Coin Change Problem.

    Thought:
        There's only one constraint in this kind of problem, which is the amount of money. Here is the Amount.
        dp[i] means the num needed to change amount i, so for amount i, we have coins to choose, we could change or not, if we change, then we need num dp[i-coins[j]]+1, if not, we need num dp[i], we need the min one.

    Function:
        dp[i] = min(dp[i - coins[j]] + 1, dp[i]), if i >= coins[j]

    Base case, this is different for each specific problem:
        dp[0] = 0, here dp[0] means given amount is 0, in this problem we know there's no coin values 0, so dp[0] should be 0.
        initially, we make other dp[i] to be Max, because we want to get the min for each dp[i].

    For Coin Change problem like this, we want the min in dp array. Since we have set the default value in dp array as Max Integer, we must be careful when we use the previous status, we only use the valid status, that means we need check whether dp[i-coins[j]] is Max Integer or not.

    TC: O(N * M), N is the amount and M is the length of coins
    SC: O(N)
    */
    private int coinChangeByDP(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }
        if (coins == null || coins.length == 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        // Base case.
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        // DP explore.
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    // !!! Only use the valid previous status. Check overflow since we make every default value in dp array is Max Integer.
                    if (dp[i - coins[j]] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                    }
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_with_transaction_fee;

class Solution {
    public int maxProfit(int[] prices, int fee) {
        return maxProfitByDPII(prices, fee);
        // return maxProfitByDP(prices, fee); // naive DP, TLE
    }

    // TLE
    // TC: O(N^2)
    // SC: O(N)
    private int maxProfitByDP(int[] prices, int fee){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len + 1];
        for(int i = 1; i <= len; i++){
            dp[i] = Math.max(dp[i], dp[i - 1]);

            for(int j = 1; j < i; j++){
                dp[i] = Math.max(dp[i], dp[j - 1] + prices[i - 1] - prices[j - 1] - fee);
            }
        }

        return dp[len];
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108871/2-solutions-2-states-DP-solutions-clear-explanation!
    private int maxProfitByDPII(int[] prices, int fee){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;
        // !!! base case is given at least 1 elem in prices
        int[] buy = new int[len + 1];
        buy[1] = 0 - prices[0];
        int[] sell = new int[len + 1];
        sell[1] = 0;

        for(int i = 2; i <= len; i++){
            buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i - 1]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i - 1] - fee);
        }

        return sell[len];
    }
}

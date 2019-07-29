package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock;

class Solution {
    public int maxProfit(int[] prices) {
        return maxProfiltByDP(prices);
    }

    /*
    出错点：
    1 dp问题确定
    2 currentMaxProfit来源为prices[i-1]-minPrice, 这里我们需要一个prices[i-1]之前的minPrice，所以我们用一个变量来记录和更新他
    */

    // dp problem, because it could be reduced ot sub-problem and with the ans of sub-problem we could get the ans of the problem
    // dp[i] means given first i elems in the prices array, ie prices[0:i-1], the best profit we could get.
    // dp[i] = max(dp[i-1], currentMaxProfit), the currentMaxProfit is price[i - 1]-minPrice, where minPrice is the lowest price before prices[i-1].
    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 52, 121
    private int maxProfiltByDP(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len + 1];
        int minPrice = Integer.MAX_VALUE;
        for(int i = 1; i <= len; i++){
            dp[i] = Math.max(dp[i - 1], prices[i - 1] - minPrice);

            minPrice = Math.min(minPrice, prices[i - 1]);
        }

        return dp[len];
    }
}
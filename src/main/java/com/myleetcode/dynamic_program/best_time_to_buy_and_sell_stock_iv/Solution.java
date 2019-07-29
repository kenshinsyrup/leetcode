package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_iv;

class Solution {
    public int maxProfit(int k, int[] prices) {
        return maxProfitByDP(k, prices);
        // return maxProfitByDPII(k, prices);
    }

    // intuition: generalization form of problem 123. Best Time to Buy and Sell Stock III
    // 分开处理K>=len/2的情况是因为直接用DP的话会MLE

    // optimization: https://www.geeksforgeeks.org/maximum-profit-by-buying-and-selling-a-share-at-most-k-times/
    // localMaxProfit = Math.max(localMaxProfit, dp[k - 1][j - 1] + prices[i - 1] - prices[j - 1]); ie, we are looking for the max of dp[k - 1][j - 1] + prices[i - 1] - prices[j - 1]
    // since for current i, prices[i-1] is fixed, so max(dp[k - 1][j - 1] + prices[i - 1] - prices[j - 1]), j in [1:i-1], could be rewritten as max(prices[i-1] + max(dp[j-1][k-1] - prices[j-1]))
    // to get max(dp[k-1][j-1]-prices[j-1]) where j in [1:i-1], so we could make prevDiff=dp[k-1][j-1]-prices[j-1], then for current i, prevDiff = max(prevDiff, dp[k-1][i-1-1]-prices[i-1-1]), by this way, we could get the max local pofit in O(1) time
    // TC: O(K * N)
    // SC: O(K * N)
    private int maxProfitByDPII(int K, int[] prices){
        if(K <= 0){
            return 0;
        }
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        //if K >= len/2, then it's problem 122. Best Time to Buy and Sell Stock II
        if (K >=  len/2) {
            int maxPro = 0;
            for (int i = 1; i < len; i++) {
                if (prices[i] > prices[i-1])
                    maxPro += prices[i] - prices[i-1];
            }
            return maxPro;
        }

        // otherwise, it's problem 123. Best Time to Buy and Sell Stock III
        int transactionTimes = K;
        int[][] dp = new int[transactionTimes + 1][len + 1];

        for(int k = 1; k <= transactionTimes; k++){
            int prevDiff = Integer.MIN_VALUE;
            for(int i = 1; i <= len; i++){
                // no transanction
                dp[k][i] = Math.max(dp[k][i], dp[k][i - 1]);

                // do transacntion
                if(i >= 2){
                    prevDiff = Math.max(prevDiff, dp[k - 1][i - 1 - 1] - prices[i - 1 - 1]);
                    dp[k][i] = Math.max(dp[k][i], prices[i - 1] + prevDiff);
                }
            }
        }

        return dp[transactionTimes][len];
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
    // dp[i][k] means given the first i elems in prices and first k elems in transaction times
    // dp[i][k] = max(dp[i-1][k], max(dp[j-1][k-1] + prices[i-1] - prices[j-1])), where j=[1:i-1]
    // TC: O(K * N^2)
    // SC: O(K * N)
    private int maxProfitByDP(int K, int[] prices){
        if(K <= 0){
            return 0;
        }
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        //if K >= len/2, then it's problem 122. Best Time to Buy and Sell Stock II
        if (K >=  len/2) {
            int maxPro = 0;
            for (int i = 1; i < len; i++) {
                if (prices[i] > prices[i-1])
                    maxPro += prices[i] - prices[i-1];
            }
            return maxPro;
        }

        // otherwise, it's problem 123. Best Time to Buy and Sell Stock III
        int transactionTimes = K;
        int[][] dp = new int[len + 1][transactionTimes + 1];
        for(int i = 1; i <= len; i++){
            for(int k = 1; k <= transactionTimes; k++){
                // not do transaction
                dp[i][k] = Math.max(dp[i][k], dp[i - 1][k]);

                // do transaction
                int localMaxProfit = 0;
                for(int j = 1; j < i; j++){
                    localMaxProfit = Math.max(localMaxProfit, dp[j - 1][k - 1] + prices[i - 1] - prices[j - 1]);
                }
                dp[i][k] = Math.max(dp[i][k], localMaxProfit);
            }
        }

        return dp[len][transactionTimes];
    }
}

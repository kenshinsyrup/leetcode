package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_with_cooldown;

class Solution {
    public int maxProfit(int[] prices) {
        return maxProfitByDP(prices);
    }

    // 另外有一种基于FSM的解法，但还是maxProfitByDPII比较容易理解
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/240097/Come-on-in-you-will-not-regret-most-general-java-code-like-all-other-DP-solutions
    // TC: O(N^2)
    // SC: O(N)
    // intuition: DP. 198, 213, 309, 740, 790, 801
    private int maxProfitByDP(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len + 1];
        for(int i = 1; i <= len; i++){
            // i-1 th day is the cool down day
            dp[i] = Math.max(dp[i], dp[i - 1]);

            // i-1 th day is the sell day
            // if we sell at i-1th day, and reach max profit when buy day is j-1th day. then profit is prices[i-1]-prices[j-1]+prevProfit. prevProfit is the max profit before j-1th day. here be careful, this max profit is not dp[j-1], because since j-1th day we buy the stock, then j-2th day must not a buy or sell day, so only could be a colldown day, so j-2th day's max profit is j-3th day's max profit is dp[j-2]
            for(int j = 1; j < i; j++){
                if(j >= 2){
                    dp[i] = Math.max(dp[i], prices[i - 1] - prices[j - 1] + dp[j - 2]);
                }else{
                    dp[i] = Math.max(dp[i], prices[i - 1] - prices[j - 1]);
                }
            }
        }

        return dp[len];
    }

}

package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_with_cooldown;

class Solution {
    public int maxProfit(int[] prices) {
        // return maxProfitByDP(prices); WRONG
        return maxProfitByDPII(prices);
    }

    // 另外有一种基于FSM的解法，但还是maxProfitByDPII比较容易理解
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)

    // TC: O(N^2)
    // SC: O(N)
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/240097/Come-on-in-you-will-not-regret-most-general-java-code-like-all-other-DP-solutions
    /*
    Let's denote M[i] as the max profit ending at day i, then for day i, what kind of day it is?
3 options: buy day, cooldown day, sell day?

It cannot be a buy day, since we cannot make any profit buying at the last day because you have no chance to sell it. So 2 possibilities left: cooldown day, sell day.
If It is a cooldown day, then the max profit is the same as day i - 1 =>M[i] = M[i - 1](1)
If It is a sell day, then which day is the buy day? The buy day could be any days before day i(exclusive, i.e. 0 to i - 1). Let's denote it as j, then the profit we get = nums[i] - nums[j] + previous day's profit. The problem becomes what is the previous days' profit? To figure it out, ask youself what about the day j - 1?
3.1. j - 1 cannot be a buy day. Since you cannot have 2 continuous days(j - 1 and j) as the buy day, you must sell the stock before you buy again as required.
3.2. j - 1 cannot be a sell day as well, because if it is, day j must be a cooldown day.
3.3. Thus, day j - 1 must be a cooldown day. If it is a cooldown day, then the max profit we can get at day j - 1 is M[j - 2]
Above all, M[i] = Math.max(M[j - 2] + nums[i] - nums[j]) for j = 0 to i - 1 (2)
With (1) and (2), we know that:
M[i] = max(M[i - 1], Math.max(M[j - 2] + nums[i] - nums[j]) for j = 0 to i - 1)
    */
    private int maxProfitByDPII(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        if(prices.length == 1){
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len];
        // base
        dp[1] = Math.max(prices[1] - prices[0], 0);

        for(int i = 2; i < len; i++){
            dp[i] = dp[i - 1];
            for(int j = 0; j < i; j++){
                if(j < 2){
                    dp[i] = Math.max(dp[i], prices[i] - prices[j]);
                }else{
                    dp[i] = Math.max(dp[i], dp[j - 2] + prices[i] - prices[j]);
                }
            }
        }

        return dp[len - 1];
    }

    // this is wrong, greedy in local dont necessary give us the optimal sol at global, for example [1,2,4]
    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 198, 213, 309, 740, 790, 801
    // we have only one constraint is the prices, so we need 1-d array
    // dp[i] means we are given i days to buy or sell, so we need extra dp[0] to represent that no days giving, so dp[0] = 0;
    // dp[i] has two conditions to choose:
    // 1 we sell it, ie we sell at the prices[i-1], then we could not sell it yesterday because we will get a cooldown today, so dp[i] = dp[i-1-1] + prices[i-1] - min(prices[last_sell:today])
    // 2 we dont sell, dp[i] = dp[i-1]
    // we choose the max
    // here we find to caculate the profit, we need to track the best buy price, since every time we must operate in pair, means we must first buy, then sell, then buy, then sell... so every time we sell, we could keep to update a min price to as the buy price
    private int maxProfitByDP(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len + 1];

        int buyPrice = prices[0];

        for(int i = 2; i <= len; i++){
            int tempProfit = dp[i - 1 - 1] + prices[i - 1] - buyPrice;
            if(tempProfit > dp[i - 1]){
                dp[i] = tempProfit;

                // if sell, then we make the next price as the buy price
                if(i < len){
                    buyPrice = prices[i];
                }
            }else{
                dp[i] = dp[i - 1];

                // if not sell, see if the price is good
                buyPrice = Math.min(buyPrice, prices[i - 1]);
            }
        }

        return dp[len];
    }

}

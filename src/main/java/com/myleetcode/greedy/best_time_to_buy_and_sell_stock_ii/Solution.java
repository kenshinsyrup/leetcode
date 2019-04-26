package com.myleetcode.greedy.best_time_to_buy_and_sell_stock_ii;

class Solution {
    public int maxProfit(int[] prices) {
        return maxProfitByPeakValley(prices);
    }

    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/discuss/208241/Explanation-for-the-dummy-like-me
    // Peak Valley Approach
    private int maxProfitByPeakValley(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int profit = 0;
        int i = 0;
        int len = prices.length;
        while(i < len - 1){
            // find the valley
            while(i < len - 1 && prices[i + 1] <= prices[i]){
                i++;
            }
            int buy = prices[i];

            while(i < len - 1 && prices[i + 1] > prices[i]){
                i++;
            }
            int sell = prices[i];

            profit+= sell - buy;
        }

        return profit;
    }
}

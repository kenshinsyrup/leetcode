package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_ii;

class Solution {
    public int maxProfit(int[] prices) {
        // return maxProfitByGreedy(prices);
        // return maxProfitByGreedyII(prices);

        return maxProfitByDP(prices);
    }

    // sol1: DP
    // TC: O(N^2)
    // SC: O(N)
    private int maxProfitByDP(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;
        int[] dp = new int[len + 1];
        for(int i = 1; i <= len; i++){
            // no transanction
            dp[i] = Math.max(dp[i], dp[i - 1]);

            // transanction
            for(int j = 1; j < i; j++){
                dp[i] = Math.max(dp[i], dp[j - 1] + prices[i - 1] - prices[j - 1]);
            }
        }

        return dp[len];
    }

    /*
    出错点：
    1 思路：多次买卖，就不用dp了，直接找波峰-波谷就是一个local max profit，然后将他们相加
    2 思路：见solution. 优化上述思路，波峰-波谷的值，不一定要直接找到波峰波谷然后计算，可以这样：遇到pirces[i]>prices[i-1]就获取该值，这样最终得到的也是同样的一个local max profit.
    */

    private int maxProfitByGreedyII(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int profit = 0;
        int i = 0;
        int len = prices.length;
        while(i < len - 1){
            if(prices[i + 1] > prices[i]){
                profit += prices[i + 1] - prices[i]; // 所有上升区间都是profit
            }

            i++;
        }

        return profit;
    }

    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/discuss/208241/Explanation-for-the-dummy-like-me
    // Peak Valley Approach
    private int maxProfitByGreedy(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int profit = 0;
        int i = 0;
        int len = prices.length;
        int valleyPriceLocal = prices[0];
        int peekPriceLocal = prices[0];
        while(i < len - 1){
            // find the valley
            while(i < len - 1 && prices[i + 1] <= prices[i]){
                i++;
            }
            valleyPriceLocal = prices[i];

            while(i < len - 1 && prices[i + 1] > prices[i]){
                i++;
            }
            peekPriceLocal = prices[i];

            profit += peekPriceLocal - valleyPriceLocal;
        }

        return profit;
    }
}
package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock;

public class Solution {
    public int maxProfit(int[] prices) {
        return maxProfiltByDP(prices);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: DP. 52, 121
    // dp[i] means the max profit at ith day, then dp[i] = max(dp[i-1], prices[i] - min(prices[0:i-1]))
    // base is the dp[0], because we are only have the 0th day, then profit must be 0. and the min price is prices[0]. so we have no need to have extra row as base
    private int maxProfiltByDP(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }

        int len = prices.length;
        // dp
        int[] dp = new int[len];// initial profit are all 0, the is base

        // min price at first is prices[0]
        int minPrice = prices[0];

        // dp
        for(int i = 1; i < len; i++){
            dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);

            // update minPrice
            minPrice = Math.min(minPrice, prices[i]);
        }

        return dp[len - 1];
    }
    
    // 严格套用Kadane's Algorithm的写法：
    //*maxCur = current maximum value
// *maxSoFar = maximum value found so far
    private int maxProfitByKadane(int[] prices){
    int maxCur = 0, maxSoFar = 0, min = prices[0];
        
    /*begin with 1 because we already set the first element to min, no need to run this redundant loop; of course you can begin with 0 too, but it slows the runtime by 1ms*/
    for(int i = 1; i < prices.length; i++){
        //check the difference of each element and the current min, update min if necessary.
        maxCur = prices[i] - ( (prices[i] < min) ? (min = prices[i]) : (min = min) );
        maxSoFar = Math.max(maxSoFar, maxCur); 
     }
        
    return maxSoFar;
}
}
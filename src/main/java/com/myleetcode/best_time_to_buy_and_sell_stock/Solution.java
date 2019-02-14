package com.myleetcode.best_time_to_buy_and_sell_stock;

class Solution {
    public int maxProfit(int[] prices) {

//         整体思路还是很符合Kadane's Algorithm，也就是很像maximum subarray那道题


        // 注意，array之类的，不是null也可以是空数组
        //special case
        if(prices == null){
            return 0;
        }

        int len = prices.length;
        if(len == 0){
            return 0;
        }

        // 推荐。dp
        return maxProfieByDP(prices);

        // kadane likely
        // return maxProfitByMaybeKadane(prices);
        // return maxProfitByKadane(prices);
    }

    private int maxProfieByDP(int[] prices){
        //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/discuss/39112/Why-is-this-problem-tagged-with-"Dynamic-programming"/36893
        //dp的思路就是，我想知道i这个位置，能得到的最大利润是多少，那么：首先我得知道i这个地方的利润，然后，跟别的利润比，取最大值。这里就有两个东西要得到，1是i之前的最低点价格(否则就要遍历i之前所有的值然后i去减再取最小值，当然也是可以的)；2是i之前的最大的利润。这个2是dp的内容。

        int pLen = prices.length;

        // 1based，i天卖，得profit为value. 之所以不计第几天买，是因为我们一定要在价格最低的时候买，而不是遍历着用j来买，没必要.
        int[] dp = new int[pLen + 1];

        // 这个是个严重错误，不要把这个设置为max，这样会严重影响dp[i] = dp[i - 1] + minPrice - curP;这个公式。不论从哪个方面想，我们都应该初始化minPrice为prices[0]。
        // int minPrice = Integer.MAX_VALUE;
        int minPrice = prices[0];

        // base case
        // dp[0] = 0;


        // normal case
        for(int i = 1; i <= pLen; i++){
            int curP = prices[i - 1];



            // 这个计算方式是错的，因为我们不能再第二天买，第一天卖。所以如果curP小于minP，我们也不可能获得minP - curP这部分收益的。所以 唯一的 收益获得方式就是curP - minP。
            // if(curP < minPrice){
            //     dp[i] = dp[i - 1] + minPrice - curP;
            //     minPrice = curP;
            // }else{
            //     dp[i] = dp[i - 1];
            // }

            // 改正上面的问题，在curP > minP时，当前的收益curP - minP，与dp[i - 1]取大值。当curp<=minP时，那么肯定dp[i]=dp[i - 1]因为curP - minP是负数，这时候 也应该更新minP。
            // if(curP > minPrice){
            //     dp[i] = Math.max(dp[i - 1], curP - minPrice);
            // }else{
            //     dp[i] = dp[i - 1];
            //     minPrice = curP;
            // }

            // 改良上面这段繁复的代码，更简洁的解决
            int curProfit = curP - minPrice;
            dp[i] = Math.max(dp[i - 1], curProfit);
            minPrice = Math.min(minPrice, curP);

        }

        return dp[pLen];

    }

    private int maxProfitByMaybeKadane(int[] prices){
        int minPriceBefore = prices[0];
        int maxProfitBefore = 0;

        int len = prices.length;
        for(int i = 0; i < len; i++){
            maxProfitBefore = Math.max(maxProfitBefore, prices[i] - minPriceBefore);
            minPriceBefore = Math.min(minPriceBefore, prices[i]);
        }


        return maxProfitBefore;
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

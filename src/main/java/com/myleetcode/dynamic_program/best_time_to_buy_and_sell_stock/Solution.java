package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock;

class Solution {
    public int maxProfit(int[] prices) {
        //dp的思路就是，我想知道i这个位置，能得到的最大利润是多少，那么：首先我得知道i这个地方的利润，然后，跟别的利润比，取最大值。这里就有两个东西要得到，1是i之前的最低点价格(否则就要遍历i之前所有的值然后i去减再取最小值，当然也是可以的)；2是i之前的最大的利润。这个2是dp的内容。
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
        
        int minPriceBefore = prices[0];
        int maxProfitBefore = 0;
        
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
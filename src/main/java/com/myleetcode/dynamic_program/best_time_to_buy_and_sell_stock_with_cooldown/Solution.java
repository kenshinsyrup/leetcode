package com.myleetcode.dynamic_program.best_time_to_buy_and_sell_stock_with_cooldown;

class Solution {
    public int maxProfit(int[] prices) {
        // return maxProfitByDP(prices);
        return maxProfitByDPForInfiniteK(prices);
    }

    /*
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems

    Part 1, Idea

    The Stock DP template is:
    T[i][k][0] means give i prices and k operations, ie prices[0:i-1] K[0:k-1],  after this day if we has 0 Stock in hand, the most profit.
    T[i][k][1] means give i prices and k operations, ie prices[0:i-1] K[0:k-1], if we has 1 Stock in hand,  after this day the most profit.

    To reach 0 Stock in hand, we have two ways: 1. Rest, don't buy Stock; 2. Sell, sell a Stock and get a profit
    To reach 1 Stock in hand, we have two ways: 1. Rest, don't sell Stock; 2. Buy, buy a Stock and cost some money

    Another important part is, the problem call Buy+Sell a Transaction, but this in our defination relates to two operations: Buy and Sell. So we only consider one of them as a Operation, here we use the Buy part as a Operation. This means when we Buy a Stock, to get the T[i+1][k+1][1], the previous related dp is: No Operation T[i][k + 1][1] and Do Buy Operation T[i+1][k][0] - prices[i]

    Function:
    T[i + 1][k + 1][0] = max(T[i][k + 1][0], T[i][k + 1][1] + prices[i])
    T[i + 1][k + 1][1] = max(T[i][k + 1][1], T[i][k][0] - prices[i])

    Base Case:
    T[0][k][0] = 0; // Means given 0 Stock totally, end with 0 Stock in hand, the profit is 0
    T[0][k][1] = Integer.MIN_VALUE; // Means given 0 Stock totally, end with 1 Stock in hand, that's impossible, the profit is Integer.MIN_VALUE

    About the final return value, we always return the T[len][K][0] because has 0 Stock in hand must has more profit than has Stock in hand.

    Part 2, Problems

    For 121. Best Time to Buy and Sell Stock
    K is 1 or any positive integer smaller than or equal to prices.length/2 which means the K operations could be exhausted.

    For 122. Best Time to Buy and Sell Stock II
    K is infinite or any positive integer larger than prices.length/2 which means the K operations could never be exhausted, so it's not a constrain.
    T[i + 1][0] = max(T[i][0], T[i][1] + prices[i])
    T[i + 1][1] = max(T[i][1], T[i][0] - prices[i])

    For 123. Best Time to Buy and Sell Stock III
    K is 2, this is same with 121, K is any positive integer smaller than or equal to prices.length/2.

    For 188. Best Time to Buy and Sell Stock IV
    K is not sure, so this problem is a combination of 121 and 122, we caculate the result based on the relation of given K and prices.length/2.

    For 309. Best Time to Buy and Sell Stock with Cooldown
    This is a transformation of 122, K is infinite, since we could only buy Stock if at previous day we did not do a Sell Operation, so the function will be:
    T[i + 1][0] = max(T[i][0], T[i][1] + prices[i])
    T[i + 1][1] = max(T[i][1], T[i - 1][0] - prices[i])

    And this function used the i-1 index, but when we do the DP caculation, we should traverse prices ie the i from 0 to len-1, then to avoid out of boundary error, we need additionally caculate the two base cases: dp[1][0], dp[1][1]

    For 714. Best Time to Buy and Sell Stock with Transaction Fee
    This is a transformation of 122, K is infinite, the only difference is we need additional fee when buy a Stock.

    Part 3, Summary
    So, the Sotck series problems have a common template, they could be seperated to two kinds: K is infinite(122) or not(123).

    */
    private int maxProfitByDPForInfiniteK(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int len = prices.length;
        int[][] dp = new int[len + 1][2];
        // Base case.
        // Given 0 Stock.
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        dp[1][0] = 0;
        dp[1][1] = -prices[0];

        // Normal case.
        for (int i = 1; i < len; i++) {
            // End today with 0 Stock.
            dp[i + 1][0] = Math.max(dp[i][0], dp[i][1] + prices[i]);

            // End today with 1 Stock.
            dp[i + 1][1] = Math.max(dp[i][1], dp[i - 1][0] - prices[i]);
        }

        return dp[len][0];

    }

    // 另外有一种基于FSM的解法，但还是maxProfitByDPII比较容易理解
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/240097/Come-on-in-you-will-not-regret-most-general-java-code-like-all-other-DP-solutions
    // TC: O(N^2)
    // SC: O(N)
    // intuition: DP. 198, 213, 309, 740, 790, 801
    private int maxProfitByDP(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int len = prices.length;

        int[] dp = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            // i-1 th day is the cool down day
            dp[i] = Math.max(dp[i], dp[i - 1]);

            // i-1 th day is the sell day
            // if we sell at i-1th day, and reach max profit when buy day is j-1th day. then profit is prices[i-1]-prices[j-1]+prevProfit. prevProfit is the max profit before j-1th day. here be careful, this max profit is not dp[j-1], because since j-1th day we buy the stock, then j-2th day must not a buy or sell day, so only could be a colldown day, so j-2th day's max profit is j-3th day's max profit is dp[j-2]
            for (int j = 1; j < i; j++) {
                if (j >= 2) {
                    dp[i] = Math.max(dp[i], prices[i - 1] - prices[j - 1] + dp[j - 2]);
                } else {
                    dp[i] = Math.max(dp[i], prices[i - 1] - prices[j - 1]);
                }
            }
        }

        return dp[len];
    }

}

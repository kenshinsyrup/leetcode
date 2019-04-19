package com.myleetcode.dynamic_program.coin_change;

class Solution {
    public int coinChange(int[] coins, int amount) {

        if(coins == null || amount < 0){
            return -1;
        }

        return coinChangeByDP(coins, amount);
    }

    private int coinChangeByDP(int[] coins, int amount){
        // 要知道amount的min，那么如果知道了amount - 1的min，就只要在此基础上遍历一遍所有coin尝试。注意的点在于dp的状态关系，dp[curAmount]相关的不是dp[curAmount - 1]而是dp[curAmount - coins[i]]。这里用curAmount而不是i去趋近amount是为了方便理解。

        int[] dp = new int[amount + 1];

        // 初始化成最大值的状态，使用MAX而不用-1是为了在下面判断min的时候方便
        for(int i = 0; i < dp.length; i++){
            dp[i] = Integer.MAX_VALUE;
        }

        // base case，成功，coins中选取0个对应amount0
        dp[0] = 0;

        // normal case
        for(int curAmount = 1; curAmount <= amount; curAmount++){
            int tempMin = Integer.MAX_VALUE;

            for(int j = 0; j < coins.length; j++){
                if(curAmount >= coins[j] && dp[curAmount - coins[j]] != Integer.MAX_VALUE){
                    tempMin = Math.min(tempMin, dp[curAmount - coins[j]]);
                }
            }

            if(tempMin != Integer.MAX_VALUE){
                dp[curAmount] = tempMin + 1;
            }

        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];

    }
}
package com.myleetcode.dynamic_program.guess_number_higher_or_lower_ii;

class Solution {

    //intuition: looks like 374. Guess Number Higher or Lower. BUT IT'S NOT.
    // 题意： https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84764/Simple-DP-solution-with-explanation~~
    // https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84762/Improve-the-Question-and-Example/166225
    // https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84766/Clarification-on-the-problem-description.-Problem-description-need-to-be-updated-!!!

    // solution: https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84764/Simple-DP-solution-with-explanation~~/191983

    public int getMoneyAmount(int n) {
        return getMoneyAmountByDP(n);
    }

    // DP to solve MinMax Problem
    /*
Big Idea: Given any n, we make a guess k. Then we break the interval [1,n] into [1,k - 1] and [k + 1,n]. The min of worst case cost can be calculated recursively as

cost[1,n] = k + max{cost[1,k - 1] + cost[k+1,n]}

Also, it takes a while for me to wrap my head around "min of max cost". My understand is that: you strategy is the best, but your luck is the worst. You only guess right when there is no possibilities to guess wrong.*/
    /*
Definition of dp[i][j]: minimum number of money to guarantee win for subproblem [i, j].

Target: dp[1][n]

Corner case: dp[i][i] = 0 (because the only element must be correct)

Equation: we can choose k (i<=k<=j) as our guess, and pay price k. After our guess, the problem is divided into two subproblems. Notice we do not need to pay the money for both subproblems. We only need to pay the worst case (because the system will tell us which side we should go) to guarantee win. So dp[i][j] = min (i<=k<=j) { k + max(dp[i][k-1], dp[k+1][j]) }
    */
    private int getMoneyAmountByDP(int n){
        if(n < 1){
            return -1;
        }

        int[][] dp = new int[n + 1][n + 1];
        // !!! base case
        for(int i = n; i >= 0; i--){
            int j = i;
            dp[i][j] = 0;
        }

        // diagonal area dp
        for(int i = n; i >= 1; i--){ // i from bottom to top, start with 1 according the problem discription
            for(int j = i + 1; j <= n; j++){ // j from left to right, start with i+1, because j is from i to n, and j == i is the base case that we have solved

                // !!! initial are max
                dp[i][j] = Integer.MAX_VALUE;

                // !!! then we try all possibilities in [i:j), because we have the range [i:j], we guess from i to j, so we dont need to guess j, becuase if i:j-1 is wrong, j is correct. this logic to avoid the outofboundary in dp[i][k+1]
                for(int k = i; k <= j - 1; k++){
                    dp[i][j] = Math.min(dp[i][j], k + Math.max(dp[i][k - 1], dp[k + 1][j]));
                }
            }
        }

        return dp[1][n];
    }
}

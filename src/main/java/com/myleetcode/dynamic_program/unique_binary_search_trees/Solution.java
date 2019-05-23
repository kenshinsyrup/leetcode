package com.myleetcode.dynamic_program.unique_binary_search_trees;

class Solution {
    public int numTrees(int n) {
        return numTreesByDP(n);
    }

    // TC: Catalan number
    // SC: O(N)
    // https://leetcode.com/problems/unique-binary-search-trees/discuss/31666/DP-Solution-in-6-lines-with-explanation.-F(i-n)-G(i-1)-*-G(n-i)
    // DP.
    // the complete version of this induction rule :f(n) = f(0) * f(n - 1) + f(1) * f(n - 2) + ...+ f(n - 1) * f(0),
// while the n in f(n) means the number of BST trees with n nodes.
    private int numTreesByDP(int n){
        if(n <= 0){
            return 1;
        }

        long[] dp = new long[n + 1];
        dp[0] = 1;

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= i; j++){
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }

        return (int)dp[n];
    }
}

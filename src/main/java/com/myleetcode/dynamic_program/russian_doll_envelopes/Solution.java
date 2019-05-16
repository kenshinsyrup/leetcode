package com.myleetcode.dynamic_program.russian_doll_envelopes;

import java.util.Arrays;

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        return maxEnvelopesByDP(envelopes);
    }

    // there's a solution combine the DP and BS: https://leetcode.com/problems/russian-doll-envelopes/discuss/82763/Java-NLogN-Solution-with-Explanation
    // the thought is the same with pure DP, but the TC is O(NlogN), harder to figure out

    // TC: O(N^2)
    // SC: O(N^2)
    // pure DP solution
    // https://leetcode.com/problems/russian-doll-envelopes/discuss/82810/Short-and-simple-Java-solution-(15-lines)
    // first, we sort all envelopes, ascending by width, then our problem is converted to: find the Longest Increasing Subsequence in height 300. Longest Increasing Subsequence
    // second, using DP: dp[i] means num of envelope we could have in envelopes[0:i], then dp[i] = max(dp[j]) + 1, if envelopes[j] fit envelopes[i] and j is in [0:i]
    // base case if dp[i] = 1, i in [0:n]
    private int maxEnvelopesByDP(int[][] envelopes){
        if(envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0){
            return 0;
        }

        // sort enveolpes by width ascending
        Arrays.sort(envelopes, (env1, env2) -> {
            return env1[0] - env2[0];
        });

        int len = envelopes.length;
        int[] dp = new int[len];
        for(int i = 0; i < len; i++){
            dp[i] = 1;
        }

        int maxLen = Integer.MIN_VALUE;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < i; j++){
                if(envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(dp[i], maxLen);
        }

        return maxLen;
    }
}

package com.myleetcode.dynamic_program.edit_distance;

public class Solution {
    public int minDistance(String word1, String word2) {
        return minDistanceByDP(word1, word2);
    }

    /*
    Edit Distance Problem. DP.

    Thought:
        dp[i][j] means given first i chars in word1 and j chars in word2, how many steps needed to convert word1 to word2

    Function:
        dp[i][j] = dp[i - 1][j - 1], if word1[i - 1]==word2[j - 1]
        dp[i][j] = min(
            dp[i][j-1], insert into word2
            dp[i-1][j], delete from word1
            dp[i-1][j-1] + 1, replace in word1 and word2 to the same
        ) + 1, if word1[i-1]!=word2[j-1]

    Base case:
        dp[0][j] = j;
        dp[i][0] = i;
        others default is Max Integer.
    */
    private int minDistanceByDP(String word1, String word2) {
        if (word1 == null || word1.length() == 0) {
            if (word2 == null) {
                return 0;
            }
            return word2.length();
        }
        if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // DP explore.
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        return dp[len1][len2];
    }
}
package com.myleetcode.dynamic_program.longest_common_subsequence;

public class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        return longestCommonSubsequenceByDP(text1, text2);
    }

    private int longestCommonSubsequenceByDP(String text1, String text2) {
        if (text1 == null || text1.length() == 0 || text2 == null || text2.length() == 0) {
            return 0;
        }

        int len1 = text1.length();
        int len2 = text2.length();

        // DP array.
        int[][] dp = new int[len1 + 1][len2 + 1];
        // Base case. 0.
        // Explore.
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        // For reference: get the LCS based on the dp array.
        // String lcs = getLCS(dp, text1, text2);
        // System.out.println(lcs);

        return dp[len1][len2];
    }

    private String getLCS(int[][] dp, String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        StringBuilder sb = new StringBuilder();
        int i = len1;
        int j = len2;
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                sb.append(text1.charAt(i - 1));

                i--;
                j--;
            } else {
                if (dp[i][j - 1] > dp[i - 1][j]) {
                    j--;
                } else {
                    i--;
                }
            }
        }

        return sb.reverse().toString();
    }
}

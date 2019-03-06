package com.myleetcode.dynamic_program.longest_palindromic_subsequence;

class Solution {
    public int longestPalindromeSubseq(String s) {

        // special case
        if(s == null || s.length() == 0){
            return 0;
        }

        return longestPalindromeSubseqByDP(s);
    }

    private int longestPalindromeSubseqByDP(String s){
        // DP of CS5800

        // recursion function
        // 2 dimension dp[i][j], i for start, j for end of palindrome substr.
        // use n for s.length().
        // bottom up, so outter i is [n-1, 0], inner j is [i+1, n-1]
        // case 1: if s[i] == s[j], dp[i][j] = 2 + dp[i + 1][j - 1]
        // case 2: otherwise, dp[i][j] = max(dp[i+1][j], dp[i][j-1])

        // base case
        // consider the classic matrix, i is [0, n-1] up to bottom (i move form bottom to up), j is [0, n-1] left to right (j moves from left to right). here we find j is based on i and j is greater than i, so we only use the upper right of matrix, and every dp[i][j] only depends on three possible suqare(left one, up one, diagonal left up one), so the base is the diagonal. (please draw it if feel not clear).
        // so base case is the diagonal, ie, dp[i][i]. Every dp[i][i] is 1 because single char is palinedrome.

        int len = s.length();
        int[][] dp = new int[len][len];//base case is default 0s.

        // base case, one char is palindrome
        for(int i = 0; i < len; i++){
            dp[i][i] = 1;
        }

        // i [0, len - 1] in matrix [0, n-1][0,n-1] moves from bottom to up in matrix, j [i + 1, len - 1] in matrix [0,n-1][0,n-1] moves from left to right.
        for(int i = len - 1; i >= 0; i--){
            for(int j = i + 1; j <= len - 1; j++){
                if(s.charAt(i) == s.charAt(j)){
                    dp[i][j] = 2 + dp[i + 1][j - 1];// here is 2!!!
                }else{
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // at last, the up right corner square is our result
        return dp[0][len - 1];

    }
}

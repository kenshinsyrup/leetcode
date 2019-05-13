package com.myleetcode.dynamic_program.wildcard_matching;

class Solution {
    public boolean isMatch(String s, String p) {
        return isMatchByDP(s, p);
    }

    // https://leetcode.com/problems/wildcard-matching/discuss/17812/My-java-DP-solution-using-2D-table
    // https://leetcode.com/problems/wildcard-matching/discuss/17856/Put-these-two-questions-together-(Wildcard-Matching-and-Regular-Expression-Matching)
    // DP:
    // we have two dimensions here is the String str and the Strint patt, this is like the 10. Regular Expression Matching.
    // this should start form back to head
    // dp[i][j] means given str[i:end] and patt[j:end], the string match or not
    // if(patt[j] == '*'), dp[i][j] = dp[i+1][j+1] || dp[i+1][j] || dp[i][j+1], means the * match 0, 1, or multiple char in str respectively
    // if(patt[j] == '?' || patt[j] == str[i]), dp[i][j] = dp[i+1][j+1]
    // otherwist, dp[i][j] = false
    // base case: given 0 char of str, then if given char from patt is *, dp[strLen][j] = true;
    // TC: O(N^2)
    // SC: O(N^2)
    private boolean isMatchByDP(String str, String patt){
        if((str == null && patt == null) || (str.length() == 0 && patt.length() == 0)){
            return true;
        }
        // dont check like this, consider this: is str is empty and patt are all *
        // if(str == null || str.length() == 0 || patt == null || patt.length() == 0){
        //     return false;
        // }

        int strLen = str.length();
        int pattLen = patt.length();


        boolean[][] dp = new boolean[strLen + 1][pattLen + 1];

        // base case
        dp[strLen][pattLen] = true;
        for(int j = pattLen - 1; j >= 0; j--){
            if(patt.charAt(j) == '*'){
                dp[strLen][j] = true;
            }else{ // if find not * in patt, break, ie patt[0:j] could not match emtpy string str
                break;
            }
        }

        for(int i = strLen - 1; i >= 0; i--){
            for(int j = pattLen - 1; j >= 0; j--){
                if(str.charAt(i) == patt.charAt(j) || patt.charAt(j) == '?'){
                    dp[i][j] = dp[i + 1][j + 1];
                }else if(patt.charAt(j) == '*'){
                    dp[i][j] = dp[i + 1][j + 1] || dp[i + 1][j] || dp[i][j + 1];
                }else{
                    dp[i][j] = false;
                }
            }
        }

        return dp[0][0];
    }
}

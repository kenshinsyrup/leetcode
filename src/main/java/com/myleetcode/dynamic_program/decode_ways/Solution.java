package com.myleetcode.dynamic_program.decode_ways;

class Solution {
    public int numDecodings(String s) {
        return numDecodingsByDP(s);
    }

    // interesting, if "0000123999" as input, the OJ tests thinks its answer is 0, so we dont need bother to check the prefix 0, just to check if S has prefix 0 then return 0
    // intuition: DP
    // 一维
    // dp[i] means the encoded ways if given i chars of str
    /*
    dp[i] = {
    dp[i - 1] + dp[i - 2], if str[i-1] could be encoded, and str[i-2]str[i-1] could be endoced.
    dp[i - 1], if str[i-1] could be encoded, ie str[i-1] is not 0.
    dp[i - 2], if str[i-2]str[i-1] could be endoced.
    0, if str[i - 1] cold not be encoded, and str[i - 1]str[i - 2] could not be encoded too
    }
    */
    /*
    base case
    we need dp[i-1] and dp[i-2] so dp[0] and dp[1] are both base. in dp exploration, i is from 2, though in the definition dp[0] seems to be more reasonable to be 0, but we have to make the dp[2] valid, when we have a string "12", dp[2] == dp[0] + dp[1] == 2, so our base case dp[0] should be set to 1.
    */
    // TC: O(N), traverse once String s
    // SC: O(N), dp array
    private int numDecodingsByDP(String s){
        if(s == null || s.length() == 0 || s.charAt(0) == '0'){
            return 0;
        }

        int len = s.length();
        int[] dp = new int[len + 1];
        // !!! important part, base case dp[0] should be 1, this is deduced by example, if you have "12", then dp[2] += dp[1] and dp[2] += dp[0] should be 2, then dp[0] should be 1
        dp[0] = 1;
        dp[1] = 1;

        for(int i = 2; i <= len; i++){
            char curChar = s.charAt(i - 1);
            char previousChar = s.charAt(i - 2);

            if(curChar != '0' && hasTwo(previousChar, curChar)){
                dp[i] = dp[i - 1] + dp[i - 2];
            }else if(curChar != '0'){
                dp[i] = dp[i - 1];
            }else if(hasTwo(previousChar, curChar)){
                dp[i] = dp[i - 2];
            }
        }

        return dp[len];
    }

    private boolean hasTwo(char firstChar, char secondChar){
        if((firstChar == '1' && secondChar <= '9' && secondChar >= '0') || (firstChar == '2' & secondChar >= '0' && secondChar <= '6')){
            return true;
        }
        return false;
    }
}

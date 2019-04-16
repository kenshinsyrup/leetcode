package com.myleetcode.dynamic_program.decode_ways;

class Solution {
    public int numDecodings(String s) {
        return numDecodingsByDP(s);
    }


    // TC: O(N), traverse once String s
    // SC: O(N), dp array
    // interesting, if "0000123999" as input, the OJ tests thinks its answer is 0, so we dont need bother to trim the prefix 0, just to check if S has prefix 0 then return 0
    // intuition: dp seems work.
    // dp[i] means add s[i - 1] could add how many more collections. since the encode only takes 2 char at most, so dp[i] += dp[i-1] if s[i-1] is not a char '0'. and if s[i-1] could append behind s[i-1-1] to form a num 10-26 then dp[i] += dp[i-2]
    // we make the dp[0] mean an empty string will have one way to decode.
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
            int curIdx = i - 1;
            char previousChar = s.charAt(curIdx - 1);
            char curChar = s.charAt(curIdx);

            // this actually no need, thoug if we add this could speed up the running time because this is a early termination in bad case like "100000000..."
            // for case like "100", two contiguous 0 will make us return 0 because leading 0 could not form a code
            // if(previousChar == '0' && curChar == '0'){
            //     return 0;
            // }

            // 如果str的curChar不是0，那么他可以自己独立作为一个code，所以dp[i]可以加上dp[i-1]个组合
            if(curChar != '0'){
                dp[i] += dp[i - 1];
            }

            // 如果curChar和previousChar可以组成一个10-26之间的数，那么他们两个可以作为一个code，那么dp[i]可以加上dp[i-2]个组合
            if(hasTwo(previousChar, curChar)){
                dp[i] += dp[i - 2];
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

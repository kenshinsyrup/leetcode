package com.myleetcode.word_break;

import java.util.List;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {

        // special case
        if(s == null || wordDict == null){
            return false;
        }

        return wordBreakByDP(s, wordDict);

    }

    private boolean wordBreakByDP(String s, List<String> wordDict){

        int sLen = s.length();

        boolean[] dp = new boolean[sLen + 1];

        // base case
        dp[0] = true;

        // i是dp用来存储给 string中第i个char的时候 的结果。由于要使用substring，其是0based的，所以找[0,i]的不包含子串时，可以直接用j从[0,i)来找。这样，subStr就正好是substring(j,i)。然后dp[i]就依赖于 substr是否存在于dict 以及 dp[j]是否breakable。
        // 外层循环和dp的i保持一致，用来指代1based的情况下，第1到第i个(全包含)字符串对应的dp解。内层循环中，遍历外层循环的字符串的子串，也就是substring(j,i)。dp[0]作为base我们已经设定为了true。
        for(int i = 1; i <=sLen; i++){
            for(int j = 0; j < i; j++){
                String subStr = s.substring(j, i);
                if(dp[j] && wordDict.contains(subStr)){
                    dp[i] = true;
                }
            }
        }

        return dp[sLen];

    }
}
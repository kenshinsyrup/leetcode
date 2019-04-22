package com.myleetcode.dynamic_program.regular_expression_matching;

class Solution {
    public boolean isMatch(String s, String p) {
        return isMatchByDP(s, p);
    }

    // intuition: DP. 72, 10, 44, 97, 115, 583, 712
    // base case is we dont pick any char from S and P, use dp[0][0] to represent it, then dp[i][j] means the S[0:i-1] and P[0:j-1] match or not. then:
    // 1 dp[i][j] = dp[i-1][j-1], if S[i-1] == P[j-1]
    // 2 dp[i][j] = dp[i-1][j-1], if P[j-1] == '.'
    // 3 if P[j-1] == '*', then
    // 3.1 if S[i-1] != P[j-2], dp[i][j] = dp[i-1][j-2]
    // 3.2 if S[i-1] == P[j-2], dp[i][j] = or(dp[i-1][j], dp[i][j-1], dp[i-1][j-2]), dp[i-1][j] means * as multiple, dp[i][j-1] means * as single char, dp[i-1][j-2] means * as 0 char, we choose any of true status from this
    // https://leetcode.com/problems/regular-expression-matching/discuss/161365/Java-solution-with-more-detailed-explanation
    private boolean isMatchByDP(String s, String p){
        if(s == null || p == null){
            return false;
        }

        int sLen = s.length();
        int pLen = p.length();

        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // dp中有一个重点是坐标的转换，比较容易懵逼。比如下面代码里，for循环条件里的i和j从1到len，是给dp用的坐标，表达的是：1based的系统下，第i个位置的char和第j个位置的char的匹配关系，存入dp[i][j]。但是由于charAt方法是0based的，所以所有charAt方法处取char时，都比正常的 取第几个char 这个操作多减去了一个1.其余如思路链接所示。

        // base case
        dp[0][0] = true;
        for(int j = 1; j <= pLen; j++){
            if(p.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j - 2]; // *必须当做0也就是-2
            }
        }


        for(int i = 1; i <= sLen; i++){
            for(int j = 1; j <= pLen; j++){
                if(s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.'){
                    dp[i][j] = dp[i - 1][j - 1];
                }else if(p.charAt(j - 1) == '*'){
                    if(p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.'){
                        //  *对应0个s中的char        *对应多个s中的char
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    }else{
                        // *必须当做0
                        dp[i][j] = dp[i][j - 2];
                    }
                }
            }
        }

        return dp[sLen][pLen];
    }
}

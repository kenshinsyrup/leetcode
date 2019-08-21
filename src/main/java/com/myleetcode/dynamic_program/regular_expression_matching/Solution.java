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
    // 3.1 if S[i-1] != P[j-2], dp[i][j] = dp[i-1][j-2], *代表匹配0次
    // 3.2 if S[i-1] == P[j-2], dp[i][j] = or(dp[i-1][j], dp[i-1][j-2]), dp[i-1][j] means * as multiple, dp[i-1][j-2] means * as 0 char, we choose any of true status from this. 只需要分为两个情况，* 用作匹配0次或者大于0次，0次我们知道了表达式；对于大于0次，实际就是匹配完当前s的char，继续用她去匹配其他(之后仍然是0次或者多于0次)
    /*
    https://leetcode.com/problems/regular-expression-matching/discuss/333806/topic

当s[i]==p[j]或者p[j]=='.'时，目标串和模式串的最后两个字符匹配，那么原问题转移为i-1，j-1大小的子问题,ismatch[i][j]=ismatch[i-1][j-1]
当p[j]=='*'时，'*'可以匹配前面一个字符任意多次，可以分成两类：匹配0次或大于0次，最后的结果是这两种情况只要有一个为true则为true
当匹配0次时，ismatch[i][j]=ismatch[i][j-2]
当匹配大于0次时，有一种递归的思想在里面，先匹配1次，然后再递归地匹配0次或者大于0次
我举例子说明
s=baa
p=ba*
当比较到第3个字符时，匹配大于0次，那么先匹配1次，后面还能再匹配0次或大于0次，p变成baa*
因为s的第3个字符和p的第3个字符是相等的，那么可以同时去掉这两个字符，s=ba，p=ba*再比较这两个字符串。这种情况原问题ismatch[i][j]转移成了ismatch[i-1][j]的子问题，条件是s[i]==p[j-1]||p[j-1]=='.'
最后，初始情况是s的大小为空，模式串的大小变化，就只有p[j]=='*'且匹配0次的情况。
    */
    // https://leetcode.com/problems/regular-expression-matching/discuss/161365/Java-solution-with-more-detailed-explanation
    private boolean isMatchByDP(String s, String p){
        if(s == null || p == null){
            return false;
        }

        int sLen = s.length();
        int pLen = p.length();

        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

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
                        //  *对应0个s中的char     *对应多个s中的char
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

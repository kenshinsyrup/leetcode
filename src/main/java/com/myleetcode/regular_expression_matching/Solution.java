package com.myleetcode.regular_expression_matching;

class Solution {
    public boolean isMatch(String s, String p) {

        if(s == null || p == null){
            return false;
        }

        return isMatchByDP(s, p);

    }

    private boolean isMatchByDP(String s, String p){
        // https://leetcode.com/problems/regular-expression-matching/discuss/161365/Java-solution-with-more-detailed-explanation

        int sLen = s.length();
        int pLen = p.length();

        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // dp中有一个重点是坐标的转换，比较容易懵逼。比如下面代码里，for循环条件里的i和j从1到len，是给dp用的坐标，表达的是：1based的系统下，第i个位置的char和第j个位置的char的匹配关系，存入dp[i][j]。但是由于charAt方法是0based的，所以所有charAt方法处取char时，都比正常的 取第几个char 这个操作多减去了一个1.其余如思路链接所示。
        // 重点就是坐标的简历，链接思路里给出的用额外数组来构建一个1based的系统来辅助思考的方法挺好的.
        // 之所以要初始化一个相对输入的字符串多一个长度的dp，是为了构建出当字符串为0的时候的dp base case。

        // base case
        dp[0][0] = true;
        for(int j = 1; j <= pLen; j++){
            if(p.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j - 2]; // *必须当做0也就是-2。 这里根据题目设定，不考虑开头第一个char就是*的情况。
                System.out.println(dp[0][j]);
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
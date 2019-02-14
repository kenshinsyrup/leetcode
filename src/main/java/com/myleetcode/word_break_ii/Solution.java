package com.myleetcode.word_break_ii;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        // 最初的想法就是完全利用word break1.先dp解决完是否的问题，再总结一个string list出来。https://leetcode.com/problems/word-break-ii/discuss/44329/Java-DP-and-Backtracking-Solution

        // 但是，是可以在dp的过程中就保存好最终的string list的。只不过需要一点点改动。
        // 使用dp的方法，和word break基本一样的，难度增加的点在于word break1只需要记录是否可以break，这里要记录break之后的substring们。那么就是在dp的过程中，记录下合格的substring(本题目要求把substring组成句子那就拼接下)，用list来存。所以就是把word break1中的boolean[] dp改成 list[] dp. https://leetcode.com/problems/word-break-ii/discuss/194615/DP-solution-with-detailed-text-and-video-explanation

        // special case
        if(s == null || wordDict == null){
            return new ArrayList<String>();
        }

        return wordBreakByDP(s, wordDict);


    }

    /**这个解法，在面对test case aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"时,会导致MLE - Memory Limit Exceeded。。

     在discuss区，有一个解法https://leetcode.com/problems/word-break-ii/discuss/217082/This-is-a-real-DP-solution提到，加入一行个方法检查
     if(!breakable(s, wordDict)){
     return new ArrayList<String>();
     }
     在wordBreakByDP最开始的时候检查下是否breakable，从而可以通过submit，但是实际上，breakable就是word break 1，而在判断玩breakable之后的代码，还是work break 1的框架只不过是同时收集了符合要求的substring。这样的话，只是应付了leetcode的一个test case，并没有从根本解决问题。比如如果把test case中的list内加入与input string一样的一个字符串来让breakable通过，那么通用会MLE或者TLE，也就是说，出问题的点在于收集substring的阶段而这个解法并没有从根本解决掉，不可取。

     but, 不想在研究这个case了，偏离了dp太远 ：）。
     */
    private List<String> wordBreakByDP(String s, List<String> wordDict){

        int sLen = s.length();

        List<String>[] dp = new List[sLen + 1];

        // base case
        List<String> basestring = new ArrayList<>();
        basestring.add("");
        dp[0] = basestring;

        for(int i = 1; i <= sLen; i++){
            List<String> sentence = new ArrayList<String>();

            for(int j = 0; j < i; j++){
                String substr = s.substring(j, i);

                if(dp[j].size() > 0 && wordDict.contains(substr)){

                    // append
                    for(String ss: dp[j]){
                        if(ss != ""){
                            ss += " ";
                        }
                        ss += substr;
                        sentence.add(ss);
                    }
                }
            }

            dp[i] = sentence;
        }

        return dp[sLen];
    }

    // 对于https://leetcode.com/problems/word-break-ii/discuss/217082/This-is-a-real-DP-solution方法不解决问题的解释
    // public boolean breakable(String s, List<String> wordDict) {
    //     int len = s.length();
    //     boolean[] dp = new boolean[len+1];
    //     dp[0] = true;
    //     for (int i = 1; i<=len; i++) {
    //         for (int j = 0; j<i; j++) {
    //             String temp = s.substring(j,i);
    //             if (dp[j] && wordDict.contains(temp)) {
    //                 dp[i] = true;
    //                 break;
    //             }
    //         }
    //     }
    //     return dp[len];
    // }
}
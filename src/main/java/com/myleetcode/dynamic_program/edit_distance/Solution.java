package com.myleetcode.dynamic_program.edit_distance;

class Solution {
    public int minDistance(String word1, String word2) {
        return minDistanceByDP(word1, word2);
    }

    // TC: O(L1 * L2)
    // SC: O(L1 * L2)
    // intuition: DP. 72, 10, 44, 97, 115, 583, 712
    // https://leetcode.com/problems/edit-distance/discuss/25849/Java-DP-solution-O(nm)
    // dp[i][j] means the min edit distance if we are given i chars of word2 [0:i-1] and j chars of word1 [0:j-1], so have two cases: word2[i-1] == word1[j-1] or not.
    // if equal, dp[i][j] ==dp[i-1][j-1]
    // otherwise, dp[i][j] = min(dp[i-1][j-1]+1, dp[i-1][j]+1, dp[i][j-1]+1)
    // dp[i-1][j] means delete char i, dp[i][j-1] means insert char i, dp[i-1][j-1] means replace char i
    // base case, if given word1 length is 0, then need insert word2.length times; if given word2 length is 0, then need delete word1.length times
    // we need extra row and col to represent that we are not given any char in word1 or word2
    private int minDistanceByDP(String word1, String word2){
        // special case
        if(word1 == null && word2 == null){
            return -1;
        }
        if(word1 == null || word2 == null){
            return -1;
        }

        int word1Len = word1.length();
        int word2Len = word2.length();

        int[][] dp = new int[word2Len + 1][word1Len + 1];
        //init
        for(int i = 0; i <= word2Len; i++){
            for(int j = 0; j <= word1Len; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        // base case
        for(int i = 0; i <= word2Len; i++){
            dp[i][0] = i;
        }
        for(int j = 0; j <= word1Len; j++){
            dp[0][j] = j;
        }

        // dp
        for(int i = 1; i <= word2Len; i++){
            for(int j = 1; j <= word1Len; j++){
                if(word2.charAt(i - 1) == word1.charAt(j - 1)){
                    // no operation need
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    // insert, delete, replace + 1, choose the min one
                    int minDist = Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                    minDist = Math.min(minDist, dp[i - 1][j - 1] + 1);
                    dp[i][j] = minDist;
                }
            }
        }

        return dp[word2Len][word1Len];

    }
}

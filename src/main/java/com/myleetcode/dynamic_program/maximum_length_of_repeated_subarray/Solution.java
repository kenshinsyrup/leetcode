package com.myleetcode.dynamic_program.maximum_length_of_repeated_subarray;

class Solution {
    public int findLength(int[] A, int[] B) {
        // return findLengthByDP(A, B);// wrong, subarray is not subsequence
        return findLengthByDPSubarray(A, B);
    }

    // dp[i][j] means the Longest Common Subarray of A[0:i-1] and B[0:j-1] when the end of the the subarray is A[i-1] and B[j-1]
    // dp[i][j] = dp[i-1][j-1] + 1, if A[i-1]==B[j-1]
    //  dp[i][j] = 0, otherwise
    private int findLengthByDPSubarray(int[] A, int[] B){
        // special case
        if(A == null || A.length == 0 || B == null || B.length == 0){
            return 0;
        }

        int lenA = A.length;
        int lenB = B.length;

        int[][] dp = new int[lenA + 1][lenB + 1];

        int maxLen = Integer.MIN_VALUE; // since we are looking for the subarray, so if A[i-1]!=B[j-1] then dp[i][j] should be 0, not max(dp[i-1][j], dp[i][j-1]), so the dp[n][m] not necessary be the max value

        // base case

        // normal case
        for(int i = 1; i <= lenA; i++){
            for(int j = 1; j <= lenB; j++){
                if(A[i - 1] == B[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    dp[i][j] = 0;
                }

                maxLen = Math.max(dp[i][j], maxLen);
            }
        }

        return maxLen;

    }


    // Wrong: Subarray of array is more like Substring of string, not sub sequence
    // intuition: looks similar as Longest Common Subsequence.
    // DP
    // 2-d array: dp[n + 1][m + 1]
    // formula: dp[i][j] means the Longest Common Subarray of A[0:i-1] and B[0:j-1], so
    // dp[i][j] = dp[i-1][j-1] + 1, if A[i - 1] == B[j - 1]
    // dp[i][j] = max(dp[i-1][j], dp[i][j-1]), otherwise
    // base case: dp[0][0:m] = 0; dp[0:n][0] = 0; // no A or B given
    private int findLengthByDP(int[] A, int[] B){
        // special case
        if(A == null || A.length == 0 || B == null || B.length == 0){
            return 0;
        }

        int lenA = A.length;
        int lenB = B.length;

        int[][] dp = new int[lenA + 1][lenB + 1];

        // base case

        // normal case
        for(int i = 1; i <= lenA; i++){
            for(int j = 1; j <= lenB; j++){
                if(A[i - 1] == B[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[lenA][lenB];

    }
}

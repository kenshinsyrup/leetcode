package com.myleetcode.dynamic_program.largest_sum_of_averages;

class Solution {
    public double largestSumOfAverages(int[] A, int K) {
        return largestSumOfAveragesByDP(A, K);
    }

    // https://leetcode.com/problems/largest-sum-of-averages/discuss/122775/Java-bottom-up-DP-with-Explanation
    // TC: O(N^3)
    // SC: O(N^2)
    // intuition: DP.
    // we have two constraints: the A and the K. so we need a 2-D array dp
    // dp[i][j] means the LSoA of that we are given i nums in A, and we partition them to j groups where j <= K. so
    // A[i-1] try to insert to every goup, find the LSoA.
    /*
Let f[i][j]be the largest sum of averages for first i numbers(A[0], A[1], ... , A[i-1]) tojgroups. f[i][j] consists of two parts: first j-1 groups' averages and the last group' s average. Considering the last group, its last number must be A[i-1] and its first number can be from A[0] to A[i-1]. Suppose the last group starts from A[p+1], we can easily get the average form A[p+1] to A[i-1]. The sum of first j-1 groups' average is f[p][j-1] which we have got before. So now we can write the DP equation:
f[i][j] = max {f[p][j-1] + (A[p] + A[p+1] + ... + A[i-1]) / ((i-1) - p + 1}, p = 0,1,...,i-1
    */
    // base case is when K == 1, the LSoA is the average of the sum A. dp[i][1] = sum(A[0:i-1])/(i-1+1)
    private double largestSumOfAveragesByDP(int[] A, int K){
        if(A == null || A.length == 0){
            return 0;
        }
        if(K <= 0){
            return 0;
        }

        int len = A.length;
        double[][] dp = new double[len + 1][K + 1];
        // base
        double sum = 0;
        double[] sums = new double[len + 1];//prifix sum
        for(int i = 0; i < A.length; i++){
            sum += A[i];
            sums[i+1] = sum;
            dp[i+1][1] = sum/(i+1);
        }

        for(int i = 1; i <= len; i++){
            for(int j = 2; j <= K; j++){
                for(int p = 1; p < i; p++){
                    dp[i][j] = Math.max(dp[i][j], dp[p][j - 1] + (sums[i] - sums[p])/(i - p));
                }
            }
        }

        return dp[len][K];
    }
}

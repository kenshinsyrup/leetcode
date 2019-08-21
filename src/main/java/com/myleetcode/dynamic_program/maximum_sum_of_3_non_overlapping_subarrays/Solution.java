package com.myleetcode.dynamic_program.maximum_sum_of_3_non_overlapping_subarrays;

class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        return maxSumOfThreeSubarraysByDP(nums, k);
    }

    // intuition: naive O(N^3) sol is try every pos to cut the arr to 3 parts, then caculate the sum of each part, until find the max

    // https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/discuss/169710/Concise-Java-DP-solution-generalized-to-K-Non-Overlapping-Subarrays

    // DP:
    // 二维, sub-problem, dp[i][j] for given first i intervals(this problem given 3 intervals so the i is in range [0:2]), given first j elems of nums array, the max sum non-overlapping subarrays
    /*
    dp[i][j] = max{
        dp[i - 1][j - K] + sum(nums[j-K:j-1]) // 斜线方向
        dp[i][j - 1] // 左边一个
    }
    另外用一个idx dp array来记录经过的坐标
    */
    // TC: O(N * M), N is nums length, M is subarrays num, this problem M is 3, so O(N)
    // SC: O(N * M)
    private int[] maxSumOfThreeSubarraysByDP(int[] nums, int K){
        if(nums == null || nums.length == 0 || K <= 0){
            return new int[0];
        }

        // get presum array
        int len = nums.length;
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = presums[i - 1] + nums[i - 1];
        }

        // dp
        int intervalNum = 3;
        int[][] dp = new int[intervalNum + 1][len + 1];
        int[][] idxes = new int[intervalNum + 1][len + 1];
        for(int i = 1; i <= intervalNum; i++){
            for(int j = K; j <= len; j++){ // j at least K to avoid overboundary
                // here dp[i-1][j-K], j-K to make sure we dont overlap, because we need the nums[j-K: j-1] sum. so all nums before this could be consider into previous dp
                int curSum = presums[j] - presums[j - K] + dp[i - 1][j - K];
                if(curSum > dp[i][j - 1]){
                    dp[i][j] = curSum;
                    idxes[i][j] = j - K;
                }else{
                    dp[i][j] = dp[i][j - 1];
                    idxes[i][j] = idxes[i - 1][j];
                }
            }
        }

        // get idx array
        int[] ret = new int[intervalNum];
        int m = intervalNum - 1;
        int i = intervalNum;
        int j = len;
        while(m >= 0){
            if(idxes[i][j] == j - K){
                ret[m] = idxes[i][j];
                i--;
                j = j - K;
                m--;
            }else{
                j--;
            }
        }

        return ret;

    }
}

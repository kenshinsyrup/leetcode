package com.myleetcode.dynamic_program.number_of_longest_increasing_subsequence;

class Solution {
    public int findNumberOfLIS(int[] nums) {
        return findNumberOfLISByDP(nums);
    }

    // TC: O(N^2)
    // SC: O(N)
    // intuition: DP. 300, 673
    // same as 673, but we need another array to help us with the counting.
    // https://leetcode.com/problems/number-of-longest-increasing-subsequence/discuss/107303/python-DP-solution-with-DETAILED-explanation
    // dp[i] means the LIS in nums[0:i], then dp[i] = max(dp[i], dp[i-1]+1, dp[i-2]+1, dp[i-3]+1...dp[0]+1) if nums[i] > nums[j] where j is the "i-1", "i-2"..."0"
    // base case is the i it self, so dp[] init is 1
    // about the counting: only if nums[i] > nums[j], we need update counts, and then we have two cases: if dp[i] == dp[j]+1 means there are more sequences could reach i as IS, so counts[i] += counts[j]; if dp[i] > dp[j] + 1, means counts[i] need update to counts[j] because the length to i have to keep longest
    private int findNumberOfLISByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len];
        int[] counts = new int[len];
        for(int i = 0; i < len; i++){
            dp[i] = 1;
            counts[i] = 1;
        }

        int maxLen = Integer.MIN_VALUE;
        // dp
        for(int i = 0; i < len; i++){
            for(int j = i - 1; j >= 0 ; j--){
                if(nums[i] > nums[j]){
                    if(dp[i] < dp[j] + 1){
                        dp[i] = dp[j] + 1;
                        counts[i] = counts[j];
                    }else if(dp[i] == dp[j] + 1){
                        counts[i] += counts[j];
                    }
                }
            }
            // get the max length
            maxLen = Math.max(maxLen, dp[i]);
        }

        int ret = 0;
        // counts the LIS num
        for(int i = 0; i < len; i++){
            if(dp[i] == maxLen){
                ret += counts[i];
            }
        }

        return ret;
    }
}

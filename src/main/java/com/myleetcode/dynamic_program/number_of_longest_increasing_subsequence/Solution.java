package com.myleetcode.dynamic_program.number_of_longest_increasing_subsequence;

public class Solution {
    public int findNumberOfLIS(int[] nums) {
        return findNumberOfLISByDP(nums);
    }

    // TC: O(N^2)
    // SC: O(N)
    // intuition: DP. 300, 673
    // same as 300, but we need another array count[i] to help us keep record of the LIS number of dp[i].
    private int findNumberOfLISByDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len + 1];
        int[] counts = new int[len + 1];

        // Base case.
        dp[0] = 0;
        for (int i = 1; i <= len; i++) {
            dp[i] = 1;
            counts[i] = 1;
        }

        int maxLen = 1;
        // Normal case.
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j < i; j++) {
                if (nums[i - 1] > nums[j - 1]) {
                    if (dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        counts[i] = counts[j];
                    } else if (dp[i] == dp[j] + 1) {
                        counts[i] += counts[j];
                    }
                }
            }

            // Get the max length
            maxLen = Math.max(maxLen, dp[i]);
        }

        int ret = 0;
        // Counts the LIS num
        for (int i = 1; i <= len; i++) {
            if (dp[i] == maxLen) {
                ret += counts[i];
            }
        }

        return ret;
    }
}
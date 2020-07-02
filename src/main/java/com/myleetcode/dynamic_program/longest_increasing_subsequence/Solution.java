package com.myleetcode.dynamic_program.longest_increasing_subsequence;

public class Solution {
    public int lengthOfLIS(int[] nums) {
        return lenghtOfLISByDP(nums);
    }

    /*
    Given sequence A[0: lenA-1]

    1. dp array为一维

    2. dp[i]代表，给定A中的前i个elems即A[0:i-1], sequence最后一个elem为A[i-1]的情况下，LIS的长度

    3.
    dp[i] = max{
      dp[i-1] + 1, if A[i-1] > A[i-2]
      dp[i-2] + 1, if A[i-1] > A[i-3]
      ...
      dp[1] + 1, if A[i-1] > A[0]
    }
    最后取dp数组中最大值即为数组A的LIS

    4 Base case, dp[i]中i取值范围为[0:lenA]
        对于i==0, dp[0]=0
        对于任意 i!=0, dp[i]代表的LIS最少也是只包含了A[i-1]这一个elem，所以dp[i]=1
    */
    // TC: O(N^2)
    // SC: O(N)
    private int lenghtOfLISByDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len + 1];

        // Base case 1, because dp array default is all 0, so actually we don't need to set dp[0]=0 explicitly.
        dp[0] = 0;
        // Base case 2, every char itself is a LIS.
        for (int i = 1; i <= len; i++) {
            dp[i] = 1;
        }

        int maxLen = 1;
        // Normal case.
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j < i; j++) {
                if (nums[i - 1] > nums[j - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            // Update the maxLen.
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }
}

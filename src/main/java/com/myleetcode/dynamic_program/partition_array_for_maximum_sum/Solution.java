package com.myleetcode.dynamic_program.partition_array_for_maximum_sum;

public class Solution {
    public int maxSumAfterPartitioning(int[] A, int K) {
        return maxSumAfterPartitioningByDPWithMemo(A, K);
    }

    /*
    DP with Memo
    https://leetcode.com/problems/partition-array-for-maximum-sum/discuss/370807/dfs-solution-using-memoization-super-easy-to-understand
    DP
    https://leetcode.com/problems/partition-array-for-maximum-sum/discuss/290863/JavaC%2B%2BPython-DP

    */
    private int maxSumAfterPartitioningByDPWithMemo(int[] nums, int K) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (K <= 0) {
            return 0;
        }

        int len = nums.length;
        int[] memo = new int[len];
        return dpWithMemo(nums, 0, K, memo);
    }

    private int dpWithMemo(int[] nums, int start, int K, int[] memo) {
        int len = nums.length;
        if (start >= len) {
            return 0;
        }

        if (memo[start] != 0) {
            return memo[start];
        }

        int limit = Math.min(len, start + K);
        int maxSum = 0;
        int maxElement = 0;
        for (int i = start; i < limit; i++) {
            maxElement = Math.max(maxElement, nums[i]);
            maxSum = Math.max(maxSum, maxElement * (i - start + 1) + dpWithMemo(nums, i + 1, K, memo));
        }
        memo[start] = maxSum;

        return memo[start];
    }
}

package com.myleetcode.dynamic_program.maximum_sum_of_two_non_overlapping_subarrays;

public class Solution {
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        return maxSumTwoNoOverlapByDP(A, L, M);
    }

    /*
    DP
    https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/discuss/355352/Full-Explanation-and-idea-formation.-JAVA-beat-99
    Max

    */
    private int maxSumTwoNoOverlapByDP(int[] nums, int L, int M) {
        if (nums == null || nums.length == 0 || nums.length < L + M) {
            return 0;
        }

        // 1. Prefix sum to get range sum easily.
        int len = nums.length;
        int[] preSum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        return maxSum(preSum, L, M);
    }

    private int maxSum(int[] preSum, int L, int M) {
        int ret = 0;

        int len = preSum.length;
        int maxL = 0;
        int maxM = 0;

        // i means we take the elements from 0 to i, and i must be taken in the right part(no matter is L part or M part)
        for (int i = L + M; i < len; i++) {
            // maxL means the case when L contiguous elements are taken first in range [0:i]
            maxL = Math.max(maxL, preSum[i - L] - preSum[i - L - M]);
            // sumML means when M taken first, then we need plus the right L part to get the sum
            int sumML = maxM + (preSum[i] - preSum[i - M]); // (preSum[i] - preSum[i - M]) is M part sum including i

            // maxM means the case when M contiguous elements are taken first in range [0:i]
            maxM = Math.max(maxM, preSum[i - M] - preSum[i - L - M]);
            // maxLM means when L taken first, then we need plus the right M part to get the sum
            int sumLM = maxL + (preSum[i] - preSum[i - L]); // (preSum[i] - preSum[i - L]) is L part sum including i

            // ret is the max of these
            ret = Math.max(ret, Math.max(sumLM, sumML));
        }

        return ret;
    }
}

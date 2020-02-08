package com.myleetcode.presum.maximum_size_subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        // return maxSubArrayLenByPresumsAndMap(nums, k);
        return maxSubArrayLenByPresumsAndMap(nums, k);
    }

    /*
    General version of 525
    https://leetcode.com/problems/contiguous-array/


    */
    private int maxSubArrayLenByPresumsAndMapII(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] presums = new int[len + 1];
        for (int i = 0; i < len; i++) {
            presums[i + 1] = presums[i] + nums[i];
        }

        Map<Integer, Integer> presumIdxMap = new HashMap<>();
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            // in current existing presumIdxMap to check
            int presum = presums[i + 1];

            if (presum == k) {
                maxLen = i - 0 + 1;
            } else {
                int target = presum - k;
                if (presumIdxMap.containsKey(target)) {
                    maxLen = Math.max(maxLen, (i - 1) - presumIdxMap.get(target) + 1);
                } else {
                    presumIdxMap.put(presum, i);
                }
            }
        }

        return maxLen;


    }

    // naive solution with two nested for loop and presums cost O(N^2)

    // same like the 560. Subarray Sum Equals K
    // with a map to store the presum->leftMostIdx to help us
    // TC: O(N)
    // SC: O(N)
    private int maxSubArrayLenByPresumsAndMap(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] presums = new int[len + 1];
        Map<Integer, Integer> presumIdxMap = new HashMap<>();
        // presums[y] - presums[x] == k, means we want to find the presums[x] = presums[y]-k in map
        int maxLen = 0;
        for (int i = 0; i < presums.length; i++) {
            // get presum
            if (i >= 1) {
                presums[i] = presums[i - 1] + nums[i - 1];
            }

            // in current existing presumIdxMap to check
            int target = presums[i] - k;
            if (presumIdxMap.containsKey(target)) {
                maxLen = Math.max(maxLen, i - presumIdxMap.get(target));
            }

            // !!! now we have the new presum could be used after, should keep the left most idx
            if (!presumIdxMap.containsKey(presums[i])) {
                presumIdxMap.put(presums[i], i);
            }
        }

        return maxLen;

    }
}

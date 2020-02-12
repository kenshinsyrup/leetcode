package com.myleetcode.presum.subarray_sums_divisible_by_k;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int subarraysDivByK(int[] A, int K) {
        // return subarraysDivByKByMap(A, K);
        return subarraysDivByKByPrsumAndMap(A, K);
    }

    private int subarraysDivByKByPrsumAndMap(int[] nums, int K) {
        if (nums == null || nums.length == 0 || K <= 0) {
            return 0;
        }

        int ret = 0;
        int len = nums.length;
        int[] presum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }

        // Presum remainder divided by K and its corresponding number.
        Map<Integer, Integer> remainderNumMap = new HashMap<>();
        // !!! Important. The first prefix sum is 0, it need to be counted as a mod of K, right? Rest of prefix sum from 1 to K-1 don't include the first prefix sum.
        remainderNumMap.put(0, 1);

        for (int i = 0; i < len; i++) {
            // Get remainder
            int remainder = presum[i + 1] % K;
            // For Java, need make the remainder not negative.
            if (remainder < 0) {
                remainder += K;
            }

            if (remainderNumMap.containsKey(remainder)) {
                ret += remainderNumMap.get(remainder);
            }

            remainderNumMap.put(remainder, 1 + remainderNumMap.getOrDefault(remainder, 0));
        }

        return ret;
    }

    /*
    https://leetcode.com/problems/subarray-sums-divisible-by-k/discuss/217980/Java-O(N)-with-HashMap-and-prefix-Sum

    */
    private int subarraysDivByKByMap(int[] nums, int K) {
        if (nums == null || nums.length == 0 || K <= 0) {
            return 0;
        }

        int ret = 0;
        int len = nums.length;
        int accumulateSum = 0;
        Map<Integer, Integer> remainderNumMap = new HashMap<>();

        // !!! Important. The first prefix sum is 0, it need to be counted as a mod of K, right? Rest of prefix sum from 1 to K-1 don't include the first prefix sum.
        remainderNumMap.put(0, 1);

        for (int i = 0; i < len; i++) {
            // Get remainder
            accumulateSum += nums[i];
            int remainder = accumulateSum % K;
            if (remainder < 0) {
                accumulateSum += K;
            }

            if (remainderNumMap.containsKey(remainder)) {
                ret += remainderNumMap.get(remainder);
            }

            remainderNumMap.put(remainder, 1 + remainderNumMap.getOrDefault(remainder, 0));
        }

        return ret;
    }
}

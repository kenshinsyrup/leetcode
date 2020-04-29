package com.myleetcode.presum.subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraySum(int[] nums, int k) {
        // return subarraySumByPreSum(nums, k);
        // return subarraySumByPreSumII(nums, k);
        // return subarraySumByPrefixSum(nums, k);
        return subarraySumByPresumAndMap(nums, k);
    }

    private int subarraySumByPresumAndMap(int[] nums, int K){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int count = 0;
        int[] presums = new int[len + 1];
        Map<Integer, Integer> presumNumMap = new HashMap<>();
        for(int i = 0; i < len; i++){
            // Get current presum.
            presums[i + 1] = presums[i] + nums[i];

            // For convience.
            int presumIdx = i + 1;

            // Check current presum.
            if(presums[presumIdx] == K){
                count++;
            }
            // Check target.
            int target = presums[presumIdx] - K;
            if(presumNumMap.containsKey(target)){
                count += presumNumMap.get(target);
            }

            // Put for later use.
            presumNumMap.put(presums[presumIdx], presumNumMap.getOrDefault(presums[presumIdx], 0) + 1);
        }

        return count;
    }

    /*
   Use the prefix sum
   The needed subarry sum is: prefisSum - k. Then it'll be similar with two sum.

   TC: O(N)
   SC: O(N)
   */
    private int subarraySumByPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> complementTimeMap = new HashMap<>();
        complementTimeMap.put(0, 1);

        int len = nums.length;
        int ret = 0;
        int prefixSum = 0;
        for (int i = 0; i < len; i++) {
            prefixSum += nums[i];

            int complement = prefixSum - k;
            if (complementTimeMap.containsKey(complement)) {
                ret += complementTimeMap.get(complement);
            }

            complementTimeMap.put(prefixSum, complementTimeMap.getOrDefault(prefixSum, 0) + 1);
        }

        return ret;

    }

    // 这个有一个优化，就是我们不需要真的保存前项和到数组，可以用一个变量替代，这样节省额外的O(N)空间，最终可以达到O(1)空间
    private int subarraySumByPreSumII(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int count = 0;
        int sum = 0;

        for (int start = 0; start < len; start++) {
            sum = 0;

            // sum of the A[start:end]
            for (int end = start; end < len; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }

    // TC: O(N^2)
    // SC: O(N)
    // intuition: 前项和
    private int subarraySumByPreSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] sum = new int[len + 1];
        int count = 0;

        for (int i = 1; i <= len; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        for (int start = 0; start < len; start++) {
            for (int end = start; end < len; end++) {
                // because A[0:i] presum is sum[i+1] => A[start:end] == sum[end + 1] - sum[start]
                if (sum[end + 1] - sum[start] == k) {
                    count++;
                }
            }
        }

        return count;
    }
}

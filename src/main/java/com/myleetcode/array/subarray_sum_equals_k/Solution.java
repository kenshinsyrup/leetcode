package com.myleetcode.array.subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraySum(int[] nums, int k) {
        // return subarraySumByPreSum(nums, k);
        // return subarraySumByPreSumII(nums, k);
        return subarraySubByPreSumAndMap(nums, k);
    }

    // 这个题还有一个优化，就是按照two sum那道题的样子，找余数。https://leetcode.com/problems/subarray-sum-equals-k/discuss/164431/Python-or-3-tm
    // TC: O(N)
    // SC: O(N)
    // !!! a very important thing is, we could not first get all presums and then do another loop to find the target, this way may give us not subarray ans because when we are at idx i, we could not use the presum after i, but this way, we maybe used it.
    // SO, MUST in one loop, first get presum and target, check target in current useable presums in presumNuMap and then put current presum to be used after
    private int subarraySubByPreSumAndMap(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] presums = new int[len + 1];
        Map<Integer, Integer> presumNumMap = new HashMap<>();
        int count = 0;
        for(int i = 0; i < presums.length; i++){
            // get presum
            if(i >= 1){
                presums[i] = presums[i - 1] + nums[i - 1];
            }

            // check target in current existing presumNumMap
            int target = presums[i] - k;
            if(presumNumMap.containsKey(target)){
                count += presumNumMap.get(target);
            }

            // we have a new presum that we could use after
            presumNumMap.put(presums[i], presumNumMap.getOrDefault(presums[i], 0) + 1);
        }

        return count;
    }

    // 这个有一个优化，就是我们不需要真的保存前项和到数组，可以用一个变量替代，这样节省额外的O(N)空间，最终可以达到O(1)空间
    private int subarraySumByPreSumII(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int count = 0;
        int sum = 0;

        for(int start = 0; start < len; start++){
            sum = 0;

            // sum of the A[start:end]
            for(int end = start; end < len; end++){
                sum += nums[end];
                if(sum == k){
                    count++;
                }
            }
        }

        return count;
    }

    // TC: O(N^2)
    // SC: O(N)
    // intuition: 前项和
    private int subarraySumByPreSum(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] sum = new int[len + 1];
        int count = 0;

        for(int i = 1; i <= len; i++){
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        for(int start = 0; start < len; start++){
            for(int end = start; end < len; end++){
                // because A[0:i] presum is sum[i+1] => A[start:end] == sum[end + 1] - sum[start]
                if(sum[end + 1] - sum[start] == k){
                    count++;
                }
            }
        }

        return count;
    }
}

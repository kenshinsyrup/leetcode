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
    private int subarraySubByPreSumAndMap(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int count = 0;
        int sum = 0;
        int remains = 0;
        Map<Integer, Integer> sumNumMap = new HashMap<>();
        sumNumMap.put(0, 1); // ！！！重要，map存储的是前项和及对应的出现次数，那么前项和为0的情况我们应该计作1，也就是nums[0]的前项和

        for(int start = 0; start < len; start++){
            sum += nums[start];
            remains = sum - k;

            // find is there's remains in map, if is, plus its occurence times to count
            if(sumNumMap.containsKey(remains)){
                count += sumNumMap.get(remains);
            }

            // put current pre sum to map
            sumNumMap.put(sum, sumNumMap.getOrDefault(sum, 0) + 1);

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

    // 最初想用DP做，做的过程中发现是个前项和的问题
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

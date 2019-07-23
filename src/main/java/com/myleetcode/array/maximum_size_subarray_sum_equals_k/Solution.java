package com.myleetcode.array.maximum_size_subarray_sum_equals_k;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        return maxSubArrayLenByPresumsAndMap(nums, k);
    }

    // naive solution with two nested for loop and presums cost O(N^2)
    // with a map to store the presum->leftMostIdx to help us
    // TC: O(N)
    // SC: O(N)
    private int maxSubArrayLenByPresumsAndMap(int[] nums, int k){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] presums = new int[len + 1];
        Map<Integer, Integer> presumIdxMap = new HashMap<>();
        // presums[y] - presums[x] == k, means we want to find the presums[x] = presums[y]-k in map
        int maxLen = 0;
        for(int i = 0; i < presums.length; i++){
            // get presum
            if(i >= 1){
                presums[i] = presums[i - 1] + nums[i - 1];
            }

            // in current existing presumIdxMap to check
            int target = presums[i] - k;
            if(presumIdxMap.containsKey(target)){
                maxLen = Math.max(maxLen, i - presumIdxMap.get(target));
            }

            // now we have the new presum could be used after
            if(!presumIdxMap.containsKey(presums[i])){
                presumIdxMap.put(presums[i], i);
            }
        }

        return maxLen;

    }
}

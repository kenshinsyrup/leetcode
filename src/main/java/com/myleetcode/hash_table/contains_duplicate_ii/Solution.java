package com.myleetcode.hash_table.contains_duplicate_ii;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        return containsNearbyDuplicateByMap(nums, k);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: use a map, the num as key, the num's idx as value, caculate the distance, once find a distance is less than k we return true
    private boolean containsNearbyDuplicateByMap(int[] nums, int k){
        if(nums == null || nums.length == 0 || k < 0){
            return false;
        }

        Map<Integer, Integer> numIdxMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(numIdxMap.containsKey(nums[i])){
                if(i - numIdxMap.get(nums[i]) <= k){
                    return true;
                }
            }

            numIdxMap.put(nums[i], i);
        }

        return false;
    }
}

package com.myleetcode.two_pointers.two_sum;

import java.util.*;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // return twoSumByMap(nums, target);
        return twoSumByTwoPointers(nums, target);
    }

    // this sol uses the trick: assume that each input would have exactly one solution, so we could store the num->idx into Map use nums as Set. So this is a part must contact with the interviewer, if no such definition, dont use this sol
    // TC: O(N)
    // SC: O(N)
    private int[] twoSumByMap(int[] nums, int target) {
        if(nums == null || nums.length < 2){
            return null;
        }

        int len = nums.length;
        Map<Integer, Integer> numIdxMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int complement = target - nums[i];
            if (numIdxMap.containsKey(complement)) {
                return new int[] { i, numIdxMap.get(complement) };
            }

            numIdxMap.put(nums[i], i);
        }

        return null;

    }

    // this is not efficient as Map sol and not the optimal sol, but it's useable in 15. 3Sum. and if not guaranteed has a sol, we could use this sol
    // TC: O(N * logN)
    // SC: O(N)
    private int[] twoSumByTwoPointers(int[] nums, int target){
        if(nums == null || nums.length < 2){
            return null;
        }

        // copy and sort
        int len = nums.length;
        int[] numsCopy = new int[len];
        for(int i = 0; i < len; i++){
            numsCopy[i] = nums[i];
        }
        Arrays.sort(numsCopy);

        // two pointers find
        int leftP = 0;
        int rightP = len - 1;
        while(leftP < rightP){
            if(numsCopy[leftP] + numsCopy[rightP] == target){
                break;
            }else if(numsCopy[leftP] + numsCopy[rightP] < target){
                leftP++;
            }else{
                rightP--;
            }
        }
        // if no valid ans(this problem has guaranteed ans)
        if(leftP > rightP){
            return null;
        }

        // build ret: one loop from start to end, one loop from end to start, this way the ans is guaranteed correct. otherwise, if in one loop, maybe numsCopy[leftP]==numsCopy[rightP], then we will get same idx in the ret which is not correct.
        int[] ret = new int[2];
        for(int i = 0; i < len; i++){
            if(numsCopy[leftP] == nums[i]){
                ret[0] = i;
            }
        }
        for(int i = len - 1; i >= 0; i--){
            if(numsCopy[rightP] == nums[i]){
                ret[1] = i;
            }
        }

        return ret;
    }
}
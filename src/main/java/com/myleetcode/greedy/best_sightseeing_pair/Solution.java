package com.myleetcode.greedy.best_sightseeing_pair;

class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        // return maxScoreSightseeingPairByTraversal(A); // TLE
        return maxScoreSightseeingPairByGreedy(A);
    }

    // intuition:
    // naive sol, translate the description. TLE.
    // 1. for every i, try all combination with it as end, cost O(N)
    // 2. for every combination, caculate and update max, cost O(N)
    // TC: O(N^2)
    // SC: O(1)
    private int maxScoreSightseeingPairByTraversal(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < i; j++){
                max = Math.max(max, nums[i] + nums[j] + j - i);
            }
        }

        if(max == Integer.MIN_VALUE){
            return 0;
        }
        return max;
    }

    // Greedy
    // get the max of nums[i] + nums[j] + i - j, consider as nums[i] + i + nums[j] - j, so for every j we could get the nus[j]-j, then we only need get the max nums[i]+i, since i is smaller than j, so we could use the processed j to update the i
    // TC: O(N)
    // SC: O(1)
    private int maxScoreSightseeingPairByGreedy(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int i = 0;
        int max = Integer.MIN_VALUE;
        for(int j = 1; j < len; j++){
            max = Math.max(max, nums[j] - j + nums[i] + i);

            if(nums[j] + j > nums[i] + i){
                i = j;
            }
        }

        if(max == Integer.MIN_VALUE){
            return 0;
        }
        return max;
    }

}

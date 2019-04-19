package com.myleetcode.two_pointers.three_sum_smaller;

import java.util.Arrays;

class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        return threeSumSmallerByTraverse(nums, target);
    }

    // TC: O(N^2)
    // SC: O(1)
    // intuition: very similar with the 16. 3Sum Closest. a O(N^2) solution is traverse the nums, for each nums[i], we have two pointers called start and end to traverse the value from i+1 and len-1 respectively like 2sum, then check.
    private int threeSumSmallerByTraverse(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return 0;
        }

        Arrays.sort(nums);

        int ret = 0;
        for(int i = 0; i < nums.length; i++){
            int start = i + 1;
            int end = nums.length - 1;
            while(start < end){
                int curSum = nums[i] + nums[start] + nums[end];

                // if >=, then reduce the end by one. if <, then start+1 through end is good to fit curSum < target
                if(curSum >= target){
                    end--;
                }else{
                    ret += end - start;

                    start++;
                }
            }
        }

        return ret;
    }
}

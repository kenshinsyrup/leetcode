package com.myleetcode.array.missing_ranges;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        return findMissingRangesByTraverse(nums, lower, upper);
    }

    // TC: O(N)
    // SC: O(1) if not consider the output List
    // intuition: traverse the array
    // first we should check the lower and upper boundary. if lower is less than nums[0], then missing [lower, nums[0] -1], if upper is larger than nums[len-1], then missing [nums[len-1] + 1, upper]
    // second, int the sorted array, if nums[i] + 1 != nums[i+1], then missing [nums[i] + 1, nums[i+1]-1]
    private List<String> findMissingRangesByTraverse(int[] nums, int lower, int upper){
        List<String> ret = new ArrayList<>();

        // special case1: nums is invalid
        if(nums == null || nums.length == 0){
            // !!! OJ has a test case is lower==upper and nums is empty
            if(lower == upper){
                ret.add("" + lower);
            }else{
                ret.add(lower + "->" + upper);
            }

            return ret;
        }
        // special case2: lower upper range is invalid
        if(lower > upper || nums[0] > upper || nums[nums.length - 1] < lower){
            return ret;
        }

        int len = nums.length;

        // 1: lower to nums[0]
        if(nums[0] > lower){
            if(lower == nums[0] - 1){
                ret.add("" + lower);
            }else{
                ret.add(lower + "->" + (nums[0] - 1));
            }
        }

        // 2: in the array
        for(int i = 0; i < len - 1; i++){
            // !!! same or consecutive, continue
            if(nums[i] == nums[i + 1] || nums[i] + 1 == nums[i + 1]){
                continue;
            }

            // missing
            if(nums[i] + 1 == nums[i + 1] - 1){
                ret.add("" + (nums[i] + 1));
            }else{
                ret.add((nums[i] + 1) + "->" + (nums[i + 1] - 1));
            }
        }

        // 3: nums[len-1] to upper
        if(nums[len - 1] < upper){
            if(nums[len - 1] + 1 == upper){
                ret.add("" + upper);
            }else{
                ret.add((nums[len - 1] + 1) + "->" + upper);
            }
        }

        return ret;
    }

}

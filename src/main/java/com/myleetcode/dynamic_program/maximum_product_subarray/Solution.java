package com.myleetcode.dynamic_program.maximum_product_subarray;

class Solution {
    public int maxProduct(int[] nums) {
        return maxProductByDP(nums);
    }

    // but use dp is actually a little too overdone, we could oberse this is not a good dp problem, just two variables to store the max and min could solve the problem.
    // https://leetcode.com/problems/maximum-product-subarray/discuss/48230/Possibly-simplest-solution-with-O(n)-time-complexity

    // TC: O(N)
    // SC: O(N)
    // https://leetcode.com/problems/maximum-product-subarray/discuss/118535/C++-DP-Solution-using-2-Arrays
    // we could get the max[i] and min[i] for given i+1 num, then for the ith num
    // max[i] = max(nums[i], max[i] * nums[i], min[i] * nums[i]);
    // min[i] = min(nums[i], max[i] * nums[i], min[i] * nums[i]);
    // the min[i] menas we want to keep the track of the negative if there is.
    private int maxProductByDP(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dpMax = new int[len];
        int[] dpMin = new int[len];

        // base
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];

        int ret = nums[0];

        // dp
        for(int i = 1; i < len; i++){
            int max = Math.max(dpMax[i - 1] * nums[i], nums[i]);
            max = Math.max(max, dpMin[i - 1] * nums[i]);
            dpMax[i] = max;

            int min = Math.min(dpMax[i -  1] * nums[i], nums[i]);
            min = Math.min(min, dpMin[i - 1] * nums[i]);
            dpMin[i] = min;

            // get the ret
            ret = Math.max(dpMax[i], ret);
        }

        return ret;
    }
}

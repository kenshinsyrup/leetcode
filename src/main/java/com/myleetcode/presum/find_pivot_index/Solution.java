package com.myleetcode.presum.find_pivot_index;

public class Solution {
    public int pivotIndex(int[] nums) {
        return pivotIndexByPresum(nums);
    }

    /*
    TC: O(N)
    SC: O(N)
    */
    private int pivotIndexByPresum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int len = nums.length;
        int[] presums = new int[len + 1];
        for (int i = 0; i < len; i++) {
            presums[i + 1] = presums[i] + nums[i];
        }

        // presums[i-1] means sum of nums[0]:nums[i-2]
        // presums[len] means sum of nums[0]:nums[len-1], presums[i] means sum of nums[0]:nums[i-1], so presums[len]-presums[i] means sum of nums[i]:nums[len-1]
        // So the if condition is checking whether nums[i-1] is the pivot num
        for (int i = 1; i < presums.length; i++) {
            if (presums[i - 1] == presums[len] - presums[i]) {
                return i - 1;
            }
        }

        return -1;
    }
}

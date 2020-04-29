package com.myleetcode.array.minimum_value_to_get_positive_step_by_step_sum;

public class Solution {
    public int minStartValue(int[] nums) {
        return minStartValueByStimulate(nums);
    }

    private int minStartValueByStimulate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        int res = 1;
        int min = Integer.MAX_VALUE;
        int sum = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            sum += nums[i];

            if (sum <= 0 && min > sum) {
                min = sum;
                res = 1 - min;
            }
        }

        return res;
    }
}

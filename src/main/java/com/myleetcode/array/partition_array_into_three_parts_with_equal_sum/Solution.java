package com.myleetcode.array.partition_array_into_three_parts_with_equal_sum;

public class Solution {
    public boolean canThreePartsEqualSum(int[] A) {
        return canThreeParsEqualSumByPartSum(A);
    }

    private boolean canThreeParsEqualSumByPartSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 3 != 0) {
            return false;
        }

        int target = sum / 3;
        int len = nums.length;
        int partSum = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            partSum += nums[i];
            if (partSum == target) {
                partSum = 0;
                count++;
                if (count == 3) {
                    return true;
                }
            }
        }
        return false;

    }
}

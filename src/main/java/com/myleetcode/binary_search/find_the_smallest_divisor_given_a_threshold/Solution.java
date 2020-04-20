package com.myleetcode.binary_search.find_the_smallest_divisor_given_a_threshold;

public class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        return smallestDivisorByBS(nums, threshold);
    }

    /*
    Binary Search:
    Similar with 1011. Capacity To Ship Packages Within D Days

    */
    private int smallestDivisorByBS(int[] nums, int threshold) {
        if (nums == null || nums.length == 0 || threshold <= 0) {
            return 0;
        }

        int maxNum = Integer.MIN_VALUE;
        // int minNum = Integer.MAX_VALUE;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
            // minNum = Math.min(minNum, num);
        }

        // !!! Here should not use minNum as left, consider the case: [19] 5
        int left = 1; // Left is the min of nums, means the smallest divisor we could try.
        int right = maxNum; // Right is the max of nums, means the largest divisor we could try.
        while (left <= right) {
            // Try mid as the divisor.
            int mid = left + (right - left) / 2;

            // Caculate the sum of quotient when mid as divisor.
            int val = 0;
            for (int num : nums) {
                val += num / mid;
                if (num % mid != 0) {
                    val++;
                }
            }

            if (val > threshold) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}

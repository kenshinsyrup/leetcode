package com.myleetcode.sliding_window.count_number_of_nice_subarrays;

public class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        return numberOfSubarraysBySlidingWindow(nums, k);
    }

    /*
    Sliding Window

    Important:
    how to count

    TC: O(N)
    SC: O(1)
    */
    private int numberOfSubarraysBySlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k <= 0) {
            return 0;
        }

        int ret = 0;
        int oddCount = 0;
        int len = nums.length;
        int left = 0;
        int right = 0;
        while (right < len) {
            if (nums[right] % 2 != 0) {
                oddCount++;
            }

            // Shrink.
            while (oddCount > k && left <= right) {
                if (nums[left] % 2 != 0) {
                    oddCount--;
                }
                left++;
            }

            // Count.
            if (oddCount == k) {
                ret++;

                int i = left;
                while (i < right && nums[i] % 2 == 0) {
                    i++;
                    ret++;
                }
            }

            right++;
        }

        return ret;
    }

}

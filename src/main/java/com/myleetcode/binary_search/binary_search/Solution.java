package com.myleetcode.binary_search.binary_search;

public class Solution {
    public int search(int[] nums, int target) {
        return searchByBS(nums, target);
    }

    private int searchByBS(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int len = nums.length;
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}

package com.myleetcode.binary_search.search_insert_position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    public int searchInsert(int[] nums, int target) {
        // return searchInsertByBS(nums, target);
        return searchInsertByBSII(nums, target);
    }

    private int searchInsertByBSII(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int idx = binarySearchForFirstLarger(nums, target);
        if (idx == -1) {
            return nums.length;
        }
        return idx;
    }

    private int binarySearchForFirstLarger(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int num = nums[mid];

            if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (left == len) {
            return -1;
        }

        return left;
    }

    /*
    Built-in Binary Search.
    */
    private int searchInsertByBS(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        List<Integer> numList = new ArrayList<>();
        for (int num : nums) {
            numList.add(num);
        }

        int idx = Collections.binarySearch(numList, target);
        if (idx < 0) {
            idx = -idx - 1;
        }
        return idx;
    }
}

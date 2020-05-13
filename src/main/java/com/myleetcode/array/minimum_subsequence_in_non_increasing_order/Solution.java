package com.myleetcode.array.minimum_subsequence_in_non_increasing_order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<Integer> minSubsequence(int[] nums) {
        return minSubsequenceBySort(nums);
    }

    /*
    TC: O(N * logN)
    SC: O(N)
    */
    private List<Integer> minSubsequenceBySort(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // Sort, ascending order.
        Arrays.sort(nums);

        int seqSum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            res.add(nums[i]);
            seqSum += nums[i];

            if (seqSum * 2 > sum) {
                break;
            }
        }

        return res;
    }
}

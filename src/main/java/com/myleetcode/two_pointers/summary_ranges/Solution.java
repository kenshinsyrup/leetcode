package com.myleetcode.two_pointers.summary_ranges;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> summaryRanges(int[] nums) {
        // return summaryRangesByTraverse(nums);
        return summaryrangesByTwoPointers(nums);
    }

    /*
    Two Pointers

    TC: O(N)
    SC: O(1)
    */
    private List<String> summaryrangesByTwoPointers(int[] nums) {
        List<String> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }

        StringBuilder sb = new StringBuilder();
        int len = nums.length;
        int i = 0;
        int j = 0;
        while (j < len) {
            if (j < len - 1 && nums[j] + 1 == nums[j + 1]) {
                j++;
                continue;
            }

            if (i == j) {
                sb.append(nums[i]);
            } else {
                sb.append(nums[i]).append("->").append(nums[j]);
            }
            ret.add(sb.toString());

            sb = new StringBuilder();
            i = j + 1;
            j++;
        }

        return ret;
    }

    /*
    Traverse array.

    */
    private List<String> summaryRangesByTraverse(int[] nums) {
        List<String> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }

        StringBuilder sb = new StringBuilder();
        int len = nums.length;
        // Init.
        int start = nums[0];
        for (int i = 0; i < len - 1; i++) {
            // For given index i, if not continuous with next num.
            if (nums[i] + 1 != nums[i + 1]) {
                sb.append(start);
                if (start != nums[i]) {
                    sb.append("->");
                    sb.append(nums[i]);
                }
                ret.add(sb.toString());

                sb = new StringBuilder();
                start = nums[i + 1];
            }
        }
        // At last, process the len-1 num.
        sb.append(start);
        if (start != nums[len - 1]) {
            sb.append("->");
            sb.append(nums[len - 1]);
        }
        ret.add(sb.toString());

        return ret;
    }
}

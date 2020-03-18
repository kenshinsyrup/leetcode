package com.myleetcode.sliding_window.get_equal_substrings_within_budget;

public class Solution {
    public int equalSubstring(String s, String t, int maxCost) {
        return equalSubstringBySlidingWindwo(s, t, maxCost);
    }

    /*
    Sliding Window with funny camouflage.
    https://leetcode.com/problems/get-equal-substrings-within-budget/discuss/392950/Java-Sliding-Window-with-Clear-Explanation

    TC: O(N)
    SC: O(N)
    */
    private int equalSubstringBySlidingWindwo(String sStr, String tStr, int maxCost) {
        if (sStr == null || sStr.length() == 0 || tStr == null || tStr.length() == 0 || maxCost < 0) {
            return 0;
        }

        // 1. Build the diff array.
        int len = sStr.length();
        int[] diff = new int[len];
        for (int i = 0; i < len; i++) {
            diff[i] = Math.abs(sStr.charAt(i) - tStr.charAt(i));
        }

        // 2. Sliding Window to find the max length subarray within maxCost
        int ret = 0;
        int windowCost = 0;
        int left = 0;
        int right = 0;
        while (right < len) {
            windowCost += diff[right];

            while (windowCost > maxCost && left <= right) {
                windowCost -= diff[left];

                left++;
            }

            ret = Math.max(ret, right - left + 1);

            right++;
        }

        return ret;
    }
}
package com.myleetcode.dynamic_program.largest_divisible_subset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        return largestDivisbleSubsetByDP(nums);
    }

    /*
    https://leetcode.com/problems/largest-divisible-subset/discuss/684677/3-STEPS-c%2B%2B-or-python-or-java-dp-PEN-PAPER-DIAGRAM-explanation-simple-and-clear
    */
    private List<Integer> largestDivisbleSubsetByDP(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        // Sort first.
        Arrays.sort(nums);

        int len = nums.length;
        int[] dp = new int[len]; // end at i, the length of largest divisible subset.
        int[] parents = new int[len]; // helper, record the parent index of current i in a largest divisible subset.
        for (int i = 0; i < len; i++) {
            dp[i] = 1;
            parents[i] = -1;
        }

        int maxLen = 0;
        int finalIdx = -1;
        for (int i = 0; i < len; i++) {
            for (int j = i - 1; j >= 0; j--) { // !!! we want to get dp[i], and we only know dp answers before it.
                if (nums[i] % nums[j] == 0) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        parents[i] = j;
                    }
                }
            }

            if (dp[i] > maxLen) {
                maxLen = dp[i];
                finalIdx = i;
            }
        }

        while (finalIdx != -1) {
            res.add(nums[finalIdx]);

            finalIdx = parents[finalIdx];
        }

        return res;
    }
}

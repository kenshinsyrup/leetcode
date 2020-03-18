package com.myleetcode.sliding_window.subarrays_with_k_different_integers;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraysWithKDistinct(int[] A, int K) {
        return subarraysWithKDistinctBySlidingWindow(A, K);
    }

    // TC: O(N)
    // SC: O(N)
    // 3, 159, 340, 992
    // https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!
    // use the at most K distincts template
    // similar: 3. Longest Substring Without Repeating Characters
    /*
to get the number of subarrays with at most K distinct elements.
Then f(exactly K) = f(atMost K) - f(atMost K-1).
    */
    private int subarraysWithKDistinctBySlidingWindow(int[] nums, int K) {
        return subarraysAtMostKDistincts(nums, K) - subarraysAtMostKDistincts(nums, K - 1); // !!! f(exactly K) = f(atMost K) - f(atMost K-1)
    }

    private int subarraysAtMostKDistincts(int[] nums, int K) {
        if (nums == null || nums.length == 0 || K <= 0 || K > nums.length) {
            return 0;
        }

        int len = nums.length;
        Map<Integer, Integer> numNumMap = new HashMap<>();
        int leftP = 0;
        int rightP = 0;
        int distinct = 0;
        int ret = 0;
        while (rightP < len) {// expand window
            int curNum = nums[rightP];
            // counting the # of elem in nums[] we meet
            numNumMap.put(curNum, numNumMap.getOrDefault(curNum, 0) + 1);
            // if first meet then its distinct in cur window range
            if (numNumMap.get(curNum) == 1) {
                distinct++;
            }

            // if distinct > K we need shrink window
            while (distinct > K && leftP <= rightP) {
                int leftNum = nums[leftP];
                // reduce the # of elem
                numNumMap.put(leftNum, numNumMap.get(leftNum) - 1);

                // if a distinct elem is totally removed
                if (numNumMap.get(leftNum) == 0) {
                    distinct--;
                }

                // left margin moves
                leftP++;
            }

            // !!! here, we get a window [left: right], that has no more than K distincts
            /*
j - i + 1 equal to the total number of subarrays ending at j contains at most K distinct integers.
Why?
we can find totally j - i + 1 arrays starting from i,i+1,i+2...j, end with j. [i, j], ie the cur window which contains no more than K distincts, For e.g. array = [1,2,3,4,5], i = 0, j = 4, we can get [[1,2,3,4,5],[2,3,4,5],[3,4,5],[4,5],[5]] totally 4-0+1 = 5 subarray which contains at most K integers.
            */
            // so, we culmulatively sum these windows up, we could get atmost K distincts subarrays num
            ret += rightP - leftP + 1;

            rightP++;
        }

        return ret;
    }
}
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
    private int subarraysWithKDistinctBySlidingWindow(int[] nums, int K){
        return subarraysAtMostKDistincts(nums, K) - subarraysAtMostKDistincts(nums, K - 1)   ;
    }

    private int subarraysAtMostKDistincts(int[] nums, int K){
        if(nums == null || nums.length == 0 || K <= 0 || K > nums.length){
            return 0;
        }

        int len = nums.length;
        Map<Integer, Integer> numNumMap = new HashMap<>();
        int left = 0;
        int distinct = 0;
        int ret = 0;
        for(int i = 0; i < len; i++){// expand window, i is the right margin
            // counting the # of elem in nums[] we meet
            numNumMap.put(nums[i], numNumMap.getOrDefault(nums[i], 0) + 1);

            // if first meet
            if(numNumMap.get(nums[i]) == 1){
                distinct++;
            }

            // shrink window
            while(left <= i && distinct > K){
                // reduce the # of elem
                numNumMap.put(nums[left], numNumMap.get(nums[left]) - 1);

                // if a distinct elem is totally removed
                if(numNumMap.get(nums[left]) == 0){
                    distinct--;
                }

                // left margin moves
                left++;
            }

            // here, we get a window [left: right], that has no more than K distincts, we could form right-left+1 # subarrays with this window
            ret += i - left + 1;
        }

        return ret;
    }
}

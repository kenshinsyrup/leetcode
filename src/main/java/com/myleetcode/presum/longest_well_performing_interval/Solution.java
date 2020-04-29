package com.myleetcode.presum.longest_well_performing_interval;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int longestWPI(int[] hours) {
        // return longestWPIByStack(hours);
        return longestWPIByMap(hours);
    }

    /*
    Similar like 560. Subarray Sum Equals K
    */
    private int longestWPIByMap(int[] hours) {
        if (hours == null || hours.length == 0) {
            return 0;
        }

        int res = 0;
        int len = hours.length;
        int[] presums = new int[len + 1];
        Map<Integer, Integer> presumIdxMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            // Current presum.
            if (hours[i] > 8) {
                presums[i + 1] = presums[i] + 1;
            } else {
                presums[i + 1] = presums[i] - 1;
            }

            // For convience when caculate len of subarray, for presums[i:j], its corresponding subarray is hours[i:j-1], so len is (j-1) - i + 1.
            int presumIdx = i + 1;

            // Check current presum.
            if (presums[presumIdx] > 0) {
                res = Math.max(res, (presumIdx - 1) - 0 + 1);
            }
            // Check target.
            int target = presums[presumIdx] - 1;
            if (presumIdxMap.containsKey(target)) {
                res = Math.max(res, (presumIdx - 1) - presumIdxMap.get(target) + 1);
            }

            // Put in for later use.
            if (!presumIdxMap.containsKey(presums[presumIdx])) {
                presumIdxMap.put(presums[presumIdx], presumIdx);
            }
        }

        return res;
    }

    /*
    Mono Stack
    https://leetcode.com/problems/longest-well-performing-interval/discuss/335163/O(N)-Without-Hashmap.-Generalized-ProblemandSolution%3A-Find-Longest-Subarray-With-Sum-greater-K.

    Compute prefix sum of arr as prefixSum so that prefixSum[j] - prefixSum[i] = sum(arr[i], ... arr[j-1])
Iterate through prefixSum from begin to end and build a strictly monotone decreasing stack smdStack. (smdStack.top() is the smallest)
Iterate through prefixSum from end to begin. For each prefixSum[i], while smdStack.top() is less than prefixSum[i] by at least K, pop smdStackand try to update result by subarray [index of top,i). Until top element is not less than it by K.
Return result.

    For simplicity, call (i, j) a valid pair if the inequation prefixSum[j] - prefixSum[i] >= K holds. Our goal is to optimize j-i over all valid pair (i, j).

    Firstly, fix j and minimize i. Consider any i1 and i2 that i1 < i2 < j and prefixSum[i1] <= prefixSum[i2]. It is obvious that (i2, j) can't be a candidate of optimal subarray because (i1, j) is more promising to be valid and longer than (i2, j). Therefor candidates are monotone decreasing, and we can use a strictly monotonic descresing stack to find all candidates.
    Secondly, fix i and maximize j. Consider any j1 and j2 that i < j1 < j2 and prefixSum[j2] - prefix[i] >= K. We can find that (i, j1) can't be a candidate of optimal subarrary because (i, j2) is better. This discovery tells us that we should iterate j from end to begin and if we find a valid (i, j), we don't need to keep i in smdStack any longer.

    TC: O(N)
    SC: O(N)
    */
    private int longestWPIByStack(int[] hours) {
        if (hours == null || hours.length == 0) {
            return 0;
        }

        int len = hours.length;
        int[] presums = new int[len + 1];
        for (int i = 0; i < len; i++) {
            if (hours[i] > 8) {
                presums[i + 1] = presums[i] + 1;
            } else {
                presums[i + 1] = presums[i] - 1;
            }
        }

        // Keep a left most mono decreasing stack.
        Deque<Integer> idxStack = new ArrayDeque<>();
        for (int i = 0; i < presums.length; i++) {
            if (!idxStack.isEmpty() && presums[idxStack.peek()] <= presums[i]) {
                continue;
            }

            idxStack.push(i);
        }

        // From right to left presums, try get the longest subarray sum larger than 0.
        int res = 0;
        for (int i = presums.length - 1; i >= 0; i--) {
            while (!idxStack.isEmpty() && presums[i] > presums[idxStack.peek()]) {
                res = Math.max(res, i - idxStack.pop());
            }
        }

        return res;

    }
}
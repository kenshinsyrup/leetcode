package com.myleetcode.monotonic_deque.maximum_width_ramp;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int maxWidthRamp(int[] A) {
        return maxWidthRampByStack(A);
    }

    /*
    Similar 1124. Longest Well-Performing Interval
    Mono Descreasing Stack
    Why decreasing stack? -- It is sort of greedy here, because for each number with index i, we want to find smaller numbers whose index is less than i; when going from right to left, the top element of stack is at least <= current number.
    */
    private int maxWidthRampByStack(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int res = 0;

        // Keep a descreasing stack. Fix end.
        int len = nums.length;
        Deque<Integer> idxStack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            if (idxStack.isEmpty() || nums[idxStack.peek()] > nums[i]) {
                idxStack.push(i);
            }
        }

        // Get the max width. Fix start.
        for (int i = len - 1; i >= 0; i--) {
            while (!idxStack.isEmpty() && nums[idxStack.peek()] <= nums[i]) {
                res = Math.max(res, i - idxStack.pop());
            }
        }

        return res;
    }
}
package com.myleetcode.priority_queue.split_array_into_consecutive_subsequences;

import java.util.PriorityQueue;

public class Solution {
    public boolean isPossible(int[] nums) {
        return isPossibleByPQ(nums);
    }

    /*
    Priority Queue, Greedy

    https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/130452/20ms-Java-PriorityQueue-with-Explanations
    https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/130452/20ms-Java-PriorityQueue-with-Explanations/198049

    TC: O(N * logN)
    SC: O(N)
    */
    private boolean isPossibleByPQ(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        PriorityQueue<Interval> intervalPQ = new PriorityQueue<>((Interval a, Interval b) -> {
            if (a.end == b.end) {
                return a.length - b.length;
            }
            return a.end - b.end;
        });

        for (int num : nums) {
            // Check smallest intervals with current num.
            // Case 1, The smallest interval has no further usage any more, poll out. If its length is invalid, false.
            while (!intervalPQ.isEmpty() && intervalPQ.peek().end + 1 < num) {
                Interval interval = intervalPQ.poll();
                if (interval.length < 3) {
                    return false;
                }
            }

            // Case 2, Need a new interval.
            if (intervalPQ.isEmpty() || intervalPQ.peek().end == num) {
                intervalPQ.offer(new Interval(num, 1));
            } else {
                // Case 3, interval.end + 1 = num, so need update the smalest interval.
                Interval interval = intervalPQ.poll();
                interval.end = num;
                interval.length += 1;
                intervalPQ.offer(interval);
            }
        }

        // Chck all intervals' length.
        while (!intervalPQ.isEmpty()) {
            Interval interval = intervalPQ.poll();
            if (interval.length < 3) {
                return false;
            }
        }

        return true;
    }

    class Interval {
        int length;
        int end;

        public Interval(int end, int length) {
            this.end = end;
            this.length = length;
        }
    }
}
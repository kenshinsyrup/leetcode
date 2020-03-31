package com.myleetcode.queue.number_of_recent_calls;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {

    class RecentCounter {

        Deque<Integer> timeQ;

        public RecentCounter() {
            this.timeQ = new ArrayDeque<>();
        }

        public int ping(int t) {
            while (!this.timeQ.isEmpty() && this.timeQ.peek() < t - 3000) {
                this.timeQ.poll();
            }
            this.timeQ.offer(t);

            return this.timeQ.size();
        }
    }

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */

}

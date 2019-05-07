package com.myleetcode.design.design_hit_counter;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    class HitCounter {

        // second granularity, 5min is 300 seconds
        // use PQ to maintain a window that is in the early 5 min, but since the timestamp is chronological, so we only need a normal Q
        // so hits is actually Q.size()
        Deque<Integer> timestampQueue;

        /** Initialize your data structure here. */
        public HitCounter() {
            timestampQueue = new ArrayDeque<>();
        }

        // TC: O(N), best O(1), N is Q size, at most 300
        // SC: O(N)
        /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            updateQueue(timestamp);

            timestampQueue.offer(timestamp);
        }

        // TC: O(N), best O(1)
        // SC: O(N)
        /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            updateQueue(timestamp);

            return timestampQueue.size();
        }

        private void updateQueue(int timestamp){
            // update, only keep the recent 5min hits
            while(!timestampQueue.isEmpty()){
                if(timestamp >= timestampQueue.peek() + 300){
                    timestampQueue.poll();
                }else{
                    return;
                }
            }
        }
    }

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
}

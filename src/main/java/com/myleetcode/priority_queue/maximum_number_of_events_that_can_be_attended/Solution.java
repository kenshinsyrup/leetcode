package com.myleetcode.priority_queue.maximum_number_of_events_that_can_be_attended;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
    public int maxEvents(int[][] events) {
        return maxEventsByPQ(events);
    }

    /*
    Priority Queue
    https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/discuss/510262/Detailed-analysisLet-me-lead-you-to-the-solution-step-by-step

    TC: O(NlogN)
    SC: O(N)
    */
    private int maxEventsByPQ(int[][] events) {
        if (events == null || events.length == 0 || events[0] == null || events[0].length == 0) {
            return 0;
        }

        int count = 0;

        // Get total days.
        int totalDays = 0;
        for (int[] event : events) {
            totalDays = Math.max(totalDays, event[1]);
        }

        // Sort events by start date.
        Arrays.sort(events, (int[] a, int[] b) -> a[0] - b[0]);

        int len = events.length;
        PriorityQueue<Integer> endPQ = new PriorityQueue<>();
        int eventIdx = 0;
        int day = 1;
        while (day <= totalDays) {
            // if no events are available to attend today, let time flies to the next available event.
            if (eventIdx < len && endPQ.isEmpty()) {
                day = events[eventIdx][0];
            }

            // all events starting from today are newly available. add them to the heap.
            while (eventIdx < len && events[eventIdx][0] <= day) {
                endPQ.offer(events[eventIdx][1]);
                eventIdx++;
            }

            // if the event at heap top already ended, then discard it.
            while (!endPQ.isEmpty() && endPQ.peek() < day) {
                endPQ.poll();
            }

            // Greedy: attend the event that will end the earliest.
            if (!endPQ.isEmpty()) {
                endPQ.poll();
                count++;
            }

            day++;
        }

        return count;
    }
}

package com.myleetcode.greedy.minimum_cost_to_connect_sticks;

import java.util.PriorityQueue;

class Solution {
    public int connectSticks(int[] sticks) {
        return connectSticksByGreedy(sticks);
    }

    /*
    Greedy with MinHeap:
    https://leetcode.com/problems/minimum-cost-to-connect-sticks/discuss/365849/JavaPython-3-Greedy-%2B-PriorityQueue-w-analysis.

    why does greedy guarantee the minimum?
    The earlier combined sticks are added more times, so if we want to minimize cost, we will want to combine the smaller ones as early as possible.

    N is total number of sticks.
    TC: O(N * logN)
    SC: O(N)
    */
    private int connectSticksByGreedy(int[] sticks) {
        if (sticks == null || sticks.length == 0) {
            return 0;
        }

        PriorityQueue<Integer> stickPQ = new PriorityQueue<>();
        for (int stick : sticks) {
            stickPQ.offer(stick);
        }

        int count = 0;
        while (stickPQ.size() > 1) {
            int stick1 = stickPQ.poll();
            int stick2 = stickPQ.poll();
            int newStick = stick1 + stick2;

            count += newStick;

            stickPQ.offer(newStick);
        }

        return count;
    }
}
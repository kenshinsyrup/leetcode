package com.myleetcode.priority_queue.ipo;

import java.util.PriorityQueue;

public class Solution {
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        return findMaximizedCapitalByPQ(k, W, Profits, Capital);
    }

    /*
    Priority Queue:
    Do the most profitable project within current capital
    https://leetcode.com/problems/ipo/discuss/98210/Very-Simple-(Greedy)-Java-Solution-using-two-PriorityQueues
    */
    private int findMaximizedCapitalByPQ(int K, int W, int[] profits, int[] capital) {
        if (K < 0 || W < 0 || profits == null || profits.length == 0 || capital == null || capital.length == 0) {
            return 0;
        }

        // int[] is pair of project profit and capital.
        // first we put all projects to projectPQ sorting by capital ascending.
        // if current W is larger than project needed capital, we poll the project out and put into waitingPQ to wait for implement. In waitingPQ, we sort pairs by profits descending, so we could implement most profitable project first, after implement, add its profit to W.
        PriorityQueue<int[]> projectPQ = new PriorityQueue<>((int[] a, int[] b) -> a[1] - b[1]);
        PriorityQueue<int[]> waitingPQ = new PriorityQueue<>((int[] a, int[] b) -> b[0] - a[0]);

        // Put projects to projectPQ.
        int len = profits.length;
        for (int i = 0; i < len; i++) {
            projectPQ.offer(new int[]{profits[i], capital[i]});
        }

        // Process.
        for (int i = 0; i < K; i++) {
            // If could done within current W, poll out from projectPQ to waitingPQ.
            while (!projectPQ.isEmpty() && projectPQ.peek()[1] <= W) {
                waitingPQ.offer(projectPQ.poll());
            }

            // Choose most profitable project to implement.
            if (waitingPQ.isEmpty()) {
                break;
            }
            W += waitingPQ.poll()[0];
        }

        return W;
    }
}

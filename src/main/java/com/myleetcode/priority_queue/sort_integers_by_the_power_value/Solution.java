package com.myleetcode.priority_queue.sort_integers_by_the_power_value;

import java.util.PriorityQueue;

public class Solution {
    public int getKth(int lo, int hi, int k) {
        return getKthByPQ(lo, hi, k);
    }

    /*
    Get each num's power, put them into pq as a pair, return the kth(1 based) element in pq.

    TC: O(N*logN)
    SC: O(N)
    */
    private int getKthByPQ(int low, int high, int k) {
        if (low > high || k <= 0) {
            return -1;
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a, int[] b) -> {
            if (a[1] == b[1]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });

        for (int num = low; num <= high; num++) {
            int power = getPower(num, 0);
            pq.offer(new int[]{num, power});
        }

        // Here k is 1 based.
        while (!pq.isEmpty() && k > 1) {
            pq.poll();
            k--;
        }
        return pq.isEmpty() ? -1 : pq.poll()[0];
    }

    private int getPower(int num, int steps) {
        if (num == 1) {
            return steps;
        }

        if (num % 2 == 0) {
            return getPower(num / 2, steps + 1);
        }
        return getPower(3 * num + 1, steps + 1);
    }
}

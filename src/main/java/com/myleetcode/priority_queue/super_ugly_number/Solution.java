package com.myleetcode.priority_queue.super_ugly_number;

import java.util.PriorityQueue;

public class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        return nthSuperUglyNumberByPQ(n, primes);
    }

    /*
    Priority Queue

    Transformation of 264. Ugly Number II

    */
    private int nthSuperUglyNumberByPQ(int N, int[] primes) {
        if (N <= 0 || primes == null || primes.length == 0) {
            return 0;
        }

        PriorityQueue<Long> uglyPQ = new PriorityQueue<>();
        uglyPQ.offer(1L);

        for (int i = 1; i < N; i++) {
            long uglyNum = uglyPQ.poll();

            // Remove duplicates.
            while (!uglyPQ.isEmpty() && uglyPQ.peek() == uglyNum) {
                uglyPQ.poll();
            }

            for (int factor : primes) {
                uglyPQ.offer(uglyNum * factor);
            }
        }

        return uglyPQ.poll().intValue();
    }
}

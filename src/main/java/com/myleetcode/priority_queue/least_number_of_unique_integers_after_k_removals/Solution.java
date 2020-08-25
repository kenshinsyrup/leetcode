package com.myleetcode.priority_queue.least_number_of_unique_integers_after_k_removals;

import java.util.*;

public class Solution {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        return findLeastNumOfUniqueIntsByPQ(arr, k);
        // return findLeastNumOfUniqueIntsByList(arr, k);
    }

    /*
    In LC, the findLeastNumOfUniqueIntsByList will get TLE and findLeastNumOfUniqueIntsByPQ works fine. And we should use the PQ one in normal situation since it's much more clear.

    But if we analyze the TC, we could find, if K is very small while the arr is very large(like K << log(arr.length)), then the two methods will have same big-O TC. BUT, it's the same because they all do the sort operation, when considering "Try Removal" part, since K << log(arr.length), so the findLeastNumOfUniqueIntsByList will be much faster.

    This is a lesson from an Amazon's OA. In that problem, if we use the PQ, we'll get TLE, while if we use the List thought, we could pass all tests.
    */

    /*
    TC: O(MAX(N * logN, K * N))
    SC: O(N)
    */
    private int findLeastNumOfUniqueIntsByList(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // <Number in arr, Appear times of this number>
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // <int[]{number, appear times of this number}>, sort by appear times ascending.
        List<int[]> list = new ArrayList<>();
        for (int num : map.keySet()) {
            list.add(new int[]{num, map.get(num)});
        }
        Collections.sort(list, (a, b) -> {
            return a[1] - b[1];
        });

        // Try removal
        while (k > 0) {
            boolean removed = false;
            ;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)[1] > 0) {
                    list.get(i)[1]--;
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                return 0;
            }

            k--;
        }

        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[1] > 0) {
                count++;
            }
        }

        return count;

    }

    /*
    N is length of arr.
    TC: O(N * logN)
    SC: O(N)
    */
    private int findLeastNumOfUniqueIntsByPQ(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // <Number in arr, Appear times of this number>
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // <int[]{number, appear times of this number}>, sort by appear times ascending.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });
        for (int num : map.keySet()) {
            pq.offer(new int[]{num, map.get(num)});
        }

        // Try removal;
        while (!pq.isEmpty() && k > 0) {
            int[] info = pq.poll();

            info[1]--;
            if (info[1] > 0) {
                pq.offer(info);
            }

            k--;
        }

        return pq.size();

    }
}
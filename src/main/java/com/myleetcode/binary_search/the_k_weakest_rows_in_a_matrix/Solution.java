package com.myleetcode.binary_search.the_k_weakest_rows_in_a_matrix;

import java.util.PriorityQueue;

public class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        return kWeakestRowsByBSAndPQ(mat, k);
    }

    /*
    BinarySearch

    TC: O(R * (logC + logK))
    SC: O(K)
    */
    private int[] kWeakestRowsByBSAndPQ(int[][] mat, int k) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) {
            return new int[0];
        }
        if (k <= 0) {
            return new int[0];
        }

        // int[]: 0th is rowIdx, 1th is solider number. Ordering by solider number descending, then rowIdx descending.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (b[1] != a[1]) {
                return b[1] - a[1];
            }

            return b[0] - a[0];
        });

        int rowLen = mat.length;
        int colLen = mat[0].length;
        for (int i = 0; i < rowLen; i++) {
            int lastSoliderIdx = findIdxOfLastSolider(mat, i, colLen);

            pq.offer(new int[]{i, lastSoliderIdx + 1});
            if (pq.size() > k) {
                pq.poll();
            }
        }

        int[] ret = new int[k];
        int idx = k - 1;
        while (!pq.isEmpty()) {
            ret[idx] = pq.poll()[0];
            idx--;
        }

        return ret;
    }

    private int findIdxOfLastSolider(int[][] mat, int rowIdx, int colLen) {
        int left = 0;
        int right = colLen - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (mat[rowIdx][mid] == 1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // left is first idx make not(mat[rowIdx][idx]==1) ie first civilian, then left-1 is last solider
        return left - 1;

    }
}

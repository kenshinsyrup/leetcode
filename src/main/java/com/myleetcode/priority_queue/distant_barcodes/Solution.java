package com.myleetcode.priority_queue.distant_barcodes;

import java.util.*;

public class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        return rearrangeBarcodesByPQ(barcodes);
    }

    /*
    Priority Queue
    Use same template with Problem 358 Rearrange String k Distance Apart.
    https://leetcode.com/problems/distant-barcodes/discuss/299227/Java-Solution-Using-PriorityQueue-Similar-to-K-Distance-Apart-Question-where-K-2

    TC: O(N * logN)
    SC: O(N)
    */
    private int[] rearrangeBarcodesByPQ(int[] barcodes) {
        if (barcodes == null || barcodes.length == 0) {
            return new int[0];
        }

        // Get the barcode->num to map.
        Map<Integer, Integer> barcodeNumMap = new HashMap<>();
        for (int barcode : barcodes) {
            barcodeNumMap.put(barcode, barcodeNumMap.getOrDefault(barcode, 0) + 1);
        }

        // Use PQ to do the K distance apart procesure.
        PriorityQueue<Map.Entry<Integer, Integer>> entryPQ = new PriorityQueue<>((Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) -> {
            if (a.getValue() == b.getValue()) {
                return a.getKey() - b.getKey(); // barcode ascending.
            }
            return b.getValue() - a.getValue(); // num descending.
        });
        for (Map.Entry entry : barcodeNumMap.entrySet()) {
            entryPQ.offer(entry);
        }

        int len = barcodes.length;
        int[] res = new int[len];
        int idx = 0;
        while (!entryPQ.isEmpty()) {
            int K = 2; // Distance of same barcode's index at least 2.

            List<Map.Entry<Integer, Integer>> waitingList = new ArrayList<>();
            while (K > 0 && !entryPQ.isEmpty()) {
                Map.Entry<Integer, Integer> entry = entryPQ.poll();

                // Add to res and reduce barcode's num.
                res[idx] = entry.getKey();
                entry.setValue(entry.getValue() - 1);

                // Still useful.
                if (entry.getValue() > 0) {
                    waitingList.add(entry);
                }

                idx++;
                K--;
            }

            // Put back still useful entry.
            for (Map.Entry<Integer, Integer> entry : waitingList) {
                entryPQ.offer(entry);
            }
        }

        return res;

    }
}

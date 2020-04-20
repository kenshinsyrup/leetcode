package com.myleetcode.binary_search.capacity_to_ship_packages_within_d_days;

public class Solution {
    public int shipWithinDays(int[] weights, int D) {
        return shipWithinDaysByBS(weights, D);
    }

    /*
    Binary Search:
    https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/discuss/256729/JavaC%2B%2BPython-Binary-Search

    */
    private int shipWithinDaysByBS(int[] weights, int D) {
        if (weights == null || weights.length == 0 || D <= 0) {
            return 0;
        }

        int maxWeightItem = 0;
        int weightSum = 0;
        for (int weight : weights) {
            maxWeightItem = Math.max(maxWeightItem, weight);
            weightSum += weight;
        }

        int left = maxWeightItem; // Left use maxWeightItem, means the belt at least should be able to convey maxWeightItem weight item one time.
        int right = weightSum; // Right use weightSum, means the belt at most need to be able to convey weightSum weight item one time.
        while (left <= right) {
            // Try, mid means the weight to convey every day.
            int mid = left + (right - left) / 2;

            // With this mid as weight limt, we caculate the days needed.
            int days = 1;
            int cur = 0;
            for (int weight : weights) {
                if (cur + weight > mid) {
                    days++;
                    cur = 0;
                }

                cur += weight;
            }

            // If days larger than D, means the mid weight limit is too small. When loop end, the left is the first weight value that makes this days>D condition false, which means the first appeared weight value makes days<=D successful.
            if (days > D) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;

    }
}

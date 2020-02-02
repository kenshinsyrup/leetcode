package com.myleetcode.array.pour_water;

class Solution {
    public int[] pourWater(int[] heights, int V, int K) {
        return pourWaterByTry(heights, V, K);
    }

    /*
    https://leetcode.com/problems/pour-water/discuss/171104/Share-my-Java-straightforward-and-concise-solution

    TC: O(V * N)
    SC: O(1)
    */
    private int[] pourWaterByTry(int[] heights, int dropletNum, int K) {
        if (heights == null || heights.length == 0) {
            return heights;
        }
        if (dropletNum <= 0) {
            return heights;
        }

        int len = heights.length;
        for (int i = 0; i < dropletNum; i++) {
            int cur = K;

            // Move to left when possible.
            while (cur > 0 && heights[cur] >= heights[cur - 1]) {
                cur--;
            }
            // Move to right when possible.
            while (cur < len - 1 && heights[cur] >= heights[cur + 1]) {
                cur++;
            }

            // Try to move to K as close as it could. If the whole level is plane, stop at K.
            while (cur > K && heights[cur] == heights[cur - 1]) {
                cur--;
            }

            // Droplet stopped, current index height increase.
            heights[cur]++;
        }

        return heights;
    }
}

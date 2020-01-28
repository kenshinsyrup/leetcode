package com.myleetcode.line_sweep.car_pooling;

import java.util.Map;
import java.util.TreeMap;

class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        return carPoolingByTreeMap(trips, capacity);
    }

    /*
    Line Sweep with TreeMap:
    https://leetcode.com/problems/car-pooling/discuss/317610/JavaC%2B%2BPython-Meeting-Rooms-III

    N is length of trips.
    TC: O(NlogN)
    SC: O(N)
    */
    private boolean carPoolingByTreeMap(int[][] trips, int capacity) {
        // Special parameters.
        if (trips == null || trips.length == 0 || trips[0] == null || trips[0].length == 0) {
            return true;
        }
        if (capacity <= 0) {
            return false;
        }

        // 1. TreeMap stores trip information and sort by locations.
        Map<Integer, Integer> locationNumberTM = new TreeMap<>();
        for (int[] trip : trips) {
            int num = trip[0];
            int start = trip[1];
            int end = trip[2];

            // Early break, at any trip location, if num is more than capacity, it's impossible to pick up them all.
            if (num > capacity) {
                return false;
            }

            // At current trip
            // at this start location, must pick up this num passengers.
            locationNumberTM.put(start, locationNumberTM.getOrDefault(start, 0) + num);
            // at this end location, must drop off this num passengers.
            locationNumberTM.put(end, locationNumberTM.getOrDefault(end, 0) - num);
        }

        // 2. Check.
        for (int location : locationNumberTM.keySet()) {
            capacity -= locationNumberTM.get(location);

            if (capacity < 0) {
                return false;
            }
        }

        return true;

    }
}

package com.myleetcode.line_sweep.corporate_flight_bookings;

import java.util.TreeMap;

public class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // return corpFlightBookingsByTreeMap(bookings, n); // TLE
        return corpFlightBookingsByMath(bookings, n);
    }

    // Problem explaination picture: https://leetcode.com/problems/corporate-flight-bookings/discuss/328871/C%2B%2BJava-with-picture-O(n)

    /*
    https://leetcode.com/problems/corporate-flight-bookings/discuss/353616/Another-view.Explaination-easy-to-understand

    TC: O(N)
    SC: O(N)
    */
    private int[] corpFlightBookingsByMath(int[][] bookings, int n) {
        int[] ret = new int[n];
        // Special parameters.
        if (bookings == null || bookings.length == 0 || bookings[0] == null || bookings[0].length == 0) {
            return ret;
        }

        // 1. Get all booking seats status at booking timepoint.
        // Here the status length is n+2: Because of the inclusive requirement, we have flight time from 1 to n+1, so need status length is n+2, thoug the element status[n+1] is useless.
        int[] status = new int[n + 2];
        for (int[] booking : bookings) {
            int start = booking[0];
            int end = booking[1] + 1;
            int seats = booking[2];

            status[start] += seats;
            status[end] -= seats;
        }

        // 2. Accumulate the total seats at each flight time. For each time, we have its current status and its previous reserved seats to get current time total seats.
        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                ret[i - 1] = status[i];
            } else {
                ret[i - 1] = ret[i - 2] + status[i];
            }
        }

        return ret;
    }

    /*
    TLE

    LineSweep with TreeMap

    TC: O(NlogN)
    SC: O(N)
    */
    private int[] corpFlightBookingsByTreeMap(int[][] bookings, int n) {
        int[] ret = new int[n];
        // Special parameters.
        if (bookings == null || bookings.length == 0 || bookings[0] == null || bookings[0].length == 0) {
            return ret;
        }

        // 1. Store booking to TreeMap.
        TreeMap<Integer, Integer> timeNumTM = new TreeMap<>();
        for (int[] booking : bookings) {
            int startFlight = booking[0];
            int endFlight = booking[1];
            int num = booking[2];

            // TreeMap.
            // Important: Since booking is start and end flights inclusive, so we should add num when at start timepoint and minus at end+1 flight.
            timeNumTM.put(startFlight, timeNumTM.getOrDefault(startFlight, 0) + num);
            timeNumTM.put(endFlight + 1, timeNumTM.getOrDefault(endFlight + 1, 0) - num);
        }

        // 2. Just like other Line Sweep problems, we acculumate the number at each timepoint.
        // The difference is, other problem may return when sum is not valid, here when we get a sum, we update the TreeMap.
        int sum = 0;
        for (int time : timeNumTM.keySet()) {
            sum += timeNumTM.get(time);

            timeNumTM.put(time, sum);
        }

        // 3. Find total booking num for each flight.
        for (int i = 1; i <= n; i++) {
            Integer time = timeNumTM.floorKey(i);

            if (time != null) {
                ret[i - 1] = timeNumTM.get(time);
            }
        }

        return ret;
    }
}

package com.myleetcode.line_sweep.corporate_flight_bookings;

import java.util.TreeMap;

public class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // return corpFlightBookingsByMath(bookings, n);
        // return corpFlightBookingsByTreeMapI(bookings, n); // TLE
        return corpFlightBookingsByTreeMapII(bookings, n);
    }

    /*
    LineSweep with TreeMap
    Optimize from the first TreeMap solution, we dont need to keep record of every flight point in TreeMap, just keep tracking of every flight range's start and end(end is the bookings[i][1]+1), and then do line sweep.
    The most important part is the second process to the TreeMap to get the exact number of each flight point by accumulating prev seats to current.

    TC: O(N + logK)
    SC: O(N)
    */
    private int[] corpFlightBookingsByTreeMapII(int[][] bookings, int n) {
        int[] ret = new int[n];
        // Special parameters.
        if (bookings == null || bookings.length == 0 || bookings[0] == null || bookings[0].length == 0) {
            return ret;
        }

        // 1. Store booking to TreeMap.
        TreeMap<Integer, Integer> timeNumTM = new TreeMap<>();
        for (int[] booking : bookings) {
            // Important: Since booking is start and end flights inclusive, so we should add num when at start timepoint and minus at end+1 flight.
            timeNumTM.put(booking[0], timeNumTM.getOrDefault(booking[0], 0) + booking[2]);
            timeNumTM.put(booking[1] + 1, timeNumTM.getOrDefault(booking[1] + 1, 0) - booking[2]);
        }

        // 2. Just like other Line Sweep problems, we acculumate the number at each timepoint.
        // The difference is, other problem may return when sum is not valid, here when we get a sum, we update the TreeMap.
        int prevSeats = 0;
        for (int time : timeNumTM.keySet()) {
            prevSeats += timeNumTM.get(time);

            timeNumTM.put(time, prevSeats);
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

    /*
    N is bookings length, K is flights number.
    TC: O(N * logK)
    SC: O(K)
    */
    private int[] corpFlightBookingByTreeMapI(int[][] bookings, int num) {
        if (bookings == null || bookings.length == 0 || num <= 0) {
            return new int[0];
        }

        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for (int[] booking : bookings) {
            int flightStart = booking[0];
            int flightEnd = booking[1];
            int seats = booking[2];

            for (int i = flightStart; i <= flightEnd; i++) {
                tm.put(i, tm.getOrDefault(i, 0) + seats);
            }
        }

        int[] ret = new int[num];
        for (int i = 0; i < num; i++) {
            ret[i] = tm.getOrDefault(i + 1, 0);
        }

        return ret;
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
}

package com.myleetcode.line_sweep.insert_interval;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // return insertByGreedy(intervals, newInterval);
        return insertByTreeMap(intervals, newInterval);
    }

    /*
    TreeMap:
    https://leetcode.com/problems/insert-interval/discuss/167137/TreeMap-solution

    TC: O(NlogN), logN is remove and put
    SC: O(N)
    */
    private int[][] insertByTreeMap(int[][] intervals, int[] newInterval) {
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return new int[][]{newInterval};
        }

        // 1. Store interval start->end pair to TreeMap.
        TreeMap<Integer, Integer> startEndTM = new TreeMap<>();
        for (int[] interval : intervals) {
            startEndTM.put(interval[0], interval[1]);
        }

        // 2. Keep track of the new interval start and end, find the prev interval start and end in the TreeMap, check if there's overlapping then remove the prev interval on TreeMap and update newStart and newEnd.
        // When try to find prev interval, we use newEnd to find its flooKey, this means a interval start before the newInterval's end. If use newStart to find, then may miss some condition like [[1,8], [6,7]] with [1,8] as newStart and newEnd, the interval will stop merging, because the start time 1 could not find another prev interval
        int newStart = newInterval[0];
        int newEnd = newInterval[1];
        Integer prevIntervalStart = startEndTM.floorKey(newEnd);
        while (prevIntervalStart != null) {
            Integer prevIntervalEnd = startEndTM.get(prevIntervalStart);

            // Overlapping.
            if (prevIntervalEnd >= newStart) {
                startEndTM.remove(prevIntervalStart);

                newStart = Math.min(newStart, prevIntervalStart);
                newEnd = Math.max(newEnd, prevIntervalEnd);

                prevIntervalStart = startEndTM.floorKey(newEnd);
            } else {
                break;
            }
        }
        startEndTM.put(newStart, newEnd);

        // 3. Build result.
        List<int[]> retList = new ArrayList<>();
        for (int start : startEndTM.keySet()) {
            retList.add(new int[]{start, startEndTM.get(start)});
        }

        int size = retList.size();
        int[][] ret = new int[size][];
        for (int i = 0; i < size; i++) {
            ret[i] = retList.get(i);
        }

        return ret;
    }

    // like 56, Sort and Greedy
    // the intervals are sorted and no over lapping, so we need to find the pos for newInterval.start and then do merge after
    private int[][] insertByGreedy(int[][] intervals, int[] newInterval) {
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return new int[][]{newInterval};
        }

        List<int[]> ret = new ArrayList<>();
        int len = intervals.length;

        int i = 0;

        // before new interval
        while (i < len && intervals[i][1] < newInterval[0]) {
            ret.add(intervals[i]);
            i++;
        }
        // overlap
        while (i < len && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);

            i++;
        }
        ret.add(newInterval);
        // after new interval
        while (i < len) {
            ret.add(intervals[i]);
            i++;
        }

        return ret.toArray(new int[][]{});
    }
}
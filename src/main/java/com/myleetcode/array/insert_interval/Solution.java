package com.myleetcode.array.insert_interval;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        return insertByGreedy(intervals, newInterval);
    }

    // like 56, Sort and Greedy
    // the intervals are sorted and no over lapping, so we need to find the pos for newInterval.start and then do merge after
    private int[][] insertByGreedy(int[][] intervals, int[] newInterval){
        if(intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0){
            return new int[][]{newInterval};
        }

        List<int[]> ret = new ArrayList<>();
        int len = intervals.length;

        int i = 0;

        // before new interval
        while(i < len && intervals[i][1] < newInterval[0]){
            ret.add(intervals[i]);
            i++;
        }
        // overlap
        while(i < len && intervals[i][0] <= newInterval[1]){
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);

            i++;
        }
        ret.add(newInterval);
        // after new interval
        while(i < len){
            ret.add(intervals[i]);
            i++;
        }

        return ret.toArray(new int[][]{});
    }
}

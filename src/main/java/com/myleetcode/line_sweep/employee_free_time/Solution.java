package com.myleetcode.line_sweep.employee_free_time;

import com.myleetcode.utils.interval.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
// Definition for an Interval.
class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};
*/
class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        return employeeFreeTimeByList(schedule);
    }

    /*
    Line Sweep:

    Similar with Merge Interval series problems.
    TC: O(N * log(N)), N is total number of intervals.
    SC: O(N)
    */
    private List<Interval> employeeFreeTimeByList(List<List<Interval>> schedule) {
        // Special case.
        if (schedule == null || schedule.size() == 0 || schedule.get(0) == null || schedule.get(0).size() == 0) {
            return new ArrayList<>();
        }

        // Put all intervals into a whole list and sort the list by interval's start time and end time in ascending order.
        List<Interval> intervals = new ArrayList<>();
        for (List<Interval> employeeInterval : schedule) {
            for (Interval interval : employeeInterval) {
                intervals.add(interval);
            }
        }
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                if (i1.start != i2.start) {
                    return i1.start - i2.start;
                }
                return i1.end - i2.end;
            }
        });

        // Compare prevInterval's end and current interval's start time to get free interval.
        List<Interval> ret = new ArrayList<>();
        Interval prevInterval = null;
        for (Interval interval : intervals) {
            if (prevInterval == null) {
                prevInterval = interval;
                continue;
            }

            if (prevInterval.end < interval.start) {
                ret.add(new Interval(prevInterval.end, interval.start));
                prevInterval = interval;
            } else {
                if (prevInterval.end < interval.end) {
                    prevInterval = interval;
                }
            }
        }

        return ret;

    }
}
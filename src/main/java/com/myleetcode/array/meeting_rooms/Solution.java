package com.myleetcode.array.meeting_rooms;

import com.myleetcode.utils.interval.Interval;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {

        // special case, true or false should be told by interviewer
        if(intervals == null || intervals.length == 0 | intervals.length == 1){
            return true;
        }

        return canAttendMeetingsBySort(intervals);
    }

    //简单数学题，第一想法就是：可以按照interval的start升序排列，然后遍历intervals，如果后面的interval的start早于前面的start，那么就是overlap。
    //另外，一个想法是，可以想到另一道calendar题目，我们可以借助map，把interval的start作为key存入value为1，end作为key存入value为-1，这样我们再遍历map的keys，获取到value求和，只要和出现大于1的情况说明有某个interval的start在另一个interval的start和end之间，那么就是overlap。

    // TC: sort O(nlogn), traverse O(n), overall is O(nlogn)
    // SC: O(1)
    // sort 方法,练习Comparator写法
    private boolean canAttendMeetingsBySort(Interval[] intervals){
        // dont use Collections.sort because Collections is for ArrayList and so on, here should use Arrays.sort and Comparator to sort intervals, ascending
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        for(int i = 0; i < intervals.length - 1; i++){
            Interval iCur = intervals[i];
            Interval iNext = intervals[i + 1];
            if(iNext.start < iCur.end){// here this problem think iNext.start euqals to iCur.end is not overlap
                return false;
            }
        }

        return true;
    }
}

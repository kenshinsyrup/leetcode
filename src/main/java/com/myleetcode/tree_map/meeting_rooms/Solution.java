package com.myleetcode.tree_map.meeting_rooms;

import com.myleetcode.utils.interval.Interval;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        return canAttendMeetingsByTreeMap(intervals);
    }

    // TC: O(N * logN), N intervals, every interval cost logN to put into treeMap
    // SC: O(N)
    // https://leetcode.com/problems/meeting-rooms-ii/discuss/203658/HashMapTreeMap-resolves-Scheduling-Problem
    private boolean canAttendMeetingsByTreeMap(int[][] intervals){
        // special case, true or false should be told by interviewer
        if(intervals == null || intervals.length == 0 | intervals.length == 1){
            return true;
        }

        // put start and end to treemap
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for(int[] itv: intervals){
            treeMap.put(itv[0], treeMap.getOrDefault(itv[0], 0) + 1);
            treeMap.put(itv[1], treeMap.getOrDefault(itv[1], 0) - 1);
        }

        // scan, count num in every timepoint
        int count = 0;
        for(int val: treeMap.values()){
            count += val;

            if(count > 1){
                return false;
            }
        }

        return true;

    }

    //简单数学题，第一想法就是：可以按照interval的start升序排列，然后遍历intervals，如果后面的interval的start早于前面的start，那么就是overlap。
    //另外，一个想法是，可以想到另一道calendar题目，我们可以借助map，把interval的start作为key存入value为1，end作为key存入value为-1，这样我们再遍历map的keys，获取到value求和，只要和出现大于1的情况说明有某个interval的start在另一个interval的start和end之间，那么就是overlap。

    // TC: sort O(nlogn), traverse O(n), overall is O(nlogn)
    // SC: O(1)
    // sort 方法,练习Comparator写法
    private boolean canAttendMeetingsBySort(int[][] intervals){
        // special case, true or false should be told by interviewer
        if(intervals == null || intervals.length == 0 | intervals.length == 1){
            return true;
        }

        // dont use Collections.sort because Collections is for ArrayList and so on, here should use Arrays.sort and Comparator to sort intervals, ascending
        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare(int[] i1, int[] i2){
                return i1[0] - i2[0];
            }
        });

        for(int i = 0; i < intervals.length - 1; i++){
            int[] iCur = intervals[i];
            int[] iNext = intervals[i + 1];
            if(iNext[0] < iCur[1]){// here this problem think iNext.start euqals to iCur.end is not overlap
                return false;
            }
        }

        return true;
    }
}
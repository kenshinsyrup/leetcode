package com.myleetcode.tree_map.my_calendar_iii;

import java.util.Map;
import java.util.TreeMap;

class MyCalendarThree {
    Map<Integer, Integer> treeMap;

    public MyCalendarThree() {
        treeMap = new TreeMap<Integer, Integer>();
    }

    // https://leetcode.com/problems/meeting-rooms-ii/discuss/203658/HashMapTreeMap-resolves-Scheduling-Problem
    public int book(int start, int end) {
        treeMap.put(start, treeMap.getOrDefault(start, 0) + 1);
        treeMap.put(end, treeMap.getOrDefault(end, 0) - 1);

        int count = 0;
        int ret = 0; // 记录count在从events.values的头向尾求和过程中的最大值，即为答案
        for(int val: treeMap.values()){
            count += val;

            ret = Math.max(ret, count);
        }

        return ret;

    }
}

/**
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(start,end);
 */

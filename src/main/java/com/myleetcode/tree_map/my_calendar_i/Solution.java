package com.myleetcode.tree_map.my_calendar_i;

import java.util.TreeMap;

class MyCalendar {
    // private attribute. First we need a map, key is start time and value is end time. Then we need a TreeMap, so we could find the keys near a given 'key' but we don't know if this given 'key' exists in the map.
    private TreeMap<Integer, Integer> calendarTreeMap;

    public MyCalendar() {
        // init
        calendarTreeMap = new TreeMap<Integer, Integer>();
    }

    public boolean book(int start, int end) {
        // previous event's start time
        // floorKey() : It returns the greatest key less than or equal to the given key, or null if there is no such key.
        Integer prevStart = calendarTreeMap.floorKey(start);

        // next event's start time
        // his method returns the least key which is greater than or equal to the given key value. If such a key is absent, null is returned.
        Integer nextStart = calendarTreeMap.ceilingKey(start);

        // (prevStart not exist or prevStart's corresponding event's end time is less than start) AND (nextStart not exist or nextStart is greater than end), there's no overlap.
        if((prevStart == null || calendarTreeMap.get(prevStart) <= start) && (nextStart == null || nextStart >= end)){// be careful with the two <=
            calendarTreeMap.put(start, end);// must put
            return true;// true
        }

        return false;

    }
}

/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(start,end);
 */


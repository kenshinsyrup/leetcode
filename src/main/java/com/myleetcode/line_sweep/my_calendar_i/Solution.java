package com.myleetcode.line_sweep.my_calendar_i;

import java.util.TreeMap;

class MyCalendar {
    // private attribute. First we need a map, key is start time and value is end time. Then we need a TreeMap, so we could find the keys near a given 'key' but we don't know if this given 'key' exists in the map.
    private TreeMap<Integer, Integer> calendarTM;

    public MyCalendar() {
        this.calendarTM = new TreeMap<>();
    }

    /*
    N is total nodes in TreeMap.
    TC: O(logN)
    SC: O(1)
    */
    public boolean book(int start, int end) {
        // return bookByCheckFirst(start, end); // Much faster.
        return simulateBookByTreeMap(start, end); // Template for My Calendar II: double book.
    }

    private boolean bookByCheckFirst(int start, int end) {
        // previous event's start time
        // floorKey() : It returns the greatest key less than or equal to the given key, or null if there is no such key.
        Integer prevStart = calendarTM.floorKey(start);

        // next event's start time
        // his method returns the least key which is greater than or equal to the given key value. If such a key is absent, null is returned.
        Integer nextStart = calendarTM.ceilingKey(start);

        // (prevStart not exist or prevStart's corresponding event's end time is less than start) AND (nextStart not exist or nextStart is greater than end), there's no overlap.
        if ((prevStart == null || calendarTM.get(prevStart) <= start) && (nextStart == null || nextStart >= end)) {// be careful with the two <=
            calendarTM.put(start, end);// must put
            return true;// true
        }

        return false;
    }

    /*
    Template for My Calendar II: double book
    https://leetcode.com/problems/my-calendar-ii/solution/

    N is total nodes in TreeMap.
    TC: O(N)
    SC: O(1)
    */
    private boolean simulateBookByTreeMap(int start, int end) {
        // Book.
        this.calendarTM.put(start, this.calendarTM.getOrDefault(start, 0) + 1);
        this.calendarTM.put(end, this.calendarTM.getOrDefault(end, 0) - 1);

        // Check.
        int meetingOnGoing = 0;
        for (int time : this.calendarTM.keySet()) {
            meetingOnGoing += this.calendarTM.get(time);

            if (meetingOnGoing > 1) {
                // Should not book, remove.
                this.calendarTM.put(start, this.calendarTM.get(start) - 1);
                this.calendarTM.put(end, this.calendarTM.get(end) + 1);
                return false;
            }
        }

        return true;
    }
}

/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(start,end);
 */
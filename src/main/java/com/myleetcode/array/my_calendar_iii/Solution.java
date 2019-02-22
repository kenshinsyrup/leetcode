package com.myleetcode.array.my_calendar_iii;

import java.util.Map;
import java.util.TreeMap;

public class Solution {
    class MyCalendarThree {
        Map<Integer, Integer> events;

        public MyCalendarThree() {
            events = new TreeMap<Integer, Integer>();
        }

        public int book(int start, int end) {
            int value = 0;
            if (events.get(start) != null) {
                value = events.get(start);
            }
            events.put(start, value + 1);

            value = 0;
            if (events.get(end) != null) {
                value = events.get(end);
            }
            events.put(end, value - 1);

            int count = 0;
            int ans = 0; // 记录count在从events.values的头向尾求和过程中的最大值，即为答案
            for (int v : events.values()) {
                count += v;
                ans = Math.max(ans, count);
            }

            return ans;

        }
    }

/**
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(start,end);
 */
}

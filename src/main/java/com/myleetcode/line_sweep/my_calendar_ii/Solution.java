package com.myleetcode.line_sweep.my_calendar_ii;

import java.util.Map;
import java.util.TreeMap;

class MyCalendarTwo {
    Map<Integer, Integer> treeMap;

    public MyCalendarTwo() {
        treeMap = new TreeMap<Integer, Integer>();
    }

    // https://leetcode.com/problems/meeting-rooms-ii/discuss/203658/HashMapTreeMap-resolves-Scheduling-Problem
    public boolean book(int start, int end) {
        // 这道题没有要求说必须保证event的开始截止时间我们有保存，也没有要求返回。
        // 我们使用TreeMap，在要book一个event时，对于start，直接put(start, events.get(start)||0 + 1);对于end，直接put(end, events.get(end)||0 - 1).
//         这样达到的效果就是，在没有events交叉时，我们遍历events map的values，和应该是0.如果存在events交叉，那么肯定说明有一个event的start时间在另一个event的start和end之间，或者一个event的end时间在另一个event的start和end之间，或者二者均满足，总之这样的话，在遍历的过程中肯定存在某一个value时，value sum不是0.
        //比如两个event，一个是[10, 20], 一个是[15, 30].那么当遍历到15时，values的和时1而不是0，这说明这个点的时候存在一次交叉。那么本题就是当values sum大于2时，也就是有两个以上交叉时，返回false。
        // 这个思路也延续到III

        // 我们会一直这样直接put，因为我们不考虑key相同的情况下的保留和对应问题，只考虑计数，因为本题目只要求返回是否可以book。
        // 既然要尝试放入该event，那么直接放入
        treeMap.put(start, treeMap.getOrDefault(start, 0) + 1);
        treeMap.put(end, treeMap.getOrDefault(end, 0) - 1);

        int count = 0;
        for(int val: treeMap.values()){
            count += val;

            if(count > 2){ // triple overlap
                // 既然该event不合法，那么移除，这里的移除不是移除键值对，因为我们的key可能是多个event的多个相同key，所以这里只是把前面对他的增减值移除
                treeMap.put(start, treeMap.get(start) - 1);
                treeMap.put(end, treeMap.get(end) + 1);

                return false;
            }
        }

        return true;

    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */

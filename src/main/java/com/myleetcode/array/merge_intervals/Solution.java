package com.myleetcode.array.merge_intervals;

import com.myleetcode.utils.interval.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public List<Interval> merge(List<Interval> intervals) {

        // special case
        if(intervals == null || intervals.size() == 0 || intervals.size() == 1){
            return intervals;
        }

        return mergeBySort(intervals);
    }

    // 用排序的方法做，纯数组数据结构tag下的题目都比较像数学题，每个题有每个题的思路
    // 按照start时间排序，然后遍历intervals，对于每一个interval，如果其start时间早于上一个interval的end时间说明overlap，那么更新一下end时间(二者最大值)然后这个interval不需要放入ret
    // Comparator
    private List<Interval> mergeBySort(List<Interval> intervals){
        // sort
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });
        // 其实不用Comparator接口的写法直接对intervals进行排序也可以，只不过比较复杂才能达到nlogn的复杂度，双重循环会导致n^2。

        // result
        List<Interval> ret = new ArrayList<Interval>();

        // init, add first interval in
        ret.add(intervals.get(0));

        // traverse intervals, if i.start <= retLastI.end, then overlap, update retLastI.end(we dont need update retLastI.start because we sorted the intervals) and next loop; if i.start > retLastI.end, then no overlap between these two, then we should add the i to ret and use it as comparision target in next loop
        for(Interval i: intervals){
            // ret中最后一个interval是比较目标
            Interval retLastI = ret.get(ret.size() - 1);
            // overlap
            if(i.start <= retLastI.end){
                retLastI.end = Math.max(retLastI.end, i.end);
            }else{
                ret.add(i);
            }
        }

        return ret;

    }
}
package com.myleetcode.array.non_overlapping_intervals;

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
    public int eraseOverlapIntervals(Interval[] intervals) {

        // special case
        if(intervals == null || intervals.length == 0 || intervals.length == 1){
            return 0;
        }

        return eraseOverlapIntervalsBySort(intervals);
        // return eraseOverlapIntervalsBySortII(intervals);
    }

    // 另一种思路是：最少需要删除多少，就是找到 最多的不overlap的interval，然后剩下的interval就是要被删除的
    // 关于如何找最多的非overlap：排序，按照end升序，遍历，维护一个合法的end，如果start >= end，那么就是一个非overlap，那么更新end。否则end维持自己因为当前这个interval我们不要.
    // 关于为何按照end升序后，遇到overlap就不要当前的interval，这个本质属于贪心，就是，我们已经按照end升序了，那么当前的interval的end肯定比和他overlap的前面的interval的end大，那么我们既然顺序遍历，那么后面的next interval如果跟前面的pre interval有overlap即next.start < pre.end，那么肯定和当前的cur interval有overlap因为cur.end > pre.end，所以最好的策略一定是选择不要当前的interval
    private int eraseOverlapIntervalsBySortII(Interval[] intervals){
        //https://leetcode.com/problems/non-overlapping-intervals/discuss/91713/Java%3A-Least-is-Most
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.end - i2.end;
            }
        });

        int count = 1;// 第一个interval自己肯定非overlap
        int end = intervals[0].end;
        for(int i = 1; i < intervals.length; i++){
            if(intervals[i].start >= end){
                // 非overlap，更新end，count++
                count++;
                end = intervals[i].end;
            }
        }

        return intervals.length - count;

    }

    // intuition: start sort ascending。
    //然后,维护一个interval作为pre，这个interval是在遍历intervals到i的过程中：1 如果有overlap，那么如果intervals[pre].end > intervals[i].end,说明intervals[pre]完全覆盖了intervals[i],那么我们继续删掉intervals[pre]因为他比较长，这是一种“贪心算法？”，就是我们既然要删掉两个其中之一，既然要求我们删除的数量最少，那么我们就删除那个最长的,所以我们更新pre为i，否则就维持pre不变;2 如果没有overlap，更新pre为i
    private int eraseOverlapIntervalsBySort(Interval[] intervals){
        // Solution: Approach #4 Using Greedy Approach based on starting points [Accepted]
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        int pre = 0;
        int count = 0;
        for(int i = 1; i < intervals.length; i++){
            if(intervals[i].start < intervals[pre].end){
                // overlap,  count++;
                count++;

                // 让pre保证自己的end是overlap中的二者中的小者，这样才可以实现最少overlap
                if(intervals[pre].end > intervals[i].end){
                    pre = i;
                }
            }else{
                // 没有overlap，直接更新pre为i
                pre = i;
            }
        }

        return count;
    }
}

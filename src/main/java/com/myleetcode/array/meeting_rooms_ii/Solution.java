package com.myleetcode.array.meeting_rooms_ii;

import com.myleetcode.utils.interval.Interval;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

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
    public int minMeetingRooms(Interval[] intervals) {
        //https://leetcode.com/problems/meeting-rooms-ii/discuss/68030/Java-greedy-algorithm-with-priority-queue

        // special case
        if(intervals == null){
            return 0;
        }
        if(intervals.length == 0 || intervals.length == 1){
            return intervals.length;
        }

        return minMeetingRoomsByPriorityQueue(intervals);
    }

    // 正确的思路1：用priority queue
    // 首先把intervals[]按照start升序排列好,然后开始考虑遍历.
    // 用一个priority queue，这个queue的排序方式是根据interval的end，结束越早的越靠前。这样一来，我们遍历整个intervals，每次如果queue为空，那么把当前的intervals[i]存入queue;如果不为空，每次都从queue中取出最前面的元素，也就是结束最早的interval，然后和当前的intervals[i]比较，如果intervals[i].start < interval.end,说明 结束最早的会议还没结束就有一个新的开始了，(且由于我们前面排序好了intervals才开始遍历intervals的，所以也不会出现intervals[i].end<interval.start导致的错误认为没有overlap的两个会议overlap了的情况)，就需要一个新的会议室，所以把intervals[i]存入queue，同时记得把pop出来的interval再塞回queue。如果不小于，则说明不用新开会议室，因为前面有会议结束了我们可以用前面的会议室，那么我们把当前会议存入queue，不用管pop出来的那个已经结束的会议.
    // TC: 排序O(nlogn), 遍历O(n), overall O(nlogn)
    // SC: queue额外空间 O(n)
    private int minMeetingRoomsByPriorityQueue(Interval[] intervals){
        // 排序start，升序
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        // 用一个会自动把结束时间最早会议放在最前面的queue来记录overlap的会议，也就是会议室数量
        PriorityQueue<Interval> pq = new PriorityQueue<Interval>(new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.end - i2.end;// 升序按照end，这样queue中最靠前的是结束最早的
            }
        });

        pq.add(intervals[0]);//初始状态，存入第一节课。注意这里已经存入了第一个interval，所以遍历的时候一定不要再从第一个开始了，因为queue是可以多次存入同一个元素的，如果从第一个开始，那么就会导致pq的size最后多1。当然也可以在for循环从0开始，然后当i==0的时候存入pq的第一个元素，这样也很符合逻辑，如同下面注释掉的那个i==0的if.
        for(int i = 1; i < intervals.length; i++){
            // if(i == 0){// 初始状态，把第一个会议放入queue
            //     pq.add(intervals[i]);
            //     continue;
            // }
            // 开门看看结束最早的会议结束了没
            Interval earliestEndI = pq.poll();
            if(intervals[i].start < earliestEndI.end){
                // 没结束，那么去新开个房
                pq.add(intervals[i]);
                // 把人家的门关上
                pq.add(earliestEndI);
            }else{
                // 结束了，占他的房子就好了,已经pop出来的那个会议不用管了反正结束了
                pq.add(intervals[i]);
            }
        }

        return pq.size();
    }


    // 错误的:
    // 基本跟题目1相同，需要的会议室的数目，overlap的数量
    // 所以还是找overlap，找的时候，如果基于Meeting Room I,那么就是排序后遍历的时候每次发现一次overlap就计数一次即可，但是这个是错误的，这样会漏掉一些overlaps，考虑这个{[0,5],[1,2],[3,4]}.
    //所以我们需要维护一个最大的end，也就是维护一个假的interval的end，这个interval是所有产生了overlap的interval组成的最长interval。每次这个假的interval不和当前interval冲突时，更新假interval为当前interval。考虑这个[[0, 30],[5, 10],[15, 20]], 如果按这个思路需要3个房子，其实只需要两个.
   /* private int minMeetingRoomsBySort(Interval[] intervals){
        // Sort, intervals is array, so use Arraus.sort, ascending
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        // traverse to record overlaps
        int count = 0;
        int end = intervals[0].end;// fake interval end
        for(int i = 0; i < intervals.length; i++){
            Interval iCur = intervals[i];
            if(iCur.start < end){
                // 有冲突，让end维持最长，多分配一个会议室
                end = Math.max(end, iCur.end);
                count++;
            }else{
                // 无冲突，更新end为新的interval的end，不需要再分配额外会议室，count不变
                end = iCur.end;
            }
        }

        return count;
    }
    */


    // 错误的:
  /*  private int minMeetingRoomsBySort(Interval[] intervals){
        // Sort, intervals is array, so use Arraus.sort, ascending
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        // traverse to record overlaps
        int count = 1;
        for(int i = 0; i < intervals.length - 1; i++){
            Interval iCur = intervals[i];
            Interval iNext = intervals[i + 1];
            if(iNext.start < iCur.end){
                count++;
            }
        }

        return count;
    }
    */
}

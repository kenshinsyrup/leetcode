package com.myleetcode.line_sweep.meeting_rooms_ii;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // return minMeetingRoomsByPriorityQueue(intervals);

        return minMeetingRoomsByTreeMap(intervals);
    }

    // sol1: TreeMap
    /* same sol for
56. Merge Intervals
57. Insert Interval (hard)
252. Meeting Rooms
253. Meeting Rooms II
729. My Calendar I
731. My Calendar II
732. My Calendar III (hard)
    */
    /* Why TreeMap?
The most intuitive data structure for timeline would be sorted array, but the time spot we have could be very sparse, so we can use sorted map to simulate the time line to save space.
*/
    /*
1 Load all intervals to the TreeMap, where keys are intervals' start/end boundaries, and values accumulate the changes at that point in time. Meet start plus 1, meet end minus 1. then we could use the values of treeMap to know how many events are ongoing. Put into the treeMap is normally sort all the time
2 Traverse the TreeMap (in other words, sweep the timeline). If a new interval starts, increase the counter (k value) by 1, and the counter decreases by 1, if an interval has finished.
3 Calcalulate the number of the active ongoing intervals.
    */
    /*
Why we could put start and end time into treeMap to sort?

A meeting is defined by its start and end times. However, for this specific algorithm, we need to treat the start and end times individually. This might not make sense right away because a meeting is defined by its start and end times. If we separate the two and treat them individually, then the identity of a meeting goes away. This is fine because:

When we encounter an ending event, that means that some meeting that started earlier has ended now. We are not really concerned with which meeting has ended. All we need is that some meeting ended thus making a room available.
    */
    private int minMeetingRoomsByTreeMap(int[][] intervals){
        if(intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0){
            return 0;
        }

        // TreeMap inner implementation is BST
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        for(int[] itv: intervals){
            treeMap.put(itv[0], treeMap.getOrDefault(itv[0], 0) + 1);
            treeMap.put(itv[1], treeMap.getOrDefault(itv[1], 0) - 1);
        }

        int maxRooms = 0;
        int count = 0;
        for(int val: treeMap.values()){
            count += val; // count means, on this time point, how many events are ongoing

            maxRooms = Math.max(count, maxRooms); // we need the max count
        }

        return maxRooms;
    }

    // 正确的思路1：用priority queue
    // 首先把intervals[]按照start升序排列好,然后开始考虑遍历.
    // 用一个priority queue，这个queue的排序方式是根据interval的end，结束越早的越靠前。这样一来，我们遍历整个intervals，每次如果queue为空，那么把当前的intervals[i]存入queue;如果不为空，每次都从queue中取出最前面的元素，也就是结束最早的interval，然后和当前的intervals[i]比较，如果intervals[i].start < interval.end,说明 结束最早的会议还没结束就有一个新的开始了，(且由于我们前面排序好了intervals才开始遍历intervals的，所以也不会出现intervals[i].end<interval.start导致的错误认为没有overlap的两个会议overlap了的情况)，就需要一个新的会议室，所以把intervals[i]存入queue，同时记得把pop出来的interval再塞回queue。如果不小于，则说明不用新开会议室，因为前面有会议结束了我们可以用前面的会议室，那么我们把当前会议存入queue，不用管pop出来的那个已经结束的会议.
    // TC: 排序O(nlogn), 遍历O(n), overall O(nlogn)
    // SC: queue额外空间 O(n)
    private int minMeetingRoomsByPriorityQueue(int[][] intervals){
        //https://leetcode.com/problems/meeting-rooms-ii/discuss/68030/Java-greedy-algorithm-with-priority-queue

        // special case
        if(intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0){
            return 0;
        }

        // 排序所有会议intervals，按照start时间升序，用于遍历
        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare(int[] i1, int[] i2){
                return i1[0] - i2[0];
            }
        });

        // 用一个会自动把结束时间最早会议放在最前面的queue来记录需要的会议室数量，这样我们只需要在遍历intervals时，将itv的开始时间和pq的第一个元素的结束时间比较，不重叠则pq最前面的会议结束了itv才开始，所以将pq最前面的会议室删掉，然后增加itv入队；重叠则直接入队。最后pq的size就是需要的所有的会议室数量
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] i1, int[] i2){
                return i1[1] - i2[1];// 升序按照end，这样queue中最靠前的是结束最早的
            }
        });

        pq.offer(intervals[0]);//初始状态，存入第一节课
        for(int i = 1; i < intervals.length; i++){
            int[] itv = intervals[i];

            // 开门看看结束最早的会议结束了没, 如果结束了，从队列移除这个结束的会议
            if(itv[0] >= pq.peek()[1]){
                pq.poll();
            }

            // 无论最先结束的会议有没有结束，新的会议必须占用一个会议室
            pq.offer(itv);
        }

        return pq.size();
    }
}

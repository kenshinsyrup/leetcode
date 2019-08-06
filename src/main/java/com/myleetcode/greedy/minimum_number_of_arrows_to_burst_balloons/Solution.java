package com.myleetcode.greedy.minimum_number_of_arrows_to_burst_balloons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
    public int findMinArrowShots(int[][] points) {
        // return findMinArrowShotsBySort(points); // optimization
        return findMinArrowShotsByMerge(points);
    }

    // same like Merge Interval but to make it min
    // TC: O(N)
    // SC: O(N)
    private int findMinArrowShotsByMerge(int[][] points){
        // special case
        if(points == null || points.length == 0){
            return 0;
        }

        // Sort, start ascending
        Arrays.sort(points, new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                return p1[0] - p2[0];
            }
        });

        int len = points.length;
        List<int[]> ret = new ArrayList<>();
        ret.add(points[0]);
        for(int i = 0; i < len; i++){
            int[] point = points[i];

            int[] retEndPoint = ret.get(ret.size() - 1);
            if(retEndPoint[1] >= point[0]){
                retEndPoint[0] = Math.min(retEndPoint[0], point[0]);
                retEndPoint[1] = Math.min(retEndPoint[1], point[1]);
            }else{
                ret.add(point);
            }
        }

        return ret.size();
    }

    // Optimization: reduce the SC
    // 画一下图还是很有帮助的对这道题
    // intuition: first we sort all points by start, then we check for overlaps
    // if find overlaps between cur and next, we know we could use one arrow to burst these two, and then, we need to remember the min value of ends, and keep looking for next overlap with this end.
    // if no overlap foud with cur end, meaning we should shoot another arrow, so we update the new end and back to our starting.
    // same like Merge Interval but to make it min
    // TC: O(N)
    // SC: O(1)
    private int findMinArrowShotsBySort(int[][] points){
        // special case
        if(points == null || points.length == 0){
            return 0;
        }

        // Sort, start ascending
        Arrays.sort(points, new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                return p1[0] - p2[0];
            }
        });

        int len = points.length;
        // initial count of arrow, 我们从第二个point开始找overlap的，所以第一个point需要一支箭
        int count = 1;
        // initial end
        int end = points[0][1];
        for(int i = 0; i < len - 1; i++){
            int[] pointCur = points[i];
            int[] pointNext = points[i + 1];
            // overlap
            if(end >= pointNext[0]){
                // keep track of min end
                end = Math.min(end, pointNext[1]);
            }else{
                // need another arrow, new end
                count++;
                end = pointNext[1];
            }
        }

        return count;
    }
}
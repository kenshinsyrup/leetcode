package com.myleetcode.greedy.minimum_number_of_arrows_to_burst_balloons;

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int findMinArrowShots(int[][] points) {

        // special case
        if(points == null || points.length == 0){
            return 0;
        }

        return findMinArrowShotsBySort(points);

    }

    private int findMinArrowShotsBySort(int[][] points){
        // 画一下图还是很有帮助的对这道题
        // intuition: first we sort all points by start, then we check for overlaps
        // if find overlaps between cur and next, we know we could use one arrow to burst these two, and then, we need to remember the min value of ends, and keep looking for next overlap with this end.
        // if no overlap foud with cur end, meaning we should shoot another arrow, so we update the new end and back to our starting.

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
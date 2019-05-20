package com.myleetcode.math.line_reflection;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean isReflected(int[][] points) {
        return isReflectedBySet(points);
    }

    // TC: O(N)
    // SC: O(N)
    // 题意: https://leetcode.com/problems/line-reflection/discuss/202760/Bad-problem-description-come-and-read-what-it-really-means.
    // Still the same consideration, when we need to hash num, we could build a String to hash
    private boolean isReflectedBySet(int[][] points){
        if(points == null || points.length == 0 || points[0] == null || points[0].length == 0){
            return true;
        }

        Set<String> pointSet = new HashSet<>();

        // this is the tricky part, we need to find the reflect line, if ther's such line, then every point has a reflected point with this line, but we dont know how to find this line. Here we could consider, since every point has reflected point, then the point with Max xVal must has the reflected point with the Min xVal(reflected points have same yVal), so we could find the maxXVla and minXVal then use them to find the line
        int maxXVal = Integer.MIN_VALUE;
        int minXVal = Integer.MAX_VALUE;
        for(int[] point: points){
            maxXVal = Math.max(maxXVal, point[0]);
            minXVal = Math.min(minXVal, point[0]);

            // put all point to Set, because we only consider duplicates points once
            pointSet.add(point[0] + "_" + point[1]);
        }

        // then we find the line, here wo dont use Division, we use Plus, we know since points mirrored with the line, then reflected points should has the same sum xVal
        long reflectedSumX = (long)(maxXVal + minXVal);

        // find if all points has reflected points, since we have put all point[0]_point[1] to Set, and flected points has same point[1], and should has same xVal sum reflectedSumX, then we could check for every point, do the Set has reflected point ie the (reflectedSumX - point[0])_point[1]
        for(int[] point: points){
            if(!pointSet.contains((reflectedSumX - (long)(point[0])) + "_" + point[1])){
                return false;
            }
        }

        return true;
    }
}

package com.myleetcode.map.number_of_boomerangs;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int numberOfBoomerangs(int[][] points) {
        return numberOfBoomerangsByMap(points);
    }

    /*
    Map and Math
    https://leetcode.com/problems/number-of-boomerangs/discuss/92861/Clean-java-solution%3A-O(n2)-166ms

    For every i, we capture the number of points equidistant from i. Now for this i, we have to calculate all possible permutations of (j,k) from these equidistant points.

    Total number of permutations of size 2 from n different points is nP2 = n!/(n-2)! = n * (n-1). hope this helps.


    */
    private int numberOfBoomerangsByMap(int[][] points) {
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0) {
            return 0;
        }

        int ret = 0;
        int len = points.length;
        Map<Integer, Integer> distanceNumMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int[] pointI = points[i];
            distanceNumMap = new HashMap<>();

            for (int j = 0; j < len; j++) {
                if (i == j) {
                    continue;
                }

                int[] pointJ = points[j];
                int distance = getDistance(pointI, pointJ);
                distanceNumMap.put(distance, distanceNumMap.getOrDefault(distance, 0) + 1);
            }

            for (int distance : distanceNumMap.keySet()) {
                int num = distanceNumMap.get(distance); // For pointI, there're num number points having same distance to it. So total number of boomerangs is the permutation of choosing two from the num ie num*(num-1).

                ret += num * (num - 1);
            }
        }

        return ret;
    }

    private int getDistance(int[] point1, int[] point2) {
        int dx = point1[0] - point2[0];
        int dy = point1[1] - point2[1];

        return dx * dx + dy * dy;
    }

}
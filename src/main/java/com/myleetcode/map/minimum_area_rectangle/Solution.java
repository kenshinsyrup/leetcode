package com.myleetcode.map.minimum_area_rectangle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solution {
    public int minAreaRect(int[][] points) {
        return minAreaRectBySet(points);
    }

    /*
    Check every rectangle
    https://leetcode.com/problems/minimum-area-rectangle/discuss/192759/Simple-AF-JAVA-solution-with-explanation-O(n2)

    TC: O(N^2)
    SC: O(N^2)
    */
    /*
    Know how to write the customed equals() and hashCode() method.
    */
    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            return (this.x == ((Point) obj).x && this.y == ((Point) obj).y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }
    }

    private int minAreaRectBySet(int[][] coordinates) {
        if (coordinates == null || coordinates.length == 0 || coordinates[0] == null || coordinates[0].length == 0) {
            return 0;
        }

        Set<Point> pointSet = new HashSet<>();
        for (int[] coor : coordinates) {
            Point point = new Point(coor[0], coor[1]);
            pointSet.add(point);
        }

        int minArea = Integer.MAX_VALUE;
        int size = coordinates.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int[] iCoor = coordinates[i];
                int[] jCoor = coordinates[j];
                // Given 2 points
                Point iPoint = new Point(iCoor[0], iCoor[1]);
                Point jPoint = new Point(jCoor[0], jCoor[1]);
                // Deduced 2 points
                Point point3 = new Point(iCoor[0], jCoor[1]);
                Point point4 = new Point(jCoor[0], iCoor[1]);
                // Omit duplicates.
                if (iPoint.equals(jPoint) || iPoint.equals(point3) || iPoint.equals(point4) || jPoint.equals(point3) || jPoint.equals(point4) || point3.equals(point4)) {
                    continue;
                }

                // Check whether the deduced 2 points really exist in pointSet.
                if (pointSet.contains(point3) && pointSet.contains(point4)) {
                    int area = Math.abs(iCoor[0] - jCoor[0]) * Math.abs(iCoor[1] - jCoor[1]);
                    minArea = Math.min(minArea, area);
                }
            }
        }

        if (minArea == Integer.MAX_VALUE) {
            return 0;
        }
        return minArea;
    }

}

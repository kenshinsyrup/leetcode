package com.myleetcode.bad_problems.generate_random_point_in_a_circle;

public class Solution {

    /*
    https://leetcode.com/problems/generate-random-point-in-a-circle/discuss/154037/Polar-Coordinates-10-lines
    */

    double radius, x_center, y_center;

    public Solution(double radius, double x_center, double y_center) {
        this.radius = radius;
        this.x_center = x_center;
        this.y_center = y_center;
    }

    public double[] randPoint() {
        double len = Math.sqrt(Math.random()) * radius;
        double degree = Math.random() * 2 * Math.PI;

        double x = x_center + len * Math.cos(degree);
        double y = y_center + len * Math.sin(degree);

        return new double[]{x, y};
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(radius, x_center, y_center);
 * double[] param_1 = obj.randPoint();
 */

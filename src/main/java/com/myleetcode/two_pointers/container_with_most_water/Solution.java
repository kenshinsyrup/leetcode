package com.myleetcode.two_pointers.container_with_most_water;

public class Solution {
    public int maxArea(int[] height) {
        // return maxAreaByBruteForce(height);
        return maxAreaByTwoPointers(height);
    }

    /*
    Greedy with Two Pointers could solve this in O(N)
    Let's say we have a area (right - left) * Math.min(height[left], height[right])
    If we want to get another area that is potencial larger than this area, since we're decrease the (right - left), and we only move one boundary(left move to right or rigth move to left), we must try to increase the height, so we move the shorter one so that we're possible to find a larger area with the higher boundary fixed.
    And, when height[left] == height[right] , left++ and right-- are both right. it will not miss the maximun value

    https://leetcode.com/problems/container-with-most-water/discuss/6100/Simple-and-clear-proofexplanation

    TC: O(N)
    SC: O(1)
    */
    private int maxAreaByTwoPointers(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int len = heights.length;
        int left = 0;
        int right = len - 1;
        int maxArea = 0;
        while (left <= right) {
            int area = (right - left) * Math.min(heights[left], heights[right]);
            maxArea = Math.max(maxArea, area);

            if (heights[left] > heights[right]) {
                right--;
            } else {
                left++;
            }
        }

        return maxArea;
    }

    /*
    M is the largest value in height, N is height legnth.
    TC: O(M * N)
    SC: O(1)
    */
    private int maxAreaByBruteForce(int[] height) {
        if (height == null || height.length <= 1) {
            return 0;
        }

        int length = height.length;
        int maxArea = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int out = height[j];
                int in = height[i];
                int area = (j - i) * Math.min(in, out);
                maxArea = Math.max(area, maxArea);
            }
        }

        return maxArea;
    }
}
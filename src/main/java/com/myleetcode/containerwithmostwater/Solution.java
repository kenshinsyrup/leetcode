package com.myleetcode.containerwithmostwater;

public class Solution {
    // 一个很直观的解法，就是双重遍历数组的一个变形。
    public int maxArea(int[] height) {
        int length = height.length;
        int maxArea = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int out = height[j];
                int in = height[i];
                int h = j - i;
                int area = h * Math.min(in, out);
                maxArea = Math.max(area, maxArea);
            }
        }
        return maxArea;
    }

    // 以下来自leetcode：https://leetcode.com/problems/container-with-most-water/
    // Approach 1: Brute Force。就是最直观的解法。
    public int maxAreaBruteForce(int[] height) {
        int maxarea = 0;
        for (int i = 0; i < height.length; i++)
            for (int j = i + 1; j < height.length; j++)
                maxarea = Math.max(maxarea, Math.min(height[i], height[j]) * (j - i));
        return maxarea;
    }

    // Approach 2: Two Pointer Approach
    // 原理：根据算法，双指针从首尾开始，在计算得到的面积之后，如果想得到比当前更大的，那么只能寄希望于矮的竖线向中间移动，因为只要开始移动，那么底边就变小了
    // 如果高度再变小，那么不可能得到比当前面积更大的区域。
    public int maxAreaTwoPointer(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
            if (height[right] > height[left]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
package com.myleetcode.array.height_checker;

import java.util.Arrays;

public class Solution {
    public int heightChecker(int[] heights) {
        // return heightCheckerBySort(heights);
        return heightCheckerByCountingSort(heights);
    }

    /*
    Counting Sort solution, since we know the given height is from 1 to 100 which is pretty small range, and the count sort algo is good at this.
    https://www.youtube.com/watch?v=OKd534EWcdk
    https://leetcode.com/problems/height-checker/discuss/300472/Java-0ms-O(n)-solution-no-need-to-sort/325693

    TC: O(N)
    SC: O(N)
    */
    private int heightCheckerByCountingSort(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        // Counting sort.
        int[] sortedHeights = countingSort(heights, 100);

        // Height checker.
        int res = 0;
        for (int i = 0; i < heights.length; i++) {
            if (sortedHeights[i] != heights[i]) {
                res++;
            }
        }

        return res;

    }

    // K is the largest possible element, in this problem is 100.
    private int[] countingSort(int[] heights, int K) {
        int len = heights.length;
        int[] countingArr = new int[K + 1]; // countingArr array len is largest posible integer + 1.
        // 1. Get each height's occurence number.
        for (int i = 0; i < len; i++) {
            countingArr[heights[i]]++;
        }
        // 2. Add up accumulatively.
        for (int i = 1; i < countingArr.length; i++) {
            countingArr[i] += countingArr[i - 1];
        }
        // 3. Find starting index for each height.
        for (int i = countingArr.length - 1; i >= 1; i--) {
            countingArr[i] = countingArr[i - 1];
        }
        // 4. Build output array, sorted.
        int[] output = new int[len];
        for (int i = 0; i < len; i++) {
            int height = heights[i];
            int idx = countingArr[height];
            output[idx] = height;

            // !!! this idx is usded for this height, so increase one.
            countingArr[height]++;
        }

        return output;
    }

    /*
    Poor description question.
    Acutally, it wants number of students who are out of order.

    TC: O(N*logN)
    SC: O(N)
    */
    private int heightCheckerBySort(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int len = heights.length;
        int[] heightsCopy = new int[len];
        for (int i = 0; i < len; i++) {
            heightsCopy[i] = heights[i];
        }
        Arrays.sort(heightsCopy);

        int res = 0;
        for (int i = 0; i < len; i++) {
            if (heights[i] != heightsCopy[i]) {
                res++;
            }
        }

        return res;
    }

}

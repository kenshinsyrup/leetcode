package com.myleetcode.divide_and_conquer.largest_rectangle_in_histogram;

class Solution {
    public int largestRectangleArea(int[] heights) {
        // return largestRectangleAreaByTwoPointers(heights);//wrong
        // return largestRectangleAreaByTraverse(heights);
        return largestRectangleAreaByDivideAndConquer(heights);
    }

    // divide and conquer approach
    private int largestRectangleAreaByDivideAndConquer(int[] heights){
        if(heights == null || heights.length == 0){
            return 0;
        }

        return maxArea(heights, 0, heights.length - 1);
    }

    // TC: O(N*log(N) ~ N^2)
    // SC: O(log(N) ~ N)
    /*
    from the solution
This approach relies on the observation that the rectangle with maximum area will be the maximum of:

The widest possible rectangle with height equal to the height of the shortest bar.

The largest rectangle confined to the left of the shortest bar(subproblem).

The largest rectangle confined to the right of the shortest bar(subproblem).
    */
    private int maxArea(int[]heights, int start, int end){
        if(start > end){
            return 0;
        }

        // find the min height index
        int minIdx = start;
        for(int i = start; i <= end; i++){
            if(heights[i] < heights[minIdx]){
                minIdx = i;
            }
        }

        int areaWithMinIdx = heights[minIdx] * (end - start + 1);

        int leftArea = maxArea(heights, minIdx + 1, end);
        int rightArea = maxArea(heights, start, minIdx - 1);

        return Math.max(areaWithMinIdx, Math.max(leftArea, rightArea));
    }

    // TC: O(N^2)
    // SC: O(1)
    // from left to right, try every possible pair, use a minHeight to keep the lowest
    private int largestRectangleAreaByTraverse(int[] heights){
        if(heights == null || heights.length == 0){
            return 0;
        }

        int maxarea = 0;
        for (int i = 0; i < heights.length; i++) {
            int minheight = Integer.MAX_VALUE;

            for (int j = i; j < heights.length; j++) {
                minheight = Math.min(minheight, heights[j]);
                maxarea = Math.max(maxarea, minheight * (j - i + 1));
            }
        }
        return maxarea;
    }

    // this is wrong because if start equals to end, which pointer to move?for example input is [4,2,0,3,2,4,3,4], according to this algo the output will be 4 but answer is 10
    // TC: O(N^2)
    // SC: O(1)
    // intuition: first we need a helper to give us the lowest height through [Start:End]. then we need move the min one of Start and End to find a bigger one that it to try to find the new Start or End, then update the Low and area
    private int largestRectangleAreaByTwoPointers(int[] heights){
        if(heights == null || heights.length == 0){
            return 0;
        }

        int start = 0;
        int end = heights.length - 1;
        int maxArea = 0;
        while(start <= end){
            int low = lowest(heights, start, end);
            int area = low * (end - start + 1);

            maxArea = Math.max(maxArea, area);

            if(heights[start] < heights[end]){
                start++;
            }else{
                end--;
            }
        }

        return maxArea;

    }

    private int lowest(int[] heights, int start, int end){
        int low = Integer.MAX_VALUE;
        while(start <= end){
            low = Math.min(low, heights[start]);
            low = Math.min(low, heights[end]);

            start++;
            end--;
        }
        return low;
    }

}

package com.myleetcode.monotonic_stack.largest_rectangle_in_histogram;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int largestRectangleArea(int[] heights) {
        // return largestRectangleAreaByTwoPointers(heights);//wrong
        // return largestRectangleAreaByTraverse(heights);
        // return largestRectangleAreaByDivideAndConquer(heights);

        return largestRectangleAreaByStack(heights);
    }

    // TC: O(N)
    // SC: O(N)
    // this is a classic Monotonic Stack problem, about Monotonic Stack(单调栈):https://www.geeksforgeeks.org/largest-rectangle-under-histogram/
    // this is the best explanation: https://stackoverflow.com/a/35931960/6491717
    private int largestRectangleAreaByStack(int[] heights){
        if(heights == null || heights.length == 0){
            return 0;
        }

        int maxArea = 0;

        Deque<Integer> heightMonotonicStack = new ArrayDeque<>();
        int len = heights.length;
        int idx = 0;
        // build the monotonic stack, during this, caculate the area of popped bars
        while(idx < len){
            // all bars are only in and out of stack once
            if(heightMonotonicStack.isEmpty() || heights[heightMonotonicStack.peek()] <= heights[idx]){
                heightMonotonicStack.push(idx);

                idx++;
            }else{
                // keep in mind, here we are using the same heights[idx], since the bar on stack top is higher than it, so the rectangle is use heights[idx] as height, use the diff betweetn their idx as width
                // keep in mind we dont update var idx here, because we have to push heights[idx]'s idx, ie current idx, to stack, so we are trying to find a lower bar in stack, we pop out all higher bars, until find one or stack is empty, then push the idx in(in next while loop idx will goto if, not this else), this way we keep all bar in/out stack just once and keep the stack is increasing monotonic

                int curHeight = heights[heightMonotonicStack.pop()];// use the stack top bar as the smallest bar to find the biggest rectangle, so height is it,

                int rightBoundary = idx; // !!! current idx is first smaller than top at its right
                int leftBoundary = heightMonotonicStack.isEmpty() ? -1 : heightMonotonicStack.peek(); // !!! we popped out the bar compared with heights[idx], so the bar in stack is the left one of it, ie the first one smaller than it at its left. Here we must keep in mind we use this boundary range[-1:len], this way we could make the whole Histogram in the range. so if now stack is empty, the non-exist -1 pos i s the bar smaller than this bar at its left

                // the first smaller one at left, and the first smaller one at right, between them is the rectangle thait the curHeight is the lowest bar
                int width = rightBoundary - leftBoundary - 1;

                maxArea = Math.max(maxArea, (curHeight * width));
            }
        }

        // caculate the area of bars in stack, these bars are increasing monotonic
        while(!heightMonotonicStack.isEmpty()){
            int curHeight = heights[heightMonotonicStack.pop()];
            int rightBoundary = len; // so now the non-exist len pos is the first bar smaller than current bar, ie its right boundary
            int leftBoundary = heightMonotonicStack.isEmpty() ? -1 : heightMonotonicStack.peek();
            int width = rightBoundary - leftBoundary - 1;

            maxArea = Math.max(maxArea, (curHeight * width));
        }

        return maxArea;
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

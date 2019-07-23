package com.myleetcode.monotonic_deque.largest_rectangle_in_histogram;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int largestRectangleArea(int[] heights) {
        // return largestRectangleAreaByTwoPointers(heights);//wrong
        // return largestRectangleAreaByTraverse(heights);
        // return largestRectangleAreaByDivideAndConquer(heights); // good

        return largestRectangleAreaByStack(heights);// good
    }

    // TC: O(N)
    // SC: O(N)
    // this is a classic Monotonic Stack or Monotonic Deque problem, about Monotonic Stack(单调栈):https://www.geeksforgeeks.org/largest-rectangle-under-histogram/
    // this is the best explanation: https://stackoverflow.com/a/35931960/6491717
    private int largestRectangleAreaByStack(int[] heights){
        if(heights == null || heights.length == 0){
            return 0;
        }

        int maxArea = 0;

        // monoDeque stores the leftP which is the possible window left limit
        // !!! be careful: for a area
        // the leftIdx is the curIdx's previous idx+1, ie, after pollLast we get the curidx, then we could get the area's left start idx by monoDeque.peekLast()+1
        // the rightIdx is the i's previous idx, ie the i-1
        Deque<Integer> monoDeque = new ArrayDeque<>();
        int len = heights.length;
        for(int i = 0; i < len; i++){
            while(!monoDeque.isEmpty() && heights[monoDeque.peekLast()] > heights[i]){
                int rightIdx = i - 1;
                int curIdx = monoDeque.pollLast();
                int leftIdx = 0;
                if(!monoDeque.isEmpty()){
                    leftIdx = monoDeque.peekLast() + 1;
                }

                maxArea = Math.max(maxArea, (rightIdx - leftIdx + 1) * (heights[curIdx]));
            }

            monoDeque.offer(i);
        }

        // !!! be careful, at last, we should check the monoDeque to get the remaining area.
        int rightIdx = len - 1;
        while(!monoDeque.isEmpty()){
            int curIdx = monoDeque.pollLast();
            int leftIdx = 0;
            if(!monoDeque.isEmpty()){
                leftIdx = monoDeque.peekLast() + 1;
            }

            maxArea = Math.max(maxArea, (rightIdx - leftIdx + 1) * (heights[curIdx]));
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

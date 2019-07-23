package com.myleetcode.monotonic_deque.sliding_window_maximum;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // return maxSlidingWindowBySlidingWindow(nums, k);
        return maxSlidingWindowBySlidingWindowAndDeque(nums, k);
    }

    // TC: O(2*N) => O(N)
    // SC: O(N - K + 1) => O(N) if K is relatively very small compared with N. this is the space for the result array.
    // https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation
    // to reach a linear time, we need a Deque to help us
    // the basic idea is, we keep a srted Deque, every time we want to put a new elem to it, ie the window move forward, we first remove all tail elem less than current elem from the queue. then we offer the current elem.
    // how this queue is sorted? because if we keep do this from the very start, when queue is empty, we could make sure all elems in queue are sorted. then, when window size reached k and keep moving, we just need to: remove the first elem from Deque; remove all tails less than nums[i]; offer i; the first elem is the current window's max.
    // BTW our Deque stores the num's index for convience to check if the head elem are out of current window range, while head is small than window left we keep remove them. AND because we need to operates head and tail so Deque is good.
    private int[] maxSlidingWindowBySlidingWindowAndDeque(int[] nums, int k){
        if(nums == null || nums.length == 0 || nums.length < k){
            return new int[0];
        }

        int numsLen = nums.length;
        int[] ret = new int[numsLen - k + 1];

        // store idx descending by value
        Deque<Integer> sortedNumIdxDeque = new ArrayDeque<>();

        for(int i = 0; i < numsLen; i++){
            // check, descending order, by this way, we know the max of deque is head of deque of the i. so after ofer i, we could get the max of deque by peek
            while(!sortedNumIdxDeque.isEmpty() && nums[sortedNumIdxDeque.peekLast()] < nums[i]){
                sortedNumIdxDeque.pollLast();
            }
            // offer the current num into deque, now the deque is done
            sortedNumIdxDeque.offer(i);

            // check window width with deque leftP and i
            while(!sortedNumIdxDeque.isEmpty() && i - sortedNumIdxDeque.peek() + 1 > k){
                sortedNumIdxDeque.poll();
            }

            // get max of cur window, peek the head of queue
            if(!sortedNumIdxDeque.isEmpty() && i >= k - 1){ // !!! i must >= k-1
                ret[i - (k - 1)] = nums[sortedNumIdxDeque.peek()];
            }
        }

        return ret;

    }


    //intuition: fix size window, we use two pointers to form the window, use a variable called max to caculate current sum of window. when we move forward one position, then caculate max value in current window. this cost TC: (N * K)
    // how to linear time?
    private int[] maxSlidingWindowBySlidingWindow(int[] nums, int k){
        if(nums == null || nums.length == 0 || nums.length < k){
            return new int[0];
        }

        int numsLen = nums.length;
        int max = Integer.MIN_VALUE;
        int leftP = 0;
        int[] ret = new int[numsLen - k + 1];

        for(int i = 0; i < numsLen; i++){
            if(i + 1>= k){
                max = Integer.MIN_VALUE;
                for(int j = leftP; j <= i; j++){
                    max = Math.max(max, nums[j]);
                }
                ret[i + 1 - k] = max;
                leftP++;
            }
        }

        return ret;

    }
}

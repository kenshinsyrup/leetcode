package com.myleetcode.sliding_window.sliding_window_maximum;

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

        // store idx, num of idx guarantee head >= next >=...>= tail
        Deque<Integer> sortedNumIdxDeque = new ArrayDeque<>();

        for(int i = 0; i < numsLen; i++){
            // check head, while first elem in queue is already out of range we remove it. other wise we just do the remove tail and offer stuff. This is why we keep index in queue.
            while(!sortedNumIdxDeque.isEmpty() && sortedNumIdxDeque.peek() < i - (k - 1)){
                sortedNumIdxDeque.poll();
            }

            // check tail, if smaller than nums[i] then it's no possible be max elem, remove it
            while(!sortedNumIdxDeque.isEmpty() && nums[i] > nums[sortedNumIdxDeque.peekLast()]){
                sortedNumIdxDeque.pollLast();
            }

            // offer the current num as new tail
            sortedNumIdxDeque.offer(i);

            // get max, peek the head of queue
            if(i >= k - 1){
                ret[i - (k - 1)] = nums[sortedNumIdxDeque.peek()];// not poll() because we dont remove it here, we already shrink the window when we first get into this loop
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

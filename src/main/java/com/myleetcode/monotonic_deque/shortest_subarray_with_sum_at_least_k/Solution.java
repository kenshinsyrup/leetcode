package com.myleetcode.monotonic_deque.shortest_subarray_with_sum_at_least_k;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int shortestSubarray(int[] A, int K) {
        // return shortestSubarrayByPresumAndBS(A, K);// wrong
        // return shortestSubarrayBySlidingWindow(A, K);// wrong

        return shortestSubarrayByMonotonicDeque(A, K);
    }

    // Monotonic Deque
    // 为什么用这个方法: https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/189039/Detailed-intuition-behind-Deque-solution
    // https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/143966/A-Java-Deque-Solution-Time-O(n)-Space-O(n)
    // https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/204290/Monotonic-Queue-Summary
    /*
What does the Deque store :
The deque stores the possible values of the start pointer. Unlike the sliding window, values of the start variable will not necessarily be contiguous.

Why is it increasing :
So that when we move the start pointer and we violate the condition, we are sure we will violate it if we keep taking the other values from the Deque. In other words, if the sum of the subarray from start=first value in the deque to end is smaller than target, then the sum of the subarray from start=second value in the deque to end is necessarily smaller than target.
So because the Deque is increasing (B[d[0]] <= B[d[1]]), we have B[i] - B[d[0]] >= B[i] - B[d[1]], which means the sum of the subarray starting from d[0] is greater than the sum of the sub array starting from d[1].
    */
    private int shortestSubarrayByMonotonicDeque(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return -1;
        }

        // get presums
        int len = nums.length;
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = nums[i - 1] + presums[i - 1];
        }

        int minLen = Integer.MAX_VALUE;
        Deque<Integer> monoDeque = new ArrayDeque<>();
        // monoDeque.offer(presums[0]);
        for(int i = 0; i < presums.length; i++){
            // 1 check
            while(!monoDeque.isEmpty() && (presums[i] - presums[monoDeque.peek()] >= target)){
                minLen = Math.min(minLen, i - monoDeque.poll());
            }

            // 2 keep monoDeque ascending
            while(!monoDeque.isEmpty() && (presums[monoDeque.peekLast()] >= presums[i])){
                monoDeque.pollLast();
            }
            monoDeque.offer(i);
        }

        return minLen == Integer.MAX_VALUE? -1: minLen;
    }

    // with negative num in nums array, this solution is incorrect
    // Sliding Window
    // TC: O(N)
    // SC: O(1)
    private int shortestSubarrayBySlidingWindow(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return -1;
        }

        int sum = 0;
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];

            // shink window
            while(sum >= target){
                System.out.println("left: "+left+" i: "+i+" "+sum);
                minLen = Math.min(minLen, i - left + 1);

                sum -= nums[left];
                left += 1;
            }
        }

        return minLen == Integer.MAX_VALUE? -1: minLen;
    }

    // but this is incorrect, because the 209's nums are all positive so presums is implictly ordered ascendingly. But this problem contains negative in A ie the nums, so directly solve this problem like this is incorrect.
    // intuition:
    // similar with 209. Minimum Size Subarray Sum
    // TC: O(N * logN)
    // SC: O(N)
    private int shortestSubarrayByPresumAndBS(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return -1;
        }

        // get presums
        int len = nums.length;
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = nums[i - 1] + presums[i - 1];
        }

        // get the shortest subarray
        // presums[i] - presums[j] >= k, ie presums[i] >= presums[j] + k
        int minLen = Integer.MAX_VALUE;
        for(int j = 0; j < presums.length; j++){
            int curTarget = presums[j] + target;
            int i = binarysearch(presums, j, presums.length - 1, curTarget);
            if(j < i){
                minLen = Math.min(minLen, i - j);
            }
        }

        return minLen == Integer.MAX_VALUE? -1: minLen;
    }

    private int binarysearch(int[] presums, int start, int end, int target){
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(presums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        // if no valid idx
        if(start == presums.length){
            return -1;
        }

        return start;

    }
}

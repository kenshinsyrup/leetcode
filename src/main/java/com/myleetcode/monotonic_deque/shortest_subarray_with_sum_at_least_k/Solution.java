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
    /*
    We can rephrase this as a problem about the prefix sums of A. Let P[i] = A[0] + A[1] + ... + A[i-1]. We want the smallest y-x such that y > x and P[y] - P[x] >= K.
Motivated by that equation, let opt(y) be the largest x such that P[x] <= P[y] - K. We need two key observations:
1. If x1 < x2 and P[x2] <= P[x1], then opt(y) can never be x1, as if P[x1] <= P[y] - K, then P[x2] <= P[x1] <= P[y] - K but y - x2 is smaller. This implies that our candidates x for opt(y) will have increasing values of P[x].
2. If opt(y1) = x, then we do not need to consider this x again. For if we find some y2 > y1 with opt(y2) = x, then it represents an answer of y2 - x which is worse (larger) than y1 - x.
    */
    private int shortestSubarrayByMonotonicDeque(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return -1;
        }

        // get presums
        int len = nums.length;
        int[] presums = new int[len + 1]; // presums[i] stores the sum through nums[0:i-1]
        for(int i = 1; i < presums.length; i++){
            presums[i] = nums[i - 1] + presums[i - 1];
        }

        // monoDeque stores the possible leftP, since we nee the shortes subarray, so we keep presums[i] ascending where i is a possible leftP.
        Deque<Integer> monoDeque = new ArrayDeque<>();
        int minLen = Integer.MAX_VALUE;
        for(int i = 0; i < presums.length; i++){
            // 1.1 keep monoDeque store possible start idx(window left ptr) ascending
            while(!monoDeque.isEmpty() && (presums[monoDeque.peekLast()] >= presums[i])){
                monoDeque.pollLast();
            }

            // 2 get the subarray sum and try update the minLen
            while(!monoDeque.isEmpty() && (presums[i] - presums[monoDeque.peek()] >= target)){
                minLen = Math.min(minLen, i - monoDeque.poll());
            }

            // 1.2 offer current presums[i] in
            monoDeque.offer(i);
        }

        return minLen == Integer.MAX_VALUE? -1: minLen;
    }


    // below two codes snippets are both wrong, they only works in 209 where no negative in nums

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

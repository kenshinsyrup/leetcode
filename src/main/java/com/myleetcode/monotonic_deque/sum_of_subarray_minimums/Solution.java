package com.myleetcode.monotonic_deque.sum_of_subarray_minimums;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int sumSubarrayMins(int[] A) {
        return sumSubarrayMinsByMonoDeque(A);
    }

    // intuition: Monotonic Deque
    // https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
    /*
Denote by pleDistArr[i] the distance between element A[i] and its PLE.
Denote by nleMonoDeque[i] the distance between element A[i] and its NLE.

The then the # of subarray of range [pleIdxi, i] and end with i is pleDistArr[i], the # of subarray of range [i, nleIdxi] and start with i is nleDistArr[i], so the total num of subarray in range [pleIdxi, nleIdxi] is pleDistArr[i]*nleMonoDeque[i]

So, for i, the sum of the min of the subarrays is nums[i]*pleDistArr[i]*nleMonoDeque[i])

So, final ans is: sum(A[i]*pleDistArr[i]*nleMonoDeque[i])
    */
    // TC: O(N)
    // SC: O(N)
    private int sumSubarrayMinsByMonoDeque(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        Deque<Integer> pleMonoDeque = new ArrayDeque<>();
        Deque<Integer> nleMonoDeque = new ArrayDeque<>();

        int len = nums.length;
        int[] pleArr = new int[len];
        int[] nleArr = new int[len];
        // init pleArr and nleArr
        for(int i = 0; i < len; i++) {
            pleArr[i] = -1;
            nleArr[i] = len;
        }
        // get the real pleArr and nleArr
        for(int i = 0; i < len; i++){
            // Previous Less Element mono deque
            while(!pleMonoDeque.isEmpty() && nums[pleMonoDeque.peekLast()] > nums[i]){
                pleMonoDeque.pollLast();
            }
            if(!pleMonoDeque.isEmpty()){
                pleArr[i] = pleMonoDeque.peekLast();
            }

            pleMonoDeque.offer(i);

            // Next Less Element mono deque
            while(!nleMonoDeque.isEmpty() && nums[nleMonoDeque.peekLast()] > nums[i]){
                nleArr[nleMonoDeque.peekLast()] = i;

                nleMonoDeque.pollLast();
            }

            nleMonoDeque.offer(i);
        }

        int mod = (int)Math.pow(10, 9) + 7;
        int ans = 0;
        for(int i = 0; i < len; i++){
            int pleDist = (i - 1) - pleArr[i] + 1;
            int nleDist = nleArr[i] - (i + 1) + 1;

            ans = (ans + nums[i] * pleDist * nleDist) % mod;
        }

        return ans;
    }

}

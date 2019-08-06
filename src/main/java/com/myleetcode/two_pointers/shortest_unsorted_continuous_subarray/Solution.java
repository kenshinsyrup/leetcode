package com.myleetcode.two_pointers.shortest_unsorted_continuous_subarray;

import java.util.Arrays;

class Solution {
    public int findUnsortedSubarray(int[] nums) {
        // return findUnsortedSubarrayBySort(nums);
        return findUnsortedSubarrayByTwoPass(nums);
    }

    // optimization but not intuitive:
    // https://leetcode.com/problems/shortest-unsorted-continuous-subarray/discuss/103066/Ideas-behind-the-O(n)-two-pass-and-one-pass-solutions
    // TC: O(N)
    // SC: O(1)
    private int findUnsortedSubarrayByTwoPass(int[] nums){
        if(nums == null || nums.length <= 1){
            return 0;
        }

        int len = nums.length;
        int leftP = 0;
        int rightP = len - 1;

        // leftP move
        while(leftP < len - 1 && nums[leftP] <= nums[leftP+1]){
            leftP++;
        }
        if(leftP >= len - 1){
            return 0;
        }

        // rightP move
        while(rightP > 0 && nums[rightP - 1] <= nums[rightP]){
            rightP--;
        }
        if(rightP <= 0){
            return 0;
        }

        // get the max and min in [leftP:rightP], and then correct the two ptr.
        // [leftP+1: rightP-1] is not sorted part
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = leftP; i <= rightP; i++){
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        while(leftP >= 0 && nums[leftP] > min){
            leftP--;
        }
        while(rightP <= len - 1 && nums[rightP] < max){
            rightP++;
        }

        return (rightP - 1) - (leftP + 1) + 1;

    }

    // intuiton: sort, then find the first not correct idx from start and end
    // TC: O(N * logN)
    // SC: O(N)
    private int findUnsortedSubarrayBySort(int[] nums){
        if(nums == null || nums.length <= 1){
            return 0;
        }

        int len = nums.length;
        int[] numsClone = nums.clone();
        Arrays.sort(numsClone);

        // init left to max and right to min
        int left = len - 1;
        int right = 0;
        for(int i = 0; i < len; i++){
            if(numsClone[i] != nums[i]){
                left = Math.min(left, i); // find min left
                right = Math.max(right, i); // find max right
            }
        }

        return right > left? right - left + 1: 0;

    }
}

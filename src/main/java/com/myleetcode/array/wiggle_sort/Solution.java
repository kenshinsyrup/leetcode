package com.myleetcode.array.wiggle_sort;

import java.util.Arrays;

class Solution {
    public void wiggleSort(int[] nums) {
        // wiggleSortBySort(nums);
        wiggleSortByOnePass(nums);
    }

    // https://leetcode.com/problems/wiggle-sort/discuss/71693/My-explanations-of-the-best-voted-Algo
    // TC: O(N)
    // SC: O(1)
    private void wiggleSortByOnePass(int[] nums){
        if(nums == null || nums.length == 0){
            return;
        }

        // if idx is even and nums[idx]>nums[idx+1] or idx is odd and nums[idx]<nums[idx+1], swap(proof is above)
        for(int i = 0; i < nums.length - 1; i++){
            if((i % 2 == 0 && nums[i] > nums[i + 1]) || (i % 2 != 0 && nums[i] < nums[i + 1])){
                swap(nums, i, i + 1);
            }
        }

    }

    // intuition: like 324. Wiggle Sort II
    // TC: O(NlogN)
    // SC: O(1)
    // the idea for 324 is we could partition the nums to two parts, then merge. the same as this problem but since it needs to do it in-place and no need whinth O(N) time, we could use the Sort and Swap method.
    private void wiggleSortBySort(int[] nums){
        if(nums == null || nums.length == 0){
            return;
        }

        Arrays.sort(nums);

        int len = nums.length;
        for(int i = 1; i < len - 1; i += 2){
            swap(nums, i, i + 1);
        }

    }

    private void swap(int[] nums, int i, int j){
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

}

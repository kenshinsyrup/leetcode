package com.myleetcode.binary_search.find_minimum_in_rotated_sorted_array_ii;

class Solution {
    public int findMin(int[] nums) {
        return findMinByTraverse(nums);
    }

    // intuition: looks like 153. Find Minimum in Rotated Sorted Array, but has duplicates. if we want to use the BS, we should have a non-duplicates sorted array, so  we could first remove duplicates and then solve it like 153, but... if we remove duplicates, we shoulc traverse the whole nums which cost O(N), then why we just do a traverse and find the nums[i] > nums[i+1] or even find the first i that nums[i] > nums[0]?
    // TC: O(N)
    // SC: O(1)
    private int findMinByTraverse(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        for(int i = 0; i < nums.length - 1; i++){
            if(nums[i] > nums[i + 1]){
                return nums[i + 1];
            }
        }

        return nums[0];
    }

    // worst case TC: O(N)
    // check here: Stop wasting your time. It most likely has to be O(n).
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/discuss/48849/Stop-wasting-your-time.-It-most-likely-has-to-be-O(n).

    // but we still could writ a BS-like solution to sovle this, when the input is not worst case, we could save some time, maybe...
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/discuss/48808/My-pretty-simple-code-to-solve-it
}

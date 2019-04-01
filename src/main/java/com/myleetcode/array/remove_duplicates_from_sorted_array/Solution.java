package com.myleetcode.array.remove_duplicates_from_sorted_array;

class Solution {
    public int removeDuplicates(int[] nums) {
        return removeDuplicatesByTwoPointers(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // intuition: two pointers. slowP points to the first "blank" space that could be populate by a value; fastP to find the valid value and put it into slowP.
    private int removeDuplicatesByTwoPointers(int[] nums){
        // special case
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int realLen = 0;
        int slowP = 0;
        // int fastP = 0;// i is the fastP

        nums[slowP] = nums[0];
        realLen++;
        for(int i = 0; i < len; i++){
            if(nums[slowP] == nums[i]){
                continue;
            }

            slowP++;
            nums[slowP] = nums[i];
            realLen++;
        }

        return realLen;
    }
}

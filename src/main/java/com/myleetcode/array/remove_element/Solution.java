package com.myleetcode.array.remove_element;

class Solution {
    public int removeElement(int[] nums, int val) {
        return removeElementByTwoPointers(nums, val);
    }

    // intuition: two pointers traverse array, fastP to traverse and find no-val value, slowP is the space to be populated
    private int removeElementByTwoPointers(int[] nums, int val){
        // special case
        if(nums == null || nums.length == 0){
            return 0;
        }

        int slowP = 0;
        int realLen = 0;
        int len = nums.length;

        for(int i = 0; i < len; i++){
            if(nums[i] != val){
                nums[slowP] = nums[i];
                slowP++;
                realLen++;
            }
        }

        return realLen;
    }
}

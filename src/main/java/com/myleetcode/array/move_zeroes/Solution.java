package com.myleetcode.array.move_zeroes;

class Solution {
    public void moveZeroes(int[] nums) {
        moveZeroesByTwoPointers(nums);
    }

    /*
    出错点:
    1 思路：想到用两个指针去处理题目，将TC降低到O(N)
    2 思路: 想到我们只要交换0和他后面到第一非0值，如无则结束
    */

    // intuition:
    // first, we need consider the requirement of: in-place; minimize operations. So we need do swap in the given nums array
    // then, how to swap to make 0s in the end and keep the relative order of other elems?
    // The naive thought is, we could traverse the nums, if we met a 0, we swap it with its next elem until reach the end of array, if until we reach the end, we could not find an elem not 0, break the loop, we are all done. This cost O(N^2) TC and O(1) SC.
    // But, we could reduce the TC by Two Pointers. Since the hard part is to keep the relative order of non 0 elem, so we could use a pointer to track 0 and another pointer to track next non 0. Let's say, we have a zeroP from start and a nonZeroP fron start, first, we move them to correct pos, that is, move the zeroP to first 0 elem, then move the nonZeroP from zeroP+1 to find the first non 0 elem, then we swap them. Then cur zeroP points to the swaped non 0 elem, so we move it one step, ie zeroP++.
    // TC: O(N)
    // SC: O(1)
    private void moveZeroesByTwoPointers(int[] nums){
        if(nums == null || nums.length <= 1){
            return;
        }

        int len = nums.length;
        int zeroP = 0;
        int nonZeroP = 0;
        while(zeroP < len && nonZeroP < len){
            // 1 find the next first 0 elem
            while(zeroP < len && nums[zeroP] != 0){
                zeroP++;
            }
            if(zeroP >= len){
                return;
            }

            // 2 find the next first non 0 elem after cur 0
            nonZeroP = zeroP + 1;
            while(nonZeroP < len && nums[nonZeroP] == 0){
                nonZeroP++;
            }
            if(nonZeroP >= len){
                return;
            }

            // 3 swap values: here zeroP points to 0, so we dont need the traditional temp variable to help us
            nums[zeroP] = nums[nonZeroP];
            nums[nonZeroP] = 0;

            // update ptrs
            zeroP++; // now zeroP is not 0, we start from its next
        }
    }


}

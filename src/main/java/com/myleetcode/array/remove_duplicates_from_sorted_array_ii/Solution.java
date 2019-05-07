package com.myleetcode.array.remove_duplicates_from_sorted_array_ii;

class Solution {
    public int removeDuplicates(int[] nums) {
        // return removeDuplicatesByTwoPointers(nums);
        return removeDuplicatesByTwoPointersII(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // 26, 80
    // https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/discuss/28067/O(N)-Time-and-O(1)-Java-Solution-When-Allowed-at-Most-K-times-of-Duplicates
    // this type proble, not use i+1, use i-1
    public int removeDuplicatesByTwoPointersII(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums.length;
        }

        int len = nums.length;
        int slowP = 1;
        int count = 1; // count the repeat num we have processed
        for(int i = 1; i < len; i++){
            if(nums[i] == nums[i - 1]){
                count++; // nums[i] is not new num, count++

                if(count <= 2){ // at most 2, here 2 could be any int k based on specific problem
                    nums[slowP] = nums[i];
                    slowP++;
                }
            }else{
                count = 1; // nums[i] is a new num, reset count to 1

                nums[slowP] = nums[i];

                slowP++;
            }
        }

        return slowP;
    }

    // this one is a little annoying because of the fastP not move one by one step, at last we have to check if it's len-1, not good to remember
    // intuition: similar with 26. Remove Duplicates from Sorted Array
    // we use two pointers: slowP and fastP. slowP points to the first not known elem the elems before it are all non-duplicates; fastP traverse the array, if fastP find a num, then: if the num is same with next, we put these two elems to the slowP and slowP+1 and move fastP to the pointer that not this num; if the num is not same with next, we put the num to slowP.
    private int removeDuplicatesByTwoPointers(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length <= 1){
            return nums.length;
        }

        int len = nums.length;
        int fastP = 0;
        int slowP= 0;
        while(fastP < len - 1){
            if(nums[fastP] == nums[fastP + 1]){
                nums[slowP] = nums[fastP];
                nums[slowP + 1] = nums[fastP];

                // move fastP to exhasust this num
                while(fastP < len - 1 && nums[fastP] == nums[fastP + 1]){
                    fastP++;
                }
                fastP++;

                // move slowP
                slowP += 2;
            }else{
                // put one num
                nums[slowP] = nums[fastP];

                // move
                fastP++;
                slowP++;
            }
        }

        // additional check for len-1, if the len-1 elem is not the same with len-1-1, the fastP will be len-1; otherwise, fastP wil be len
        if(fastP == len - 1){
            nums[slowP] = nums[len - 1];
            slowP++;
        }

        return slowP;
    }
}

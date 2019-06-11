package com.myleetcode.binary_search.find_the_duplicate_number;

class Solution {
    public int findDuplicate(int[] nums) {
        return findDuplicateByBS(nums); // more intuitive
        // return findDuplicateByDetectCycle(nums); // kind of tricky, more normal to sove 142. Linked List Cycle II with this solution
    }

    // here should be aware that the nums's values are [1, nums.lenght-1], and there're one value has at least two or more duplicates

    // BS
    // TC: O(NlogN)
    // SC: O(1)
    // solution: https://leetcode.com/problems/find-the-duplicate-number/discuss/72906/JAVA-Easy-Version-To-UnderStand!!!!!!!!!/75614
    // https://leetcode.com/problems/find-the-duplicate-number/discuss/72844/Two-Solutions-(with-explanation)%3A-O(nlog(n))-and-O(n)-time-O(1)-space-without-changing-the-input-array
    // every time we count a mid idx; then, we traverse the nums array, count the # of num that is smaller than mid value, if # is larger than mid, then the duplicates must be in this range [low, mid]
    private int findDuplicateByBS(int[] nums){
        if(nums == null || nums.length == 1){
            return 0;
        }

        int lowVal = 1;
        int highVal = nums.length - 1;
        while(lowVal < highVal){
            int midVal = lowVal + ((highVal - lowVal) >> 1);

            // count the # of elems that not larger than midVal
            int count = 0;
            for(int num: nums){
                if(num <= midVal){
                    count++;
                }
            }

            // if there are more than midVal num elems that not larger than midVal, then the duplicates must exist in the range [low, mid]
            if(count > midVal){
                highVal = midVal;
            }else{
                lowVal = midVal + 1;
            }
        }

        return lowVal;
    }

    // solution 2: detect cycle with Floyd's Tortoise and Hare Algorithm
    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/find-the-duplicate-number/discuss/72846/My-easy-understood-solution-with-O(n)-time-and-O(1)-space-without-modifying-the-array.-With-clear-explanation./75491
    // https://leetcode.com/problems/find-the-duplicate-number/discuss/72846/My-easy-understood-solution-with-O(n)-time-and-O(1)-space-without-modifying-the-array.-With-clear-explanation.
    private int findDuplicateByDetectCycle(int[] nums){
        if(nums == null || nums.length == 1){
            return 0;
        }

        // find the meet point
        int slowP = nums[0];
        int fastP = nums[nums[0]];
        while(slowP != fastP){
            slowP = nums[slowP];
            fastP = nums[nums[fastP]];
        }

        // find the entry of cycle, that's the duplicates
        fastP = 0;
        while(slowP != fastP){
            slowP = nums[slowP];
            fastP = nums[fastP];
        }

        return slowP;
    }

}

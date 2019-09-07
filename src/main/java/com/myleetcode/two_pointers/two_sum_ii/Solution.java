package com.myleetcode.two_pointers.two_sum_ii;

class Solution {
    public int[] twoSum(int[] numbers, int target) {
        return twoSumByTwoPointers(numbers, target);
    }

    // intuition:
    // Two Pointers
    // TC: O(N)
    // SC: O(1)
    private int[] twoSumByTwoPointers(int[] numbers, int target){
        if(numbers == null || numbers.length == 0){
            return new int[0];
        }

        int len = numbers.length;
        int leftP = 0;
        int rightP = len - 1;
        while(leftP < rightP){
            // sum use long to avoid overflow
            long sum = numbers[leftP] + numbers[rightP];

            if(sum == target){
                return new int[]{leftP + 1, rightP + 1};
            }else if(sum < target){
                leftP++;
            }else{
                rightP--;
            }
        }

        // since there's must exactly one solution and could not use same elem twice, so we must have return in while loop, here we just return int[0] in case
        return new int[0];
    }
}
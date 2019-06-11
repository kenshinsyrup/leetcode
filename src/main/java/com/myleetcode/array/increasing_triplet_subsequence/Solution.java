package com.myleetcode.array.increasing_triplet_subsequence;

class Solution {
    public boolean increasingTriplet(int[] nums) {
        return increasingTripletByPatienceSorting(nums);
    }

    // actually this is a special case of problem 300. Longest Increasing Subsequence. so definitly we could use DP to get the LIS and check if the length larger than 3 or not. BUT, it will be O(N^2) TC which is even slower than Sort and Scan solution.

    // solution: with Patience Sorting
    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/increasing-triplet-subsequence/discuss/79023/Just-a-simplified-version-of-patient-sort.
    // http://wordaligned.org/articles/patience-sort
    private boolean increasingTripletByPatienceSorting(int[] nums){
        if(nums == null || nums.length <= 2){
            return false;
        }

        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;
        // greedy patience sorting
        for(int num: nums){
            if(num <= min){
                min = num;
            }else if(num <= secondMin){
                secondMin = num;
            }else{
                return true;
            }
        }

        return false;
    }
}

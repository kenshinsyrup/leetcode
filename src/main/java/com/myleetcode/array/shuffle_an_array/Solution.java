package com.myleetcode.array.shuffle_an_array;

import java.util.Random;

public class Solution {

    int[] originalNums; // keep the original nums
    int[] nums; // for shuffle

    Random random; // random

    public Solution(int[] nums) {
        if(nums == null){
            return;
        }

        // please be aware of the four !!! marks, we always use the originalNums to keep a new copy of array, and the this.nums always get the array that we are going to operating

        // !!! this.nums kee the input nums
        this.nums = nums;

        // !!! this.originalNums get a new copy of the input nums
        this.originalNums = nums.clone();

        this.random = new Random();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        // !!! this.nums get the originalNums
        this.nums = this.originalNums;

        // !!! this.originalNums get a new copy of current originalNums
        this.originalNums = this.originalNums.clone();

        return this.nums;
    }

    // solutionsection: Fisher-Yates Algorithm
    // https://leetcode.com/problems/shuffle-an-array/discuss/247470/Optimized-Accepted-Java-Solution
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int len = this.nums.length;

        for(int i = 0; i < len; i++){
            // a idx between i and len-1
            int idx = this.random.nextInt(len - i) + i;

            // swap
            swap(i, idx);
        }

        return this.nums;
    }

    private void swap(int i, int j){
        int temp = this.nums[i];
        this.nums[i] = this.nums[j];
        this.nums[j] = temp;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
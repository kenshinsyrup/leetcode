package com.myleetcode.twosum;
import java.util.Arrays;

/*
Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
*/
public class TwoSum {
    public static void main(String args[]) {
        System.out.println("Hello TwoSum");
        int[] nums = { 3, 7, 11, 15 };
        int target = 10;
        Solution twoSumSolution = new Solution();
        int[] ans = twoSumSolution.twoSum(nums, target);
        System.out.println(Arrays.toString(ans));

        System.out.println("***************");
        int[] ansHashMap = twoSumSolution.twoSumHashMap(nums, target);
        System.out.println(Arrays.toString(ansHashMap));

        System.out.println("***************");
        nums = new int[] { 5, 5 };
        ansHashMap = twoSumSolution.twoSumHashMap(nums, target);
        System.out.println(Arrays.toString(ansHashMap));

    }
}

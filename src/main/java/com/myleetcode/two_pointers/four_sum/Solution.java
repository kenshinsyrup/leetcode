package com.myleetcode.two_pointers.four_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Solution {
    // 以下来自leetcode:https://leetcode.com/problems/4sum/discuss/8547/7ms-java-code-win-over-100
    // 基本思路就是降维，4sum变3sum再变2sum
    // 分类属于HashTable，但是使用HashTable的方法都过于复杂了
    public List<List<Integer>> fourSum(int[] nums, int target) {
        return fourSumByThreeSum(nums, target);
    }

    // TC: O(N*logN + N^3) => O(N^3)
    // SC: O(1)
    private List<List<Integer>> fourSumByThreeSum(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<>();

        if(nums == null || nums.length < 4) {
            return ret;
        }

        // 排序nums
        Arrays.sort(nums);

        int len = nums.length;
        for(int i = 0; i < len; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // three sum
            threeSum(nums, nums[i], target - nums[i], i + 1, ret);
        }

        return ret;
    }

    // TC: O(N^2)
    void threeSum(int[] nums, int firstElem, int target, int left, List<List<Integer>> ret) {
        int len = nums.length;
        if(left  > len - 1 - 2) {
            return;
        }

        for(int i = left; i < len; i++) {
            if(i > left && nums[i] == nums[i - 1]) {
                continue;
            }

            // two sum
            twoSum(nums, firstElem, nums[i], target - nums[i], i + 1, ret);
        }
    }

    // TC: O(N)
    void twoSum(int[] nums, int firstElem, int secondElem, int target, int left, List<List<Integer>> ret) {
        int len = nums.length;
        if(left > len - 1 - 1) {
            return;
        }

        int i = left;
        int j = len - 1;
        while(i < j) {
            if(nums[i] + nums[j] == target){
                ret.add(Arrays.asList(firstElem, secondElem, nums[i], nums[j]));
                i++;
                j--;

                while(i < j && nums[i] == nums[i - 1]) {
                    i++;
                }

                while(i < j && nums[j] == nums[j + 1]) {
                    j--;
                }
            }else if(nums[i] + nums[j] < target) {
                i++;
            }else {
                j--;
            }
        }
    }

}
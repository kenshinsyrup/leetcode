package com.myleetcode.two_pointers.three_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        return threeSumByTwoSum(nums);
    }

    /*
    出错点:
    1 审题: 输出要求的数组,元素为num组成的数组，不是idx组成的数组
    2 思路: 双指针two sum问题的解决
    */

    // TC: O(N*logN + O(N^2)) => O(N^2)
    // SC: O(1)
    private List<List<Integer>> threeSumByTwoSum(int[] nums){
        List<List<Integer>> ret = new ArrayList<>();

        // special case
        if (nums == null || nums.length < 3){
            return ret;
        }

        // must sort first, this is the soul guys
        Arrays.sort(nums);

        for (int i = 0; i + 2 < nums.length; i++) {
            // skip same with nums[i] elem
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            twoSum(nums, nums[i], i + 1, nums.length - 1, ret);
        }

        return ret;
    }

    // two sum
    // TC: O(N)
    private void twoSum(int[] nums,int a, int leftP, int rightP, List<List<Integer>> ret){
        int target = -a;
        while (leftP < rightP) {
            if (nums[leftP] + nums[rightP] == target) {
                ret.add(Arrays.asList(a, nums[leftP], nums[rightP]));
                leftP++;
                rightP--;

                // find next possible elem: skip same with nums[leftP] elem
                while (leftP < rightP && nums[leftP] == nums[leftP - 1]){
                    leftP++;
                }

                // find next possible elem: skip same with nums[rightP] elem
                while (leftP < rightP && nums[rightP] == nums[rightP + 1]){
                    rightP--;
                }
            } else if (nums[leftP] + nums[rightP] < target) {
                leftP++;
            } else {
                rightP--;
            }
        }
    }

}
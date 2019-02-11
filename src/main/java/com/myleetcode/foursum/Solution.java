package com.myleetcode.foursum;

import java.util.Arrays;

public class Solution {

    // 以下来自leetcode:https://leetcode.com/problems/4sum/discuss/8547/7ms-java-code-win-over-100
    // 基本思路就是降维，4sum变3sum再变2sum
    // 分类属于HashTable，但是使用HashTable的方法都过于复杂了
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<>();

        int len = nums.length;
        if (nums == null || len < 4) {
            return ret;
        }

        // 排序nums
        Arrays.sort(nums);

        // 检查特殊情况
        int max = nums[len - 1];
        if (4 * max < target || 4 * nums[0] > target) {
            return ret;
        }

        int temp = 0;
        for (int i = 0; i < len; i++) {
            temp = nums[i];
            // 在i开始移动之后，如果当前的i对应的值与其前面的值一样，去重
            if (i > 0 && temp == nums[i - 1]) {
                continue;
            }
            if (temp + 3 * max < target) {
                continue;
            }
            if (temp * 4 > target) {
                break;
            }
            if (temp * 4 == target) {
                if (i + 3 < len && nums[i + 3] == temp) {
                    ret.add(Arrays.asList(temp, temp, temp, temp));
                }
                break;
            }

            // three sum
            threeSum(nums, target - nums[i], i + 1, ret, temp);

        }

        return ret;
    }

    void threeSum(int[] nums, int target, int left, List<List<Integer>> ret, int firstElem) {
        int len = nums.length;
        if (left > len - 1 - 2) {
            return;
        }

        int max = nums[len - 1];
        if (3 * nums[left] > target || 3 * nums[len - 1] < target) {
            return;
        }

        int temp = 0;
        // 注意，i从left开始
        for (int i = left; i < len; i++) {
            temp = nums[i];
            // 同样，如果当前i对应的值与前面的值相同，去重
            if (i > left && nums[i - 1] == temp) {
                continue;
            }
            if (temp + 2 * nums[len - 1] < target) {
                continue;
            }
            if (3 * temp > target) {
                break;
            }
            if (3 * temp == target) {
                if (i + 2 < len && nums[i + 2] == temp) {
                    ret.add(Arrays.asList(firstElem, temp, temp, temp));
                }
                break;
            }

            // two sum
            twoSum(nums, target - nums[i], i + 1, ret, firstElem, temp);
        }

    }

    void twoSum(int[] nums, int target, int left, List<List<Integer>> ret, int firstElem, int secondElem) {
        int len = nums.length;
        if (left > len - 1 - 1) {
            return;
        }

        if (2 * nums[left] > target || 2 * nums[len - 1] < target) {
            return;
        }

        // i从left开始，j从len-1开始，双指针相向移动
        int i = left;
        int j = len - 1;
        int sum = 0;
        while (i < j) {
            sum = nums[i] + nums[j];
            if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            } else {
                ret.add(Arrays.asList(firstElem, secondElem, nums[i], nums[j]));
                // 为了去重，检查当前的i对应的值是否与其后面的值相同
                int temp = nums[i];
                while (i < j && temp == nums[i]) {
                    i++;
                }
                // 为了去重，检查当前的j对应的值是否与其前面的值相同
                temp = nums[j];
                while (i < j && temp == nums[j]) {
                    j--;
                }
            }
        }
        return;
    }

}
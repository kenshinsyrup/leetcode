package com.myleetcode.array.twosum;

import java.util.*;

class Solution {
    // 最简单的思路：双指针遍历数组
    // 注意：不要直接在外层for循环中，判断到nums[i] > target的时候直接continue，因为nums可能会有负数
    // Time complexity: O(n)
    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        if (length <= 1) {
            return null;
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }

        return null;
    }

    // 下面的实现来自于Leetcode:https://leetcode.com/problems/two-sum/
    // 最暴力的解法
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    // 使用HashMap降低时间复杂度
    // 注意：使用HashMap的方法有个很tricky的地方：其实题目的输入数组并没有说value是唯一的，所以将nums的value作为HashMap的key的话，
    // 会出现一种情况就是，nums里面相同的value，在HashMap里面存储的时候，后面的value:index对会覆盖前面的
    // Time complexity: O(n)
    public int[] twoSumHashMap(int[] nums, int target) {
        int length = nums.length;
        if (length <= 1) {
            return null;
        }

        Map<Integer, Integer> kv = new HashMap<>();
        for (int i = 0; i < length; i++) {
            kv.put(nums[i], i);
        }
        for (int i = 0; i < length; i++) {
            int complement = target - nums[i];
            // kv中存在nums[i]对应的completement的时候也要注意检查completement对应的index是不是和i相同,不能使用两次同一个nums的value
            if (kv.containsKey(complement) && kv.get(complement) != i) {
                return new int[] { i, kv.get(complement) };
            }
        }

        return null;
    }

    // 基于该题目的要求，实际可以进一步优化HashMap的实现方法
    // 注意：基于上面提到的注意内容。nums中相同的value，在HashMap中保存时，后面的value：index对会覆盖前面的。所以需要保证先检查
    // 当前nums的value对应的complement有没有在kv中作为key出现，然后才把value作为key存入kv。这样，我们也就避免了检查complement在kv中
    // 对应的i是否与nums当前的i相同的问题，因为我们还没存过当前nums的index，value信息
    // Time complexity: O(n)
    public int[] twoSumWithHashMap2(int[] nums, int target) {
        int length = nums.length;
        if (length <= 1) {
            return null;
        }

        Map<Integer, Integer> kv = new HashMap<>();
        for (int i = 0; i < length; i++) {
            int complement = target - nums[i];
            if (kv.containsKey(complement)) {
                return new int[] { i, kv.get(complement) };
            }
            kv.put(nums[i], i);
        }

        return null;
    }

}
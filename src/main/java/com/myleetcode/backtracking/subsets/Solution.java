package com.myleetcode.backtracking.subsets;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        //https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
        // subset类的问题是经典的backtrack问题

        List<List<Integer>> subsets = new ArrayList<List<Integer>>();
        List<Integer> subset = new ArrayList<Integer>();
        // special case
        if(nums == null){
            return subsets;
        }

        subsetsByBacktrack(nums, subset, subsets, 0);

        return subsets;
    }

    private void subsetsByBacktrack(int[] nums, List<Integer> subset, List<List<Integer>> subsets, int cur){
        // backtrack的递归动作是增加一个num到subset，如果一个subset合法，那么加入到subsets
        // 这个函数，每次传入的都是同一个subset的引用，我们要记录到subsets中，要深拷贝，否则backtrack结束subset都一样而且是空
        subsets.add(new ArrayList<Integer>(subset));

        // 从给定cur开始，遍历到nums结尾，每个数字都可以单独加入到subset中组成一个不同到subset
        for(int i = cur; i < nums.length; i++){
            // 加入当前的num，也就是变成了一个新的subset
            subset.add(nums[i]);

            // 用当前的新subset作为基础再次传入，同时遍历的起始index变成i+1，用来递归
            subsetsByBacktrack(nums, subset, subsets, i + 1);

            // 当前subset为基础的递归返回之后，把当前num移除。然后开始循环下一个num
            subset.remove(subset.size() - 1);
        }

    }
}
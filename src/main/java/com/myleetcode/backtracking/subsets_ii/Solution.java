package com.myleetcode.backtracking.subsets_ii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
        // subsets 2与 1的唯一区别是给定nums可能含有duplicates。那么我们想办法去重就可以。最好用的就是排序，然后在遍历的时候检查是否与前一个num相同。

        List<List<Integer>> subsets = new ArrayList<List<Integer>>();
        List<Integer> subset = new ArrayList<Integer>();
        // special case
        if(nums == null){
            return subsets;
        }

        // 排序
        Arrays.sort(nums);

        subsetsByBacktrack(nums, subset, subsets, 0);

        return subsets;
    }

    private void subsetsByBacktrack(int[] nums, List<Integer> subset, List<List<Integer>> subsets, int cur){
        // backtrack的递归动作是增加一个num到subset，如果一个subset合法，那么加入到subsets
        // 这个函数，每次传入的都是一个新的subset，我们要记录到subsets中，要深拷贝，否则backtrack结束subset都一样而且是空
        subsets.add(new ArrayList<Integer>(subset));

        // 从给定cur开始，遍历到nums结尾，每个数字都可以单独加入到subset中组成一个不同到subset
        for(int i = cur; i < nums.length; i++){
            // 去重
            if(i > cur && nums[i] == nums[i - 1]){
                continue;
            }

            // 加入当前的num，也就是变成了一个新的subset
            subset.add(nums[i]);

            // 用当前的新subset作为基础再次传入，同时遍历的起始index变成i+1，用来递归
            subsetsByBacktrack(nums, subset, subsets, i + 1);

            // 当前subset为基础的递归返回之后，把当前num移除。然后开始循环下一个num
            subset.remove(subset.size() - 1);
        }

    }

}
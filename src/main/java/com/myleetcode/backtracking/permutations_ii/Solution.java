package com.myleetcode.backtracking.permutations_ii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        //https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<Integer>();
        boolean[] used = new boolean[nums.length];

        Arrays.sort(nums);//必须sort
        permuteUniqueByBacktracking(nums, ans, temp, used);

        return ans;

    }

    public void permuteUniqueByBacktracking(int[] nums, List<List<Integer>> ans, List<Integer> temp, boolean[] used){
        // 第一种解法：直觉上，最直接的解法是：sort nums，去重；然后就变成Permutations I.
        //第二种解法，因为多了duplicates，所以在排序好对基础上，用额外对一个boolean数组来保存状态来标记用的元素：我们不向temp增加元素有两种情况：1、用过了； 2、当前的和前面的相同且前面的用过了

        if(temp.size() == nums.length){
            ans.add(new ArrayList(temp));
        }else{
            for(int i = 0; i < nums.length; i++){
                if(used[i] || i > 0 && nums[i] == nums[i - 1] && used[i - 1]){
                    continue;
                }
                temp.add(nums[i]);
                used[i] = true;

                permuteUniqueByBacktracking(nums, ans, temp, used);

                temp.remove(temp.size() - 1);
                used[i] = false;
            }
        }
    }
}
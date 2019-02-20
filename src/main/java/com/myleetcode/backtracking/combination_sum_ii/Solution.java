package com.myleetcode.backtracking.combination_sum_ii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
//        https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
        // 跟1基本一样，只不过要跳过重复的元素，那么排序好之后在加入temp之前检查下去重

        List<List<Integer>> ans = new ArrayList<List<Integer>>();

        if(candidates == null || target < 0){
            return ans;
        }

        List<Integer> temp = new ArrayList<Integer>();

        Arrays.sort(candidates);
        combinationSum2ByBacktracking(candidates, ans, temp, target, 0);

        return ans;
    }

    private void combinationSum2ByBacktracking(int[] nums, List<List<Integer>> ans, List<Integer> temp, int remains, int start){
        if(remains < 0 ){
            return;
        }else if(remains == 0){
            ans.add(new ArrayList(temp));
        }else{
            for(int i = start; i < nums.length; i++){
                if(i > start && nums[i] == nums[i - 1]){// 去重从start开始考虑
                    continue;
                }

                temp.add(nums[i]);

                // 这里和1不同，每个num只可以用一次，所以i+1
                combinationSum2ByBacktracking(nums, ans, temp, remains - nums[i], i + 1);

                temp.remove(temp.size() - 1);
            }
        }
    }
}
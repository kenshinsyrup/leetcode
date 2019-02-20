package com.myleetcode.backtracking.permutations;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        // https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // special case
        if(nums == null){
            return ans;
        }

        List<Integer> temp = new ArrayList<Integer>();

        permuteByBacktracking(nums, ans, temp);

        return ans;

    }

    private void permuteByBacktracking(int[] nums, List<List<Integer>> ans, List<Integer> temp){
        // add the temp list to ans if temp has collected all elements
        if(temp.size() == nums.length){
            // ans.add(temp);不要直接add，这个是引用，要deep copy
            ans.add(new ArrayList(temp));
        }else{
            // every backtracking we start from index 0, skip existing elements, at last we will get permutations.
            for(int i = 0; i < nums.length; i++){
                if(temp.contains(nums[i])){
                    continue;
                }
                temp.add(nums[i]);

                // back tracking: 1深入
                permuteByBacktracking(nums, ans, temp);

                // back trakcing: 2回退
                temp.remove(temp.size() - 1);
            }
        }
    }
}
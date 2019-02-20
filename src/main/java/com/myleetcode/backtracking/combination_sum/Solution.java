package com.myleetcode.backtracking.combination_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
//        https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

        List<List<Integer>> ans = new ArrayList<List<Integer>>();

        // special case
        if(candidates == null || target < 0){
            return ans;
        }

        List<Integer> temp = new ArrayList<Integer>();

        Arrays.sort(candidates);

        combinationSumByBacktracking(candidates, ans, temp, target, 0);

        return ans;
    }

    private void combinationSumByBacktracking(int[] candidates, List<List<Integer>> ans, List<Integer> temp, int remains, int start){
        if(remains < 0){
            return;
        }else if(remains == 0){
            ans.add(new ArrayList(temp));
        }else{
            // from start to end, since we could reuse candidate, we dont make start + 1 or i + 1
            for(int i = start; i < candidates.length; i++){
                temp.add(candidates[i]);

                combinationSumByBacktracking(candidates, ans, temp, remains - candidates[i], i);

                temp.remove(temp.size() - 1);
            }
        }
    }
}

package com.myleetcode.backtracking.combinations;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        return combineByBacktracking(n, k);
    }

    /*
    Time complexity : O(K * C(N, K)) , C is the Combination operator, is a number of combinations to build. append / pop (add / removeLast) operations are constant-time ones and the only consuming part here is to append the built combination of length k to the output.

Space complexity : O(C (N k)) to keep all the combinations for an output.
    */
    // intuition: we could use dfs backtracking to get all combinations, just need add a restriction about the k length
    private List<List<Integer>> combineByBacktracking(int n, int k){
        List<List<Integer>> ret = new ArrayList<>();
        if(n < 1 || k > n){
            return ret;
        }

        List<Integer> snippet = new ArrayList<>();
        backtrackingII(n, k, 1, ret, snippet);

        return ret;
    }

    // optimize a small place, TC could be reduced
    // https://github.com/bephrem1/backtobackswe/blob/master/Dynamic%20Programming%2C%20Recursion%2C%20%26%20Backtracking/subsetsOfSizeK.java
    // since we have a restriction in our snippet size, so if there's no enough num left from i to n, so we dont neet to caculate them, for eg, if n is 5, k is 4,and our start is 4 and snippet size is 1, now we still need k-snippet.size() == 3 nums in our snippet, but there's only 5-4+1== 2 num left, so we prune these useless branches.
    private void backtrackingII(int n, int k, int start, List<List<Integer>> ret, List<Integer> snippet){
        if(snippet.size() == k){
            ret.add(new ArrayList(snippet));
            return;
        }

        int stillNeedInSnippet = k - snippet.size();
        for(int i = start; i <= n; i++){
            if(stillNeedInSnippet > n - i + 1){
                break;
            }

            snippet.add(i);
            backtracking(n, k, i + 1, ret, snippet);
            snippet.remove(snippet.size() - 1);
        }
    }

    // original backtracking, 36ms
    private void backtracking(int n, int k, int start, List<List<Integer>> ret, List<Integer> snippet){
        if(snippet.size() == k){
            ret.add(new ArrayList(snippet));
            return;
        }

        for(int i = start; i <= n; i++){
            snippet.add(i);
            backtracking(n, k, i + 1, ret, snippet);
            snippet.remove(snippet.size() - 1);
        }
    }
}

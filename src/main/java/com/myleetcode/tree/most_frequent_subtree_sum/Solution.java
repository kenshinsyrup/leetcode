package com.myleetcode.tree.most_frequent_subtree_sum;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int[] findFrequentTreeSum(TreeNode root) {
        return findFrequentTreeSumByDFS(root);
    }

    /*
    DFS

    TC: O(N)
    SC: O(N)
    */
    private int[] findFrequentTreeSumByDFS(TreeNode root) {
        if (root == null) {
            return new int[0];
        }

        Result ret = new Result();
        Map<Integer, Integer> sumNumMap = new HashMap<>();
        dfs(root, sumNumMap, ret);

        int size = ret.valList.size();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = ret.valList.get(i);
        }
        return arr;
    }

    private int dfs(TreeNode curNode, Map<Integer, Integer> sumNumMap, Result ret) {
        if (curNode == null) {
            return 0;
        }

        int leftSum = dfs(curNode.left, sumNumMap, ret);
        int rightSum = dfs(curNode.right, sumNumMap, ret);

        int curSum = leftSum + rightSum + curNode.val;
        sumNumMap.put(curSum, sumNumMap.getOrDefault(curSum, 0) + 1);

        // Keep the most frequence number.
        int curFreq = sumNumMap.get(curSum);
        if (curFreq == ret.mostFreq) {
            ret.valList.add(curSum);
        } else if (curFreq > ret.mostFreq) {
            ret.mostFreq = curFreq;
            ret.valList = new ArrayList<>();
            ret.valList.add(curSum);
        }

        return curSum;
    }

    class Result {
        int mostFreq;
        List<Integer> valList;

        public Result() {
            this.mostFreq = 0;
            this.valList = new ArrayList<>();
        }
    }
}

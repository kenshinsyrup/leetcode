package com.myleetcode.tree.maximum_product_of_splitted_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
public class Solution {
    long mod = (long) Math.pow(10, 9) + 7;

    public int maxProduct(TreeNode root) {
        // return maxProductByDFS(root);
        return maxProductByDFSII(root);
    }

    /*
    Improve from original DFS solution, use Map store subtree sum to avoid repeat calculation.

    !!! Must do the mod operation when return, if do mod early, like in the dfsII part update the ret.max, then we may get not largest answer.

    TC: O(N)
    SC: O(H)
    */
    private int maxProductByDFSII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Map<TreeNode, Long> nodeSumMap = new HashMap<>();
        long total = getSumII(root, nodeSumMap);

        Result ret = new Result();
        dfsII(root, total, ret, nodeSumMap);

        return (int) (ret.max % mod);

    }

    private void dfsII(TreeNode curNode, long total, Result ret, Map<TreeNode, Long> nodeSumMap) {
        if (curNode == null) {
            return;
        }

        long curSum = nodeSumMap.get(curNode);
        long curProduct = curSum * (total - curSum);
        ret.max = Math.max(ret.max, curProduct);

        dfsII(curNode.left, total, ret, nodeSumMap);
        dfsII(curNode.right, total, ret, nodeSumMap);
    }

    private long getSumII(TreeNode curNode, Map<TreeNode, Long> nodeSumMap) {
        if (curNode == null) {
            return 0;
        }

        long leftSum = getSumII(curNode.left, nodeSumMap);
        long rightSum = getSumII(curNode.right, nodeSumMap);

        long curSum = leftSum + rightSum + curNode.val;
        nodeSumMap.put(curNode, curSum);

        return curSum;
    }

    /*
    TLE
    DFS
    First get whole sum
    For node act as a root of subtree, subtreeSum * (total-subtreeSum) is product of this cut.

    TC: O(N^2)
    SC: O(H)
    */
    private int maxProductByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        long sum = getSum(root);

        Result ret = new Result();
        dfs(root, sum, ret);

        return (int) (ret.max % mod);

    }

    private void dfs(TreeNode curNode, long sum, Result ret) {
        if (curNode == null) {
            return;
        }

        long subtreeSum = getSum(curNode);
        long curProduct = subtreeSum * (sum - subtreeSum);
        ret.max = Math.max(ret.max, curProduct);

        dfs(curNode.left, sum, ret);
        dfs(curNode.right, sum, ret);
    }

    private long getSum(TreeNode curNode) {
        if (curNode == null) {
            return 0;
        }

        return getSum(curNode.left) + getSum(curNode.right) + curNode.val;
    }

    private class Result {
        long max;

        public Result() {
            this.max = 0;
        }
    }
}

package com.myleetcode.tree.maximum_difference_between_node_and_ancestor;

import com.myleetcode.utils.tree_node.TreeNode;

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
    public int maxAncestorDiff(TreeNode root) {
        return maxAncestorDiffByPostorder(root);
        // return maxAncestorDiffByPreorder(root);
    }

    /*
    Postorder
    Similar to LCA problem.
    TC: O(N)
    SC: O(H)
    */
    private int maxAncestorDiffByPostorder(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = postorder(root);
        return ret.maxDiff;
    }

    private Result postorder(TreeNode curNode) {
        // Base case.
        if (curNode == null) {
            return new Result();
        }
        // Leaf.
        if (curNode.left == null && curNode.right == null) {
            Result ret = new Result();
            ret.minVal = curNode.val;
            ret.maxVal = curNode.val;
            return ret;
        }

        // Process subtrees.
        Result leftRet = postorder(curNode.left);
        Result rightRet = postorder(curNode.right);

        // Update ret with current tree.
        int subMinVal = Math.min(leftRet.minVal, rightRet.minVal);
        int subMaxVal = Math.max(leftRet.maxVal, rightRet.maxVal);
        int subMaxDiff = Math.max(leftRet.maxDiff, rightRet.maxDiff);

        Result ret = new Result();
        ret.maxDiff = Math.max(subMaxDiff, Math.max(Math.abs(curNode.val - subMinVal), Math.abs(curNode.val - subMaxVal)));
        ret.minVal = Math.min(curNode.val, subMinVal);
        ret.maxVal = Math.max(curNode.val, subMaxVal);
        return ret;
    }

    private class Result {
        int minVal;
        int maxVal;
        int maxDiff;

        public Result() {
            this.minVal = Integer.MAX_VALUE;
            this.maxVal = Integer.MIN_VALUE;
            this.maxDiff = 0;
        }
    }

    /*
    Preorder
    https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/discuss/274610/JavaC%2B%2BPython-Top-Down
We pass the minimum and maximum values to the children,
At the leaf node, we return max - min through the path from the root to the leaf.

    TC: O(N)
    SC: O(H)
    */
    public int maxAncestorDiffByPreorder(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return preorder(root, root.val, root.val);
    }

    public int preorder(TreeNode curNode, int min, int max) {
        if (curNode == null) {
            return max - min;
        }

        min = Math.min(min, curNode.val);
        max = Math.max(max, curNode.val);

        return Math.max(preorder(curNode.left, min, max), preorder(curNode.right, min, max));
    }
}

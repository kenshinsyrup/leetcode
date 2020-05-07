package com.myleetcode.tree.binary_tree_tilt;

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
    public int findTilt(TreeNode root) {
        return findTiltByDFS(root);
    }

    private int findTiltByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        dfs(root, ret);

        return ret.tiltSum;
    }

    private int dfs(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return 0;
        }

        int leftSum = dfs(curNode.left, ret);
        int rightSum = dfs(curNode.right, ret);

        int curTilt = Math.abs(leftSum - rightSum);
        ret.tiltSum += curTilt;

        return leftSum + rightSum + curNode.val;
    }

    class Result {
        int tiltSum;

        public Result() {
            this.tiltSum = 0;
        }
    }
}

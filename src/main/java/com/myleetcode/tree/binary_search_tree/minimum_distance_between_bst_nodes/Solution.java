package com.myleetcode.tree.binary_search_tree.minimum_distance_between_bst_nodes;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int minDiffInBST(TreeNode root) {
        return getMinimumDifferenceByInorderAndListII(root);
    }

    /*
    Exactly same as the 530. Minimum Absolute Difference in BST

    TC: O(N)
    SC: O(H)
    */
    class Result {
        int minDiff;
        TreeNode prevNode;

        Result() {
            this.minDiff = Integer.MAX_VALUE;
            this.prevNode = null;
        }

    }

    private int getMinimumDifferenceByInorderAndListII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        inordeAndCompare(root, ret);

        return ret.minDiff;

    }

    private void inordeAndCompare(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return;
        }

        inordeAndCompare(curNode.left, ret);

        if (ret.prevNode != null) {
            ret.minDiff = Math.min(ret.minDiff, Math.abs(curNode.val - ret.prevNode.val));
        }
        ret.prevNode = curNode;

        inordeAndCompare(curNode.right, ret);

    }
}
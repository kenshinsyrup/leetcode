package com.myleetcode.tree.binary_search_tree.binary_search_tree_to_greater_sum_tree;

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
    public TreeNode bstToGst(TreeNode root) {
        return bstToGstByDFS(root);
    }

    /*
    DFS

    https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/discuss/286906/Java-3-iterative-and-recursive-codes-w-comments-and-explanation.

    Reverse Inorder Traversal.
    */
    private TreeNode bstToGstByDFS(TreeNode root) {
        if (root == null) {
            return root;
        }

        Arg arg = new Arg();
        reverseInorder(root, arg);

        return root;
    }

    private void reverseInorder(TreeNode curNode, Arg arg) {
        if (curNode == null) {
            return;
        }

        reverseInorder(curNode.right, arg);

        arg.sum += curNode.val;
        curNode.val = arg.sum;

        reverseInorder(curNode.left, arg);
    }

    private class Arg {
        int sum;
    }

}

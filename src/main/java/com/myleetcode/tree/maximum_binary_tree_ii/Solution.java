package com.myleetcode.tree.maximum_binary_tree_ii;

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
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        return insertIntoMaxTreeByRecursion(root, val);
    }

    /*
    Similar with problem insert into BST.

    The "Not So Good" part of this problem is it's based on the "Previous Problem" which is 654. Maximum Binary Tree. This does not mean we need the previous problem, but we should know it to understand this problem description.
    The problem actually want us to do is:
        Condition 1. insert the val to *right subtree*;
        Condition 2 act as new subtree root of a existing subtree and the existing subtree act as *left subtree* of the root.

    TC: O(N)
    SC: O(H)
    */
    private TreeNode insertIntoMaxTreeByRecursion(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        return construct(root, val);
    }

    private TreeNode construct(TreeNode curNode, int val) {
        if (curNode == null) {
            return new TreeNode(val);
        }

        // Current node should become root of left subtree.
        if (curNode.val < val) {
            TreeNode curRoot = new TreeNode(val);
            curRoot.left = curNode;
            return curRoot;
        }

        // Insert to right subtree.
        curNode.right = construct(curNode.right, val);

        return curNode;
    }

}

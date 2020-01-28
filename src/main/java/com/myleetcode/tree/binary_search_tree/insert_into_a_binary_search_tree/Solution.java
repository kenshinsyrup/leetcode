package com.myleetcode.tree.binary_search_tree.insert_into_a_binary_search_tree;

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
public class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        return insertIntoBSTByRecursion(root, val);
    }

    /*

    H is tree height.
    TC: O(H)
    SC: O(H)
    */
    private TreeNode insertIntoBSTByRecursion(TreeNode curNode, int val) {
        // Base case, if current node is null, we make a new node with val and return it.
        if (curNode == null) {
            return new TreeNode(val);
        }

        // Must insert to right subtree.
        if (curNode.val < val) {
            curNode.right = insertIntoBSTByRecursion(curNode.right, val);
        }

        // Must insert to left subtree.
        if (curNode.val > val) {
            curNode.left = insertIntoBSTByRecursion(curNode.left, val);
        }

        // After insert, return current node.
        return curNode;
    }
}
package com.myleetcode.tree.binary_search_tree.search_in_a_binary_search_tree;

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
    public TreeNode searchBST(TreeNode root, int val) {
        return searchBSTByRecursion(root, val);
    }

    /*

    H is tree height.
    TC: O(H)
    SC: O(H)
    */
    private TreeNode searchBSTByRecursion(TreeNode rootNode, int val) {
        if (rootNode == null) {
            return rootNode;
        }

        if (rootNode.val < val) {
            return searchBSTByRecursion(rootNode.right, val);
        }

        if (rootNode.val > val) {
            return searchBSTByRecursion(rootNode.left, val);
        }

        return rootNode;
    }
}

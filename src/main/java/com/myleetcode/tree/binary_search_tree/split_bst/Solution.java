package com.myleetcode.tree.binary_search_tree.split_bst;

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
    public TreeNode[] splitBST(TreeNode root, int V) {
        return splitBSTByRecursion(root, V);
    }

    /*
    https://leetcode.com/problems/split-bst/discuss/159985/Python-DFS-tm
    https://leetcode.com/problems/split-bst/discuss/137797/Binary-Search-Method%3A-Recursion-and-Iterative-best-O(logn)-time-worst-O(n)-time

    TC: O(H)
    SC: O(H)
    */
    private TreeNode[] splitBSTByRecursion(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode[]{null, null};
        }

        if (node.val <= val) {
            TreeNode[] subtreeAns = splitBSTByRecursion(node.right, val);
            TreeNode leftSubRoot = subtreeAns[0];
            TreeNode rightSubRoot = subtreeAns[1];

            node.right = leftSubRoot;
            return new TreeNode[]{node, rightSubRoot};
        } else {
            TreeNode[] subtreeAns = splitBSTByRecursion(node.left, val);
            TreeNode leftSubRoot = subtreeAns[0];
            TreeNode rightSubRoot = subtreeAns[1];

            node.left = rightSubRoot;
            return new TreeNode[]{leftSubRoot, node};
        }
    }
}

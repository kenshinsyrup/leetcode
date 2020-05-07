package com.myleetcode.tree.add_one_row_to_tree;

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
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        return addOneRowByDFS(root, v, d);
    }

    /*
    Similar with problem: Delete/Insert node into BST

    TC: O(N)
    SC: O(H)
    */
    private TreeNode addOneRowByDFS(TreeNode root, int v, int d) {
        if (root == null) {
            return new TreeNode(v);
        }

        // !!! Must process special case.
        if (d == 1) {
            TreeNode newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }

        return dfs(root, v, d, 1);
    }

    private TreeNode dfs(TreeNode curNode, int v, int d, int depth) {
        if (curNode == null) {
            return null;
        }

        // Should insert as child here.
        if (depth + 1 == d) {
            TreeNode oldLeft = curNode.left;
            TreeNode oldRight = curNode.right;

            curNode.left = new TreeNode(v);
            curNode.right = new TreeNode(v);
            curNode.left.left = oldLeft;
            curNode.right.right = oldRight;

            return curNode;
        }

        // Recurse and Update this subtree.
        curNode.left = dfs(curNode.left, v, d, depth + 1);
        curNode.right = dfs(curNode.right, v, d, depth + 1);

        return curNode;
    }
}

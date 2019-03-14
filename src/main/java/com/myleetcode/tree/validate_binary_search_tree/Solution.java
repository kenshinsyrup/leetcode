package com.myleetcode.tree.validate_binary_search_tree;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidBSTByRecursion(root, null, null);
    }

    private boolean isValidBSTByRecursion(TreeNode root, TreeNode leftMost, TreeNode rightMost){
        if(root == null){
            return true;
        }

        if(leftMost != null && root.val <= leftMost.val){
            return false;
        }

        if(rightMost != null && root.val >= rightMost.val){
            return false;
        }

        return isValidBSTByRecursion(root.left, leftMost, root) &&
                isValidBSTByRecursion(root.right, root, rightMost);
    }
}

package com.myleetcode.tree;

import com.myleetcode.utils.tree_node.TreeNode;

public class Solution {
    public boolean isBalanced(TreeNode root) {
        // special case
        if (root == null) {
            return true;
        }

        return isBalancedByRecurse(root);

    }

    private boolean isBalancedByRecurse(TreeNode node) {
        // recurse
        // left is balanced && right is balanced && abs(left.height - right.height)<2. So the essential part is to get tree height. So we use the help function.

        // base case
        if (node == null) {
            return true;
        }

        int leftHeight = getTreeHeight(node.left);
        int rightHeight = getTreeHeight(node.right);

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isBalancedByRecurse(node.left) && isBalancedByRecurse(node.right);

    }

    private int getTreeHeight(TreeNode node) {
        // base case
        if (node == null) {
            return 0;
        }

        int leftHeight = getTreeHeight(node.left);
        int rightHeight = getTreeHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
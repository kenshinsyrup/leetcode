package com.myleetcode.tree.univalued_binary_tree;

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
    public boolean isUnivalTree(TreeNode root) {
        return isUnivalTreeByRecursion(root);
    }

    /*
    TC: O(N)
    SC: O(H)
    */
    private boolean isUnivalTreeByRecursion(TreeNode root) {
        if (root == null) {
            return true;
        }

        return recurse(root);
    }

    private boolean recurse(TreeNode curNode) {
        if (curNode == null) {
            return true;
        }

        // If left has value not same with curNode, false
        if (curNode.left != null && curNode.left.val != curNode.val) {
            return false;
        }
        // If right has value not same with curNode, false
        if (curNode.right != null && curNode.right.val != curNode.val) {
            return false;
        }

        // If left and right has same value with curNode or curNode is leaf, answer depends on subtrees.
        return recurse(curNode.left) && recurse(curNode.right);
    }
}

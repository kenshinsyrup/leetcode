package com.myleetcode.tree.construct_string_from_binary_tree;

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
    public String tree2str(TreeNode t) {
        return tree2StrByRecursion(t);
    }

    /*
    DFS, postorder recursion.

    After get String representation of left and right subtree, we could get current subtree String.

    !!! Be careful about has only right child, should not omit left empty () pair. Consider the difference between two examples in the description.

    TC: O(N)
    SC: O(H)
    */
    private String tree2StrByRecursion(TreeNode root) {
        if (root == null) {
            return "";
        }

        return postorder(root);
    }

    private String postorder(TreeNode curNode) {
        // Base case.
        if (curNode == null) {
            return null;
        }

        // Normal.
        String leftStr = postorder(curNode.left);
        String rightStr = postorder(curNode.right);
        if (leftStr == null && rightStr == null) { // Current is leaf.
            return String.valueOf(curNode.val);
        }
        if (rightStr == null) { // Only has left child, omit right empty () pair.
            return curNode.val + "(" + leftStr + ")";
        }
        if (leftStr == null) { // Only has right child, , should not omit left empty () pair, cause should keep one-to-one mapping relationship.
            return curNode.val + "()" + "(" + rightStr + ")";
        }
        return curNode.val + "(" + leftStr + ")" + "(" + rightStr + ")"; // Has two children.
    }
}
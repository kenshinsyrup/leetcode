package com.myleetcode.tree.smallest_string_starting_from_leaf;

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
    public String smallestFromLeaf(TreeNode root) {
        return smallestFromLeafByDFS(root);
    }

    /*
    !!! Naive postorder is wrong.
    https://leetcode.com/problems/smallest-string-starting-from-leaf/discuss/231117/java-dfs-O(N)

    Keep the current string as suffix to children, important part is if has only one child, should not just do the dfs(left);dfs(right); because if we do so, then if left is null and right is not, then this will give us a path to curNode that means it's treated as a root->leaf path but actually is not.

    */
    private String smallestFromLeafByDFS(TreeNode root) {
        if (root == null) {
            return "";
        }

        return dfs(root, "");
    }

    private String dfs(TreeNode curNode, String suffix) {
        // Base case.
        if (curNode == null) {
            return suffix;
        }

        // Update suffix.
        String curStr = (char) ('a' + curNode.val) + suffix;

        // Leaf.
        if (curNode.left == null && curNode.right == null) {
            return curStr;
        }

        // Only one child.
        if (curNode.left == null) {
            return dfs(curNode.right, curStr);
        }
        if (curNode.right == null) {
            return dfs(curNode.left, curStr);
        }

        // Two child.
        String leftMinStr = dfs(curNode.left, curStr);
        String rightMinStr = dfs(curNode.right, curStr);
        return leftMinStr.compareTo(rightMinStr) <= 0 ? leftMinStr : rightMinStr;
    }
}

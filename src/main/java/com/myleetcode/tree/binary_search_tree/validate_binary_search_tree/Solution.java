package com.myleetcode.tree.binary_search_tree.validate_binary_search_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

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
    public boolean isValidBST(TreeNode root) {
        return isValidBSTByRecursion(root, null, null);
        // return isValidBSTByInorderAndStack(root);
    }

    /*
    intuition:
    Iterative way, inorder traversal with stack
    https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)

    Say N is total # of nodes
    TC: O(N)
    SC: O(H)
    */
    private boolean isValidBSTByInorderAndStack(TreeNode root) {
        if (root == null) {
            return true;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        TreeNode preNode = null;
        TreeNode curNode = root;
        // !!! ||
        while (curNode != null || !nodeStack.isEmpty()) {
            // to the left most, push the nodes on path to stack
            while (curNode != null) {
                nodeStack.push(curNode);
                curNode = curNode.left;
            }

            //pop the leftmost
            curNode = nodeStack.pop();

            // check
            if (preNode != null) {
                if (preNode.val >= curNode.val) {
                    return false;
                }
            }

            // next
            preNode = curNode;
            curNode = curNode.right;
        }

        return true;
    }

    private boolean isValidBSTByRecursion(TreeNode root, TreeNode leftMost, TreeNode rightMost) {
        if (root == null) {
            return true;
        }

        if (leftMost != null && root.val <= leftMost.val) {
            return false;
        }

        if (rightMost != null && root.val >= rightMost.val) {
            return false;
        }

        return isValidBSTByRecursion(root.left, leftMost, root) &&
                isValidBSTByRecursion(root.right, root, rightMost);
    }
}

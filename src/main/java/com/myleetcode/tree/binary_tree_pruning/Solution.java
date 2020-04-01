package com.myleetcode.tree.binary_tree_pruning;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

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
    public TreeNode pruneTree(TreeNode root) {
        return pruneTreeByRecursion(root); // Better.
        // return pruneTreeByIterative(root);
    }


    /*
    Postorder traversal problem.

    TC: O(N)
    SC: O(H)
    */
    private TreeNode pruneTreeByRecursion(TreeNode root) {
        if (root == null) {
            return root;
        }

        return recursePrune(root);
    }

    private TreeNode recursePrune(TreeNode curNode) {
        // Base case.
        if (curNode == null) {
            return null;
        }

        // Left subtree.
        TreeNode left = recursePrune(curNode.left);
        if (left == null) {
            curNode.left = null;
        }

        // Right subtree.
        TreeNode right = recursePrune(curNode.right);
        if (right == null) {
            curNode.right = null;
        }

        // Current node.
        if (curNode.val == 0 && left == null && right == null) {
            return null;
        }
        return curNode;
    }

    /*
    Postorder traversal by Stack, need know.

    TC: O(N)
    SC: O(N)
    */
    private TreeNode pruneTreeByIterative(TreeNode root) {
        if (root == null) {
            return root;
        }

        Set<TreeNode> seenSet = new HashSet<>();
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);
        while (!nodeStack.isEmpty()) {
            TreeNode curNode = nodeStack.pop();

            // First time visit this node, menas its subtree nodt processed
            if (!seenSet.contains(curNode)) {
                nodeStack.push(curNode);

                if (curNode.left != null) {
                    nodeStack.push(curNode.left);
                }
                if (curNode.right != null) {
                    nodeStack.push(curNode.right);
                }

                seenSet.add(curNode);
            } else { // If we have seen curNode, means we have processed its subtree and need process itself.
                // If left subtree is only 0 or is null, prune it.
                if ((curNode.left != null && isLeaf(curNode.left) && curNode.left.val == 0) || curNode.left == null) {
                    curNode.left = null;
                }

                // If right subtree is only 0 or is null, prune it.
                if ((curNode.right != null && isLeaf(curNode.right) && curNode.right.val == 0) || curNode.right == null) {
                    curNode.right = null;
                }
            }
        }

        return root;
    }

    private boolean isLeaf(TreeNode node) {
        if (node == null) {
            return false;
        }

        return node.left == null && node.right == null;
    }


}

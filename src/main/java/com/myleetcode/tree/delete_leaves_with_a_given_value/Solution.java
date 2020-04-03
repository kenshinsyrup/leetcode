package com.myleetcode.tree.delete_leaves_with_a_given_value;

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
class Solution {
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        return removeLeafNodesByRecursion(root, target);
        // return removeLeafNodeByIteration(root, target);
    }

    /*
    TC: O(N)
    SC: O(H)
    */
    private TreeNode removeLeafNodesByRecursion(TreeNode root, int target) {
        if (root == null) {
            return root;
        }

        return postorder(root, target);
    }

    private TreeNode postorder(TreeNode curNode, int target) {
        if (curNode == null) {
            return null;
        }

        TreeNode leftSubTree = postorder(curNode.left, target);
        TreeNode rightSubTree = postorder(curNode.right, target);

        if (leftSubTree == null && rightSubTree == null && curNode.val == target) {
            return null;
        }
        curNode.left = leftSubTree;
        curNode.right = rightSubTree;
        return curNode;
    }

    /*
    TC: O(N)
    SC: O(N)
    */
    private TreeNode removeLeafNodeByIteration(TreeNode root, int target) {
        if (root == null) {
            return root;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Set<TreeNode> seenSet = new HashSet<>();
        nodeStack.push(root);
        while (!nodeStack.isEmpty()) {
            TreeNode curNode = nodeStack.pop();

            if (!seenSet.contains(curNode)) {
                nodeStack.push(curNode);
                seenSet.add(curNode);

                if (curNode.left != null) {
                    nodeStack.push(curNode.left);
                }
                if (curNode.right != null) {
                    nodeStack.push(curNode.right);
                }
            } else {
                // Process curNode's children
                TreeNode leftSubtree = curNode.left;
                if (leftSubtree != null && leftSubtree.left == null && leftSubtree.right == null && leftSubtree.val == target) {
                    curNode.left = null;
                }
                TreeNode rightSubTree = curNode.right;
                if (rightSubTree != null && rightSubTree.left == null && rightSubTree.right == null && rightSubTree.val == target) {
                    curNode.right = null;
                }
            }
        }

        // At last, check the last one node(because in the stack we process every node's children, so at last the root node itself is not processed)
        if (root.left == null && root.right == null && root.val == target) {
            return null;
        }
        return root;
    }
}
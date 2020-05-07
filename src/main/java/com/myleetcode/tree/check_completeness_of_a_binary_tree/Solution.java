package com.myleetcode.tree.check_completeness_of_a_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

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
    public boolean isCompleteTree(TreeNode root) {
        // return isCompleteTreeByBFS(root);
        return isCompleteTreeByBFSII(root);
    }

    /*
    BFSII, actually we should not offer null element into a Queue, as the Oracle Java Doc said: https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html

    https://leetcode.com/problems/check-completeness-of-a-binary-tree/discuss/409836/Simple-Java-Solution%3A-BFS

    Improve the basic BFS solution, use a shouldComplete to record the status that we think the tree should be complete now, after this, if we meet other nodes, means false.

    TC: O(N)
    SC: O(N)
    */
    private boolean isCompleteTreeByBFSII(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean shouldComplete = false;
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode curNode = nodeQueue.poll();

            // !!! Check 1, If only has right child, false.
            if (curNode.left == null && curNode.right != null) {
                return false;
            }
            // !!! Check 2, If already should complete at some previous node, current node has any child should be false.
            if (shouldComplete) {
                if (curNode.left != null || curNode.right != null) {
                    return false;
                }
            }

            // !!! Mark, if has only one child or no child, the tree should complete at here.
            if (curNode.left == null || curNode.right == null) {
                shouldComplete = true;
            }

            // Offer non null children.
            if (curNode.left != null) {
                nodeQueue.offer(curNode.left);
            }
            if (curNode.right != null) {
                nodeQueue.offer(curNode.right);
            }
        }

        return true;

    }

    /*
    BFS

    !!! ArrayDqeue DONOT allow null element. So this solution use the LinkedList as Queue.

    TC: O(N)
    SC: O(N)
    */
    private boolean isCompleteTreeByBFS(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            TreeNode curNode = nodeQueue.poll();

            // Check
            if (curNode == null) {
                while (!nodeQueue.isEmpty()) {
                    if (nodeQueue.poll() != null) {
                        return false;
                    }
                }
                return true;
            }

            nodeQueue.offer(curNode.left);
            nodeQueue.offer(curNode.right);
        }

        return false;
    }
}
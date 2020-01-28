package com.myleetcode.tree.binary_search_tree.delete_node_in_a_bst;

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
    public TreeNode deleteNode(TreeNode root, int key) {
        return deleteNodeByRecursion(root, key);
    }

    private TreeNode deleteNodeByRecursion(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        return delete(root, key);
    }

    /*
    https://leetcode.com/problems/delete-node-in-a-bst/discuss/93296/Recursive-Easy-to-Understand-Java-Solution

    Delete the given key value in a tree whose root is given node and guraantee the tree is still a BST. Return the root node.
    When delete a key from BST, there're 3 conditions:
    1. key is larger than node.val, means we need delete it from node's right subtree, and update node.right after delete.
    2. key is smaller than node.val, means we need delete it from node's left subtree, and update node.left after delete.
    3. key equals to node.val, there're 3 conditions:
        3.1. key == node.val and node.left == null, then just return the right subtree, means delete this node itself.
        3.2. key == node.val and node.right == null, then jsut return the left subtree,means delete this node itself.
        3.3. key == node.val and node.left != null and node.right != null, then we find the successor of the given node, then assign the successor's val to node, and then delete the successor.val on node's right subtree, after delete update the node's right subtree.

    Successor is the smallest node on node's right subtree, ie the closest node to given node behind it in BST.
    Also, we could use predecessor, logic is the same.

    N is total nodes number, H is tree height
    TC: O(H), we find the node has value key and delete it with its successor totally cost O(H). In worst case, a skewed BST H == N, but it's OK to say TC is O(H)
    SC: O(H)
    */
    private TreeNode delete(TreeNode node, int key) {
        if (node == null) {
            return node;
        }

        // 1
        if (node.val < key) {
            node.right = delete(node.right, key);

            // 2.
        } else if (node.val > key) {
            node.left = delete(node.left, key);

            // 3.
        } else {
            // 3.1.
            if (node.left == null) {
                return node.right;
            }
            // 3.2.
            if (node.right == null) {
                return node.left;
            }

            // 3.3.
            TreeNode successorNode = findSuccessor(node);
            node.val = successorNode.val;
            node.right = delete(node.right, successorNode.val);
        }

        return node;
    }

    /*
    Find successor of given node. We assume that if successor not exist, like given node is null or has no right subtree, then return null. This won't affect the answer because after the prepross check, when we try to find successor, it must exist.
    */
    private TreeNode findSuccessor(TreeNode node) {
        if (node == null) {
            return node;
        }

        TreeNode curNode = node.right;
        if (curNode == null) {
            return curNode;
        }
        while (curNode.left != null) {
            curNode = curNode.left;
        }

        return curNode;
    }
}

package com.myleetcode.tree.binary_search_tree.delete_node_in_a_bst;

public class DeleteNodeInABST {
    /**
     * 450. Delete Node in a BST
     * Medium
     *
     * 1330
     *
     * 70
     *
     * Add to List
     *
     * Share
     * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
     *
     * Basically, the deletion can be divided into two stages:
     *
     * Search for a node to remove.
     * If the node is found, delete the node.
     * Note: Time complexity should be O(height of tree).
     *
     * Example:
     *
     * root = [5,3,6,2,4,null,7]
     * key = 3
     *
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * Given key to delete is 3. So we find the node with value 3 and delete it.
     *
     * One valid answer is [5,4,6,2,null,null,7], shown in the following BST.
     *
     *     5
     *    / \
     *   4   6
     *  /     \
     * 2       7
     *
     * Another valid answer is [5,2,6,null,4,null,7].
     *
     *     5
     *    / \
     *   2   6
     *    \   \
     *     4   7
     */

    /**
     * Tree
     *
     * BST
     */

    /**
     * 0-6m
     * Microsoft
     * |
     * 3
     *
     * Oracle
     * |
     * 3
     *
     * 6m-1y
     * Amazon
     * |
     * 4
     *
     * Google
     * |
     * 2
     *
     * Facebook
     * |
     * 2
     *
     * 1y-2y
     * LinkedIn
     * |
     * 4
     *
     * Yahoo
     * |
     * 3
     *
     * Apple
     * |
     * 2
     *
     * Uber
     * |
     * LeetCode
     */
}

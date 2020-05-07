package com.myleetcode.tree.longest_zig_zag_path_in_a_binary_tree;

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
    public int longestZigZag(TreeNode root) {
        return longestZigZagByRecursion(root);
    }

    /*
    DFS
    Transformation of dount edge length problem.

    For each tree, the root node may act as a left branch of parent node or right branch, so we use isLeft boolean variable to tell us current direction.
    1. If we are a left branch, then when we act as root:
        1.1 if we go left, we violate the zigzag pattern, so we start counting nodes from 1
        1.2 if we go right, we are keeping the zigzag pattern, so we continue counting, that's num+1
    2. If we are a right branch, then when we act as root:
        2.1 if we go left, num+1
        2.2 if we go right, 1

    For the whole tree root, we get leftZZ and rightZZ nodes number, edge length is nodes number minus 1.

    TC: O(N)
    SC: O(H)
    */
    private int longestZigZagByRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int toLeftZZ = recursion(root.left, true, 1);
        int toRightZZ = recursion(root.right, false, 1);

        return Math.max(toLeftZZ, toRightZZ) - 1; // nodes number minus 1 is edge length.
    }

    private int recursion(TreeNode curNode, boolean isLeft, int num) {
        if (curNode == null) {
            return num;
        }

        int toLeftZZ = 0;
        int toRightZZ = 0;
        if (isLeft) {
            toLeftZZ = recursion(curNode.left, true, 1);
            toRightZZ = recursion(curNode.right, false, num + 1);
        } else {
            toLeftZZ = recursion(curNode.left, true, num + 1);
            toRightZZ = recursion(curNode.right, false, 1);
        }

        return Math.max(toLeftZZ, toRightZZ);
    }
}

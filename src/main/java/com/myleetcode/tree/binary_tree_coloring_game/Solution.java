package com.myleetcode.tree.binary_tree_coloring_game;

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
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        return btreeGameWinningMoveByDFS(root, n, x);
    }

    /*
    What the problem means: https://leetcode.com/problems/binary-tree-coloring-game/discuss/350900/Confusing-problem-statement/417052

    TC: O(N)
    SC: O(H)
    */
    private boolean btreeGameWinningMoveByDFS(TreeNode root, int n, int x) {
        if (root == null) {
            return false;
        }

        Result ret = new Result();
        countNodes(root, x, ret);

        // If x node's left subtree has more than n/2 nodes, player2 could choose it and win.
        if (ret.xLeftNum > n / 2) {
            return true;
        }
        // If x node's right subtree has more than n/2 nodes, player2 could choose it and win.
        if (ret.xRightNum > n / 2) {
            return true;
        }
        // If the whole tree except x node and its subtree has more than n/2 nodes, player2 could choose x's parent node and win.
        if ((n - (ret.xLeftNum + ret.xRightNum + 1)) > n / 2) {
            return true;
        }

        return false;
    }

    private int countNodes(TreeNode curNode, int x, Result ret) {
        if (curNode == null) {
            return 0;
        }

        int leftNum = countNodes(curNode.left, x, ret);
        int rightNum = countNodes(curNode.right, x, ret);

        // Record count x's subtrees.
        if (curNode.val == x) {
            ret.xLeftNum = leftNum;
            ret.xRightNum = rightNum;
        }

        return leftNum + rightNum + 1;
    }

    private class Result {
        int xLeftNum;
        int xRightNum;

        public Result() {
            this.xLeftNum = 0;
            this.xRightNum = 0;
        }
    }
}
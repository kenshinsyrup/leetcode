package com.myleetcode.tree.binary_search_tree.maximum_sum_bst_in_binary_tree;

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
    public int maxSumBST(TreeNode root) {
        // return maxSumBSTByDFS(root); // TLE
        return maxSumBSTByDFSII(root);
    }

    /*
    Improve from naive DFS solution, we get all we need in BST Validation procedure.

    https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/discuss/552097/Faster-than-94-JAVA

    The most impartant parts:
    1. postorder traversal.
    2. check a tree is BST by the Info class help.
    3. update maxSum by the Arg class help.

    TC: O(N)
    SC: O(H)
    */
    private int maxSumBSTByDFSII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Arg arg = new Arg();
        dfs(root, arg);

        if (arg.maxSum < 0) {
            return 0;
        }
        return arg.maxSum;
    }

    private Info dfs(TreeNode curNode, Arg arg) {
        if (curNode == null) {
            return new Info(true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        Info leftInfo = dfs(curNode.left, arg);
        Info rightInfo = dfs(curNode.right, arg);

        // If current is BST.
        // The condition is tricky and strict, if current is BST: 1. its left and right subtree must mark as curIsBST; 2. and if left or right is null, we accept that by the check with leftInfo.curMax == Integer.MAX_VALUE and rightInfo.curMin == Integer.MIN_VALUE; 3. and if not null, then we check left max and right min with current val.
        if (leftInfo.curIsBST && rightInfo.curIsBST && (leftInfo.curMax == Integer.MAX_VALUE || leftInfo.curMax < curNode.val) && (rightInfo.curMin == Integer.MIN_VALUE || rightInfo.curMin > curNode.val)) {
            int curSum = leftInfo.curSum + rightInfo.curSum + curNode.val;
            int curMin = leftInfo.curMin != Integer.MIN_VALUE ? leftInfo.curMin : curNode.val;
            int curMax = rightInfo.curMax != Integer.MAX_VALUE ? rightInfo.curMax : curNode.val;

            arg.maxSum = Math.max(arg.maxSum, curSum);

            return new Info(true, curSum, curMin, curMax);
        }

        return new Info(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private class Info {
        boolean curIsBST;
        int curSum;
        int curMin;
        int curMax;

        public Info(boolean curIsBST, int curSum, int curMin, int curMax) {
            this.curIsBST = curIsBST;
            this.curSum = curSum;
            this.curMin = curMin;
            this.curMax = curMax;
        }
    }

    /*
    TLE

    TC: O(N^2)
    SC: O(H)
    */
    private int maxSumBSTByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Arg arg = new Arg();
        dfsAll(root, arg);

        return arg.maxSum;
    }

    private void dfsAll(TreeNode curNode, Arg arg) {
        if (curNode == null) {
            return;
        }

        if (isBST(curNode, null, null)) {
            int sum = getSum(curNode);
            arg.maxSum = Math.max(arg.maxSum, sum);
        }

        dfsAll(curNode.left, arg);
        dfsAll(curNode.right, arg);
    }

    private boolean isBST(TreeNode curNode, TreeNode leftBoundaryNode, TreeNode rightBoundaryNode) {
        if (curNode == null) {
            return true;
        }

        if (leftBoundaryNode != null && leftBoundaryNode.val >= curNode.val) {
            return false;
        }
        if (rightBoundaryNode != null && rightBoundaryNode.val <= curNode.val) {
            return false;
        }

        return isBST(curNode.left, leftBoundaryNode, curNode) && isBST(curNode.right, curNode, rightBoundaryNode);
    }

    private int getSum(TreeNode curNode) {
        if (curNode == null) {
            return 0;
        }

        return getSum(curNode.left) + getSum(curNode.right) + curNode.val;
    }

    private class Arg {
        int maxSum;

        public Arg() {
            this.maxSum = Integer.MIN_VALUE;
        }
    }
}

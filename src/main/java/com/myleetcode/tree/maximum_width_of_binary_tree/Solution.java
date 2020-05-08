package com.myleetcode.tree.maximum_width_of_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashMap;
import java.util.Map;

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
    public int widthOfBinaryTree(TreeNode root) {
        return widthOfBinaryTreeByDFS(root);
    }

    /*
    Position of tree nodes.
    root node has pos 0, fro each node has pos x, its left child is 2*x+1, right child is 2*x+2, its parent is (x-1)/2
    */
    private int widthOfBinaryTreeByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Map<Integer, int[]> depthValMap = new HashMap<>();
        Result ret = new Result();
        dfs(root, 0, 1, depthValMap, ret);

        return ret.maxWidth;
    }

    private void dfs(TreeNode curNode, int pos, int depth, Map<Integer, int[]> depthValMap, Result ret) {
        if (curNode == null) {
            return;
        }

        // Update Map.
        int[] posArr = depthValMap.getOrDefault(depth, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
        posArr[0] = Math.min(posArr[0], pos);
        posArr[1] = Math.max(posArr[1], pos);
        depthValMap.put(depth, posArr);

        // Update ret.
        int leftMost = depthValMap.get(depth)[0];
        int rightMost = depthValMap.get(depth)[1];

        if (leftMost != Integer.MAX_VALUE && rightMost != Integer.MIN_VALUE) {
            ret.maxWidth = Math.max(ret.maxWidth, rightMost - leftMost + 1);
        }

        dfs(curNode.left, pos * 2 + 1, depth + 1, depthValMap, ret);
        dfs(curNode.right, pos * 2 + 2, depth + 1, depthValMap, ret);
    }

    private class Result {
        int maxWidth;
    }
}
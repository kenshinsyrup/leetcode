package com.myleetcode.tree.diameter_of_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        return diameterOfBinaryTreeByDFSAll(root);
    }

    // TC: O(N), visited every node O(N)
    // SC: O(N), recursion height is N
    // intuition: DFSAll, caculate every node's left and right subtree longest path and choose the max.
    class Result{
        int max;
        Result(int max){
            this.max = max;
        }
    }
    private int diameterOfBinaryTreeByDFSAll(TreeNode root){
        if(root == null){
            return 0;
        }

        Result ret = new Result(0);

        depthByDFS(root, ret);

        return ret.max;
    }

    private int depthByDFS(TreeNode node, Result ret){
        if(node == null){
            return 0;
        }

        // 左子树节点数
        int left = depthByDFS(node.left, ret);
        // 右子树节点数
        int right = depthByDFS(node.right, ret);

        // the nodes of path that pass through the current node is: left + right + 1
        // the max depth of current node is: Math.max(left, right) + 1

        // 因为题目要求的内容是node之间的gap的数字，所以是left+right+1 - 1
        ret.max = Math.max(left + right + 1 - 1, ret.max);

        // 左子树节点数和右子树节点数的大者，加上当前node 1，就是当前node为root的树的高度
        return Math.max(left, right) + 1;
    }
}

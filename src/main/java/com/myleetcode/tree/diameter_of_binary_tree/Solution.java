package com.myleetcode.tree.diameter_of_binary_tree;

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
    public int diameterOfBinaryTree(TreeNode root) {
        return diameterOfBinaryTreeByDFS(root);
    }

    // TC: O(N), visited every node O(N)
    // SC: O(N), recursion height is N
    // intuition: DFSAll, caculate every node's left and right subtree longest path and choose the max.
    class Result {
        int max;

        Result() {
            this.max = 0;
        }
    }

    // !!! Most important part is in DFS, we return the longer branch including self as a branch to parent node. And to
    // each node itself, it should caculate the path including its left and right subtree and itself and update
    // the answer.
    // When count edges, we count nodes instead, edges number is nodes number minus 1. If directly count edges, we may
    // need handle many corner cases because we'll traverse tree and encounter null nodes in which cases we need
    // consider whether we should make edges number plus 1.
    private int diameterOfBinaryTreeByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        dfs(root, ret);

        return ret.max;
    }

    private int dfs(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return 0;
        }

        int curNodes = 1;
        int leftNodes = dfs(curNode.left, ret);
        curNodes += leftNodes;
        int rightNodes = dfs(curNode.right, ret);
        curNodes += rightNodes;

        // Update ret
        ret.max = Math.max(ret.max, curNodes - 1); // edges equals to nodes-1

        // Return larger
        int asBranchNodes = Math.max(leftNodes, rightNodes);
        return asBranchNodes + 1;

    }
//
//    private int diameterOfBinaryTreeByDFS(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//
//        Result ret = new Result();
//
//        depthByDFS(root, ret);
//
//        return ret.max;
//    }
//
//    private int depthByDFS(TreeNode node, Result ret) {
//        if (node == null) {
//            return 0;
//        }
//
//        // 左子树节点数
//        int left = depthByDFS(node.left, ret);
//        // 右子树节点数
//        int right = depthByDFS(node.right, ret);
//
//        // the nodes of path that pass through the current node is: left + right + 1
//        // the max depth of current node is: Math.max(left, right) + 1
//
//        // 因为题目要求的内容是node之间的gap的数字，所以是left+right+1 - 1
//        ret.max = Math.max(left + right + 1 - 1, ret.max);
//
//        // 左子树节点数和右子树节点数的大者，加上当前node 1，就是当前node为root的树的高度
//        return Math.max(left, right) + 1;
//    }
}

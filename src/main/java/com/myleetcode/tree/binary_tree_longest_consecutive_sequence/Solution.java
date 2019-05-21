package com.myleetcode.tree.binary_tree_longest_consecutive_sequence;

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
    class Result{
        int maxLen;
        public Result(int maxLen){
            this.maxLen = maxLen;
        }
    }

    public int longestConsecutive(TreeNode root) {
        return longestConsecutiveByDFS(root);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: Preorder Tree Traversal Problem
    // for every node, we find it's subtree's LCS, then if curNode is consecutive with subtree we should plus one additionally, then we return max of current nodes's LCS
    private int longestConsecutiveByDFS(TreeNode root){
        if(root == null){
            return 0;
        }

        Result ret = new Result(0);

        dfs(root, ret);

        return ret.maxLen;
    }

    private int dfs(TreeNode curNode, Result ret){
        // base case
        if(curNode == null){
            return 0;
        }

        // left GLS
        int leftLen = dfs(curNode.left, ret);
        // right GLS
        int rightLen = dfs(curNode.right, ret);

        // if left or right subtree is null or not consecutive with cur node, set it's len to 0
        if(curNode.left == null || curNode.val + 1 != curNode.left.val){
            leftLen = 0;
        }
        if(curNode.right == null || curNode.val + 1 != curNode.right.val){
            rightLen = 0;
        }

        // cur GLS len
        int curLen = Math.max(leftLen, rightLen) + 1;
        // update max
        ret.maxLen = Math.max(ret.maxLen, curLen);

        // return cur node's GLS
        return curLen;
    }
}

package com.myleetcode.tree.count_univalue_subtrees;

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
        int num;
        public Result(int num){
            this.num = num;
        }
    }

    public int countUnivalSubtrees(TreeNode root) {
        return countUnivalSubtreesByDFS(root);
    }

    // intuition: Postorder Tree Traversal
    // we check if our left and right subtree is univalue tree, if it's, then check if the current root and left and right could be a univalue tree, if is then count
    private int countUnivalSubtreesByDFS(TreeNode root){
        if(root == null){
            return 0;
        }

        Result ret = new Result(0);

        dfs(root, ret);

        return ret.num;
    }

    private boolean dfs(TreeNode curNode, Result ret){
        // base
        if(curNode == null){
            return true;
        }

        // check left and right subtree
        boolean left = dfs(curNode.left, ret);
        boolean right = dfs(curNode.right, ret);

        if(!left || !right){
            return false;
        }

        // current node as root, check if could be a univalue tree with left and right
        if(curNode.left != null){
            if(curNode.val != curNode.left.val){
                return false;
            }
        }
        if(curNode.right != null){
            if(curNode.val != curNode.right.val){
                return false;
            }
        }

        // is a univalue tree, count and return true;
        ret.num++;

        return true;
    }

}

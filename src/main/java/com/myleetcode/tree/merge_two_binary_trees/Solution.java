package com.myleetcode.tree.merge_two_binary_trees;

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
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        return mergeTreesByPreorderTraverse(t1, t2);
    }



    // TC: O(N + M), N is # of t1 nodes, M is # of t2 nodes
    // SC: O(1)
    // intuition: essientiall this is a traversing problem, we need to guarantee we are traverse the same position when we traverse these two tree sinmutaniously, so we should do preorder traverse.
    private TreeNode mergeTreesByPreorderTraverse(TreeNode t1, TreeNode t2){
        // return preorderByDFS(t1, t2);
        return preorderByDFSII(t1, t2);
    }

    // optimize, when t1 or t2 == null, we could ignore the null one.
    private TreeNode preorderByDFSII(TreeNode t1, TreeNode t2){
        if (t1 == null){
            return t2;
        }
        if (t2 == null){
            return t1;
        }

        t1.val += t2.val;
        t1.left = preorderByDFSII(t1.left, t2.left);
        t1.right = preorderByDFSII(t1.right, t2.right);

        return t1;
    }

    private TreeNode preorderByDFS(TreeNode t1, TreeNode t2){
        TreeNode curNode = null;

        if(t1 == null && t2 == null){
            return curNode;
        }else if(t1 == null){
            curNode = new TreeNode(t2.val);
            curNode.left = preorderByDFS(t1, t2.left);
            curNode.right = preorderByDFS(t1, t2.right);
        }else if(t2 == null){
            curNode = new TreeNode(t1.val);
            curNode.left = preorderByDFS(t1.left, t2);
            curNode.right = preorderByDFS(t1.right, t2);
        }else{
            curNode = new TreeNode(t1.val + t2.val);
            curNode.left = preorderByDFS(t1.left, t2.left);
            curNode.right = preorderByDFS(t1.right, t2.right);
        }

        return curNode;
    }

    // THIS IS WRONG!!!
    /*
     private TreeNode preorderByDFS(TreeNode t1, TreeNode t2){
        if(t1 == null && t2 == null){
            return null;
        }
        if(t1 == null){
            return new TreeNode(t2.val);
        }
        if(t2 == null){
            return new TreeNode(t1.val);
        }

        t1.val = t1.val + t2.val;

        t1.left = preorderByDFS(t1.left, t2.left);
        t1.right = preorderByDFS(t1.right, t2.right);

        return t1;
    }
    */
}

package com.myleetcode.tree.merge_two_binary_trees;

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
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        // return mergeTreesByPreorderTraverse(t1, t2);
        return mergeTreesByRecursion(t1, t2);
    }

    /*
    Recursion means we return the expected result after running.
    This recursion we want it return the merged tree of given two trees. Based on this, when we process t1 root node and t2 rootnode, their subtrees are merged. So we only need consider the root node of t1 and t2 and their left and right child(when they act as root are already done merged based on recursion definition)
    So, there're 4 conditions:
    1. base case, t1 and t2 current node are both null, return null
    2. t1CurNode is null, then t1CurNode merge with t2CurNode is t2 current tree, return it.
    3. t2CurNode is null, same as above.
    4. normal case, t1 and t2 current node are both not null, we use the t1CurNode here as base(Of course could use t2CurNode too). We merge t1CurNode.left with t2CurNode.left and after merge we update this to t1CurNode.left; the same as right; at last we also merge the t1CurNode with t2Curnode themself to get one whole tree and root is t1CurNode to return.

    TC: O(N + M)
    SC: O(max(N, M))
    */
    private TreeNode mergeTreesByRecursion(TreeNode t1CurNode, TreeNode t2CurNode) {
        // Base case, if t1 and t2 both null, return null;
        if (t1CurNode == null && t2CurNode == null) {
            return null;
        }

        if (t1CurNode == null) {
            return t2CurNode;
        }

        if (t2CurNode == null) {
            return t1CurNode;
        }

        t1CurNode.left = mergeTreesByRecursion(t1CurNode.left, t2CurNode.left);
        t1CurNode.right = mergeTreesByRecursion(t1CurNode.right, t2CurNode.right);
        t1CurNode.val = t1CurNode.val + t2CurNode.val;

        return t1CurNode;
    }


    // TC: O(N + M), N is # of t1 nodes, M is # of t2 nodes
    // SC: O(1)
    // intuition: essientiall this is a traversing problem, we need to guarantee we are traverse the same position when we traverse these two tree sinmutaniously, so we should do preorder traverse.
    private TreeNode mergeTreesByPreorderTraverse(TreeNode t1, TreeNode t2) {
        // return preorderByDFS(t1, t2);
        return preorderByDFSII(t1, t2);
    }

    // optimize, when t1 or t2 == null, we could ignore the null one.
    private TreeNode preorderByDFSII(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }

        t1.val += t2.val;
        t1.left = preorderByDFSII(t1.left, t2.left);
        t1.right = preorderByDFSII(t1.right, t2.right);

        return t1;
    }

    private TreeNode preorderByDFS(TreeNode t1, TreeNode t2) {
        TreeNode curNode = null;

        if (t1 == null && t2 == null) {
            return curNode;
        } else if (t1 == null) {
            curNode = new TreeNode(t2.val);
            curNode.left = preorderByDFS(t1, t2.left);
            curNode.right = preorderByDFS(t1, t2.right);
        } else if (t2 == null) {
            curNode = new TreeNode(t1.val);
            curNode.left = preorderByDFS(t1.left, t2);
            curNode.right = preorderByDFS(t1.right, t2);
        } else {
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
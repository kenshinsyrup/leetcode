package com.myleetcode.tree.binary_tree_upside_down;

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
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        return upsideDownBinaryTreeByDFS(root);
    }

    // intuition: totally dont understand description, have no any idea
    // thought and solution: https://leetcode.com/problems/binary-tree-upside-down/discuss/163716/Thinking-Process-(Java-Scala)
    // https://leetcode.com/problems/binary-tree-upside-down/discuss/49412/Clean-Java-solution/49658
    // https://leetcode.com/problems/binary-tree-upside-down/discuss/49406/Java-recursive-(O(logn)-space)-and-iterative-solutions-(O(1)-space)-with-explanation-and-figure
    // The leftmost node is the new root. The key is to go down along the leftmost path to find the new root and reset all pointers when poping up.
    // TC: O(H)
    // SC: O(1)
    private TreeNode upsideDownBinaryTreeByDFS(TreeNode root){
        if(root == null){
            return null;
        }

        return upsideDown(root);
    }

    // return new root
    private TreeNode upsideDown(TreeNode curNode){
        // base case, if null, return
        if(curNode == null){
            return null;
        }

        // reach the left most, new root
        if(curNode.left == null){
            return curNode;
        }

        // keep to go to the left most subtree to find the new root
        TreeNode newRoot = upsideDown(curNode.left);

        // for a basic tree:

        // curNode's left and right node, should be assigned to the curNodeLeft node as its left and right nodes
        curNode.left.left = curNode.right;
        curNode.left.right = curNode;

        // after above operations, there're links int theis basic tree: let's use the eg, say curNode is 2:
        // new tree links: curNodeLeft.left->curNode.right(4.left->5) and curNodeLeft.right->curNodeafter(4.right->2).
        // old tree links: curNode.left->curNodeLeft(2.left->4), curNode.right->curNode.right(2.right->5)
        // so we cut the curNode's original old links
        curNode.left = null;
        curNode.right = null;

        return newRoot;
    }

}

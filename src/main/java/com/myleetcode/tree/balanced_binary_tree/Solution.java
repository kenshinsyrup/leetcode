package com.myleetcode.tree.balanced_binary_tree;

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
    public boolean isBalanced(TreeNode root) {
        // special case
        if(root == null){
            return true;
        }

        // return isBalancedByDFS(root);
        return isBalancedByDFSII(root);// recommend
    }

    /*
    DFS to get tree height and do a little change, when we find two subtrees are balanced, we return -1

    TC: O(N)
    SC: O(N)
    */
    private boolean isBalancedByDFSII(TreeNode root){
        if(root == null){
            return true;
        }

        int height = getHeightByDFSII(root);

        if(height == -1){
            return false;
        }
        return true;
    }

    private int getHeightByDFSII(TreeNode curNode){
        if(curNode == null){
            return 0;
        }

        int leftH = getHeightByDFSII(curNode.left);
        if(leftH == -1){
            return -1;
        }
        int rightH = getHeightByDFSII(curNode.right);
        if(rightH == -1){
            return -1;
        }

        if(Math.abs(leftH - rightH) > 1){
            return -1;
        }

        return Math.max(leftH, rightH) + 1;

    }

    /*
    DFS to traverse every node
    for each node, use it as root, DFS to get it's leftSubTree and rightSubTree height, if not balanced, return false

    Say N is total # of tree nodes
    TC: O(N^2), nested DFS
    SC: (N), recursion depth
    */
    private boolean isBalancedByDFS(TreeNode node){
        // base case
        if(node == null){
            return true;
        }

        int leftHeight = getTreeHeightByDFS(node.left);
        int rightHeight = getTreeHeightByDFS(node.right);

        if(Math.abs(leftHeight - rightHeight) > 1){
            return false;
        }

        return isBalancedByDFS(node.left) && isBalancedByDFS(node.right);
    }

    private int getTreeHeightByDFS(TreeNode node){
        // base case
        if(node == null){
            return 0;
        }

        int leftHeight = getTreeHeightByDFS(node.left);
        int rightHeight = getTreeHeightByDFS(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
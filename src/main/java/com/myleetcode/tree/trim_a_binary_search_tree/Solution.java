package com.myleetcode.tree.trim_a_binary_search_tree;

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
    public TreeNode trimBST(TreeNode root, int L, int R) {
        return trimBSTByRecursive(root, L, R);
    }

    // intuition: recursion problem.
    // for every subtree, we have three node to check, root, left and right
    // for root: there are 4 situations, 1 is root == null, we return null; 2 is root.val < L, we only care about its right subtree then; 3 is root.val > R, we care aoubt its left subtree then; 4 is root.val >= L && root.val <= R, then we need caculate its left and right subtree and return root
    private TreeNode trimBSTByRecursive(TreeNode root, int L, int R){
        // 1
        if(root == null){
            return null;
        }

        // 2
        if(root.val < L){
            return trimBSTByRecursive(root.right, L, R);
        }

        // 3
        if(root.val > R){
            return trimBSTByRecursive(root.left, L, R);
        }

        // 4
        root.left = trimBSTByRecursive(root.left, L, R);
        root.right = trimBSTByRecursive(root.right, L, R);
        return root;
    }
}

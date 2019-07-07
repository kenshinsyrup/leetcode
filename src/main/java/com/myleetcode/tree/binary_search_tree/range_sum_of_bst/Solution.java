package com.myleetcode.tree.binary_search_tree.range_sum_of_bst;

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
    public int rangeSumBST(TreeNode root, int L, int R) {
        return rangeSumBSTByRecursion(root, L, R);
    }

    // intuiton: L and R is inclusive in the BST, so we could do Search in BST with recursion. For curNode, if null return 0; if curNode.val < L, return right+0; if curNode.val > R, return left+0; if curNode.val in the range [L, R], return left+right+cur
    // TC: O(N)
    // SC: O(H)
    private int rangeSumBSTByRecursion(TreeNode root, int L, int R){
        if(root == null){
            return 0;
        }

        return sumRange(root, L, R);
    }

    private int sumRange(TreeNode node, int L, int R){
        // base case
        if(node == null){
            return 0;
        }

        // current node out of range [L, R]
        if(node.val < L){
            return sumRange(node.right, L, R);
        }
        if(node.val > R){
            return sumRange(node.left, L, R);
        }

        // current node in range of [L, R]
        int left = sumRange(node.left, L, R);
        int right = sumRange(node.right, L, R);

        return node.val + left + right;
    }
}

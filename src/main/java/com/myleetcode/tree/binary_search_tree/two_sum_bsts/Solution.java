package com.myleetcode.tree.binary_search_tree.two_sum_bsts;

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
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        return traverseAndSearch(root1, root2, target);
    }

    /*

    N1 is node number of tree1
    TC: O(N1 * H2)
    SC: O(N1 * H2)
    */
    private boolean traverseAndSearch(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null) {
            return false;
        }

        int val = root1.val;
        if (searchOnBST(root2, target - val)) {
            return true;
        }

        return traverseAndSearch(root1.left, root2, target) || traverseAndSearch(root1.right, root2, target);
    }

    /*

    H2 is tree2 height
    TC: O(H2)
    SC: O(H2)
    */
    private boolean searchOnBST(TreeNode curNode, int tar) {
        if (curNode == null) {
            return false;
        }

        if (curNode.val < tar) {
            return searchOnBST(curNode.right, tar);
        }

        if (curNode.val > tar) {
            return searchOnBST(curNode.left, tar);
        }

        return true;
    }
}
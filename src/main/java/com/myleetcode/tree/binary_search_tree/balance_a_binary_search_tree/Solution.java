package com.myleetcode.tree.binary_search_tree.balance_a_binary_search_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
public class Solution {
    public TreeNode balanceBST(TreeNode root) {
        return balanceBSTByArrayAndBuild(root);
    }

    /*
    Solution easy to write, according to the LC hints, this is the expected way:
    Traverse binary tree in-order to get sorted array
    The problem become 108. Convert Sorted Array to Binary Search Tree

    https://leetcode.com/problems/balance-a-binary-search-tree/discuss/539686/JavaC%2B%2B-Sorted-Array-to-BST-O(N)-Clean-code

    TC: O(N)
    SC: O(N)
    */
    private TreeNode balanceBSTByArrayAndBuild(TreeNode root) {
        if (root == null) {
            return root;
        }

        List<Integer> valList = new ArrayList<>();
        inorder(root, valList);

        return buildBalanceBST(valList, 0, valList.size() - 1);
    }

    private void inorder(TreeNode curNode, List<Integer> valList) {
        if (curNode == null) {
            return;
        }

        inorder(curNode.left, valList);

        valList.add(curNode.val);

        inorder(curNode.right, valList);
    }

    private TreeNode buildBalanceBST(List<Integer> valList, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return new TreeNode(valList.get(start));
        }

        // Make it balanced.
        int mid = start + (end - start) / 2;
        TreeNode curNode = new TreeNode(valList.get(mid));

        curNode.left = buildBalanceBST(valList, start, mid - 1);
        curNode.right = buildBalanceBST(valList, mid + 1, end);

        return curNode;

    }
}
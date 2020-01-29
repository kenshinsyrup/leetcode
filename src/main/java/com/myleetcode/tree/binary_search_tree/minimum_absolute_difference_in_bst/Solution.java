package com.myleetcode.tree.binary_search_tree.minimum_absolute_difference_in_bst;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public int getMinimumDifference(TreeNode root) {
        // return getMinimumDifferenceByTraverseAndSearch(root);
        // return getMinimumDifferenceByInorderAndList(root);
        return getMinimumDifferenceByInorderAndListII(root);
    }

    /*
    Optimize getMinimumDifferenceByInorderAndList.
    We don't really need the List and to traverse two times, we could do the compare during the inorder.

    TC: O(N)
    SC: O(H)
    */
    class Result {
        int minDiff;
        TreeNode prevNode;

        Result() {
            this.minDiff = Integer.MAX_VALUE;
            this.prevNode = null;
        }

    }

    private int getMinimumDifferenceByInorderAndListII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        inordeAndCompare(root, ret);

        return ret.minDiff;

    }

    private void inordeAndCompare(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return;
        }

        inordeAndCompare(curNode.left, ret);

        if (ret.prevNode != null) {
            ret.minDiff = Math.min(ret.minDiff, Math.abs(curNode.val - ret.prevNode.val));
        }
        ret.prevNode = curNode;

        inordeAndCompare(curNode.right, ret);

    }

    /*

    Is BST, inorder traverse to a list, then get min diff which must exist between two adjacnet elements.

    TC: O(N)
    SC: O(N)
    */
    private int getMinimumDifferenceByInorderAndList(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<Integer> valList = new ArrayList<>();

        inorder(root, valList);

        int size = valList.size();
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < size; i++) {
            minDiff = Math.min(minDiff, Math.abs(valList.get(i - 1) - valList.get(i)));
        }

        return minDiff;
    }

    private void inorder(TreeNode curNode, List<Integer> valList) {
        if (curNode == null) {
            return;
        }

        inorder(curNode.left, valList);

        valList.add(curNode.val);

        inorder(curNode.right, valList);
    }

    /*
    If not BST, then ll nodes, each act as a root, find the minimum difference between it and all its children(including subtrees)

    TC: O(N^2)
    SC: O(N^2)
    */
    private int getMinimumDifferenceByTraverseAndSearch(TreeNode root) {
        if (root == null) {
            return 0;
        }

        traverse(root, root);

        return minDiff;

    }

    int minDiff = Integer.MAX_VALUE;

    private void traverse(TreeNode root, TreeNode curNode) {
        if (curNode == null) {
            return;
        }

        search(root, curNode.val);

        traverse(root, curNode.left);
        traverse(root, curNode.right);
    }

    private void search(TreeNode traverseNode, int checkVal) {
        if (traverseNode == null) {
            return;
        }

        if (traverseNode.val != checkVal) { // Avoid self compare.
            minDiff = Math.min(minDiff, Math.abs(checkVal - traverseNode.val));
        }

        search(traverseNode.left, checkVal);
        search(traverseNode.right, checkVal);
    }

}

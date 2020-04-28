package com.myleetcode.tree.delete_nodes_and_return_forest;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        return delNodesByRecursion(root, to_delete);
    }

    /*
    Transfomation of delete node problem.
    https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328853/JavaC++Python-Recursion-Solution/301984
    */
    private List<TreeNode> delNodesByRecursion(TreeNode root, int[] to_delete) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Delete val set to get O(1) check.
        Set<Integer> deleteSet = new HashSet<>();
        for (int del : to_delete) {
            deleteSet.add(del);
        }

        // Res, first check whether we need the root node.
        List<TreeNode> res = new ArrayList<>();
        if (!deleteSet.contains(root.val)) {
            res.add(root);
        }

        // Start delete and complete res.
        deleteNodes(root, deleteSet, res);

        return res;
    }

    private TreeNode deleteNodes(TreeNode curRoot, Set<Integer> deleteSet, List<TreeNode> res) {
        if (curRoot == null) {
            return null;
        }

        curRoot.left = deleteNodes(curRoot.left, deleteSet, res);
        curRoot.right = deleteNodes(curRoot.right, deleteSet, res);

        // If need delete curRoot, delete it.
        if (deleteSet.contains(curRoot.val)) {
            if (curRoot.left != null) {
                res.add(curRoot.left);
            }
            if (curRoot.right != null) {
                res.add(curRoot.right);
            }

            return null;
        }
        // Otherwise, no effect.
        return curRoot;
    }
}
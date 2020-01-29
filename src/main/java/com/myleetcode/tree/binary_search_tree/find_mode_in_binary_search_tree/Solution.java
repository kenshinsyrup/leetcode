package com.myleetcode.tree.binary_search_tree.find_mode_in_binary_search_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashSet;
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
class Solution {
    public int[] findMode(TreeNode root) {
        return findModeByRecursion(root);
    }

    /*
    Transformation of inorder traversal.

    TC: O(N)
    SC: O(N)
    */
    private int[] findModeByRecursion(TreeNode root) {
        if (root == null) {
            return new int[0];
        }

        Result ret = new Result();
        inorder(root, ret);

        int size = ret.modeSet.size();
        int[] modes = new int[size];
        int i = 0;
        for (int mode : ret.modeSet) {
            modes[i] = mode;
            i++;
        }

        return modes;
    }

    class Result {
        TreeNode prevNode;
        int count;

        int modeNum;
        Set<Integer> modeSet;

        Result() {
            this.prevNode = null;
            this.count = 0;

            this.modeNum = 0;
            this.modeSet = new HashSet<>();
        }
    }

    private void inorder(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return;
        }

        inorder(curNode.left, ret);

        if (ret.prevNode != null && ret.prevNode.val == curNode.val) {
            ret.count++;
        } else {
            ret.count = 1;
        }
        if (ret.count == ret.modeNum) {
            ret.modeSet.add(curNode.val);
        } else if (ret.count > ret.modeNum) {
            ret.modeNum = ret.count;
            ret.modeSet = new HashSet<>();
            ret.modeSet.add(curNode.val);
        }
        ret.prevNode = curNode;

        inorder(curNode.right, ret);

    }
}
package com.myleetcode.tree.flip_binary_tree_to_match_preorder_traversal;

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
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        return flipMatchVoyageByDFS(root, voyage);
    }

    private List<Integer> flipMatchVoyageByDFS(TreeNode root, int[] voyage) {
        if (root == null) {
            return new ArrayList<>();
        }
        if (voyage == null || voyage.length == 0) {
            return new ArrayList<>();
        }

        Result ret = new Result();
        dfs(root, voyage, ret);

        List<Integer> res = new ArrayList<>();
        if (!ret.nodeList.isEmpty() && ret.nodeList.get(0) == -1) {
            res.add(-1);
        } else {
            res.addAll(ret.nodeList);
        }
        return res;
    }

    private void dfs(TreeNode curNode, int[] voyage, Result ret) {
        if (curNode == null) {
            return;
        }
        if (ret.idx >= voyage.length) {
            return;
        }

        // Self.
        if (curNode.val != voyage[ret.idx]) {
            ret.nodeList = new ArrayList<>();
            ret.nodeList.add(-1);
            return;
        }

        ret.idx++;

        // Check and try flip.
        if (curNode.left != null && ret.idx < voyage.length && curNode.left.val != voyage[ret.idx]) {
            ret.nodeList.add(curNode.val);

            TreeNode temp = curNode.left;
            curNode.left = curNode.right;
            curNode.right = temp;
        }

        dfs(curNode.left, voyage, ret);
        dfs(curNode.right, voyage, ret);
    }

    private class Result {
        int idx;
        List<Integer> nodeList;

        public Result() {
            this.idx = 0;
            this.nodeList = new ArrayList<>();
        }
    }
}

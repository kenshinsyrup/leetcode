package com.myleetcode.tree.cousins_in_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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
    public boolean isCousins(TreeNode root, int x, int y) {
        // return isCousinsByBFS(root, x, y);
        return isCousinsByDFS(root, x, y);
    }

    /*
    BFS, check in same level.

    TC: O(N)
    SC: O(N)
    */
    private boolean isCousinsByBFS(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        Map<TreeNode, TreeNode> nodeParentMap = new HashMap<>();
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        boolean xFind = false;
        boolean yFind = false;
        TreeNode xParent = null;
        TreeNode yParent = null;
        while (!nodeQueue.isEmpty()) {
            // Init status.
            xFind = false;
            yFind = false;

            // Level by level.
            int size = nodeQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();
                if (curNode.val == x) {
                    xFind = true;
                    xParent = nodeParentMap.get(curNode);
                }
                if (curNode.val == y) {
                    yFind = true;
                    yParent = nodeParentMap.get(curNode);
                }
                if (xFind && yFind && (xParent != yParent)) {
                    return true;
                }

                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                    nodeParentMap.put(curNode.left, curNode);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                    nodeParentMap.put(curNode.right, curNode);
                }
            }
        }

        return false;
    }

    /*
    DFS is same idea.

    TC: O(N)
    SC: O(H)
    */
    private boolean isCousinsByDFS(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        Result ret = new Result();
        dfs(root, x, y, 0, null, ret);
        return ret.xFind && ret.yFind && (ret.xDepth == ret.yDepth) && (ret.xParent != ret.yParent);
    }

    private void dfs(TreeNode curNode, int x, int y, int depth, TreeNode parentNode, Result ret) {
        if (curNode == null) {
            return;
        }

        if (curNode.val == x) {
            ret.xDepth = depth;
            ret.xFind = true;
            ret.xParent = parentNode;
        }
        if (curNode.val == y) {
            ret.yDepth = depth;
            ret.yFind = true;
            ret.yParent = parentNode;
        }

        dfs(curNode.left, x, y, depth + 1, curNode, ret);
        dfs(curNode.right, x, y, depth + 1, curNode, ret);
    }

    private class Result {
        int xDepth;
        int yDepth;
        boolean xFind;
        boolean yFind;
        TreeNode xParent;
        TreeNode yParent;

        public Result() {
            this.xDepth = 0;
            this.yDepth = 0;
            this.xFind = false;
            this.yFind = false;
            this.xParent = null;
            this.yParent = null;
        }
    }
}

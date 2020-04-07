package com.myleetcode.tree.find_bottom_left_tree_value;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

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
    public int findBottomLeftValue(TreeNode root) {
        return findBottomLeftValueByBFS(root);
        // return findBottomLeftValueByDFS(root);
    }

    /*
    DFS, when traverse children should from right to left.

    TC: O(N)
    SC: O(H)
    */
    private int findBottomLeftValueByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        dfs(root, 0, ret);
        return ret.val;
    }

    private void dfs(TreeNode curNode, int level, Result ret) {
        if (curNode == null) {
            return;
        }

        if (level >= ret.level) {
            ret.level = level;
            ret.val = curNode.val;
        }

        dfs(curNode.right, level + 1, ret);
        dfs(curNode.left, level + 1, ret);
    }

    class Result {
        int val;
        int level;

        public Result() {
            this.val = 0;
            this.level = 0;
        }
    }


    /*
    BFS

    TC: O(N)
    SC: O(N)
    */
    private int findBottomLeftValueByBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int ret = 0;
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();

                // Left Most.
                if (i == 0) {
                    ret = curNode.val;
                }

                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                }
            }
        }

        return ret;
    }
}

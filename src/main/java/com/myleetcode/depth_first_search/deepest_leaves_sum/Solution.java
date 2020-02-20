package com.myleetcode.depth_first_search.deepest_leaves_sum;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int deepestLeavesSum(TreeNode root) {
        // return deepestLeavesSumByBFS(root);
        return deepestLeavesSumByDFS(root);
    }

    /*
    DFS

    TC: O(N)
    SC: O(H)
    */
    private class Result {
        int sum;
        int maxDepth;

        public Result() {
            this.sum = 0;
            this.maxDepth = 0;
        }
    }

    private int deepestLeavesSumByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        preorder(root, ret, 0);
        return ret.sum;
    }

    private void preorder(TreeNode curNode, Result ret, int depth) {
        if (curNode == null) {
            return;
        }

        // If leaf
        if (curNode.left == null && curNode.right == null) {
            // If equals to max depth
            if (depth == ret.maxDepth) {
                ret.sum += curNode.val;
            } else if (depth > ret.maxDepth) {
                // If deeper than max depth
                ret.sum = 0;
                ret.sum += curNode.val;
                ret.maxDepth = depth;
            }
        }

        preorder(curNode.left, ret, depth + 1);
        preorder(curNode.right, ret, depth + 1);
    }

    /*
    BFS

    TC: O(N)
    SC: O(N)
    */
    private int deepestLeavesSumByBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int sum = 0;
        Deque<TreeNode> nodeQ = new ArrayDeque<>();
        nodeQ.offer(root);
        while (!nodeQ.isEmpty()) {
            int size = nodeQ.size();
            sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQ.poll();

                if (curNode.left != null) {
                    nodeQ.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQ.offer(curNode.right);
                }

                sum += curNode.val;
            }
        }

        return sum;
    }
}

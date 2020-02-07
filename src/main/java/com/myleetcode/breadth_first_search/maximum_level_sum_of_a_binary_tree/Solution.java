package com.myleetcode.breadth_first_search.maximum_level_sum_of_a_binary_tree;

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
    public int maxLevelSum(TreeNode root) {
        return maxLevelSumByBFS(root);
    }


    /*
    BFS
    Get level sum, if larger than maxLevelSum, update maxLevelSum and ret level.

    N is nodes number.
    TC: O(N)
    SC: O(N)
    */
    private int maxLevelSumByBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int curLevel = 0;
        int maxLevelSum = 0;
        int ret = 0;

        Deque<TreeNode> nodeQ = new ArrayDeque<>();
        nodeQ.offer(root);
        while (!nodeQ.isEmpty()) {
            curLevel++;
            int size = nodeQ.size();

            int levelSum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQ.poll();
                levelSum += curNode.val;

                if (curNode.left != null) {
                    nodeQ.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQ.offer(curNode.right);
                }
            }

            if (levelSum > maxLevelSum) {
                maxLevelSum = levelSum;
                ret = curLevel;
            }
        }

        return ret;
    }
}

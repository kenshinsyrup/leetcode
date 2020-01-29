package com.myleetcode.greedy.binary_tree_cameras;

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
    public int minCameraCover(TreeNode root) {
        return minCameraCoverByDFSGreedy(root);
    }

    /*
    Greedy
    https://leetcode.com/problems/binary-tree-cameras/discuss/211180/JavaC++Python-Greedy-DFS/299037
    https://leetcode.com/problems/binary-tree-cameras/discuss/211966/Super-Clean-Java-solution-beat-100-DFS-O(n)-time-complexity

    TC: O(N)
    SC: O(H)
    */
    private int NOT_MONITORED = 0;
    private int MONITORED_NOCAM = 1;
    private int MONITORED_WITHCAM = 2;

    private int cameras = 0;

    private int minCameraCoverByDFSGreedy(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }

        int rootStatus = checkMonitorStatus(root);
        if (rootStatus == NOT_MONITORED) {
            cameras++;
        }

        return cameras++;

    }

    private int checkMonitorStatus(TreeNode curNode) {
        if (curNode == null) {
            return MONITORED_NOCAM;
        }

        int left = checkMonitorStatus(curNode.left);
        int right = checkMonitorStatus(curNode.right);

        if (left == MONITORED_NOCAM && right == MONITORED_NOCAM) {
            return NOT_MONITORED;
        } else if (left == NOT_MONITORED || right == NOT_MONITORED) {
            cameras++;
            return MONITORED_WITHCAM;
        } else {
            return MONITORED_NOCAM;
        }
    }


    /*
    Wrong.
    */
    /*
    BFS, 0-1 partition.

    TC: O(N)
    SC: O(N)
    */
    private int minCameraCoverByBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }

        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        int indicator = 0;
        int count = 0;
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();

            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();
                if (indicator == 1) {
                    count++;
                }

                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                }
            }

            // Flip the indicator.
            if (indicator == 0) {
                indicator = 1;
            } else {
                indicator = 0;
            }
        }

        return count;
    }
}
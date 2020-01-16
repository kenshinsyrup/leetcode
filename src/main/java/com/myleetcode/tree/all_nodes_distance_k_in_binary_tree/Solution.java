package com.myleetcode.tree.all_nodes_distance_k_in_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        return distanceKByDFS(root, target, K);
    }

    /*
    N.
    Two DFS:
    https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/discuss/143798/1ms-beat-100-simple-Java-dfs-with(without)-hashmap-including-explanation

    TC: O(N)
    SC: O(N)
    */
    private List<Integer> distanceKByDFS(TreeNode root, TreeNode target, int K) {
        List<Integer> ret = new ArrayList<>();
        if (root == null || target == null || K < 0) {
            return ret;
        }

        // 1. Get the distance map.
        Map<TreeNode, Integer> nodeDistMap = new HashMap<>();
        getDistanceToTarget(root, target, 0, nodeDistMap);

        // 2. Get the distance K nodes.
        preorderTraversal(root, target, K, nodeDistMap.get(root), nodeDistMap, ret);

        return ret;

    }

    private int getDistanceToTarget(TreeNode curNode, TreeNode targetNode, int dist, Map<TreeNode, Integer> nodeDistMap) {
        if (curNode == null) {
            return -1;
        }

        if (curNode == targetNode) {
            nodeDistMap.put(curNode, 0);
            return 0;
        }

        // If target is in left subtree, then leftDist should not be -1.
        int leftDist = getDistanceToTarget(curNode.left, targetNode, dist, nodeDistMap);
        if (leftDist != -1) {
            nodeDistMap.put(curNode, leftDist + 1);
            return leftDist + 1;
        }

        // If target not in left subtree, and if in the right subtree, rigthDist should not be -1.
        int rightDist = getDistanceToTarget(curNode.right, targetNode, dist, nodeDistMap);
        if (rightDist != -1) {
            nodeDistMap.put(curNode, rightDist + 1);
            return rightDist + 1;
        }

        // If target is not in left and right subtree and target is not curNode, should return -1.
        return -1;
    }

    private void preorderTraversal(TreeNode curNode, TreeNode targetNode, int K, int distToTarget, Map<TreeNode, Integer> nodeDistMap, List<Integer> ret) {
        if (curNode == null) {
            return;
        }

        // If there's dist in map, then we use this value rather than the dist given by parent.
        if (nodeDistMap.containsKey(curNode)) {
            distToTarget = nodeDistMap.get(curNode);
        }
        if (distToTarget == K) {
            ret.add(curNode.val);
        }

        // from the sight of curNode, its distToTarget + 1 is its child nodes distance to target node.
        preorderTraversal(curNode.left, targetNode, K, distToTarget + 1, nodeDistMap, ret);
        preorderTraversal(curNode.right, targetNode, K, distToTarget + 1, nodeDistMap, ret);
    }
}
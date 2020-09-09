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
public class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        // return distanceKByDFS(root, target, K);
        return distanceKByDFSII(root, target, K);
    }

    /*
    LC Solution.
    Nested DFS

    DFS to search target node and get the distance from curNode to target node:
        if curNode == target:
            distance is 0
        if target is in left subtree of curNode:
            distance = leftDist + 1
        if target is in right subtree of curNode:
            distance = rightDist + 1

    And, the important part is, during the above DFS, we also try to record all nodes having distanct K to target.
        if curNode == target:
            all nodes in curNode's subtree which have distance==K are answers
            distance is 0
        if target is in left subtree of curNode:
            distance = leftDist + 1
            since target node is in left of curNode, so to find nodes having distance K to target, we need search in curNode's right subtree, since curNode has leftDist+1 to target, and curNode has 1 to curNode.right, so we want to search in right subtree which have distance leftDist+1+1 == K
        if target is in right subtree of curNode:
            distance = rightDist + 1
            the same as left case, we want to search in left subtree in which nodes have rightDist+1+1==K

    TC: O(N)
    SC: O(N)
    */
    private List<Integer> distanceKByDFSII(TreeNode root, TreeNode target, int K) {
        if (root == null || target == null || K < 0) {
            return new ArrayList<>();
        }

        List<Integer> ret = new ArrayList<>();
        searchTargetAndCaculateDistanceToTarget(root, target, K, ret);

        return ret;
    }

    private int searchTargetAndCaculateDistanceToTarget(TreeNode curNode, TreeNode target, int K, List<Integer> ret) {
        if (curNode == null) {
            return -1;
        }

        // curNode is target node, search in its subtree in which nodes have distance K.
        if (curNode == target) {
            searchInSubtree(curNode, 0, ret, K);

            return 0;
        }

        int leftDistToTarget = searchTargetAndCaculateDistanceToTarget(curNode.left, target, K, ret);
        if (leftDistToTarget != -1) {
            if (leftDistToTarget + 1 == K) { // curNode has distance to target K, it's an answer.
                ret.add(curNode.val);
            } else { // otherwise search curDistance(leftDist+1)+1 in right subtree.
                searchInSubtree(curNode.right, leftDistToTarget + 1 + 1, ret, K);
            }

            return leftDistToTarget + 1;
        }

        int rightDistToTarget = searchTargetAndCaculateDistanceToTarget(curNode.right, target, K, ret);
        if (rightDistToTarget != -1) {
            if (rightDistToTarget + 1 == K) { // curNode has distance to target K, it's an answer.
                ret.add(curNode.val);
            } else { // otherwise search curDistance(rightDist+1)+1 in left subtree.
                searchInSubtree(curNode.left, rightDistToTarget + 1 + 1, ret, K);
            }

            return rightDistToTarget + 1;
        }

        return -1;
    }

    // Search given distance in given tree.
    private void searchInSubtree(TreeNode curNode, int distToTarget, List<Integer> ret, int K) {
        if (curNode == null) {
            return;
        }

        if (distToTarget == K) {
            ret.add(curNode.val);
            return;
        }

        searchInSubtree(curNode.left, distToTarget + 1, ret, K);
        searchInSubtree(curNode.right, distToTarget + 1, ret, K);
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
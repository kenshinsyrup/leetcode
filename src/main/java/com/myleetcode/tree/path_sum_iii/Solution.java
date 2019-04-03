package com.myleetcode.tree.path_sum_iii;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int pathSum(TreeNode root, int sum) {
        return pathSumByDFSAll(root, sum);
    }

    // TC: O(N^2), N recursion to find all nodes, for each node ie every recursion we do O(N) operation
    // SC: O(N^2)
    // intuition: follow up f Path Sum II. in the problem, we should traverse all nodes, use every node as root, then to record path value, if path value is equal to sum we could record it.
    private int pathSumByDFSAll(TreeNode root, int sum){
        if(root == null){
            return 0;
        }

        // current node as root, all valid path number
        int self = pathDFS(root, sum);

        // left tree traverse, keep in mind here is the sum, not sum - root.val.
        int left = pathSumByDFSAll(root.left, sum);
        // right tree traverse, keep in mind here is the sum, not sum - root.val.
        int right = pathSumByDFSAll(root.right, sum);

        // all valid path in left and right subtree plus self
        return self + left + right;

    }

    // return valid path number
    private int pathDFS(TreeNode node, int target){
        // base case
        if(node == null){
            return 0;
        }

        int curRet = 0;
        // if current node equals to target, we find one
        if(node.val == target){
            curRet += 1;
        }

        // no mater current node val is equal to target or not, we need to dfs subtrees, because there negative val in the tree.

        // left path
        int left = pathDFS(node.left, target - node.val);
        // right path
        int right = pathDFS(node.right, target - node.val);

        return curRet + left + right;
    }

}

package com.myleetcode.tree.lowest_common_ancestor_of_deepest_leaves;

import com.myleetcode.utils.tree_node.TreeNode;

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
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return lcaDeepestLeavesByRecursion(root);
    }

    /*
    Recursion:
    https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/discuss/334577/JavaC%2B%2BPython-Two-Recursive-Solution
    https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/discuss/334627/Recursive-Solution-with-Explanation

    If left subtree height is greater, then the result is whatever returned by the left as it has highest depth elements.
    Similarly if right subtree height is greater, then the result is whatever returned by the right as it has highest depth elements.
    If heights of both left and right subtrees are equal then the current node is the common ancestors of the deepest leaves.

    TC: O(N)
    SC: O(H)
    */
    private TreeNode lcaDeepestLeavesByRecursion(TreeNode root) {
        if (root == null) {
            return root;
        }

        return findLCAInDepth(root, 0).lca;
    }

    private Result findLCAInDepth(TreeNode curNode, int depth) {
        if (curNode == null) {
            return new Result(null, depth);
        }

        Result leftRes = findLCAInDepth(curNode.left, depth + 1);
        Result rightRes = findLCAInDepth(curNode.right, depth + 1);

        // Lca is in left.
        if (leftRes.maxDepth > rightRes.maxDepth) {
            return leftRes;
        } else if (leftRes.maxDepth < rightRes.maxDepth) {
            // LCA is in right.
            return rightRes;
        } else {
            // LCA is current(at least now to its subtrees).
            return new Result(curNode, leftRes.maxDepth);
        }
    }

    private class Result {
        int maxDepth; // Current deepest leaves's lca's depth
        TreeNode lca;

        public Result(TreeNode lca, int maxDepth) {
            this.lca = lca;
            this.maxDepth = maxDepth;
        }

    }
}

package com.myleetcode.depth_first_search.maximum_average_subtree;

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
    public double maximumAverageSubtree(TreeNode root) {
        return maximumAverageSubtreeByDFS(root);
    }

    /*
    DFS
    Postorder, if we already know the node sum and node count of left subtree, and the node sum and node count of right subtree, we could easily get current tree's avg and update the max avg.

    TC: O(N)
    SC: O(H)
    */
    private double maximumAverageSubtreeByDFS(TreeNode root) {
        if (root == null) {
            return 0.0;
        }

        Result ret = new Result();
        dfs(root, ret);

        return ret.max;
    }

    private double[] dfs(TreeNode root, Result ret) {
        if (root == null) {
            return null;
        }

        double curTotal = root.val;
        double count = 1;

        double[] left = dfs(root.left, ret);
        if (left != null) {
            curTotal += left[0];
            count += left[1];
        }

        double[] right = dfs(root.right, ret);
        if (right != null) {
            curTotal += right[0];
            count += right[1];
        }

        ret.max = Math.max(ret.max, curTotal / count);

        return new double[]{curTotal, count};
    }

    class Result {
        double max;

        public Result() {
            this.max = Double.MIN_VALUE;
        }
    }
}

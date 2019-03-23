package com.myleetcode.tree.sum_of_left_leaves;

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
    public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeavesByDFS(root);
    }

    // 这个有一个方式就是用一个全局变量保存sum，这样dfs的时候就不需要返回值，而是在找到左叶子的时候把左叶子的值加入sum就好

    // https://leetcode.com/problems/sum-of-left-leaves/discuss/89184/Accepted-Java-solution-using-Recursion-simple-easy-to-understand
    // https://leetcode.com/problems/sum-of-left-leaves/discuss/88950/Java-iterative-and-recursive-solutions
    // dfs with a mark telling us if this node is left child or not
    private int sumOfLeftLeavesByDFS(TreeNode root) {
        // special case
        if(root == null){
            return 0;
        }

        return dfsHelper(root, false);
    }

    // !!! only need leaves value to sum
    private int dfsHelper(TreeNode node, boolean left){
        if(node == null){
            return 0;
        }

        // 如果是左叶子，返回该值
        if(left && node.left == null && node.right == null){
            return node.val;
        }

        // 注意左右子树的leaves的值都需要，因为右子树也是有可能有左叶子的
        int leftValue = dfsHelper(node.left, true);
        int rightValue = dfsHelper(node.right, false);

        // 把左右子树的 左叶子 的和 返回
        return leftValue + rightValue;
    }
}

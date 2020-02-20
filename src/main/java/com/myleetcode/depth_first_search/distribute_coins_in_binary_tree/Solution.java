package com.myleetcode.depth_first_search.distribute_coins_in_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

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
    public int distributeCoins(TreeNode root) {
        return distributeCoinsByDFS(root);
    }

    /*
    DFS
    most important part is know how to caculate the moves needed.

    If the leaf of a tree has 0 coins (an excess of -1 from what it needs), then we should push a coin from its parent onto the leaf.
    If it has say, 4 coins (an excess of 3), then we should push 3 coins off the leaf.
    In total, the number of moves from that leaf to or from its parent is excess = Math.abs(num_coins - 1). Afterwards, we never have to consider this leaf again in the rest of our calculation.

    TC: O(N)
    SC: O(H)
    */
    private int distributeCoinsByDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        postorder(root, ret);

        return ret.steps;
    }

    // Return excess coins of curNode after it keeps only 1 coin for itself.
    private int postorder(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return 0;
        }

        int leftExcessCoins = postorder(curNode.left, ret);
        int rightExcessCoins = postorder(curNode.right, ret);

        // !!!abs of left subtree excess and abs of right subtree excess is the steps needed for this curnode subtree.
        ret.steps += Math.abs(leftExcessCoins) + Math.abs(rightExcessCoins);

        return leftExcessCoins + rightExcessCoins + curNode.val - 1;
    }

    private class Result {
        int steps;

        public Result() {
            this.steps = 0;
        }
    }

}
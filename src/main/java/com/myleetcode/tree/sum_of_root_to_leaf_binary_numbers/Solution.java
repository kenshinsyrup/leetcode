package com.myleetcode.tree.sum_of_root_to_leaf_binary_numbers;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public int sumRootToLeaf(TreeNode root) {
        // return sumRootToLeafByRecursion(root);
        return sumRootToLeafByRecursionII(root);
    }

    /*
    Most imporatnt part is how to keep path val.
    https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/discuss/387149/Java-100-beats-Time-and-Space.-Detailed-explanation.

    TC: O(N)
    SC: O(H)
    */
    private int sumRootToLeafByRecursionII(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        recurseII(root, 0, ret);

        return ret.sum;
    }

    private void recurseII(TreeNode curNode, int pathVal, Result ret) {
        if (curNode == null) {
            return;
        }

        pathVal = 2 * pathVal + curNode.val;
        if (curNode.left == null && curNode.right == null) {
            ret.sum += pathVal;
            return;
        }

        recurseII(curNode.left, pathVal, ret);
        recurseII(curNode.right, pathVal, ret);
    }

    /*
    TC: O(N)
    SC: O(H)
    */
    private int sumRootToLeafByRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        List<Integer> bitList = new ArrayList<>();
        recurse(root, bitList, ret);

        return ret.sum;
    }

    private void recurse(TreeNode curNode, List<Integer> bitList, Result ret) {
        if (curNode == null) {
            return;
        }
        // If leaf.
        if (curNode.left == null && curNode.right == null) {
            bitList.add(curNode.val);
            ret.sum += getVal(bitList);
            bitList.remove(bitList.size() - 1);
            return;
        }

        // Normal nodes.
        bitList.add(curNode.val);
        recurse(curNode.left, bitList, ret);
        recurse(curNode.right, bitList, ret);
        bitList.remove(bitList.size() - 1);

    }

    private int getVal(List<Integer> bitList) {
        int size = bitList.size();
        int val = 0;
        for (int i = size - 1; i >= 0; i--) {
            val += bitList.get(i) * Math.pow(2, (size - 1) - i);
        }
        return val;
    }

    class Result {
        int sum;

        public Result() {
            this.sum = 0;
        }
    }
}

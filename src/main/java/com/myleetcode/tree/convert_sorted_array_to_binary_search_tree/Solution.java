package com.myleetcode.tree.convert_sorted_array_to_binary_search_tree;

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
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTByRecursion(nums);
    }

    private TreeNode sortedArrayToBSTByRecursion(int[] nums) {
        // special case
        if (nums == null || nums.length == 0) {
            return null;
        }

        return buildBalanceBST(nums, 0, nums.length - 1);
    }

    private TreeNode buildBalanceBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return new TreeNode(nums[start]);
        }

        // Make it balanced.
        int mid = start + (end - start) / 2;
        TreeNode curNode = new TreeNode(nums[mid]);

        curNode.left = buildBalanceBST(nums, start, mid - 1);
        curNode.right = buildBalanceBST(nums, mid + 1, end);

        return curNode;

    }
}
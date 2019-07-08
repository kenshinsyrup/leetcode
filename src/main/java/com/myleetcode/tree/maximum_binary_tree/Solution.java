package com.myleetcode.tree.maximum_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

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
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTreeByRecursion(nums);// normal thought
        // return constructMaximumBinaryTreeByDeque(nums); // too hard to figure out and too hard to write in a interview. BUT if could solve and explain clearly, will be great.
    }

    // after the intuition, we could think, is there any places could be optimized? it's easy to see the findMaxIdx func is too costy and if we could reduce its time, we could reduce the total TC. BUT after a while, didnot figure out a way to do this= =.

    // intuition: recursion thought
    // in the given a array [start, end], find the MaxNum and record its idx MaxIdx. then build a node with this MaxNum, then buid left subtree with this recursion func and left subarray [start, MaxIdx-1], then build right subtree with this recursion func and right subarray [MaxIdx+1, end], then construct current tree: left-node-right, return the root node
    // in this sol, find the MaxNum cost O(N), build node cost O(1), build left subtree and right subtree is recursion, the totoal recursion times is O(N-1), so total is O(N^2)
    // TC: O(N^2)
    // SC: O(N)
    private TreeNode constructMaximumBinaryTreeByRecursion(int[] nums){
        if(nums == null || nums.length == 0){
            return null;
        }

        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int start, int end){
        // base case
        if(start < 0 || end >= nums.length || start > end){
            return null;
        }

        // 1 find the maxIdx/maxNum
        int maxIdx = findMaxIdx(nums, start, end);

        // 2 build cur node
        TreeNode curNode = new TreeNode(nums[maxIdx]);

        // 3 build left and right subtree
        TreeNode leftSubtree = buildTree(nums, start, maxIdx - 1);
        TreeNode rightSubtree = buildTree(nums, maxIdx + 1, end);

        curNode.left = leftSubtree;
        curNode.right = rightSubtree;

        return curNode;
    }

    private int findMaxIdx(int[] nums, int start, int end){
        int maxIdx = start;
        for(int i = start; i <= end; i++){
            if(nums[i] > nums[maxIdx]){
                maxIdx = i;
            }
        }

        return maxIdx;
    }


    // sol 2: Deque
    // https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution
    // thought: https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution/143674
    // TC: O(N)
    // SC: O(N)
    private TreeNode constructMaximumBinaryTreeByDeque(int[] nums){
        if(nums == null || nums.length == 0){
            return null;
        }

        Deque<TreeNode> nodeDeque = new ArrayDeque<>();

        int len = nums.length;
        for(int i = 0; i < len; i++){
            // build curNode
            TreeNode curNode = new TreeNode(nums[i]);

            while(!nodeDeque.isEmpty() && nodeDeque.peek().val < curNode.val){
                curNode.left = nodeDeque.pop();
            }
            if(!nodeDeque.isEmpty()){
                TreeNode topNode = nodeDeque.peek();
                topNode.right = curNode;
            }

            nodeDeque.push(curNode);
        }

        if(nodeDeque.isEmpty()){
            return null;
        }

        return nodeDeque.removeLast();
    }

}

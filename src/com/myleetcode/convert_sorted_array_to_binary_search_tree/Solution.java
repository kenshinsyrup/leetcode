package com.myleetcode.convert_sorted_array_to_binary_search_tree;

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
    public TreeNode sortedArrayToBST(int[] nums) {
        
        // special case
        if(nums == null || nums.length == 0){
            return null;
        }
        
        return sortedArrayToBSTRecursive(nums, 0, nums.length - 1);
        
    }
    
    private TreeNode sortedArrayToBSTRecursive(int[] nums, int left, int right){
        
//         exit
        if(left > right){
            return null;
        }
        
//         开始构建当前的树
        // 每次二分数组，就可以满足左右高度差不超过1的条件。当前的mid在sortedArray中自然比左边数据大，不小于右边数据。满足BST条件，所以当前mid就是当前root的val
        int mid = left + (right - left)/2;
        
        // mid作为当前的root，然后构建其左右子节点
        TreeNode root = new TreeNode(nums[mid]);
        
        root.left = sortedArrayToBSTRecursive(nums, left, mid - 1);
        root.right = sortedArrayToBSTRecursive(nums, mid + 1, right);
        
        return root;
    }
}
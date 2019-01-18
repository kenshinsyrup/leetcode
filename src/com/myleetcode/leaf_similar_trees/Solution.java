package com.myleetcode.leaf_similar_trees;

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
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leaves1 = new ArrayList();
        List<Integer> leaves2 = new ArrayList();
        
        // special case
        if(root1 == null && root2 == null){
            return true;
        }
        
        if((root1 != null && root2 == null) || (root1 == null && root2 != null)){
            return false;
        }
        
        // collect all levaes respcetively
        getLeavesByDFS(root1, leaves1);
        getLeavesByDFS(root2, leaves2);
        
        return leaves1.equals(leaves2);
    }
    
    private void getLeavesByDFS(TreeNode n, List<Integer> leaves){
        // 出口
        if(n == null){
            return;
        }
        
        // do your thing
        if(n.left == null && n.right == null){
            leaves.add(n.val);
        }
        
        // 递归
        getLeavesByDFS(n.left, leaves);
        getLeavesByDFS(n.right, leaves);
    }
}
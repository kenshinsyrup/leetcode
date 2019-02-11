package com.myleetcode.minimum_depth_of_binary_tree;

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
    public int minDepth(TreeNode root) {
//         通过bfs来找到第一个leaf即可
        
//         special case
        if(root == null){
            return 0;
        }
        
        return findFirstLeafByBFS(root);
        
        
    }
    
    private int findFirstLeafByBFS(TreeNode root){
        Queue<TreeNode> nodeQ = new LinkedList<TreeNode>();
        
        nodeQ.add(root);
        
        int level = 0;
        
        while(!nodeQ.isEmpty()){
            int size = nodeQ.size();//level control
            level++;
            
            for(int i = 0; i < size; i++){
                TreeNode currentNode = nodeQ.poll();
                if(currentNode.left == null && currentNode.right == null){
                    return level;
                }
                if(currentNode.left != null){
                    nodeQ.add(currentNode.left);
                }
                if(currentNode.right != null){
                    nodeQ.add(currentNode.right);
                }
            }
        }
        
        // have no need, but must have a return.因为一定有叶子节点所以一定不会走这个return
        return level;
    }
}
package com.myleetcode.tree.find_largest_value_in_each_tree_row;

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
    public List<Integer> largestValues(TreeNode root) {
        // bfs and find max in every level
        
        return largestValuesEveryLevel(root);
        
    }
    
    private List<Integer> largestValuesEveryLevel(TreeNode root){
        // store list
        List<Integer> res = new ArrayList<>();
        
        if(root == null){
            return res;
        }
        
        // queue to help
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        
        // start
        nodeQueue.add(root);
        
        while(!nodeQueue.isEmpty()){
            int levelControl = nodeQueue.size(); // level control
            int max = Integer.MIN_VALUE;
            
            for(int i = 0; i < levelControl; i++){
                // current node
                TreeNode curNode = nodeQueue.poll();
                max = Math.max(max, curNode.val);
                
                if(curNode.left != null){
                    nodeQueue.add(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.add(curNode.right);
                }
            }
            
            res.add(max);
            
        }
        
        return res;
    }
}
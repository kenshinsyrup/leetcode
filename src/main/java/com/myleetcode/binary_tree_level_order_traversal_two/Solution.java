package com.myleetcode.binary_tree_level_order_traversal_two;

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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // bfs的方式遍历树，再倒叙输出
        
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if(root == null){
            return ret;
        }
        
        traverseByBFS(root, ret);
        
         List<List<Integer>> reverseRet = new ArrayList<List<Integer>>();
        for(int i = ret.size() - 1; i >= 0; i--){
            reverseRet.add(ret.get(i));
        }
        
        return reverseRet;
        
    }
    
    private void traverseByBFS(TreeNode root, List<List<Integer>> ret){
        Queue<TreeNode> nodeQ = new LinkedList<TreeNode>();
        
        nodeQ.add(root);
        
        while(!nodeQ.isEmpty()){
            int queueSize = nodeQ.size();
            List<Integer> values = new ArrayList<>();
            
            for(int i = 0; i < queueSize; i++){
                TreeNode currentNode = nodeQ.poll();
                values.add(currentNode.val);
                
                if(currentNode.left != null){
                    nodeQ.add(currentNode.left);
                }
                if(currentNode.right != null){
                    nodeQ.add(currentNode.right);
                }
            }
            
            ret.add(values);
            
        }
    }
}
package com.myleetcode.tree.binary_tree_level_order_traversal;

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
    public List<List<Integer>> levelOrder(TreeNode root) {
        // BFS
        
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        
        // special case
        if(root == null){
            return ret;
        }
        
        traverseByBFS(root, ret);
        
        return ret;
        
    }
    
    private void traverseByBFS(TreeNode node, List<List<Integer>> ret){
        // Queue
        Queue<TreeNode> nodeQ = new LinkedList<>();
        
//         queue的基本操作：add，poll,remove,size
        // add first node
        nodeQ.add(node);
        
        // start bfs
        while(!nodeQ.isEmpty()){
//             store current node val to list
            List<Integer> currentValues = new ArrayList<>();
            
//             get this loop's queue size
            int queueSize = nodeQ.size();
            
//             遍历当前Queue中所有node：1 将每个node的子节点入队； 2 将当前size个node val加入currentValues。注意，这样可以保证一次nodeQ为空的判断的是一大层的node。如果没有这个for循环，那么每次一个node操作完就会进行nodeQ是否为空的判断，就是每次遍历一个node而不是一层node。这个for循环相当于一段一段的取queue中的数据而不是一个一个取，这样可以达到分层的目的.
            for(int i = 0; i < queueSize; i++){
//             get current node
                TreeNode currentNode = nodeQ.poll();
                currentValues.add(currentNode.val);
            
//             入队, 先左后右
                if(currentNode.left != null){
                    nodeQ.add(currentNode.left);
                }
                if(currentNode.right != null){
                    nodeQ.add(currentNode.right);
                }
            }
            
//             将该层的value作为array加入ret
            ret.add(currentValues);
            
        }
        
    }
}
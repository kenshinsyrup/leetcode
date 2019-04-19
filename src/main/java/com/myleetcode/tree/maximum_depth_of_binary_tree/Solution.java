package com.myleetcode.tree.maximum_depth_of_binary_tree;

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
    public int maxDepth(TreeNode root) {
        
        // return depthRecursive(root);
        
        // iterative: DFS
        if(root == null){
            return 0;
        }
        return maxDepthByDFS(root);
        
    }
    
//     recursive
    private int depthRecursive(TreeNode node){
        // exit
        if(node == null){
            return 0;
        }
        
//         找到左右子树中depth更大的, 加上自身的高度1，就是当前节点的总高度
        int depthLeft = depthRecursive(node.left);
        int depthRight = depthRecursive(node.right);
        
        return 1 + Math.max(depthLeft, depthRight);
    }
    
//     iterative: DFS(stack), update maxDepth value when reach leaves
    private int maxDepthByDFS(TreeNode root){
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        
//         把非空的root和对应的深度1入栈
        nodeStack.push(root);
        depthStack.push(1);
        
        int max = 0;
        
//         开始stack操作
        while(!nodeStack.isEmpty()){
//            获取当前栈中的node和depth，并得到当前的max
            TreeNode currentNode = nodeStack.pop();
            Integer currentDepth = depthStack.pop();
            
            max = Math.max(max, currentDepth);
            
//             将当前的node的左右子node和对应的depth值入栈
            if(currentNode.left != null){
                nodeStack.push(currentNode.left);
                depthStack.push(currentDepth + 1);
            }
            if(currentNode.right != null){
                nodeStack.push(currentNode.right);
                depthStack.push(currentDepth + 1);
            }
        }
        
        return max;
        
    }
    
}
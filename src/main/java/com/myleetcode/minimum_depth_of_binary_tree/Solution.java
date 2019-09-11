package com.myleetcode.minimum_depth_of_binary_tree;

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
    public int minDepth(TreeNode root) {
        return minDepthByBFS(root);
    }

    /*
    BFS最短路径问题

    Say N is total # of tree nodes
    TC: O(N)
    SC: O(N)
    */
    private int minDepthByBFS(TreeNode root){
        // special case
        if(root == null){
            return 0;
        }

        Deque<TreeNode> nodeQuque = new ArrayDeque<>();
        nodeQuque.offer(root);
        int level = 0;
        while(!nodeQuque.isEmpty()){
            int size = nodeQuque.size();//level control
            level++;

            for(int i = 0; i < size; i++){
                TreeNode currentNode = nodeQuque.poll();
                if(currentNode.left == null && currentNode.right == null){
                    return level;
                }
                if(currentNode.left != null){
                    nodeQuque.offer(currentNode.left);
                }
                if(currentNode.right != null){
                    nodeQuque.offer(currentNode.right);
                }
            }
        }

        // have no need, 因为一定有叶子节点所以一定不会走这个return, but must have a return.
        return level;
    }
}
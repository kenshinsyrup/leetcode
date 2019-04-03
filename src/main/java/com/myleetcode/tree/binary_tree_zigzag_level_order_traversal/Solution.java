package com.myleetcode.tree.binary_tree_zigzag_level_order_traversal;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        return zigzagLevelOrderByBFS(root);
    }

    // TC: O(N^2), where N is the nodes number of the tree. while loop traverse all nodes, so O(N). inside the while loop, we do Collections.reverse which costs O(N). totally O(N^2)
    // SC: O(N)
    // intuition: seems like a BFS, the challenge is when we are at the even level, we need to traverse the node from right to left
    private List<List<Integer>> zigzagLevelOrderByBFS(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        int level = 0;

        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()){
            int size = nodeQueue.size();
            level++;

            List<Integer> tempNodeList = new ArrayList<>();
            for(int i = 0; i < size; i++){
                TreeNode curNode = nodeQueue.poll();
                tempNodeList.add(curNode.val);

                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }

            if(level %2 == 0){
                Collections.reverse(tempNodeList);
                // tempNodeList.reverse();//no such method
            }

            ret.add(tempNodeList);
        }

        return ret;

    }
}

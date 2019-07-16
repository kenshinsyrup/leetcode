package com.myleetcode.tree.average_of_levels_in_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
    public List<Double> averageOfLevels(TreeNode root) {
        return averageOfLevelsByBFS(root);
    }

    // intuition: BFS
    // BFS to traverse all levels, for every level, get the average value. Be careful of the sum of all node.val, use long to avoid overflow
    // TC: O(N)
    // SC: O(N)
    private List<Double> averageOfLevelsByBFS(TreeNode root){
        List<Double> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }

        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()){
            // average of level
            int size = nodeQueue.size();
            long sum = 0;
            for(int i = 0; i < size; i++){
                TreeNode curNode = nodeQueue.poll();
                sum += curNode.val;

                // offer nodes
                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }

            ret.add(sum * 1.0 / size);
        }

        return ret;
    }
}

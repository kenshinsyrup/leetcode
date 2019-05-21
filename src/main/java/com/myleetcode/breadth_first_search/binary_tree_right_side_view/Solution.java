package com.myleetcode.breadth_first_search.binary_tree_right_side_view;

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
    public List<Integer> rightSideView(TreeNode root) {
        return rightSideViewByBFS(root);
    }

    // intuition: BFS
    // use BFS to find all nodes in the most right side, ie the last one in any level
    // TC: O(N)
    // SC: O(N)
    private List<Integer> rightSideViewByBFS(TreeNode root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        // use queue
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        while(!nodeQueue.isEmpty()){
            int size = nodeQueue.size();

            for(int i = 0; i < size; i++){
                TreeNode curNode = nodeQueue.poll();

                // if in the most right, add to ret
                if(i == size - 1){
                    ret.add(curNode.val);
                }

                // en queue
                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }
        }

        return ret;
    }

}

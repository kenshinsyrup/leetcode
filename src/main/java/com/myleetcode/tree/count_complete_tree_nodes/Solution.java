package com.myleetcode.tree.count_complete_tree_nodes;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

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
    public int countNodes(TreeNode root) {
        // return countNodesByBFS(root);
        return countNodesByHeight(root);
    }

    // TC: O(H^2) or say O(log(N) ^ 2), where H is the depth of tree, H is the recursion height and in every recursion we also do a H operation. because H is log(N) where N is the total number of nodes, so we could also say the TC is O(log(N) ^ 2)
    // SC: O(H)
    // to use the attribute of Complete Tree. we know for Full Complete Tree, the nodes number is h^2 -1. So we could caculate the left sub tree height and right sub tree height from root, if the height is the same, then we use the formula; if not, means our Complete Tree is not Full Complete Tree, then we could caculate the total nodes number by: left subtree nodes number + right subtree nodes number + 1
    // https://leetcode.com/problems/count-complete-tree-nodes/discuss/61948/Accepted-Easy-Understand-Java-Solution
    private int countNodesByHeight(TreeNode node){
        if(node == null){
            return 0;
        }


        // 使用h^2 - 1这个公式的时候，注意高度算自己
        int leftHeight = getHeight(node, true);
        int rightHeight = getHeight(node, false);

        if(leftHeight == rightHeight){
            return (1 << leftHeight) - 1; // same as Math.pow(2, leftHeight) - 1
        }else{
            return countNodesByHeight(node.left) + countNodesByHeight(node.right) + 1;
        }
    }

    private int getHeight(TreeNode node, boolean isLeft){
        int height = 0;
        while(node != null){
            height++;
            if(isLeft){
                node = node.left;
            }else{
                node = node.right;
            }
        }
        return height;
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: seems like a BFS to count all nodes, but this seems dont take advantage of the attributes of Complete Tree.
    private int countNodesByBFS(TreeNode root){
        int ret = 0;

        if(root == null){
            return ret;
        }

        Queue<TreeNode> nodeQueue = new ArrayDeque<>();

        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()){
            TreeNode curNode = nodeQueue.poll();

            ret++;

            if(curNode.left != null){
                nodeQueue.offer(curNode.left);
            }
            if(curNode.right != null){
                nodeQueue.offer(curNode.right);
            }
        }

        return ret;
    }
}

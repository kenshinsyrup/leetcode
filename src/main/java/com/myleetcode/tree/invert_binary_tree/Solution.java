package com.myleetcode.tree.invert_binary_tree;

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
    public TreeNode invertTree(TreeNode root) {
        // return invertTreeByDFS(root);
        return invertTreeByBFS(root);
    }

    // TC: O(N), visited all nodes
    // SC: O(N), Queue store N nodes.
    // 最开始想用BFS写，后来感觉可能要存储整个层的nodes不好处理，所以改用了DFS。但实际上，使用BFS并不需要存储整个层的node，我们还是采用交换子node的思路，这样的话只需要处理一个node的两个子，而不是处理一层
    private TreeNode invertTreeByBFS(TreeNode root){
        // base case
        if(root == null){
            return root;
        }

        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()){
            TreeNode curNode = nodeQueue.poll();

            TreeNode temp = curNode.left;
            curNode.left = curNode.right;
            curNode.right = temp;

            if(curNode.left != null){
                nodeQueue.offer(curNode.left);
            }
            if(curNode.right != null){
                nodeQueue.offer(curNode.right);
            }
        }

        return root;
    }

    // TC: O(N),we visited every node
    // SC: O(H), recursion height
    // intuition: maybe we could do a BFS and every level invert all the values, but it seems we need to sotre all nodes of one level and change them.
    // maybe try recursion with DFS, every time we swap node.left and node.right
    private TreeNode invertTreeByDFS(TreeNode node){
        // base case
        if(node == null){
            return node;
        }

        TreeNode left = invertTreeByDFS(node.left);
        TreeNode right = invertTreeByDFS(node.right);

        node.left = right;
        node.right = left;

        return node;
    }
}

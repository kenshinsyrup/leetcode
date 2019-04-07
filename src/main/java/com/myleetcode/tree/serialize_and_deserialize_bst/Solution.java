package com.myleetcode.tree.serialize_and_deserialize_bst;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    public class Codec {

        // looks the same as 297. Serialize and Deserialize Binary Tree, 完全可以用一样的写法来做，这样的话就是完全把BST当作BT来做

        // 但实际上BST的特性在这里是可以用的到的：https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/177617/the-General-Solution-for-Serialize-and-Deserialize-BST-and-Serialize-and-Deserialize-BT
        // 左子一定小于root一定小于右子，所以我们可以用这个做标记在deserialize时来记录当前node是否应该是null，因为如果当前给的value不在父node给定的范围内那么就不是该父node的子

        // TC: O(N)
        // SC: O(N)
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root == null){
                return "";
            }

            StringBuilder sb = new StringBuilder();
            serializeByDFS(root, sb);
            return sb.toString();
        }

        private void serializeByDFS(TreeNode node, StringBuilder sb){
            if(node == null){
                return;
            }

            sb.append(node.val);
            sb.append(",");

            serializeByDFS(node.left, sb);
            serializeByDFS(node.right, sb);
        }


        // TC: O(N)
        // SC: O(N)
        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if(data == null || data.length() == 0 || data.equals("")){
                return null;
            }

            // convert String data to Queue for convience
            String[] dataArray = data.split(",");
            Queue<String> dataQueue = new ArrayDeque<>();
            for(int i = 0; i < dataArray.length; i++){
                dataQueue.offer(dataArray[i]);
            }

            return deserializeByDFS(dataQueue, Integer.MIN_VALUE, Integer.MAX_VALUE);

        }

        private TreeNode deserializeByDFS(Queue<String> dataQueue, int low, int high){
            // base case
            if(dataQueue == null || dataQueue.size() == 0){
                return null;
            }

            // check the first
            String valStr = dataQueue.peek();
            int val = Integer.valueOf(valStr);
            // if higher than parent(should be left child but not) or lower than parent(should be right child but not), then this child position is a null child
            if(val < low || val > high){
                return null;
            }

            // remove this val and build node
            dataQueue.poll();
            TreeNode node = new TreeNode(val);

            // left child
            TreeNode leftNode = deserializeByDFS(dataQueue, low, val);
            // right child
            TreeNode rightNode = deserializeByDFS(dataQueue, val, high);

            node.left = leftNode;
            node.right = rightNode;

            return node;

        }
    }

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
}

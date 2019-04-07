package com.myleetcode.tree.serialize_and_deserialize_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

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

        //https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74253/Easy-to-understand-Java-Solution

        // TC: O(N), N is the number of nodes
        // SC: O(N)
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root == null){
                return "null";// here return "null" otherwise we could not deserialize this null tree properly
            }

            // return serializeByDFS(root);
            StringBuilder sb = new StringBuilder();
            serializeByDFSAndSB(root, sb);
            return sb.toString();
        }

        private void serializeByDFSAndSB(TreeNode node, StringBuilder sb){
            if(node == null){
                sb.append("null,");
                return;
            }

            sb.append(node.val);
            sb.append(",");

            serializeByDFSAndSB(node.left, sb);
            serializeByDFSAndSB(node.right, sb);
        }

        // 可以用StringBuilder来优化避免创建太多的String对象，需要稍微改变下代码
        private String serializeByDFS(TreeNode node){
            if(node == null){
                return "null,";
            }

            String nodeStr = node.val + ",";

            String leftStr = serializeByDFS(node.left);
            String rightStr = serializeByDFS(node.right);

            return nodeStr + leftStr + rightStr;
        }

        // TC: O(N) with Queue, O(N^2) with List
        // SC: O(N)
        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // convert string data to list for convience
            // could not write code like this: List<String> dataList = Arrays.asList(data.split(",")); because Arrays.asList gives you a fixed-length list, could not do remove or add operations and so on.
            // List<String> dataList = new ArrayList<>(Arrays.asList(data.split(",")));

            // return deserializeByDFS(dataList);

            // convert String data to queue
            String[] dataArray = data.split(",");
            Queue<String> dataQueue = new ArrayDeque<>();
            for(int i = 0; i < dataArray.length; i++){
                dataQueue.offer(dataArray[i]);
            }

            return deserializeByDFSAndQueue(dataQueue);
        }

        private TreeNode deserializeByDFSAndQueue(Queue<String> dataQueue){
            // base case
            if(dataQueue == null || dataQueue.size() == 0){
                return null;
            }

            String valStr = dataQueue.poll();
            if(valStr.equals("null")){
                return null;
            }

            int val = Integer.valueOf(valStr);
            TreeNode node = new TreeNode(val);

            TreeNode leftNode = deserializeByDFSAndQueue(dataQueue);
            TreeNode rightNode = deserializeByDFSAndQueue(dataQueue);

            node.left = leftNode;
            node.right = rightNode;

            return node;

        }

        // 这个地方的dataList可以用Queue来做，会更快，因为理论上来说list的remove应该是O(N)操作，而我们可以用Queue来做到O(1)
        private TreeNode deserializeByDFS(List<String> dataList){
            if(dataList == null || dataList.size() == 0){
                return null;
            }

            // get first elem
            String valStr = dataList.get(0);
            // and then remove the first elem because we have used it
            dataList.remove(0);

            // if "null"
            if(valStr.equals("null")){
                return null;
            }

            TreeNode node = new TreeNode(Integer.valueOf(valStr));

            TreeNode left = deserializeByDFS(dataList);
            TreeNode right = deserializeByDFS(dataList);

            node.left = left;
            node.right = right;

            return node;

        }
    }

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
}
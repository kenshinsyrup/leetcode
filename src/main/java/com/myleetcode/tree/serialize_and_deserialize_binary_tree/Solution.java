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

        // DFS解法
        //https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74253/Easy-to-understand-Java-Solution
        // TC: O(N), N is the number of nodes
        // SC: O(N)
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root == null){
                return "null";// here return "null" otherwise we could not deserialize this null tree properly
            }

            // DFS 解法1
            // return serializeByDFS(root);

            // DFS 解法2
            // StringBuilder sb = new StringBuilder();
            // serializeByDFSAndSB(root, sb);
            // return sb.toString();

            // BFS 解法
            return serializeByBFS(root);
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

        // BFS解法,建议
        // a little change to the traditional BFS
        // TC: O(N)
        // SC: O(N)
        private String serializeByBFS(TreeNode root){
            List<String> strList = new ArrayList<>();
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            nodeQueue.offer(root);
            while(!nodeQueue.isEmpty()){
                TreeNode curNode = nodeQueue.poll();

                // process null node
                if(curNode == null){
                    strList.add("null");
                    continue;
                }

                // non-null node
                strList.add(Integer.toString(curNode.val));

                // since we need process null node as "null", so we just offer all children in queue
                nodeQueue.offer(curNode.left);
                nodeQueue.offer(curNode.right);
            }
            // remove the tailing null
            while(strList.get(strList.size() - 1).equals("null")){
                strList.remove(strList.size() - 1);
            }

            // build String
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < strList.size(); i++){
                sb.append(strList.get(i));

                if(i != strList.size() - 1){
                    sb.append(",");
                }
            }

            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // DFS 解法1
            // convert string data to list for convience
            // could not write code like this: List<String> dataList = Arrays.asList(data.split(",")); because Arrays.asList gives you a fixed-length list, could not do remove or add operations and so on.
            // String[] dataArray = data.split(",");
            // List<String> dataList = new ArrayList<>();
            // Collections.addAll(dataList, dataArray);
            // return deserializeByDFS(dataList);

            // DFS 解法2
            // convert String data to queue
//         String[] dataArray = data.split(",");
//         Queue<String> dataQueue = new ArrayDeque<>();
//         for(int i = 0; i < dataArray.length; i++){
//             dataQueue.offer(dataArray[i]);
//         }
//         return deserializeByDFSAndQueue(dataQueue);

            // BFS 解法
            return deserializeByBFS(data);
        }

        // BFS deserialize解法，建议
    /*
    !!! 1) the implementation of Queue to allow null value(LinkedList rather than ArrayDeque)
    2) the algorithm to deserialize using Queue
    */
        // TC: O(N)
        // SC: O(N)
        private TreeNode deserializeByBFS(String data){
            // special case, if data is "null", return null notd immidiately
            if(data == null || data.length() == 0 || data.equals("null")){
                return null;
            }

            // convert String data to queue
            String[] dataArray = data.split(",");

            Queue<TreeNode> nodeQueue = new LinkedList<>();
            TreeNode root = new TreeNode(Integer.valueOf(dataArray[0]));
            nodeQueue.offer(root);

            for(int i = 1; i < dataArray.length; i++){
                TreeNode curNode = nodeQueue.poll();

                // left
                if(!dataArray[i].equals("null")){
                    TreeNode leftNode = new TreeNode(Integer.valueOf(dataArray[i]));
                    curNode.left = leftNode;

                    nodeQueue.offer(leftNode);
                }

                // right
                i++; // must check i < dataArray.length after increase i
                if(i < dataArray.length && !dataArray[i].equals("null")){
                    TreeNode rightNode = new TreeNode(Integer.valueOf(dataArray[i]));
                    curNode.right = rightNode;

                    nodeQueue.offer(rightNode);
                }
            }

            return root;
        }

        // DFS deserialize解法
        // 解法2: DFS 配合 Queue
        // 解法1中的List可以用Queue来处理，会更快，因为理论上来说list的remove应该是O(N)操作，而我们可以用Queue来做到O(1)
        // TC: O(N)
        // SC: O(N)
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

        // 解法1: DFS 配合 List
        // TC: O(N^2)
        // SC: O(N)
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
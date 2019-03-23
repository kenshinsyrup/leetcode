package com.myleetcode.tree.vertical_order_traversal_of_a_binary_tree;

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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        return verticalTraversalByBFS(root);
    }

    // 这个题看起来难，仔细想一下好想挺简单，但是实际写起来又有点难
    // 重点有两个：
    // 1 是针对 whenever the vertical line touches some nodes, we report the values of the nodes in order from top to bottom (decreasing Y coordinates).
// 要能想到左右分叉算权重的方式来得到处在同一"Y"值上的node
    // 2是针对 If two nodes have the same position, then the value of the node that is reported first is the value that is smaller. 实际说的是每一层的node需要按从小到大的顺序排列，这个是最初写的时候没有意识到的

    // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/discuss/231140/Add-clarification-for-the-output-order

    // intuition: root as i, then left child is i-1, right child is i+1, for the start, we make root i == 0. we want i as key and its coresponding value is list of nodes. at last turn to a whole list to output.
    // BFS seems a good way
    private List<List<Integer>> verticalTraversalByBFS(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();

        // special case
        if(root == null){
            return ret;
        }

        // Deque as Queue, we need keep track of node and its "X" direction value
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        Deque<Integer> valueQueue = new ArrayDeque<>();
        // Map to keep key-value
        Map<Integer, List<Integer>> valueNodeMap = new HashMap<>();

        // 如果不想使用min和max来保存我们在X方向上向两边走的边界，那么可以使用TreeMap，总之我们需要一个根据X方向上的值从小到大来输出map的方式，普通map的key遍历是随机的
        int min = 0;
        int max = 0;

        nodeQueue.offer(root);
        valueQueue.offer(0);
        while(!nodeQueue.isEmpty()){
            int queueSize = nodeQueue.size();

            // !!!暂存每一行的node，存好排序value list，然后再存入valueNodeMap，这样才能保证同一行的node是从小到大的，而不同行的node还是按照从上到下的顺序
            Map<Integer, List<Integer>> levelMap = new HashMap<>();

            for(int i = 0; i < queueSize; i++){
                TreeNode curNode = nodeQueue.poll();
                int curValue = valueQueue.poll();

                // !!!这里不能直接存入valueNodeMap
                levelMap.putIfAbsent(curValue, new ArrayList<>());
                levelMap.get(curValue).add(curNode.val);

                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                    valueQueue.offer(curValue - 1);

                    min--;
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                    valueQueue.offer(curValue + 1);

                    max++;
                }
            }

            // !!!这是这个题的坑点。这里需要对map保存的value lsit进行sort，因为题目要求在同一行的node，需要根据val从小到大的保存.
            for(int k: levelMap.keySet()){
                valueNodeMap.putIfAbsent(k, new ArrayList<>());
                List<Integer> list = levelMap.get(k);
                Collections.sort(list);
                valueNodeMap.get(k).addAll(list);
            }

        }

        for(int i = min; i <= max; i++){
            if(valueNodeMap.get(i) != null){
                ret.add(valueNodeMap.get(i));
            }
        }

        return ret;
    }

}

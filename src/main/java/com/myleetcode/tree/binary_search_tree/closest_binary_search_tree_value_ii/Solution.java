package com.myleetcode.tree.binary_search_tree.closest_binary_search_tree_value_ii;

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
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // return closestKValuesByBFS(root, target, k);
        return closestKValuesByInorder(root, target, k);
    }

    // for the follow up, check here: https://leetcode.com/problems/closest-binary-search-tree-value-ii/discuss/281703/From-O(n)-to-O(logn+k)-in-extreme-details

    // TC: O(K) best, O(N) worst
    // SC: O(K)
    // but we actually dont nee the PQ thing, because BST is already ordered
    // we just need a List, then we inorder traverse the BST,
    // if List is smaller than K, add node into the List;
    // if not, then we know the nodes in List is ascending ordered, so we just need to compare the first node's distance to target with the curNode's distance with target,
    //if curNode is closer, then remove the first node(and because other nodes are larger than first and smaller than curNode, so they are all closer than first, so we remove first)
    // if not, then all nodes after curNode is farther than curNode to target so we have already get all k nodes closest to target
    // but, we'd better use a Queue instead of the List, because Queue could use O(1) time to reach and remove first node, and the space cost is the same with List.
    private List<Integer> closestKValuesByInorder(TreeNode root, double target, int k) {
        List<Integer> ret = new ArrayList<>();

        if(root == null || k < 0){
            return ret;
        }

        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        inorder(root, target, k, nodeQueue);

        while(!nodeQueue.isEmpty()){
            ret.add(nodeQueue.poll().val);
        }

        return ret;
    }

    private void inorder(TreeNode curNode, double target, int k, Deque<TreeNode> nodeQueue){
        if(curNode == null){
            return;
        }

        inorder(curNode.left, target, k, nodeQueue);

        // compare
        if(nodeQueue.size() < k){
            nodeQueue.offer(curNode);
        }else{
            TreeNode firNode = nodeQueue.peek();
            // if curNode is closer, offer and poll the first
            if(Math.abs(firNode.val - target) > Math.abs(curNode.val - target)){
                nodeQueue.poll();
                nodeQueue.offer(curNode);
            }else{
                // if curNode is farther, then all nodes after curNode are much farther, so we have get our k nodes closest to target, return;
                return;
            }
        }

        inorder(curNode.right, target, k, nodeQueue);
    }

    // this is a general solution, acutally dont need tobe BST, and cost more time and space
    // intuition: use a k size PQ to store ret, PQ is descending by abs(node.val - target), so the first node is the farest in the closest k nodes.
    // when meet a node: 1 if PQ is not full, offer it; 2 if PQ is full, if node is closer than firstNode in PQ, replace, if not, process next node.
    // But, until now, we dont use any attributes of BST.  we could optimize, if curNode is smaller than target and curNode is not valid to goto the PQ, then left subtree of it all could not; if curNode is larger than target and curNode is not valid to goto the PQ, then right subtree of it all could not
    // so, this process could be done with BFS or DFS, but since the tree is BST, then BFS is better because BST father and children are ordered and use BFS could visit them easily
    // TC: O(K * N)
    // SC: O(K + N)
    private List<Integer> closestKValuesByBFS(TreeNode root, double target, int k){
        List<Integer> ret = new ArrayList<>();

        if(root == null || k < 0){
            return ret;
        }

        // PQ, descending by distance to target
        PriorityQueue<TreeNode> nodePQ = new PriorityQueue<>((node1, node2)->{
            if(Math.abs(node1.val - target) > Math.abs(node2.val - target)){
                return -1;
            }
            return 1;
        });

        // Queue, help BFS
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        while(!nodeQueue.isEmpty()){
            TreeNode curNode = nodeQueue.poll();

            // if PQ is not full of k, offer
            if(nodePQ.size() < k){
                nodePQ.offer(curNode);

                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
                continue;
            }

            TreeNode curNodePQ = nodePQ.peek();

            // if curNode is closer
            if(Math.abs(curNode.val - target) < Math.abs(curNodePQ.val - target)){
                nodePQ.poll();
                nodePQ.offer(curNode);

                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
                continue;
            }

            // curNode is farther, then we pick subtree
            if(curNode.val < target){
                if(curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }else{
                if(curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
            }
        }

        while(!nodePQ.isEmpty()){
            ret.add(nodePQ.poll().val);
        }

        return ret;

    }
}

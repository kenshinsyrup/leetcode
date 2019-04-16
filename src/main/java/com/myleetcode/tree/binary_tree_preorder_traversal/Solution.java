package com.myleetcode.tree.binary_tree_preorder_traversal;

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
    public List<Integer> preorderTraversal(TreeNode root) {
        return preorderTraversalByRecursive(root);
        // return preorderTraversalByIterative(root);
    }

    // TC: O(N), all nodes recursion depth is N
    // SC: O(N), recursion depth
    // DFS traverse tree: recursive by recursion, iterative by Stack
    private List<Integer> preorderTraversalByRecursive(TreeNode root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        dfsPreorder(root, ret);

        return ret;
    }

    private void dfsPreorder(TreeNode node, List<Integer> ret){
        // base
        if(node == null){
            return;
        }

        ret.add(node.val);
        dfsPreorder(node.left, ret);
        dfsPreorder(node.right, ret);
    }

    // poor test: when input is [3,1,2], if we push left node first and then right node, we will output [3,2,1] and test says it's wrong. after change the push order, first push right then left we could pass the test. weird.
    // TC: O(N), all nodes
    // SC: O(N), stack all nodes
    private List<Integer> preorderTraversalByIterative(TreeNode root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);

        while(!nodeStack.isEmpty()){
            TreeNode curNode = nodeStack.pop();
            ret.add(curNode.val);

            if(curNode.right != null){
                nodeStack.push(curNode.right);
            }
            if(curNode.left != null){
                nodeStack.push(curNode.left);
            }
        }

        return ret;
    }
}

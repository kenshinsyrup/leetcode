package com.myleetcode.tree.binary_tree_postorder_traversal;

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
    public List<Integer> postorderTraversal(TreeNode root) {
        // return postorderTraversalByIterative(root);
        return postorderTraversalByRecursive(root);
    }

    // TC: O(N), N nodes recursion depth
    // SC: O(N), recursion depth
    private List<Integer> postorderTraversalByRecursive(TreeNode root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        postorderDFS(root, ret);

        return ret;
    }

    private void postorderDFS(TreeNode node, List<Integer> ret){
        // base
        if(node == null){
            return;
        }

        postorderDFS(node.left, ret);
        postorderDFS(node.right, ret);
        ret.add(node.val);
    }

    // TC: O(N), traverse all nodes
    // SC: O(N), two stack for all nodes, 2*N
    // postorder in iterative way is tricky, it's the reverse order of inorder, so we could just use a stack to store the node poped by preorder, then pop them out.
    private List<Integer> postorderTraversalByIterative(TreeNode root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        Deque<TreeNode> preorderStack = new ArrayDeque<>();
        preorderStack.push(root);
        Deque<TreeNode> postorderStack = new ArrayDeque<>();
        while(!preorderStack.isEmpty()){
            TreeNode curNode = preorderStack.pop();
            postorderStack.push(curNode);
            if(curNode.left != null){
                preorderStack.push(curNode.left);
            }
            if(curNode.right != null){
                preorderStack.push(curNode.right);
            }
        }

        while(!postorderStack.isEmpty()){
            ret.add(postorderStack.pop().val);
        }

        return ret;
    }
}

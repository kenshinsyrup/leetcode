package com.myleetcode.tree.binary_tree_inorder_traversal;

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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }

        // recursive
        // inorderTraversalByRecursive(root, res);

        // iterative
        // inorderTraversalByIterative(root, res);
        inorderTraversalByIterativeII(root, res);

        return res;

    }

    // a more concise thoughts and codes: https://leetcode.com/problems/binary-tree-inorder-traversal/discuss/31213/Iterative-solution-in-Java-simple-and-readable/29960
    private void inorderTraversalByIterativeII(TreeNode root, List<Integer> ret){
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        pushNodeAndAllLeft(root, nodeStack);

        while(!nodeStack.isEmpty()){
            TreeNode curNode = nodeStack.pop();
            ret.add(curNode.val);

            curNode = curNode.right;
            pushNodeAndAllLeft(curNode, nodeStack);
        }
    }

    private void pushNodeAndAllLeft(TreeNode node, Deque<TreeNode> nodeStack){
        while(node != null){
            nodeStack.push(node);
            node = node.left;
        }
    }

    // Iterative。理解了inorder的traversal的原理才能写出，不像recursive方式直接背一下就好了。
//     https://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/
//     https://leetcode.com/problems/binary-tree-inorder-traversal/discuss/31404/Concise-JAVA-solution-based-on-Stack
    private void inorderTraversalByIterative(TreeNode node, List<Integer> res){
        Stack<TreeNode> nodeStack = new Stack<TreeNode>();

        while(node != null || !nodeStack.isEmpty()){
            while(node != null){
                nodeStack.push(node);
                node = node.left;
            }

            node = nodeStack.pop();
            res.add(node.val);

            node = node.right;

        }
    }


    // Recursive方式，比较直观简单，是最基本的要掌握的inorder，preorder，postorder的recursive方式遍历
    private void inorderTraversalByRecursive(TreeNode node, List<Integer> res){
        // exit
        if(node == null){
            return;
        }

        inorderTraversalByRecursive(node.left, res);
        res.add(node.val);
        inorderTraversalByRecursive(node.right, res);

    }
}
package com.myleetcode.tree.n_ary_tree_postorder_traversal;


import com.myleetcode.utils.n_aray_tree_node.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    public List<Integer> postorder(Node root) {
        return postorderByRecursive(root);
        // return postorderByIterative(root);
    }

    // intuition: recursive is the same as binary tree. iterative is also the same as binary tree.

    // TC: O(N), N recursion
    // SC: O(N), N recursion depty
    private List<Integer> postorderByRecursive(Node root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        postorderDFS(root, ret);

        return ret;
    }

    private void postorderDFS(Node node, List<Integer> ret){
        // base
        if(node == null){
            return;
        }

        for(Node child: node.children){
            postorderDFS(child, ret);
        }

        ret.add(node.val);
    }

    // TC: O(N), all nodes
    // SC: O(N), two stack use O(2*N) space
    private List<Integer> postorderByIterative(Node root){
        List<Integer> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        Deque<Node> preorderStack = new ArrayDeque<>();
        preorderStack.push(root);

        Deque<Node> postorderStack = new ArrayDeque<>();

        while(!preorderStack.isEmpty()){
            Node curNode = preorderStack.pop();
            postorderStack.push(curNode);

            for(Node child: curNode.children){
                preorderStack.push(child);
            }
        }

        while(!postorderStack.isEmpty()){
            ret.add(postorderStack.pop().val);
        }

        return ret;

    }
}

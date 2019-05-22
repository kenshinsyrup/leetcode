package com.myleetcode.tree.populating_next_right_pointers_in_each_node;

import com.myleetcode.utils.tree_link_node.Node;

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val,Node _left,Node _right,Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        return connectByDFS(root);
    }

    // the problem need constant extra space but allow us use recursion, so we dont use the BFS with Queue, use the DFS Recursion
    // because to a node, if it's the left child of father, so it's next is father's right; if it's the right child of father, so it's next is its father's next's left. so we need first process father node then children node, so we use preorder DFS
    private Node connectByDFS(Node root){
        if(root == null){
            return null;
        }

        preorderDFS(root);

        return root;

    }

    private void preorderDFS(Node curNode){
        if(curNode == null){
            return;
        }

        // !!!
        if(curNode.left != null){
            curNode.left.next = curNode.right;
        }
        // !!!
        if(curNode.right != null){
            if(curNode.next != null){
                curNode.right.next = curNode.next.left;
            }else{
                curNode.right.next = null;
            }
        }

        preorderDFS(curNode.left);
        preorderDFS(curNode.right);
    }

}

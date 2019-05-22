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
        // return connectByDFS(root); // only for 116 "perfect binary tree", could not work for 117
        return connectByBFS(root); // better
    }

    // this kind of problems are BFS, but Iterative BFS with Queue need extra space, but this problem dont allow
    // so we use the Node DS itself to help us with the BFS
    // TC: O(N)
    // SC: O(1)
    private Node connectByBFS(Node root){
        if(root == null){
            return null;
        }

        // curRoot move from left to right in a level act as a current tree's root
        Node curRoot = root;

        // use a extra dummy node to help us remember the first node in a level
        Node dummyRowHead = new Node(-1, null, null, null);
        // use a extra dummy node to help us traverse the level
        Node dummyRowTraveler = dummyRowHead;

        while(curRoot != null){

            // level traversal
            while(curRoot != null){
                // must from left to right traverse the curRoot's child level to link them
                if(curRoot.left != null){
                    // make .next points to curRoot's left node
                    dummyRowTraveler.next = curRoot.left;

                    // move pointer
                    dummyRowTraveler = dummyRowTraveler.next;
                }
                if(curRoot.right != null){
                    // make .next points to curRoot's right node
                    dummyRowTraveler.next = curRoot.right;

                    // move pointer
                    dummyRowTraveler = dummyRowTraveler.next;
                }

                // curRoot move to right in it's level
                curRoot = curRoot.next;
            }
            // !!! and, we shoul keep in mind that the dummyRowTraverler now is points to the last node in the level, so we need to make it's next to null. other wise, the last node in one level will have its next pointer point to next level's first node
            dummyRowTraveler.next = null;
            // !!! and, we now let dummyRowTraveler back to the dummyRowHead to get ready for next level's traversal
            dummyRowTraveler = dummyRowHead;

            // after done the curRoot's level, move curRoot to next level which is recorded by the dummyRowHead
            curRoot = dummyRowHead.next;
        }


        return root;
    }

    // the problem need constant extra space but allow us use recursion, so we dont use the BFS with Queue, use the DFS Recursion
    // because to a node, if it's the left child of father, so it's next is father's right; if it's the right child of father, so it's next is its father's next's left. so we need first process father node then children node, so we use preorder DFS
    // TC: O(N)
    // SC: O(H), but the problem dont think recursion is extra space so is O(1)
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
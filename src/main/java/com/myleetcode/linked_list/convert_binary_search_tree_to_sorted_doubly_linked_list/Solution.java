package com.myleetcode.linked_list.convert_binary_search_tree_to_sorted_doubly_linked_list;

import com.myleetcode.utils.node.Node;

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/
class Solution {
    public Node treeToDoublyList(Node root) {
        // return treeToDoublyListByInorder(root); // use global variable to keep headNode and lastNode is the disadvantage
        return treeToDoublyListByDivideAndConquer(root); // use this
    }

    // sol 2: Divide and Conquer
    // https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/discuss/154659/Divide-and-Conquer-without-Dummy-Node-Java-Solution
    // this is more interesting, the thought is: first, we divide a given BST to three part, leftSubtree, curNode, rightSubtree. we process leftSubtree and rightSubtree with our conquer func, which could return DDL of given BST. so we have leftDDL and rightDDL now, then we build the DDL of our curNode, ie, curNode.left = curNode; curNode.right = curNode; then we get 3 DDL now. Then the last step is to connect the 3 DDL.
    // TC: O(N), visit all nodes
    // SC: O(N), recursion apply on all nodes
    private Node treeToDoublyListByDivideAndConquer(Node root){
        if(root == null){
            return null;
        }

        return divideAndConquer(root);
    }

    private Node divideAndConquer(Node curNode){
        if(curNode == null){
            return null;
        }

        // left and right subtree DDL
        Node leftSubtreeDDL = divideAndConquer(curNode.left);
        Node rightSubtreeDDL = divideAndConquer(curNode.right);

        // cur node DDL
        curNode.left = curNode;
        curNode.right = curNode;

        // conquer: connect DDL
        return connect(connect(leftSubtreeDDL, curNode), rightSubtreeDDL);
    }

    private Node connect(Node n1Head, Node n2Head){
        if(n1Head == null){
            return n2Head;
        }
        if(n2Head == null){
            return n1Head;
        }

        Node n1Tail = n1Head.left;
        Node n2Tail = n2Head.left;
        n1Tail.right = n2Head;
        n2Head.left = n1Tail;
        n1Head.left = n2Tail;
        n2Tail.right = n1Head;

        return n1Head;
    }

    // sol 1: Inorder Traversal
    // intuition:
    // solution section
    // 1 we need a Node headNode to store the idx 0th node in the BST. when do inorder traversal, we keep update the headNode until the very left end, and when we back from the very left end, the last will be updated, so we use the lastNode == null to help us to find the headNode.
    // 2 inorder traverse the BST, this way we know, last node we visited and current node we are visiting should be connected: lastNode.right = curNode; curNode.left = lastNode; (here we must use curNode.left because in the inorder traversal, the left is processed when we are visiting curNode but curNode.right is not, we must dont change the curNode.right). Then, curNode is the last one, so we update it.
    private Node headNode;
    private Node lastNode;

    private Node treeToDoublyListByInorder(Node root){
        if(root == null){
            return null;
        }

        inorderByRecursion(root);

        lastNode.right = headNode;
        headNode.left = lastNode;

        return headNode;
    }

    private void inorderByRecursion(Node curNode){
        if(curNode == null){
            return;
        }

        inorderByRecursion(curNode.left);

        if(lastNode == null){ // !!! find the head node
            headNode = curNode;
        }else{ // connect the current node and last node, this is the inorder connection
            curNode.left = lastNode;
            lastNode.right = curNode;
        }
        lastNode = curNode;

        inorderByRecursion(curNode.right);
    }
}

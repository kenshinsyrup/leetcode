package com.myleetcode.tree.populating_next_right_pointers_in_each_node_ii;

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
        return connectByBFS(root);
    }

    // this kind of problems are BFS, but Iterative BFS with Queue need extra space, but this problem dont allow
    // https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/discuss/37811/Simple-solution-using-constant-space
    // so we use the Node DS itself to help us with the BFS
    // intuition: the only difference from 116. Populating Next Right Pointers in Each Node is in 116 the binary tree is "given a perfect binary tree where all leaves are on the same level, and every parent has two children"
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
}

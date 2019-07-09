package com.myleetcode.linked_list.flatten_a_multilevel_doubly_linked_list;

import com.myleetcode.utils.node_with_child.Node;

/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node() {}

    public Node(int _val,Node _prev,Node _next,Node _child) {
        val = _val;
        prev = _prev;
        next = _next;
        child = _child;
    }
};
*/
class Solution {
    public Node flatten(Node head) {
        // return flattenByRecursion(head); // use a Global to keep prev node
        return flattenByRecursionII(head); // without Global
    }

    // sol 2: optimize sol 1
    // global variable is always bad, try to remove it. we could return the last node after we done with a chain then its parent chain gets the "prevNode"
    public Node flattenByRecursionII(Node head) {
        if (head == null){
            return null;
        }

        flatII(head);

        return head;
    }

    //return the tail of the current branch...
    private Node flatII(Node curNode) {
        // if has child, then child chain's tail is parent chain's prevNode
        if (curNode.child != null) {
            Node childTail = flatII(curNode.child);

            // now curNode and its nextNode(maybe null) is connected, and we have got the child chain tailNode, we should connect like this curNode->curNode.child....childTail->nextNode
            // keep curNode.next
            Node nextNode = curNode.next;

            // 1 curNode.next link curNode.child, curNode.child.prev lind curNode, curNode.child set null
            curNode.next = curNode.child;
            curNode.child.prev = curNode;
            curNode.child = null;

            // 2 lind the childTail.next to nextNode
            childTail.next = nextNode;

            // 3 get the end of this chain
            // 3.1 if nextNode not null, keep flat this chain, the end is there
            if(nextNode != null){
                nextNode.prev = childTail;
                return flatII(nextNode);
            }

            // 3.2 otherwise, this chain is end, return the end of this chain
            return childTail;
        }

        // if has next
        if (curNode.next != null) {
            return flatII(curNode.next);
        }

        // other wise, curNode is end node
        return curNode;
    }


    // sol 1: like Preorder Recursion with Global Variable
    // intuition: Recursion
    // like preorder traversal, recursively process a given Node: if null return; link itself with prev; if child is not null, process child; process next
    // TC: O(N)
    // SC: O(N)
    Node prevNode;
    private Node flattenByRecursion(Node head){
        if(head == null){
            return null;
        }

        flat(head);

        return head;
    }

    private void flat(Node curNode){
        if(curNode == null){
            return;
        }

        if(prevNode != null){
            prevNode.next = curNode;
            curNode.prev = prevNode; // must update curNode.prev, this is DLL
        }

        prevNode = curNode;

        Node next = curNode.next;
        if(curNode.child != null){
            flat(curNode.child);
            curNode.child = null;
        }

        flat(next);
    }
}

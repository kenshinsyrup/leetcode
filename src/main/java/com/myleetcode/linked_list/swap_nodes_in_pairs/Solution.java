package com.myleetcode.linked_list.swap_nodes_in_pairs;

import com.myleetcode.utils.list_node.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        // return swapPairsByThreePointers(head);
        return swapPairsByRecursion(head);
    }

    // TC: O(N)
    // SC: O(N), recursion tree depth
    // after checked the Discuss, I found the recursion way is so easy to write...
    private ListNode swapPairsByRecursion(ListNode head){
        return swap(head);
    }

    private ListNode swap(ListNode node){
        if(node == null || node.next == null){
            return node;
        }

        // next node of given node
        ListNode nextNode = node.next;

        // finish the back of the node, node.next points to swap(...)
        node.next = swap(node.next.next);
        // nextNode.next points to node
        nextNode.next = node;

        // after swap, we return the nextNode because it's the child node of node's parent node
        return nextNode;
    }


    // TC: O(N)
    // SC: O(1)
    // intuition:
    // 1 three pointers, preSlowPointer, slowPointer and fastPointer. slowPointer and fastPointer keep a gap of 1. swap is: preSlowPointer.next = fastPointer; temp = fastPointer.next; fastPointer.next = slowPointer; slowPointer.next = temp;
    // 2 every time slow and fast move forward by 2 until end. but should be carefule here, when we swap the nodes, then the slow.next.next and fast.next.next are all different with original nodes, and we need the original: slowPointer.next.next and fastPointer.next.next
    // 3 there's a case that because we use while(fast != null && fast.next != null) to keep moving and avoid null pointer exception, then we may has the situation that when while exit the fastPointer is not null, ie the fastPointer points to the tail, then we need an additional swap, the last swap.
    private ListNode swapPairsByThreePointers(ListNode head){
        // special case1: linkedlist head is null, or linked list only has one node
        if(head == null || head.next == null){
            return head;
        }

        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;

        // 3 pointers need to consider about
        ListNode preSlow = dummyHead;
        ListNode slow = head;
        ListNode fast = head.next;

        // move and swap
        while(fast != null && fast.next != null){
            // store the original next nodes for move forward
            ListNode nextFast = fast.next.next;
            ListNode nextSlow = slow.next.next;

            // swap
            preSlow.next = fast;
            ListNode temp = fast.next;
            fast.next = slow;
            slow.next = temp;

            // move forward
            preSlow = slow;
            fast = nextFast;
            slow = nextSlow;
        }
        // if fast is not null, nee the last swap
        if(fast != null){
            // swap
            preSlow.next = fast;
            ListNode temp = fast.next;
            fast.next = slow;
            slow.next = temp;
        }

        return dummyHead.next;

    }

}
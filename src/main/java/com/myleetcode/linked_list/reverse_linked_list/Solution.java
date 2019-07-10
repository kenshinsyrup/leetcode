package com.myleetcode.linked_list.reverse_linked_list;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

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
    public ListNode reverseList(ListNode head) {
        return reverseListByIterative(head);
        // return reverseListByRecursive(head);
    }

    // sol 1: Iterative
    // TC: O(N)
    // SC: O(1)
    // 最重要是最后返回的头node是prevNode
    private ListNode reverseListByIterative(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        ListNode prevNode = null;
        ListNode nextNode = null;
        ListNode curNode = head;
        while(curNode != null){
            // keep next node
            nextNode = curNode.next;

            // reverse pointing
            curNode.next = prevNode;

            // move
            prevNode = curNode;
            curNode = nextNode;
        }

        // !!! at last, the curNode is null, the prevNode is the tail node of original LL, ie the new LL's head
        return prevNode;
    }

    // sol 2: Recursive
    // TC: O(N)
    // SC: O(N)
    // 最重要的是reverse recursion的base case
    private ListNode reverseListByRecursive(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        return reverse(head, null);
    }

    private ListNode reverse(ListNode curNode, ListNode prevNode){
        // base case
        // !!! tail node, reverse and return it
        if(curNode.next == null){
            curNode.next = prevNode;

            return curNode;
        }

        // keep next node
        ListNode nextNode = curNode.next;

        // reverse pointing
        curNode.next = prevNode;

        // move pointer and recurse
        prevNode = curNode;
        curNode = nextNode;

        return reverse(curNode, prevNode);
    }
}
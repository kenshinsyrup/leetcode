package com.myleetcode.linked_list.reverse_nodes_in_k_group;

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
    public ListNode reverseKGroup(ListNode head, int k) {
        return reverseKGroupByReverse(head, k);
    }

    // TC: O(N), this is not N^2 time although we have nested while loop because outer while loop traversed every node once, inner loop we just traversed k nodes once again and these nodes everytime are different, so it's about O(2N) time
    // SC: O(1)
    // intuition: cut LinkedList every k nodes as a new LinkedList, this cost O(K) time and O(1) space; then call reverse LinkedList to the new LinkedList, this cost O(K) time and O(1) space; keep doing this to end.
    private ListNode reverseKGroupByReverse(ListNode head, int k){
        // special case
        if(head == null || head.next == null || k <= 0){
            return head;
        }

        ListNode preHead = new ListNode(-1);
        preHead.next = head;

        ListNode dummyHead = head;

        int count = 0;
        ListNode previousListTail = preHead;
        while(head != null){
            count++;
            if(count == k){// if already count k nodes, we reverse this part
                // keep the head.next, then cut the linked list at head. we need reverse this k nodes.
                ListNode headNext = head.next;
                head.next = null;

                // reverse this k nodes
                ListNode reversedListHead = reverseLinkedList(dummyHead);
                // find the reversed k nodes linkedlist's tail
                ListNode reversedListTail = reversedListHead;
                while(reversedListTail.next != null){
                    reversedListTail = reversedListTail.next;
                }

                // append new reversedList to result and update tail
                previousListTail.next = reversedListHead;
                previousListTail = reversedListTail;

                // restore the head pointer and update the dummyHead
                dummyHead = headNext;
                head = headNext;
                count = 0;
            }else{
                // otherwise move forward
                head = head.next;
            }
        }
        if(count != 0){
            previousListTail.next = dummyHead;
        }

        return preHead.next;

    }

    // please remember how to reverse linked list
    // reverse LinkedList
    private ListNode reverseLinkedList(ListNode head){
        ListNode previousNode = null;
        while(head != null){
            // keep head.next
            ListNode headNext = head.next;

            // reverse
            head.next = previousNode;

            // move
            previousNode = head;
            head = headNext;
        }

        return previousNode;
    }
}

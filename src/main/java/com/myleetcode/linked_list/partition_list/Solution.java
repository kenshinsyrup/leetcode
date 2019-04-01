package com.myleetcode.linked_list.partition_list;

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
    public ListNode partition(ListNode head, int x) {
        // return partitionByTwoPointers(head, x);
        return partitionByTwoPointersII(head, x);
    }

    // TC: O(N), N is the length of linkedlist
    // SC: O(1), no extra space
    // a more concise solution: https://leetcode.com/problems/partition-list/discuss/29185/Very-concise-one-pass-solution
    // https://leetcode.com/problems/partition-list/discuss/29183/Concise-java-code-with-explanation-one-pass
    private ListNode partitionByTwoPointersII(ListNode head, int x){
        // special case
        if(head == null){
            return head;
        }

        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = head; // keeps the head of the given head node

        ListNode smallP = new ListNode(-1);
        ListNode dummySmallP = smallP; // keeps the head of smallP node
        ListNode bigP = new ListNode(-1);
        ListNode dummyBigP = bigP;// keeps the head of bigP node

        while(head != null){
            if(head.val < x){
                smallP.next = head;
                smallP = smallP.next;
            }else{
                bigP.next = head;
                bigP = bigP.next;
            }

            head = head.next;
        }

        bigP.next = null; // must change the next of node bigP pointed to null, otherwise the node.next is stil points some node in original linkedlist and will get a cycle. We need this one points to null because this is the tail node after we  done partition
        smallP.next = dummyBigP.next;

        return dummySmallP.next;
    }

    // intuition: two pointers, smallP points to the last node smaller than x, another pointer bigP traverse the linked list to find the node that node.next is bigger than x, if bigP.next is smaller than x, then do a swap
    private ListNode partitionByTwoPointers(ListNode head, int x){
        // special case
        if(head == null){
            return head;
        }

        //help
        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = head;

        ListNode smallP = dummyPreHead;
        ListNode preBigP = dummyPreHead;
        ListNode bigP = head;

        // find first valid bigP
        while(bigP != null){
            if(bigP.val < x){
                preBigP = bigP;
                smallP = preBigP;

                bigP = bigP.next;
            }else{
                break;
            }
        }

        // traverse
        while(bigP != null){
            if(bigP.val < x){
                // swap
                ListNode smallNext = smallP.next;
                smallP.next = bigP;

                ListNode bigNext = bigP.next;
                bigP.next = smallNext;

                preBigP.next = bigNext;

                // move
                smallP = smallP.next;
                bigP = bigNext;
            }else{
                preBigP = bigP;
                bigP = bigP.next;
            }
        }

        return dummyPreHead.next;

    }
}

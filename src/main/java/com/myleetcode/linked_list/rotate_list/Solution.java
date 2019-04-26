package com.myleetcode.linked_list.rotate_list;

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
    public ListNode rotateRight(ListNode head, int k) {
        // return rotateRightByRotate(head, k);
        return rotateRightByRing(head, k);
    }

    // TC: O(N)
    // SC: O(1)
    // there's a O(N) solution in the solution tab of leetcode, very clever
    // first, make the linkedlist a ring
    // second, find the new tail and new head, new head is the n-k node, then the new tail is just before it, ie the n-k-1 node
    // third, break the ring at the new tail, return the new head
    private ListNode rotateRightByRing(ListNode head, int k){
        if(head == null || k < 0){
            return head;
        }
        // only one node just return
        if(head.next == null){
            return head;
        }

        // get the length of linkedlist
        int len = 0;
        ListNode dummyHead = head;// we use the dummyHead to find the last node
        while(dummyHead != null && dummyHead.next != null){
            len++;

            dummyHead = dummyHead.next;
        }
        // here, because we exit the while loop before count the last node, so we should add one more to len.
        len++;

        // connect the tail to head to make a ring
        dummyHead.next = head;

        // new k
        k = k % len;

        // find the new head ie the n-k node and the new tail ie the n-k-1 node
        dummyHead = head; // at last is the new head
        ListNode dummyPreHead = new ListNode(-1);// at last is the new tail
        dummyPreHead.next = dummyHead;
        int step = len - k;
        while(step > 0){
            dummyPreHead = dummyHead;
            dummyHead = dummyHead.next;

            step--;
        }

        // break the ring, return the new head
        dummyPreHead.next = null;

        return dummyHead;
    }

    // TC: O(N ~ N^2)
    // SC: O(1)
    // intuition: seems a math problem, if really roatte the whole linkedlist everytime, will cost O(N*K) time. but observe the second example, we find that if linkedlist length is Len, given K, if K >= Len, then every Len steps we will get the original linkedlist again. so we only need to rotate K%Len steps. if K<Len, also K%Len steps.
    // then, how to rotate a linkedlist N steps: just rotate it.
    private ListNode rotateRightByRotate(ListNode head, int k){
        if(head == null || k < 0){
            return head;
        }
        // only one node just return
        if(head.next == null){
            return head;
        }

        // get the length of linkedlist
        int len = 0;
        ListNode dummyHead = head;
        while(dummyHead != null){
            len++;

            dummyHead = dummyHead.next;
        }

        // we only need rotate k steps
        k = k % len;
        if(k == 0){
            return head;
        }

        // rotate
        dummyHead = head;
        while(k > 0){
            dummyHead = rotate(dummyHead);

            k--;
        }

        return dummyHead;

    }

    // rotte the whole linkedlist and return the new head
    private ListNode rotate(ListNode head){
        ListNode dummyHead = head;
        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = dummyHead;

        // find the last two nodes
        while(dummyHead != null && dummyHead.next != null){
            // !!! first move the dummyPreHead, then move the dummyHead
            dummyPreHead = dummyHead;

            dummyHead = dummyHead.next;
        }

        // rotate
        dummyHead.next = head;
        dummyPreHead.next = null;

        // return the new head
        return dummyHead;
    }
}

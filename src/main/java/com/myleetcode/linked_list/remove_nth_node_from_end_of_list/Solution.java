package com.myleetcode.linked_list.remove_nth_node_from_end_of_list;

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // return removeNthFromEndByTwoPass(head, n);
        return removeNthFromEndByOnePass(head, n);
    }

    // TC: O(N)
    // SC: O(1)
    // follow up: one pass
    // we need two pointers, slow and fast. if we keep slow and fast have a gap of n nodes, then if fast points to the end of linkedlist, slow must points to the n node from the node.
    // for the case that the n node from the end is the head, we need add a dummy node before the head.
    // and, because we need to delete the n node from the end, in linkedlist, we must has its pre node, so we need to find the n+1 node from the end, so in the solution, we want to find the n+1 node from the end, ie the gap is n+1
    private ListNode removeNthFromEndByOnePass(ListNode head, int n){
        // special case
        if(n < 0 || head == null){
            return head;
        }

        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = head;

        ListNode fastPointer = dummyPreHead;
        ListNode slowPointer = dummyPreHead;
        // move fastPointer first to create the gap
        int i = 1;
        while(fastPointer != null && i <= n + 1){
            fastPointer = fastPointer.next;

            i++;
        }
        // here is the interesting thing, the given n is always valid according to the problem so we dont need do this. and, according to the LC, if given n is too big, it will delete the first node of the LL, so this is a point that we coudld talk with the interviewer
        // if reach null before we get the gap, means len of LL is not enough
        // if(i <= n + 1){
        //     return null;
        // }

        // fastPointer moves to the end, slowPointer follows to keep the gap
        while(fastPointer != null){
            fastPointer = fastPointer.next;
            slowPointer = slowPointer.next;
        }

        // delete
        slowPointer.next = slowPointer.next.next;

        return dummyPreHead.next;

    }


    // TC: O(N), N is the length of linkedlist
    // SC: O(1), no extra space need
    // intuition: remove the (len-n)th node, so we could first traverse the linked list to get its length, then traverse to remove the specific node
    private ListNode removeNthFromEndByTwoPass(ListNode head, int n){
        // special case
        if(n < 0 || head == null){
            return head;
        }

        // get linkedlist length
        ListNode dummyHead = head;
        int len = 0;
        while(dummyHead != null){
            dummyHead = dummyHead.next;
            len++;
        }

        // remove the (len-n)th node, we find the pre node ie the (len-n-1)th node, then easily delete the (len-n)th node.
        // so first we add ad preHead node to linkedlist, then we traverse to find the pre should be deleted node
        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = head; // keep the reference to head. and add a pre node for head to let the function work.
        ListNode dummyPre = dummyPreHead;// use for traverse to find the pre delete node
        int preIdx = len - n - 1;
        while(preIdx >= 0){
            dummyPre = dummyPre.next;
            preIdx--;
        }

        dummyPre.next = dummyPre.next.next; // delete the specific node

        return dummyPreHead.next;//return "head"
    }
}


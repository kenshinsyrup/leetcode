package com.myleetcode.linked_list.odd_even_linked_list;

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
    public ListNode oddEvenList(ListNode head) {
        // return oddEvenListByIterative(head);
        return oddEvenListByIterativeII(head);
    }

    // same idea with oddEvenListByIterative but without new any ListNode, just some Pointers
    // TC: O(N)
    // SC: O(1)
    private ListNode oddEvenListByIterativeII(ListNode head){
        // if LinkedList has 0, 1, 2 nodes only, return head
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }

        // helper
        ListNode oddHead = head;
        ListNode evenHead = head.next;

        // keep original oddHead and evenHead reference
        ListNode dummyOddHead = oddHead;
        ListNode dummyEvenHead = evenHead;

        // evenHead is faster and we will use the evenHead.next, so should check
        while(evenHead != null && evenHead.next != null){
            oddHead.next = evenHead.next;
            oddHead = oddHead.next;

            evenHead.next = oddHead.next;
            evenHead = evenHead.next;
        }
        // link
        oddHead.next = dummyEvenHead;

        return dummyOddHead;

    }

    // intuition: the problem want to us places odd-idx nodes together and even-idx nodes together and keep their relative position
    // TC: O(N)
    // SC: O(1), The LC dont think this is Space Complexity O(1), will give MLE error
    // we could use two helper nodes callder oddHead and evenHead, then traverse the LinkedList to do the connection
    private ListNode oddEvenListByIterative(ListNode head){
        // if LinkedList has 0, 1, 2 nodes only, return head
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }

        // two helper nodes
        ListNode oddHead = new ListNode(-1);
        ListNode evenHead = new ListNode(-1);

        // keep the original oddHead and evenHead
        ListNode dummyOddHead = oddHead;
        ListNode dummyEvenHead = evenHead;

        // traverse the LinkedList
        int count = 0;
        while(head != null){
            count++;

            System.out.println(head.val + " "+ count);

            // odd
            if(count % 2 != 0){
                oddHead.next = head;

                oddHead = oddHead.next;
            }else{
                // even
                evenHead.next = head;

                evenHead = evenHead.next;
            }

            // forward
            head = head.next;
        }
        // link the odd-idx LinkedList's tail to even-idx LinkedList's head
        oddHead.next = dummyEvenHead.next;

        // return the head of the result LinkedList
        return dummyOddHead.next;

    }
}

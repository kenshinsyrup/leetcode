package com.myleetcode.linked_list.plus_one_linked_list;

import com.myleetcode.utils.list_node.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode plusOne(ListNode head) {
        // return plusOneByReverseLinkedList(head); // More intuitive.
        return plusOneByPostorderTraversal(head);
    }

    /*
    Postorder Traversal:
    https://leetcode.com/problems/plus-one-linked-list/discuss/84130/Java-recursive-solution
    What kind of alg would adding one in reverse way for list? visit list in reverse way!

    */

    private ListNode plusOneByPostorderTraversal(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        int carry = plusOneToLinkedListWithPostorderTraversal(head);
        if (carry == 0) {
            return head;
        }

        ListNode newHead = new ListNode(carry);
        newHead.next = head;
        return newHead;
    }

    private int plusOneToLinkedListWithPostorderTraversal(ListNode curNode) {
        if (curNode == null) {
            return 1;
        }

        int carry = plusOneToLinkedListWithPostorderTraversal(curNode.next);
        if (carry == 0) {
            return 0;
        }

        int curSum = curNode.val + carry;
        curNode.val = curSum % 10;
        int curCarry = curSum / 10;

        return curCarry;

    }

    /*
    Reverse LinkedList:
    Reverse the LinkedList, add one from head to tail, reverse back.

    TC: O(N)
    SC: O(N)
    */
    private ListNode plusOneByReverseLinkedList(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        ListNode newHead = reverseLinkedList(head);
        plusOneToLinkedList(newHead);
        return reverseLinkedList(newHead);

    }

    private ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prevNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode nextNode = curNode.next;

            curNode.next = prevNode;

            prevNode = curNode;
            curNode = nextNode;
        }

        return prevNode;

    }

    private void plusOneToLinkedList(ListNode head) {
        ListNode prevNode = null;
        ListNode curNode = head;

        int sum = 1;
        int carry = 0;
        while (curNode != null) {
            sum += carry + curNode.val;

            carry = sum / 10;
            curNode.val = sum % 10;
            sum = 0;

            prevNode = curNode;
            curNode = curNode.next;
        }
        if (carry != 0) {
            prevNode.next = new ListNode(1);
        }
    }
}
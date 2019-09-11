package com.myleetcode.linked_list.add_two_numbers_ii;

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 看起来和2. Add Two Numbers很像。反转l1和l2之后，就一样了
        // https://leetcode.com/problems/add-two-numbers-ii/discuss/92640/Java-Solution-by-Reversing-LinkedList-beating-96

        return addTwoNumbersByReverseAndAdd(l1, l2);
    }

    private ListNode addTwoNumbersByReverseAndAdd(ListNode l1, ListNode l2){
        // reverse linked list
        l1 = reverseLinkedList(l1);
        l2 = reverseLinkedList(l2);

        ListNode retDummyHeadPre = new ListNode(-1);
        ListNode retHeadPre = retDummyHeadPre;
        int sum = 0;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0){
            sum = 0;
            if(l1 != null && l2 != null){
                sum += l1.val + l2.val + carry;

                l1 = l1.next;
                l2 = l2.next;
            }else if(l1 != null){
                sum += l1.val + carry;

                l1 = l1.next;
            }else if(l2 != null){
                sum += l2.val + carry;

                l2 = l2.next;
            }else{
                sum += carry;
            }

            carry = sum / 10;
            retDummyHeadPre.next = new ListNode(sum % 10);

            retDummyHeadPre = retDummyHeadPre.next;
        }

        return reverseLinkedList(retHeadPre.next);
    }

    // 请熟练掌握反转链表的写法
    private ListNode reverseLinkedList(ListNode curNode){
        ListNode nextNode = null; // keep the curNode's next node before swap pre and curNode
        ListNode preNode = null; // keep lHead's pre node, we will return this at last, because this is the head of the reversed list. At that time, lHead will be null
        while(curNode != null){
            // keep the next node
            nextNode = curNode.next;

            // reverse ptr direction
            curNode.next = preNode;

            // move forward
            preNode = curNode;
            curNode = nextNode;
        }

        // !!! finally, return the preNode, this is the head of reversed LL
        return preNode;
    }
}
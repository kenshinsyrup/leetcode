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
        // return reverseKGroupByReverse(head, k);
        return reverseKGroupByRecursion(head, k);
    }

    // sol 1: Recursion
    // reverseK reverse the LL from given head and by k nodes as group, return reversed LL's new head.
    // 出错点：
    // 1 这个题目和https://www.geeksforgeeks.org/reverse-a-list-in-groups-of-given-size/这个题目有点区别，这个题目要求如果以head node开头的LL长度小于k，那么不反转。所以我们需要额外改变一点，就是先检查是否需要反转也就是计数是否从head node能数出k个nodes，不可以的话直接返回head，可以的话正常去处理。
    // 2 if(nextNode != null){head.next = reverseK(next, k)}
    // TC: O(N)， 每个递归函数虽然遍历了两次k nodes group但是没有嵌套，所以TC为2N, 就是N
    // SC: O(K)
    private ListNode reverseKGroupByRecursion(ListNode head, int k){
        // special case
        if(head == null || head.next == null || k <= 0){
            return head;
        }

        return reverseK(head, k);
    }

    // reverse LL by K nodes group
    private ListNode reverseK(ListNode head, int k){
        ListNode curNode = head;
        int count = 0;
        boolean shouldR = false;

        // 1 check if we shoudl reverse, O(K)
        while(curNode != null){
            count++;
            if(count >= k){
                shouldR = true;
                break;
            }

            curNode = curNode.next;
        }
        if(!shouldR){
            return head;
        }

        curNode = head;
        count = 0;
        ListNode prevNode = null;
        ListNode nextNode = null;
        // 2 if should reverse, reverse
        while(curNode != null && count < k){ // reverse by k nodes group
            nextNode = curNode.next;

            curNode.next = prevNode;

            prevNode = curNode;
            curNode = nextNode;

            count++;
        }
        if(nextNode != null){ // !!! 正确拼接子LL的反转后的头到当前reverse之后的K group LL的尾
            head.next = reverseK(nextNode, k);
        }

        return prevNode;
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
        ListNode prevNode = null;
        ListNode nextNode = null;
        ListNode curNode = head;
        while(curNode != null){
            // keep head.next
            nextNode = head.next;

            // reverse
            curNode.next = prevNode;

            // move
            prevNode = curNode;
            curNode = nextNode;
        }

        return prevNode;
    }
}

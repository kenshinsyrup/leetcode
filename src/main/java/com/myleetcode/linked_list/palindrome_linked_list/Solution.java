package com.myleetcode.linked_list.palindrome_linked_list;

import com.myleetcode.utils.list_node.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        return isPalindromeByCutAndReverse(head);
        // return isPalindromeByStack(head);
    }

    // sol 1: Stack
    // traverse LL and store Node to Stack; then traverse LL again and in the meantime pop elem from Stack to compare
    // TC: O(N)
    // SC: O(N)
    private boolean isPalindromeByStack(ListNode head){
        if(head == null || head.next == null){
            return true;
        }

        // 1 push to stack
        ListNode curNode = head;
        Deque<ListNode> nodeStack = new ArrayDeque<>();
        while(curNode != null){
            nodeStack.push(curNode);

            curNode = curNode.next;
        }

        // 2 compare
        curNode = head;
        while(curNode != null){
            if(curNode.val != nodeStack.pop().val){
                return false;
            }

            curNode = curNode.next;
        }

        return true;
    }

    // sol 2: Reverse Second Half LL
    // this sol used the ideas of other problems: find the mid of LL; reverse LL;
    // reverse second half part LL, traverse from firstHead and secondHead, if not same then false. be careful with the len of LL, if len is odd, process the midNode seperately
    // TC: O(N)
    // SC: O(1)

    // for follow up, but should know

    // 这个解法是同样的思路，有解释https://leetcode.com/problems/palindrome-linked-list/discuss/64501/Java-easy-to-understand
    private boolean isPalindromeByCutAndReverse(ListNode head){
        if(head == null || head.next == null){
            return true;
        }

        // 1 find and cut at the mid of LL
        ListNode slow = head;
        ListNode fast = head;
        ListNode preSlow = null;
        while(fast != null && fast.next != null){
            preSlow = slow;

            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode firstEnd;
        ListNode secondHead;
        // fast == null,则说明总数为偶数，slow为第二部分的开头
        // fast.next == null， fast不为null，则node总数为奇数,slow.next为第二部分的开头(奇数时slow指向的是中间的node，不用考虑他)
        if(fast != null){
            secondHead = slow.next;
        }else{
            secondHead = slow;
        }
        // preSlow is the first half LL's tail node
        firstEnd = preSlow;

        // cut the LL to two part
        firstEnd.next = null;

        // 2 reverse the second half part
        secondHead = reverse(secondHead);

        // 3 traverse two parts to compare
        ListNode firstPtr = head;
        ListNode secondPtr = secondHead;
        while(firstPtr != null && secondPtr != null){
            if(firstPtr.val != secondPtr.val){
                return false;
            }

            firstPtr = firstPtr.next;
            secondPtr = secondPtr.next;
        }

        return true;
    }

    private ListNode reverse(ListNode head){
        ListNode prevNode = null;
        ListNode nextNode = null;
        ListNode curNode = head;

        while(curNode != null){
            // keep next node
            nextNode = curNode.next;

            // do reverse
            curNode.next = prevNode;

            // move ptr
            prevNode = curNode;
            curNode = nextNode;
        }

        // prevNode is the head of reversed LL
        return prevNode;
    }

}

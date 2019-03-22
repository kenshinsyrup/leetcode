package com.myleetcode.linked_list.palindrome_linked_list;

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
    public boolean isPalindrome(ListNode head) {
        return isPalindromeByCutAndReverse(head);
    }

    // 这个解法是同样的思路，有解释https://leetcode.com/problems/palindrome-linked-list/discuss/64501/Java-easy-to-understand

    // intuition: slow and fast pointer, cut linked list to two part, first part is 0-slow, second part is slow-end, then reverse second part, then compare first part and second part

    private boolean isPalindromeByCutAndReverse(ListNode head){
        if(head == null || head.next == null){
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode preSlow = null;
        while(fast != null && fast.next != null){
            preSlow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode first;
        ListNode second;
        // fast.next == null， fast不为null，则node总数为奇数,slow.next为第二部分的开头(奇数时slow指向的是中间的node，所以不用考虑)
        // fast == null,则说明总数为偶数，slow为第二部分的开头
        if(fast == null){
            first = head;
            second = slow;

            preSlow.next = null; // cut to two parts
        }else{
            first = head;
            second = slow.next;

            preSlow.next = null;// cut to two parts
            slow.next = null;// cut the node slow pointing off
        }

        second = reverse(second);

        while(first != null){
            if(first.val != second.val){
                return false;
            }

            first = first.next;
            second = second.next;
        }

        return true;

    }

    private ListNode reverse(ListNode head){
        ListNode pre = null;
        ListNode tempNode;

        while(head != null){
            tempNode = head.next;
            head.next = pre;

            pre = head;
            head = tempNode;
        }

        return pre;
    }

}

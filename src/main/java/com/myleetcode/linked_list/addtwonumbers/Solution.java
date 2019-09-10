package com.myleetcode.linked_list.addtwonumbers;

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

        return addTwoNumbersByTraverse(l1, l2);
    }

    /*
    intuition:
    like merge part of DnD
    顺序遍历相加，注意最后有一个LL为null时，仍需要用另一个LL和carry继续运算,注意结束
    https://leetcode.com/problems/add-two-numbers/discuss/1044/Java-concise-solution.

    Say N is the length of LL1, M is the length of LL2
    TC: O(Max(N, M)), N is the longer one of LL1 and LL2
    SC: O(N + M)
    */
    private ListNode addTwoNumbersByTraverse(ListNode l1, ListNode l2){
        // special case
        if(l1 == null && l2 == null){
            return null;
        }
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }

        ListNode dummyRetHeadPre = new ListNode(-1); // dummy result List Head pre node, use to return ret node

        ListNode curNode = dummyRetHeadPre; // traverse to build ret LL
        int carry = 0; // carry
        // loop to add
        while(l1 != null && l2 != null){
            // every time when we add, we have 3 digits, l1.val and l2.val and carry, we caculate their sum and then get the new carry to next loop and get the remainder as the new result list node.
            int sum = l1.val + l2.val + carry; // current sum is l1.val + l2.val + carry

            int remain = sum % 10; // cur remain
            carry = sum / 10; // new carry

            curNode.next = new ListNode(remain);

            // move forward
            l1 = l1.next;
            l2 = l2.next;
            curNode = curNode.next;
        }
        while(l1 != null){
            int sum = l1.val + carry; // current sum is l1.val + carry

            int remain = sum % 10; // cur remain
            carry = sum / 10; // new carry

            curNode.next = new ListNode(remain);

            // move forward
            l1 = l1.next;
            curNode = curNode.next;
        }
        while(l2 != null){
            int sum = l2.val + carry; // current sum is l1.val + carry

            int remain = sum % 10; // cur remain
            carry = sum / 10; // new carry

            curNode.next = new ListNode(remain);

            // move forward
            l2 = l2.next;
            curNode = curNode.next;
        }
        if(carry != 0){
            curNode.next = new ListNode(carry);
        }

        // return the result list head
        return dummyRetHeadPre.next;
    }

}

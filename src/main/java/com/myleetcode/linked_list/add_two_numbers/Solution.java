package com.myleetcode.linked_list.add_two_numbers;

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
        // 顺序遍历相加，注意carry,注意结束

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

        return addTwoNumbersByTraverse(l1, l2);
    }

    private ListNode addTwoNumbersByTraverse(ListNode l1, ListNode l2){
        // https://leetcode.com/problems/add-two-numbers/discuss/1044/Java-concise-solution.
        ListNode dummyL1Node = l1; // dummynode pointer, points to l1 List Head, move from head to tail
        ListNode dummyL2Node = l2; // dummynode pointer, points to l2 List Head, move from head to tail
        int carry = 0; // carry
        int sum = 0; // l1.val + l2.val + carry
        ListNode dummyRetHeadPre = new ListNode(-1); // dummy result List Head, but moves and chains the new nodes.
        ListNode retHeadPre = dummyRetHeadPre;// store the reference of result List Head.
        // loop to add
        while(dummyL1Node != null || dummyL2Node != null || carry != 0){
            // every time when we add, we have 3 digits, dummyL1Node.val and dummyL2Node.val and carry, we caculate their sum and then get the new carry to next loop and get the remainder as the new result list node.
            sum = 0;// must reset to 0

            // get the 3 digits sum, and move pointers
            if(dummyL1Node != null && dummyL2Node != null){
                sum += dummyL1Node.val + dummyL2Node.val + carry;

                dummyL1Node = dummyL1Node.next;
                dummyL2Node = dummyL2Node.next;
            }else if(dummyL1Node != null){
                sum += dummyL1Node.val + carry;

                dummyL1Node = dummyL1Node.next;
            }else if(dummyL2Node != null){
                sum += dummyL2Node.val + carry;

                dummyL2Node = dummyL2Node.next;
            }else{
                sum += carry;
            }

            // get the carry
            carry = sum / 10;
            // get the remainder and chain the new node to result List
            dummyRetHeadPre.next = new ListNode(sum % 10);

            // move pointer
            dummyRetHeadPre = dummyRetHeadPre.next;
        }

        // return the result list head
        return retHeadPre.next;
    }
}

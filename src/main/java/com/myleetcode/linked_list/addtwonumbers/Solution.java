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

        // 最初的思路是先翻转链表，然后按位求和，链表长度不一时注意检查长链表的剩余部分，注意检查最后的进位位是否大于0
        // 注意：实际上是不需要先翻转链表的，因为给的输入链表已经是和自然数反序的了，即从左到右是从低位到高位
        /*   
        // 首先翻转两个链表
        ListNode prev = null;
        ListNode curr = l1;
        ListNode next = null;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        l1 = prev;
        
        prev = null;
        curr = l2;
        next = null;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        l2 = prev;
        */

        //按位相加，记得进位
        ListNode res = new ListNode(0);
        int c = 0;
        while (l1 != null && l2 != null) {
            int currentL1 = l1.val;
            int currentL2 = l2.val;
            int sum = currentL1 + currentL2 + c;
            c = 0;
            if (sum > 9) {
                c++;
                sum = sum - 10;
            }
            ListNode last = res;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new ListNode(sum);
            l1 = l1.next;
            l2 = l2.next;
        }

        // 检查长链表
        if (l1 != null) {

            while (l1 != null) {
                int sum = c + l1.val;
                c = 0;
                if (sum > 9) {
                    c++;
                    sum = sum - 10;
                }
                ListNode last = res;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = new ListNode(sum);
                l1 = l1.next;
            }
        }
        if (l2 != null) {
            while (l2 != null) {
                int sum = c + l2.val;
                c = 0;
                if (sum > 9) {
                    c++;
                    sum = sum - 10;
                }
                ListNode last = res;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = new ListNode(sum);
                l2 = l2.next;
            }
        }

        // 检查最后的进位位
        if (c != 0) {
            ListNode last = res;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new ListNode(c);
        }

        //remove the first 0 node
        return res.next;
    }

    // 后续来自Leetcode： https://leetcode.com/problems/add-two-numbers/
    // 思路是最直接的，包括自己的解法也是同样的思路，但是这个代码要简单的多:首先就是carry的求法用除法更好；然后不考虑额外求长链表的问题，而是一直遍历
    // 完所有的链表，已经到末尾的直接视为val为0；最后，注意dummyHead的用法，比自己那个愚蠢的每次找最后一个非null节点正常多了。
    // Time complexity : O(\max(m, n))O(max(m,n)). Assume that mm and nn represents the length of l1l1 and l2l2 respectively, 
    // the algorithm above iterates at most \max(m, n)max(m,n) times.
    public ListNode addTwoNumbersElementaryMath(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null)
                p = p.next;
            if (q != null)
                q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}

// 最后，Leetcode提到了一个followup，是说如果输入链表不是对应数字的倒序，应该如何？
// 没有提如何解决，自己感觉：那就先翻转，然后按照这个题目的方式来求，然后再翻转输出
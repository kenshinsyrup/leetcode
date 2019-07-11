package com.myleetcode.linked_list.reorder_list;

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
    public void reorderList(ListNode head) {
        reorderListByTwoPointer(head);
    }

    // intuition:
    // 题目相当于是把链表从中分为两段，后面的半段先倒序，再和前面半段一一交叉
    // 最初想到用stack存后面的半段，然后遍历前面的半段的时候从stack中pop出node插入，但是需要额外的一个space
    // 不使用额外的数据结构的话，可以直接按照翻译题目的方式来求解：分成两段；reverse后半段；交叉

    // TC: O(N)
    // SC: O(1)
    private void reorderListByTwoPointer(ListNode head){
        // special case
        if(head == null || head.next == null){
            return;
        }

        // 看eg 1， 1,2,3,4变成的是1,4,2,3. eg2中1,2,3,4,5变成的是1,5,2,4,3，也就是分成两半之后左边的长
        // 1 双指针(快慢指针)找链表中间
        ListNode slow = head;
        ListNode fast = head;
        ListNode preSlow = null;
        while(fast != null && fast.next != null){
            preSlow = slow;

            // update pointer
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2 cut to two parts
        // if fast != null means LL len is odd, so slow is on the mid elem now, secondhead is slow.next and the first part's end is preSlow but we should cut the LL at slow because the mid elem should belong to the first part; otherwise secondhead is slow and first part end is preSlow
        ListNode secondHead = null;
        if(fast != null){
            secondHead = slow.next;

            // cut the LL at slow when odd
            slow.next = null;
        }else{
            secondHead = slow;

            // cut the LL at preSlow when even
            preSlow.next = null;
        }

        // 重要，要拿到新的头，不要继续是secondHead了，现在的secondHead已经是尾了。
        // 3 reverse second half
        ListNode reversedHead = reverseList(secondHead);

        // 4 merge
        mergeList(head, reversedHead);
    }

    private ListNode reverseList(ListNode head){
        // 标准写法，反转链表
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;

        while(cur != null){
            // 维持住cur.next
            next = cur.next;

            // 反转
            cur.next = pre;

            // 更新指针指向到node，即移动
            pre = cur;
            cur = next;
        }

        return pre;
    }

    private void mergeList(ListNode left, ListNode right){
        ListNode leftNext = null;
        ListNode rightNext = null;
        while(left != null && right != null){
            // preserve nexts
            leftNext = left.next;
            rightNext = right.next;

            // merge right nodes to left
            left.next = right;
            right.next = leftNext;

            // update pointers
            left= leftNext;
            right = rightNext;
        }
    }
}


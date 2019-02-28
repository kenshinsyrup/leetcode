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
        // 题目相当于是把链表从中分为两段，后面的半段先倒序，再和前面半段一一交叉
        // 最初想到用stack存后面的半段，然后遍历前面的半段的时候从stack中pop出node插入，但是需要额外的一个space

        // 不使用额外的数据结构的话，可以直接按照翻译题目的方式来求解：分成两段；reverse后半段；交叉

        // special case
        if(head == null || head.next == null){
            return;
        }

        reorderListByTwoPointer(head);
    }

    private void reorderListByTwoPointer(ListNode head){
        // 看eg 1， 1,2,3,4变成的是1,4,2,3. eg2中1,2,3,4,5变成的是1,5,2,4,3，也就是分成两半之后左边的长
        // 双指针(快慢指针)找链表中间
        ListNode slow = head;
        ListNode fast = head.next;//fast和slow不论同时从head出发还是fast从head.next出发，这样fast到尾部时，slow都会差不多在中部偏右也就是左边长:
        //如果fast从head出发，那么左边比右边长1(奇数nodes)或者2(偶数nodes)。
        //如果fast从head.next出发，那么会比从head出发到情况下，slow更靠左，也就是左边比右边长1(奇数nodes)或者0(偶数nodes).
        // 举例来说，对于[1,2,3,4,5]，fast从head出发，得到的是[1,2,3]和[4,5]，fast从head.next出发，得到的也是[1,2,3]和[4,5]
        // 对于[1,2,3,4,5,6]，fast从head出发，得到的是[1,2,3,4]和[5,6]，fast从head.next出发，得到的是[1,2,3]和[4,5,6]
        //我们这里采用了从head.next出发的写法，但这道题不论从head还是head.next出发，其他代码不变的情况下结果是相同的
        while(fast != null && fast.next != null){
            // update pointer
            slow = slow.next;
            fast = fast.next.next;
        }
        // now slow points to middle node, ie the 'tail' of first half, and the slow.next is the 'head' of second half.


        //  get second half head
        ListNode secondHead = slow.next;

        //重要，cut
        slow.next = null;

        // 重要，要拿到新的头，不要继续是secondHead了，现在的secondHead已经是尾了。
        //reverse second half
        ListNode reversedHead = reverseList(secondHead);

        // merge
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
        ListNode leftTemp = null;
        ListNode rightTemp = null;
        ListNode temp = null;
        while(left != null && right != null){
            // preserve nexts
            leftTemp = left.next;
            rightTemp = right.next;

            // merge right nodes to left
            temp = left.next;
            left.next = right;
            right.next = temp;

            // update pointers
            left= leftTemp;
            right = rightTemp;
        }
    }
}

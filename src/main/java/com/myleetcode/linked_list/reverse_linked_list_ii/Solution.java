package com.myleetcode.linked_list.reverse_linked_list_ii;

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
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // one-pass不是说只能用一次循环语句，是说循环中访问过的node不可以再访问，所以先用一个for循环找到m指代的node，再从m开始做reverse知道n，是可以的
        // 最重要的是，在reverse m到n node时，要注意保持原本m的pre和n的next，用来在reverse之后重新把整个链表串起来

        // special case, 0和1个node或者m，n不合法，直接返回
        if(head == null || head.next == null || m > n){
            return head;
        }

        return reverseBetweenByTwoLoop(head, m, n);

    }

    private ListNode reverseBetweenByTwoLoop(ListNode head, int m, int n){
        // 经典写法，添加node保持当前head指向的node用于最后返回linkedlist的头
        ListNode dummy = new ListNode(0);
        dummy.next = head; // preserve the node which head points to

        // 这样是普通写法，但是是错误的，我们要保证的是在循环时，i==1时cur指向第一个node，所以应该让cur从dummy开始，也就是从辅助node开始，这样每次循环更新cur的之后刚好对应上，否则会cur最后指向m+1 node
        // ListNode pre = dummy;
        // ListNode cur = head;
        // 重要
        ListNode pre = null;
        ListNode cur = dummy;
        for(int i = 1; i <= m ; i++){
            pre = cur;
            cur = cur.next;
        }
        // now pre points to m-1 node, cur points to m node

        // use next to points to cur.next
        // 注意，这里直接写next = cur.next其实没有必要，我们用next保持cur.next是每次循环都要做的事情，所以可以直接简写成next = null在进入while之前
        // ListNode next = cur.next;
        ListNode next = null;

        // use two helper to preserve m's pre and m's cur
        // 举例1->2->3->4->5. m为2，n为4.那么当上面for循环结束时，cur指向node2，pre指向node1.我们知道reverse完毕后，结果为1->4->3->2->5，那么值为2的node.next指向的时5，值为1的node的next指向的时4.我们知道reverse之前，当前pre指向的值为1node，而当前cur指向的2node，那么我们在reverse之后要更新他们，所以用mPre，mCur保留住。
        // 当reverse完毕时但是还没有更改必要的next指向时，1<-2<-3<-4->5pre指向的时的状态。我们知道值为1的node的next应该指向4(当前pre所指向的node)，值为2的node的next应该指向5(当前cur所指向的node).所以就是mPre.next = pre; mCur.next = cur;这样mPre1就指向了pre4，mCur2就指向了cur5，链表就构建成了1->4(mPre更新), 4->-3->2(reverse之后不变的部分), 2->5(mCur更新),完整版就变成了1->4->3->2->5.
        ListNode mPre = pre; // 到达m时，pre指向的node，这个将来reverse之后，其next要链接pre
        ListNode mCur = cur; // 到达m时，cur指向的node，这个将来reverse之后，其next要链接cur
        while(m <= n && cur != null){
            // preserve cur.next node
            next = cur.next;

            // reverse pointing
            cur.next = pre;

            // update 2 pointers
            pre = cur;
            cur = next;

            // 重要 must update m
            m++;
        }

        // 重要
        // update mPre's point and nNext's parent to make linkedlist whole
        mPre.next = pre;
        mCur.next = cur;

        return dummy.next;
    }
}

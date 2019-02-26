package com.myleetcode.linked_list.sort_list;

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
    public ListNode sortList(ListNode head) {
        // 如果不要求constant space的话，就是O(n)遍历链表，存入数组；O(nlogn)sort一下数组;再把数组做成链表返回。空间复杂度是O(n)

        //https://leetcode.com/problems/sort-list/discuss/46714/Java-merge-sort-solution
        // 要求了空间复杂度为1，那么既然时间复杂度是O(nlongn), 那么可以想到的一个解法是，merge sort的思路.
        //对于链表，没法轻松的知道其长度，就要借助快慢指针的方式来找到链表的中间和尾（一道easy题）,然后递归，merge的标准操作，就和数组的merge sort方法一样

        // special case
        if(head == null){
            return head;
        }

        return sortListByMergeSort(head);
    }

    private ListNode sortListByMergeSort(ListNode head){
        // find the middle and tail of ListNode, ie, cut one Linkedlist to half and half
        // base case
        if(head == null || head.next == null){
            return head;
        }

        ListNode slowNode = head;
        ListNode fastNode = head;
        ListNode dummyNode = head;
        while(fastNode != null && fastNode.next != null){
            dummyNode = slowNode;
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        // after for, dummyNode is the first half's tail, slowNode is the second half's head
        // 注意。 make the dummyNode.next = null to cut the linklist to two halves. head->...->dummyNode是一条新链表，slowNode->...->原来的尾部 是另一条新链表，二者和为原链表
        dummyNode.next = null;

        ListNode leftList = sortListByMergeSort(head);
        ListNode rightList = sortListByMergeSort(slowNode);

        // merge
        return merge(leftList, rightList);

    }

    private ListNode merge(ListNode left, ListNode right){
        ListNode head = new ListNode(0);
        ListNode dummyNode = head;

        while(left != null && right != null){
            if(left.val > right.val){
                dummyNode.next = right;
                right = right.next;
            }else{
                dummyNode.next = left;
                left = left.next;
            }
            dummyNode = dummyNode.next;
        }

        if(left != null){
            dummyNode.next = left;
        }

        if(right != null){
            dummyNode.next = right;
        }

        return head.next;
    }
}

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
        // return sortListByMergeSort(head);
        return sortListByMergeSortIterative(head); // Iterative way, fit the requirement of Constant Space Complexity
    }

    // sol3: Merge Sort without Recursion
    // https://leetcode.com/problems/sort-list/discuss/46712/Bottom-to-up(not-recurring)-with-o(1)-space-complextity-and-o(nlgn)-time-complextity
    /*
    this problem can be easily solved using recurrence and divide-and-conquer. But it consumes program stack to store the recurring function stack frame, actually it consumes o(lgn) space complexity. Recursion use up-to-bottom strategy , why not try the opposite way--bottom-to-up, luckily it works, it only consumes 0(1) space complexity and o(nlgn) time complextity.
    */
    private ListNode sortListByMergeSortIterative(ListNode head){
        // base case
        if(head == null || head.next == null){
            return head;
        }

        // 1 get the len of LL
        ListNode curNode = head;
        int len = 0;
        while(curNode != null){
            len++;

            curNode = curNode.next;
        }

        // 2 merge sort
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode prevNode = dummyNode;
        curNode = dummyNode.next;
        for(int size = 1; size < len; size *= 2){
            // every very first time, we back to the LL's most left, thanks for the dummy to keep it for us
            prevNode = dummyNode;
            curNode = dummyNode.next;

            // in current segment [startL..endL...startR... endR] to merge, left part is [startL, endL], right part is [startR, endR]
            // so in the whole LL perceptive, we need leftLHead, rightLHead, processedLLHead, and remainingLHead.
            // we merge leftLHead with rightLHead to get mergedLLHead, leftLLHead is curNode; rightLLHead get from the leftLLHead and move forward size steps and cut;
            // the remainingLLHead get from rightLLHead move forward size steps and cut
            // after get the mergedLLHead, we append it to the processedLLHead. then we move forward the processedLLHead to reach the new processedLL's tail node to wait for next append.
            while (curNode != null) {
                ListNode left = curNode;//leftLHead
                ListNode right = split(left, size);//rightLHead, and cut the leftLL to make it seperate to be ready for merge

                curNode = split(right, size);// !!! remaining LL Head, and cut the rightLL to make it seperate to ready for merge

                // !!! prevNode is the Processed Part LL's tail node(the last node that is not null)
                ListNode mergedLHead = merge(left, right);
                prevNode.next = mergedLHead;
                while(prevNode.next != null){
                    prevNode = prevNode.next;
                }
            }
        }

        return dummyNode.next;
    }

    // split from given node and after step-1 nodes. return the remaining LL head
    private ListNode split(ListNode head, int step) {
        if (head == null){
            return null;
        }

        // move step-1 num steps forwards
        int i = 1;
        while(head.next != null && i < step){
            head = head.next;

            i++;
        }

        // remaining LL's head
        ListNode right = head.next;

        // cut
        head.next = null;

        return right;
    }

    // sol1: List and Sort
    // 如果不要求constant space的话，就是O(n)遍历链表，存入数组；O(nlogn)sort一下数组;再把数组做成链表返回。空间复杂度是O(n)

    // sol2: Merge Sort
    //https://leetcode.com/problems/sort-list/discuss/46714/Java-merge-sort-solution
    // merge sort的思路的话，TC是O(N * logN), 但是由于Recursion的存在，SC是O(logN)
    //对于链表，没法轻松的知道其长度，就要借助快慢指针的方式来找到链表的中间和尾（一道easy题）,然后递归，merge的标准操作，就和数组的merge sort方法一样
    private ListNode sortListByMergeSort(ListNode head){
        // find the middle and tail of ListNode, ie, cut one Linkedlist to half and half
        // base case
        if(head == null || head.next == null){
            return head;
        }

        ListNode slowNode = head;
        ListNode fastNode = head;
        ListNode prevSlowNode = head;
        while(fastNode != null && fastNode.next != null){
            prevSlowNode = slowNode;
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }
        // after while, prevSlowNode is the first half's tail, slowNode is the second half's head
        // 注意。 make the dummyNode.next = null to cut the linklist to two halves. head->...->dummyNode是一条新链表，slowNode->...->原来的尾部 是另一条新链表，二者和为原链表
        // cut
        prevSlowNode.next = null;

        ListNode leftList = sortListByMergeSort(head);
        ListNode rightList = sortListByMergeSort(slowNode);

        // merge
        return merge(leftList, rightList);

    }

    // merge two sorted list
    private ListNode merge(ListNode left, ListNode right){
        // base case
        if(left == null){
            return right;
        }
        if(right == null){
            return left;
        }

        ListNode head = new ListNode(0);
        ListNode curNode = head;
        while(left != null && right != null){
            if(left.val > right.val){
                curNode.next = right;

                right = right.next;
            }else{
                curNode.next = left;

                left = left.next;
            }

            curNode = curNode.next;
        }

        if(left != null){
            curNode.next = left;
        }

        if(right != null){
            curNode.next = right;
        }

        return head.next;
    }
}

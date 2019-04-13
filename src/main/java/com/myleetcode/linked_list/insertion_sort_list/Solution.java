package com.myleetcode.linked_list.insertion_sort_list;

import com.myleetcode.utils.list_node.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode insertionSortList(ListNode head) {
        // return insertionSortListBySwap(head);
        return insertionSortListBySortWithList(head);
    }

    // TC: O(N*log(N))
    // SC: O(N)
    // actually, for all the sorting linked list problem, we should ask the interviwer a question: what you need. Because: For God's sake, don't try sorting a linked list during the interview
    // https://leetcode.com/problems/insertion-sort-list/discuss/46429/Thoughts-from-a-Google-interviewer
    // here we find the O(1) solution gives us O(N^2) TC, but we could reduce it to O(NlogN) with extra space, and most of time, space is so cheap
    // why we should talk with interviewer? because: 1 we change the data structure 2 we dont actually explicitly use the "insertion sort".
    private ListNode insertionSortListBySortWithList(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        List<Integer> nodeValList = new ArrayList<>();

        // N
        while(head != null){
            nodeValList.add(head.val);

            head = head.next;
        }

        // N*logN
        Collections.sort(nodeValList);

        // N
        ListNode preHead = new ListNode(-1);
        ListNode dummyPreHead = preHead;
        for(int val: nodeValList){
            dummyPreHead.next = new ListNode(val);

            dummyPreHead = dummyPreHead.next;
        }

        return preHead.next;
    }



    // TC: O(N^2)
    // SC: O(1)
    // intuition: one pointer every time move one step forward. then we have another pointer every time start at head to check value, when find a node is bigger than this one, exchange position.
    private ListNode insertionSortListBySwap(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        // add an help node in front of head
        ListNode preHead = new ListNode(-1);
        preHead.next = head;

        // dummy preHead
        ListNode dummyPreHead = preHead;
        // dummy head
        ListNode dummyHead = head;

        // first step
        dummyPreHead = dummyPreHead.next;
        dummyHead = dummyHead.next;

        // loop linked list
        while(dummyHead != null){
            // if smaller than previous node, then need insert
            // say dummyHead is 3, dummyPreHead is 5
            if(dummyHead.val < dummyPreHead.val){
                // check from head node
                ListNode startNode = preHead.next;
                ListNode preStartNode = preHead;
                // keep moving if valid
                while(startNode.val <= dummyHead.val){
                    preStartNode = startNode;
                    startNode = startNode.next;
                }
                // swap
                // say startNode is 4, preStartNode is 2.
                // 2->4-> ... ->5->3->
                // get a standlone dummyHead and keep the link whole
                dummyPreHead.next = dummyHead.next;// dummyPreHead.next points to dummyHead.next
                // insert the dummyHead node to position
                preStartNode.next = dummyHead;
                dummyHead.next = startNode;
            }

            // move forward
            dummyPreHead = dummyHead;
            dummyHead = dummyHead.next;
        }

        return preHead.next;

    }
}

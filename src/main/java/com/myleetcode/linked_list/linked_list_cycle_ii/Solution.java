package com.myleetcode.linked_list.linked_list_cycle_ii;

import com.myleetcode.utils.list_node.ListNode;

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        return detectCycleByTwoPointer(head);
    }

    // solution: with Floyed's algo(Floyd's Tortoise and Hare)
    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/linked-list-cycle-ii/discuss/44848/Java-solution-without-extra-space-with-explanation
    // because maybe there not exists cycle, so should be careful with this situation
    private ListNode detectCycleByTwoPointer(ListNode head){
        if(head == null || head.next == null || head.next.next == null){
            return null;
        }

        // 1 find the meet point:
        ListNode slowN = head;
        ListNode fastN = head;
        boolean isBegin = true;
        while(fastN != null && fastN.next != null){
            // meet
            if(!isBegin && slowN.equals(fastN)){
                break;
            }

            // move ptr
            slowN = slowN.next;
            fastN = fastN.next.next;
            isBegin = false;
        }
        // ther are two situations if we are out of the while loop: 1 is no cycle, ie fastN is null or fastN.next is null; 2 is we find the meet point and break the loop
        // if no cycle, return null
        if(fastN == null || fastN.next == null){
            return null;
        }

        // if find meet point, then we could try to find the entry
        // 2 find the entry:
        fastN = head;
        while(fastN != slowN){
            fastN = fastN.next;
            slowN = slowN.next;
        }

        return slowN;
    }
}


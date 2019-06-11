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
    private ListNode detectCycleByTwoPointer(ListNode head){
        if(head == null || head.next == null || head.next.next == null){
            return null;
        }

        // because maybe there not exists cycle, so should be careful with this situation
        // 1 find the meet point:
        ListNode slowN = head.next;
        ListNode fastN = head.next.next;
        while(fastN != slowN){ // !!! check the node itself, not the val of two nodes
            // no cycle
            if(fastN.next == null || fastN.next.next == null){
                return null;
            }
            slowN = slowN.next;
            fastN = fastN.next.next;
        }

        // 2 find the entry:
        fastN = head;
        while(fastN != slowN){
            fastN = fastN.next;
            slowN = slowN.next;
        }

        return slowN;
    }
}

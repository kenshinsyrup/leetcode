package com.myleetcode.linked_list.linked_list_cycle;

import com.myleetcode.utils.list_node.ListNode;

import java.util.HashMap;

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
    public boolean hasCycle(ListNode head) {
        // special case
        if(head == null || head.next == null){
            return false;
        }

        // return hasCycleByHashmap(head);

        return hasCycleByTwoPointers(head);
    }

    // follow up
    // SC: O(1)
    // two pointers, everytime, slower pointer move 1 step, faster pointer move 2 step.
    // if no cycle, we must could reach this condition: faster == null || faster.next == null
    // if has cycle, there's no null in the linkedlist, so, at some point, faster and slower must could point to a same node, then we could return(and this is the return condition in this case)
    private boolean hasCycleByTwoPointers(ListNode head){
        ListNode slower = head;
        ListNode faster = head.next; // faster at initial must not points to the same node as slower

        while(faster != null){

            // if we could reach null, then must no cycle
            if(faster == null || faster.next == null){
                return false;
            }else if(slower.equals(faster)){// if faster pointer reach slower pointer, must has a cycle
                return true;
            }

            // update pointers
            slower = slower.next;
            faster = faster.next.next;
        }

        // while循环出来之后肯定是false因为faster肯定是null
        return false;
    }

    // intuition, we could use a HashMap, use the node as key to record if we visited this node or not
    // TC: O(n)
    // SC: O(n)
    private boolean hasCycleByHashmap(ListNode head){
        HashMap<ListNode, Boolean> visited = new HashMap<ListNode, Boolean>();

        while(head != null){
            if(visited.containsKey(head)){
                return true;
            }
            visited.put(head, true);

            // update head
            head = head.next;
        }

        return false;
    }

}

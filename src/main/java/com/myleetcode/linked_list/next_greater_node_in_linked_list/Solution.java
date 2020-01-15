package com.myleetcode.linked_list.next_greater_node_in_linked_list;

import com.myleetcode.utils.list_node.ListNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] nextLargerNodes(ListNode head) {
        return nextLargerNodesByMonoStack(head);
    }

    /*
    Mono Stack:
    Build a Mono Stack with Descending Order, when a val is larger than stack peek, then its the first larger element for the peek node.

    N is the nodes number.
    TC: O(N)
    SC: O(N)
    */
    private int[] nextLargerNodesByMonoStack(ListNode head) {
        if (head == null) {
            return new int[0];
        }

        // 1. Store all val into List.
        List<Integer> valList = new ArrayList<>();
        ListNode curNode = head;
        while (curNode != null) {
            valList.add(curNode.val);
            curNode = curNode.next;
        }

        // 2. Using Descending Mono Stack to find ret.
        int size = valList.size();
        int[] ret = new int[size];
        Deque<Integer> monoStack = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            int val = valList.get(i);

            while (!monoStack.isEmpty() && valList.get(monoStack.peek()) < val) {
                ret[monoStack.peek()] = val;
                monoStack.pop();
            }

            monoStack.push(i);
        }
        while (!monoStack.isEmpty()) {
            ret[monoStack.pop()] = 0;
        }

        return ret;
    }
}
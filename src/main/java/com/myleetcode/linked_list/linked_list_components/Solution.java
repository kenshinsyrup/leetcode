package com.myleetcode.linked_list.linked_list_components;

import com.myleetcode.utils.list_node.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int numComponents(ListNode head, int[] G) {
        return numComponentsByTraverse(head, G);
    }

    /*
    The meaning of the problem:
    One connected component is part of consecutive LinkedList that all the elements are in the G.

    N is LinkedList length, M is G length.
    TC: O(Min(N, M*logM))
    SC: O(1)
    */
    private int numComponentsByTraverse(ListNode head, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (head == null) {
            return 0;
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int count = 0;

        ListNode curNode = head;
        boolean match = false;
        while (curNode != null) {
            int curValue = curNode.val;
            if (!numSet.contains(curValue)) {
                match = false;
            } else {
                if (match == false) {
                    count++;
                }

                match = true;
            }

            curNode = curNode.next;
        }

        return count;
    }
}
package com.myleetcode.linked_list.convert_binary_number_in_a_linked_list_to_integer;

import com.myleetcode.utils.list_node.ListNode;

import java.util.ArrayList;
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
    public int getDecimalValue(ListNode head) {
        // return getDecimalValueByMath(head);
        return getDecimalValueByMultiply(head);
    }

    /*
    Stimulate:
    https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/discuss/452970/JAVA-Easy-to-Understand-and-Readable-Solution

    TC: O(N)
    SC: O(1)
    */
    private int getDecimalValueByMultiply(ListNode head) {
        if (head == null) {
            return 0;
        }

        int num = 0;
        ListNode curNode = head;
        while (curNode != null) {
            num = num << 1;
            num += curNode.val;

            curNode = curNode.next;
        }

        return num;
    }

    /*
    Math:
    Traverse the LinedList and remember idx of 1 to a List, at last caculate to decimal number.

    N is node number.
    TC: O(N)
    SC: O(N)
    */
    private int getDecimalValueByMath(ListNode head) {
        if (head == null) {
            return 0;
        }

        // Get 1 idx list.
        List<Integer> idxList = new ArrayList<>();
        ListNode curNode = head;
        int idx = 0;
        while (curNode != null) {
            int val = curNode.val;
            if (val == 1) {
                idxList.add(idx);
            }

            curNode = curNode.next;
            idx++;
        }
        idx--;

        // Get decimal.
        int num = 0;
        for (int p : idxList) {
            num += Math.pow(2, idx - p);
        }

        return num;
    }
}

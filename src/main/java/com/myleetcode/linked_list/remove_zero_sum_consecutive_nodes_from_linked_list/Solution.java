package com.myleetcode.linked_list.remove_zero_sum_consecutive_nodes_from_linked_list;

import com.myleetcode.utils.list_node.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeZeroSumSublists(ListNode head) {
        return removeZeroSumSublistsByMap(head);
    }

    /*
    // The observation here is that the sum from index 0 to index M will be
    // equal to sum from index 0 to index N if sum from index (M+1) to index N is 0.
    // Thus, here we track the sum from index 0 to each index, using a Map to indicate
    // the farthest index N that we can remove from index M, then we shall be able to
    // remove M+1 -> N and continue from N+1. This works since we don't have to optimize
    // for the number of sequences to be removed
    https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/discuss/413134/Java-O(N)-with-detail-explanation

    Use the preHead val 0 to process the whole zero sum LinkedList like [0] or [1,-1].
    */
    private ListNode removeZeroSumSublistsByMap(ListNode head) {
        if (head == null) {
            return head;
        }

        Map<Integer, ListNode> sumNodeMap = new HashMap<>();
        ListNode preHead = new ListNode(0);
        preHead.next = head;

        // 1. Get the sum and its corresponding farthest node in LinkedList.
        ListNode curNode = preHead;
        int sum = 0;
        while (curNode != null) {
            sum += curNode.val;
            sumNodeMap.put(sum, curNode);

            curNode = curNode.next;
        }

        // 2. Cut the zero sum range out.
        curNode = preHead;
        sum = 0;
        while (curNode != null) {
            sum += curNode.val;
            if (sumNodeMap.get(sum) != curNode) {
                curNode.next = sumNodeMap.get(sum).next;
            }

            curNode = curNode.next;
        }

        return preHead.next;
    }
}
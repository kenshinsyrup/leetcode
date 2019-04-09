package com.myleetcode.reservoir_sampling.linked_list_random_node;

import com.myleetcode.utils.list_node.ListNode;

import java.util.Random;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    Random random;
    ListNode head;

    // TC: O(N), traverse all nodes is needed
    // SC: O(1), no extra space
    // the same as 398. Random Pick Index.
    // Reservoir Sampling Algo proof: https://leetcode.com/problems/linked-list-random-node/discuss/85659/Brief-explanation-for-Reservoir-Sampling

    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
    public Solution(ListNode head) {
        if(head == null){
            return;
        }

        this.head = head;
        this.random = new Random();
    }

    /** Returns a random node's value. */
    public int getRandom() {
        ListNode curNode = this.head;

        int count = 0; // count traversed nodes
        int randomVal = curNode.val;
        while(curNode != null){
            count++;
            int randomInt = this.random.nextInt(count);
            if(randomInt == 0){
                randomVal = curNode.val;
            }

            curNode = curNode.next;
        }

        return randomVal;

    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */

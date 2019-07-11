package com.myleetcode.linked_list.reverse_nodes_in_k_group;

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
    public ListNode swapPairs(ListNode head) {
        // return swapPairsByThreePointers(head); // Iterative, O(1) space
        return swapPairsByRecursion(head); // Recursive, O(N) space
    }

    // TC: O(N)
    // SC: O(N), recursion tree depth
    // after checked the Discuss, I found the recursion way is so easy to write...
    private ListNode swapPairsByRecursion(ListNode head){
        return swap(head);
    }

    private ListNode swap(ListNode node){
        if(node == null || node.next == null){
            return node;
        }

        // next node of given node
        ListNode nextNode = node.next;

        // finish the back of the node, node.next points to swap(...)
        node.next = swap(node.next.next);

        // nextNode.next points to node
        nextNode.next = node;

        // after swap, we return the nextNode because it's the child node of node's parent node
        return nextNode;
    }

    // sol 2: Iterative
    // TC: O(N)
    // SC: O(1)
    // intuition: 三指针分别为prev，cur，next。对prev操作时记得检查null。记得每次移动两步,nextCur = cur.next.next; nextNext = next.next.next
    private ListNode swapPairsByThreePointers(ListNode head){
        // special case1: linkedlist head is null, or linked list only has one node
        if(head == null || head.next == null){
            return head;
        }

        // keep the new head of reversed LL
        ListNode newHead = head.next;

        // 3 pointers
        ListNode prevNode = null;
        ListNode curNode = head;
        ListNode nextNode = curNode.next;
        // keep the next current node and next next node, declare outside the while to save time
        ListNode nextCurNode = null;
        ListNode nextNextNode = null;
        while(nextNode != null && nextNode.next != null){
            nextCurNode = curNode.next.next;
            nextNextNode = nextNode.next.next;

            nextNode.next = curNode;
            curNode.next = nextCurNode;
            if(prevNode != null){
                prevNode.next = nextNode;
            }

            prevNode = curNode;
            curNode = nextCurNode;
            nextNode = nextNextNode;
        }
        //last swap needed if fastPtr!=null
        if(nextNode != null){
            if(prevNode != null){
                prevNode.next = nextNode;
            }
            nextNode.next = curNode;
            curNode.next = null;
        }

        return newHead;
    }

}

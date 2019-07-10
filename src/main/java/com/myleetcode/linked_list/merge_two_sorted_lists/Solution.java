package com.myleetcode.linked_list.merge_two_sorted_lists;

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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return mergeTwoListsByIterative(l1, l2);

        // return mergeByRecursive(l1, l2);
        
    }

    // TC: O(N), N is the length of the LL
    // SC: O(1)
    private ListNode mergeTwoListsByIterative(ListNode firstHead, ListNode secondHead){
        if(firstHead == null){
            return secondHead;
        }
        if(secondHead == null){
            return firstHead;
        }

        ListNode preHead = new ListNode(-1);
        ListNode curNode = preHead;
        while(firstHead != null && secondHead != null){
            if(firstHead.val >= secondHead.val){
                curNode.next = secondHead;

                secondHead = secondHead.next;
            }else{
                curNode.next = firstHead;

                firstHead = firstHead.next;
            }

            curNode = curNode.next;
        }
        if(firstHead != null){
            curNode.next = firstHead;
        }
        if(secondHead != null){
            curNode.next = secondHead;
        }

        return preHead.next;
    }
    
    // 另外有一个recursive的方法
    // TC: O(N)
    // SC: O(N)
    private ListNode mergeByRecursive(ListNode l1, ListNode l2){
        /*The base cases are if l1 or l2 is null, return the other ListNode. This also holds for the case when l1==l2==null.
For the recursive case, if the value of l1 is less than that of l2, l1 should be ahead of l2 in the returned list. That's why we return ListNode l1 in this case. We also change the next value of l1 to keep merging. For instance: l1=1->2 ; l2=2->3. First step, l1<l2 (1<2), so the first value in the list should be l1=1. Then merge the rest of the first list (2) and the second list (2->3) to find the remaining of the list.
It's the same reasoning for the else statement.*/
        
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        
        if(l1.val <= l2.val){
            l1.next = mergeByRecursive(l1.next, l2);
            return l1;
        }else{
            l2.next = mergeByRecursive(l1, l2.next);
            return l2;
        }
    }
}
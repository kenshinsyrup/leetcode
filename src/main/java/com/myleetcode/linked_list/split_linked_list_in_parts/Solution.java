package com.myleetcode.linked_list.split_linked_list_in_parts;

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
    public ListNode[] splitListToParts(ListNode root, int k) {
        return splitListToPartsByCut(root, k);
    }

    // TC: O(N + K), N is the linkedlist length, K is the groups length
    // SC: O(1), if we dont count the result array ret
    // intuition: cut linkedlist problrm. we could first traverse the linkedlist to get its length. then with the k, we could caculate the length of every part, then we cut the whole linkedlist to parts.
    private ListNode[] splitListToPartsByCut(ListNode root, int k){
        if(k < 0){
            return new ListNode[0];
        }

        ListNode[] ret = new ListNode[k];

        // get the linkedlist length
        ListNode dummyHead = root;
        int len = 0;
        while(dummyHead != null){
            len++;
            dummyHead = dummyHead.next;
        }

        // dummyHead back to head
        dummyHead = root;
        if(len <= k){
            // cut by partLen==1
            for(int i = 0; i < k; i++){
                if(dummyHead != null){
                    ret[i] = dummyHead;
                    dummyHead = cut(dummyHead, 1);
                }else{
                    ret[i] = null;
                }
            }
        }else{
            int base = len / k;
            int remain = len % k;
            for(int i = 0; i < k; i++){
                int partLen = base;
                if(remain != 0){
                    partLen += 1;
                    remain -= 1;
                }

                ret[i] = dummyHead;
                dummyHead = cut(dummyHead, partLen);
            }
        }

        return ret;
    }

    // cut at the partLen-1 pos, so we get a part of partLen which's head is head, and return the remains
    private ListNode cut(ListNode head, int partLen){
        ListNode dummyHead = head;
        ListNode dummyPreHead = new ListNode(-1);
        dummyPreHead.next = dummyHead;
        while(dummyHead != null && partLen != 0){
            ListNode temp = dummyHead;
            dummyHead = dummyHead.next;
            dummyPreHead = temp;

            partLen--;
        }

        // likedlist has no such length of partLen
        if(partLen != 0){
            return null;
        }

        // cut
        dummyPreHead.next = null;

        return dummyHead;
    }
}

package com.myleetcode.linked_list.merge_k_sorted_lists;

import com.myleetcode.utils.list_node.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // here we could notice, if transform to List we cost about 7ms, but if use O(1) SC we need 200+ ms to run all OJ tests. and after we optimize the compare time to log(M) we could run with only 2ms.
        // return mergeKListsBySortWithList(lists);
        // return mergeKListsByMergeLinkedList(lists);
        return mergeKListsByMergeLinkedListAndDND(lists);
    }

    // TC: (N*log(N)), N is the total # of nodes
    // SC: O(N)
    // intuition: 1 we could try to transform all LinkedList to a whole List, then we sort the List and transform it to result LinkedList. let's say there are N nodes in total, then this will cost O(N*log(N)) TC and O(N) extra space.
    private ListNode mergeKListsBySortWithList(ListNode[] lists){
        // special case
        if(lists == null || lists.length == 0){
            return null;
        }
        if(lists.length == 1){
            return lists[0];
        }

        // put all nodes val to List, O(N)
        List<Integer> nodeValList = new ArrayList<>();
        for(ListNode curLinkedListNode: lists){
            while(curLinkedListNode != null){
                nodeValList.add(curLinkedListNode.val);

                curLinkedListNode = curLinkedListNode.next;
            }
        }

        // sort, O(N*log(N))
        Collections.sort(nodeValList);

        // build result LinkedList, O(N)
        ListNode preHead = new ListNode(-1);
        ListNode dummyPreHead = preHead;
        for(int val: nodeValList){
            dummyPreHead.next = new ListNode(val);

            dummyPreHead = dummyPreHead.next;
        }

        return preHead.next;

    }

    // TC: O(N*log(M)), N is total nodes # and M is lists length
    // SC: O(1)
    // mergeKListsByMergeLinkedList costs N^2 times in worst case. we could optimize by this way: we could not to compare one intermedate result with one original one. We could compare original one with original one and put the result to lists and keep do this, so called D&D.
    private ListNode mergeKListsByMergeLinkedListAndDND(ListNode[] lists){
        // special case
        if(lists == null || lists.length == 0){
            return null;
        }
        if(lists.length == 1){
            return lists[0];
        }

        // merge firstLinkedList with lastLinkedList in lists and move these two pointers towards middle, then update lastLinkedList pointer with mid.this way we reduce the compare times to log(M) times, M is the lists length.
        int len = lists.length;
        int start = 0;
        int end = len - 1;
        while(start < end){
            int mid = start + (end - start) / 2;

            for(int i = 0; i <= mid; i++){
                if(!(i == mid && end - i == mid)){
                    lists[i] = mergeTwoLinkedList(lists[i], lists[end - i]);
                }
            }

            end = mid;
        }

        return lists[0];

    }

    // TC: O(N^2)
    // SC: O(1)
    // solution with intuition 2, what if the interviewer dont allow you change LinkedList to List
    // this could be reduce scale to 21. Merge Two Sorted Lists.
    private ListNode mergeKListsByMergeLinkedList(ListNode[] lists){
        // special case
        if(lists == null || lists.length == 0){
            return null;
        }
        if(lists.length == 1){
            return lists[0];
        }

        ListNode preHead = new ListNode(-1);
        ListNode dummyPreHead = preHead;


        // let it start with lists[0]
        // dummyPreHead.next = lists[0];
        // for(int i = 1; i < lists.length; i++){
        //     dummyPreHead.next = mergeTwoLinkedList(dummyPreHead.next, lists[i]);
        // }
        int len = lists.length;
        int start = 0;
        int end = len - 1;
        while(start < end){
            int mid = start + (end - start) / 2;

            for(int i = 0; i <= mid; i++){
                if(!(i == mid && end - i == mid)){
                    lists[i] = mergeTwoLinkedList(lists[i], lists[end - i]);
                }
            }

            end = mid;
        }

        // return preHead.next;
        return lists[0];
    }

    private ListNode mergeTwoLinkedList(ListNode firstHead, ListNode secondHead){
        if(firstHead == null){
            return secondHead;
        }
        if(secondHead == null){
            return firstHead;
        }

        ListNode preHead = new ListNode(-1);
        ListNode dummyPreHead = preHead;
        while(firstHead != null && secondHead != null){
            if(firstHead.val >= secondHead.val){
                dummyPreHead.next = secondHead;

                secondHead = secondHead.next;
            }else{
                dummyPreHead.next = firstHead;

                firstHead = firstHead.next;
            }

            dummyPreHead = dummyPreHead.next;
        }

        // here we could optimize this, because we dont need to check node one bye one if only have one LinkedList
//         while(firstHead != null){
//             dummyPreHead.next = firstHead;

//             firstHead = firstHead.next;
//             dummyPreHead = dummyPreHead.next;
//         }
//         while(secondHead != null){
//             dummyPreHead.next = secondHead;

//             secondHead = secondHead.next;
//             dummyPreHead = dummyPreHead.next;
//         }
//         dummyPreHead.next = null;

        if(firstHead != null){
            dummyPreHead.next = firstHead;
        }else{
            dummyPreHead.next = secondHead;
        }

        return preHead.next;
    }
}

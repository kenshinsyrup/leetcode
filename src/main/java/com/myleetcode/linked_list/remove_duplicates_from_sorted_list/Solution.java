package com.myleetcode.linked_list.remove_duplicates_from_sorted_list;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        
//         针对此题，sorted linkedlist的写法
        return deleteDuplicatesNodesInSortedLinkedList(head);
        
        
        // 这个要审题，sorted的，所以duplicate的nodes肯定是相邻的，所以这个方法虽然是对的，但是还是给自己提个醒。
        // return deleteDuplicatesNodes(head);
    }
    
    private ListNode deleteDuplicatesNodesInSortedLinkedList(ListNode head){
        ListNode dummyNode = head;
        
        while(head != null && head.next != null){
            if(head.next.val == head.val){
                head.next = head.next.next;
            }else{
                head = head.next;
            }
        }
        
        return dummyNode;
    }
    
    private ListNode deleteDuplicatesNodes(ListNode head){
        // 这个too heavey了，这道题是sorted linkedlist，所以不需要这么复杂的写法。
        // 用map或set来保存seen的node.val，用prevNode来保存最后一个node.val不在seen中的node；如果存在，继续向后，直到node的val不存在于seen，用prevNode.next指向该node,然后更新prevNode
        
        Set<Integer> seen = new HashSet<>();
        
        ListNode dummyNode = new ListNode(0);
        ListNode prevNode = dummyNode;
        
//         head是用来遍历整个linkedlist的
        while(head != null){
            if(!seen.contains(head.val)){
                seen.add(head.val);
                
                // 当前head可用，当前prevNode开始更新，next先指向head，然后因为head可用，那么更新prevNode为head
                prevNode.next = head;
                prevNode = head;
                // head向后移动
                head = head.next;
            }else{
                // 当前head不可用，更新prevNode，使当前的prevNode的next指向当前head的next。这一步很重要，是用来构建新linkedlist的关键一步，来跳过不可用的node才能实现删除重复node的目的。
                prevNode.next = head.next;
                // head 向后移动
                head = head.next;
            }
            
        }
        
        return dummyNode.next;
    }
}
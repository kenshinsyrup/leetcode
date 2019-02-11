package com.myleetcode.linked_list.reverse_linked_list;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        // 经典题目，翻转单向链表
        
        // iterative
        return reverseListByIterative(head);
        
        // recursive
        // return reverseListByRevursive(head);
        
    }
    
    private ListNode reverseListByIterative(ListNode head){
        // 单向链表，翻转就是把 node的next 指向 其前面的node。 这里就有两个个问题： 1、当前node的next 本来指向着 下一个node，所以如果直接把 当前node的next 指向 前一个node，会丢失 下一个node 的引用。所以需要存储一下； 2、单向链表不存在对 前一个node 的指向，所以需要单独记录。
        
        ListNode prevNode = null;
        ListNode curNode = head;
        
        while(curNode != null){
            ListNode tempNode = curNode.next;
            curNode.next = prevNode;
            prevNode = curNode;
            curNode = tempNode;
        }
        
        return prevNode;
        
    }
    
    // 比较难想到。
     private ListNode reverseListByRevursive(ListNode head){
         
         // exit
         if (head == null || head.next == null){
             return head;
         }
         
         // 递归结束后，p就是原来链表的最后一个节点
         ListNode p = reverseListByRevursive(head.next);
         
         // node的翻转
         head.next.next = head;
         head.next = null;
         
         
         // 递归结束后，返回原来链表的最后一个节点，就是当前翻转后的链表的第一个节点
         return p;
         
     }
}
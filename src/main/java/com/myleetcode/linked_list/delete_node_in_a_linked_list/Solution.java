package com.myleetcode.linked_list.delete_node_in_a_linked_list;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
        // 思路很重要
        // 正常情况下，如果给定了head node，让我删除第x个node，那么我们就可以直接让 x前面的node的next 指向 x后面的node。
        // 但是这个是给定了一个node，让你从链表中删除，由于是单向链表，我们无法获取到当前node之前的node。那么就要改变思路：我们能访问的是 给定的node及其之后的nodes，那么我们可以把 当前节点后面一个节点的值 赋值给当前节点，然后 删除 当前节点后面这个节点。
        
        node.val = node.next.val;
        node.next = node.next.next;
        
        
    }
}
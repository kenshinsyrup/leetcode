package com.myleetcode.linked_list.middle_of_the_linked_list;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        // 这个本质是一个不知道长度的数组找中间的问题，那么有两个思路：1，想办法知道长度，也就是把linked list变成数组(注意这里用可变数组也就是list，而不是ListNode[])； 2，直接快慢指针遍历一遍，快指针每次走两步，慢指针每次走一步，快指针是二倍的慢指针的速度，当快指针到达尾部时，慢指针刚好到达中间
        
        // 方法1
        // return middleNodeByArray(head);
        
        // 方法2
        return middleNodeByTwoPointers(head);
        
    }
    
    // 方法1. 方法1切忌使用ListNode[] nodes = new ListNode[100];这个固定数组的形式（leetcode的solution部分这样做的），因为这样就要求提前获知linkedlist的长度范围，这样不好。
    private ListNode middleNodeByArray(ListNode head){
        List<ListNode> nodes = new ArrayList<ListNode>();
        
        while(head.next != null){
            nodes.add(head);
            head = head.next;
        }
        
        int len = nodes.size();
        return nodes.get(len/2);
    }
    
    // 方法2
    private ListNode middleNodeByTwoPointers(ListNode head){
        ListNode fastNode = head;
        ListNode slowNode = head;
        
        // 注意循环结束条件
        while(fastNode != null && fastNode.next != null){
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        
        return slowNode;
    }
}
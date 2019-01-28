package com.myleetcode.linked_list.merge_two_sorted_lists;

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
        
        // 直观
        return mergeTwoListsByIterative(l1, l2);
        
        // 不好想
        // return mergeByRecursive(l1, l2);
        
    }
    
    private ListNode mergeTwoListsByIterative(ListNode l1, ListNode l2){
        // 本质是构建一个新链表。
//         通用方式为：一个dummyNode，一个用来记录 当前要判断的两个node被选出的node 要被附着到的node，也就是prevNode；l1和l2都不为null的情况下，比较l1z指向的node和l2指向的node的val，小的那一个，被prevNode的next指向。这时，当前的prevNode的next更新好了，接下来很重要，我们要更新prevNode本身，因为我们已经链接了一个新的node，那么这个新node就是prevNode了，更新。然后小的那一个后移；最后，未完者附着结尾；最后，返回dummyNode.next
        
        ListNode dummyNode = new ListNode(0);
        ListNode prevNode = dummyNode;
        
        while(l1 != null && l2 != null){
            if(l1.val <= l2.val){
                // 更新prevNode.next和prevNode自身的顺序是严格的，不可互换.
                // 当前prevNode的下一个node应该是l1
                prevNode.next = l1;
                // 更新当前的prevNode
                prevNode = l1;
                // l1后移动
                l1 = l1.next;
            }else{
                // 同理
                prevNode.next = l2;
                prevNode  = l2;
                l2 = l2.next;
            }
        }
        
        if(l1 == null && l2 != null){
            prevNode.next = l2;
        }
        if(l2 == null && l1 != null){
            prevNode.next = l1;
        }
        
        return dummyNode.next;
        
    }
    
    // 另外有一个recursive的方法，不是很容易想通
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
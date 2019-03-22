package com.myleetcode.linked_list.remove_linked_list_elements;

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
    public ListNode removeElements(ListNode head, int val) {
        // return removeElementsByDelete(head, val);
        return removeElementsByDeleteII(head, val);
    }

    // LinkedList的题目很多思路都很简单而且清晰，但是最麻烦的问题是如何用代码实现，所以主要是考虑清楚：指针和object之间的关系
    private ListNode removeElementsByDeleteII(ListNode head, int val){
        if(head == null){
            return head;
        }

        // 新建一个object node(-1), 用preHead指向node(-1). 这个node我们用来作为删除不符合要求的node后的链表的
        ListNode preHead = new ListNode(-1);
        // 让preHead指向的node(-1)的next指向, head指针所指向的node
        preHead.next = head;

        // 让pre指针指向preHead所指向的node(-1)
        ListNode pre = preHead;
        // 让cur指针指向head所指向的node
        ListNode cur = head;

        // 当cur指针指向的node不是null时，查找
        while(cur != null){
            // 如果cur指针指向的node的value是val，那么这个node要删掉，也就是让指向他的node的next，指向cur指向的node的next,这样就跳过了cur指向的node，也就是删掉了
            if(cur.val == val){
                pre.next = cur.next;
            }else{
                // 否则，说明这个不用删，那么pre指针指向的node的next，指向cur指向的node
                pre.next = cur;
                // pre指针更新指向，让pre指向 cur指针指向的node
                // 这个不要写到这层外面，因为只有cur指向的node有效时，才更新pre，让pre指向cur指向的node。写在外面的话就变成了删掉的node也要被pre指向，会完全出错
                pre = cur;
            }

            // cur则是每次都要更新指向的,要一步一步向后移动
            // cur指针更新指向，让cur指针指向 cur指向的node的下一个node
            cur = cur.next;
        }

        // 这里必须返回preHead.next，因为虽然preHead.next 和 head在最初是指向同一个node，而我们中间也没有更新head，正常情况下preHead.next是和head指向的确实还是同一个node的，但是特殊情况就是head所指向的node被删了，比如输入[1] 1,那么这时候preHead.next就指向的是null了，而Head还指向那个被删掉的node，这个例子里就是1
        // 所以很多人写这个代码时，会不使用cur这个指针（其他代码相同），而是直接用head去移动，这样也不会有最后要返回preHead.next还是head的困惑，总之这里要返回的必须是preHead.next.
        return preHead.next;
        // return head;
    }

    // intuition: traverse from head to tail to delete node with value val. we have to strategies: 1 is delete current node with val, this way we should keep track of its parent node and make its parent node's next point to current node's next. 2 is delete current node'next node if that node has value val, and make current node next point to next node's next if its value is not val.
    // O(n), we use strategy 1
    private ListNode removeElementsByDelete(ListNode head, int val){
        // special case
        if(head == null){
            return head;
        }

        // make a dummy node points to head
        ListNode preHeadDummy = new ListNode(-1);
        // check head first
        while(head != null && head.val == val){
            head = head.next;
        }
        preHeadDummy.next = head;
        ListNode preHead = preHeadDummy;
        // traverse and delete next
        while(head != null){
            while(head != null && head.val == val){
                head = head.next;
            }

            preHeadDummy.next = head;
            preHeadDummy = head;

            if(head != null){
                head = head.next;
            }
        }

        return preHead.next;
    }
}

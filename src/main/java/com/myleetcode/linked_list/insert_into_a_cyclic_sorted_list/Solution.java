package com.myleetcode.linked_list.insert_into_a_cyclic_sorted_list;

import com.myleetcode.utils.node_with_next.Node;

/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val,Node _next) {
        val = _val;
        next = _next;
    }
};
*/
class Solution {
    public Node insert(Node head, int insertVal) {
        return insertByTraverse(head, insertVal);
    }

    /*
    出错点：
    1 ascending order不代表元素都不相同，全部相同值也是ascending。所以需要检查一种特殊情况就是全部值相同，那么当回到起点head时，插入insertVal。检查是否回到了起点，使用curNode.next == head
    */

    // intuition: traverse the LinkedList from curNode, if curNode.next > interVal, insert the insertVal here; otherwise, if curNode.next == head, means curNode is tail node, we insert insertVal between curNode and curNode.next
    // TC: O(N)
    // SC: O(1)
    private Node insertByTraverse(Node headNode, int insertVal){
        if(headNode == null){
            Node newHeadNode = new Node(insertVal, null);
            newHeadNode.next = newHeadNode;

            return newHeadNode;
        }

        Node curNode = headNode;
        while(curNode.next != null){
            // inset between tail->head
            if(curNode.val > curNode.next.val && (curNode.val <= insertVal || curNode.next.val >= insertVal) ){
                curNode.next = new Node(insertVal, curNode.next);

                return headNode;
            }

            // insert between node1->node2 where node1<insertVal<node2 and node1<node2
            if(curNode.val < curNode.next.val && curNode.val <= insertVal && curNode.next.val >= insertVal){
                curNode.next = new Node(insertVal, curNode.next);

                return headNode;
            }

            // !!!  if we back to headNode again and still not insert, means all nodes have same val, insert here
            if(curNode.next == headNode){
                curNode.next = new Node(insertVal, curNode.next);

                return headNode;
            }

            curNode = curNode.next;
        }

        return headNode;
    }
}

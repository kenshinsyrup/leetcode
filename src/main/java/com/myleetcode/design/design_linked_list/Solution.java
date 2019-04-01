package com.myleetcode.design.design_linked_list;

class MyLinkedList {
    // https://leetcode.com/problems/design-linked-list/discuss/194266/Java-single-linked-beats-99.93(65ms)

    class Node{
        Node next;
        int val;

        Node(int val){
            this.val = val;
        }
    }

    int size; // store LinkedList's length, otherwise we need O(n) time to check if a index is valid;

    Node head;// linkedlist head
    Node tail;// linkedlist tail

    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = null;
        tail = null;

        size = 0;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index < 0 || index >= size){
            return -1;
        }

        Node dummy = head;
        int count = 0;
        while(count < index){
            dummy = dummy.next;
            count++;
        }

        return dummy.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node newHead = new Node(val);
        newHead.next = head;

        head = newHead;
        size++;

        // check tail
        if(tail == null){
            tail = newHead;
        }
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node newTail = new Node(val);
        if(tail != null){
            tail.next = newTail;
        }
        tail = newTail;
        size++;

        // check head
        if(head == null){
            head = newTail;
        }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size){
            return;
        }

        if(index == 0){
            addAtHead(val);
        }else if(index == size){
            addAtTail(val);
        }else{
            Node preNode = head;
            int count = 0;
            while(count < index - 1){
                preNode = preNode.next;
                count++;
            }

            Node newNode = new Node(val);
            Node temp = preNode.next;
            preNode.next = newNode;
            newNode.next = temp;

            size++;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size){
            return;
        }

        if(index == 0){
            if(size == 1){
                head = null;
                tail = null;
                return;
            }

            head = head.next;
            size--;
            return;
        }

        Node preNode = new Node(-1);
        preNode.next = head;
        Node dummyHead = head;
        int count = 0;
        while(count < index){
            preNode = dummyHead;
            dummyHead = dummyHead.next;

            count++;
        }

        if(index == size - 1){
            preNode.next = null;
            tail = preNode;
            size--;
            return;
        }

        preNode.next = dummyHead.next;

        size--;
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */

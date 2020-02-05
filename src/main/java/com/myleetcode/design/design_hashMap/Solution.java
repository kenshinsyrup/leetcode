package com.myleetcode.design.design_hashMap;

public class Solution {
    class MyHashMap {

        /*
        Use Array of LinkedList to implement Map.
        https://leetcode.com/problems/design-hashmap/discuss/152746/Java-Solution

        Array store data which are some LinkedLists.
        LinkedList to solve collision. If some keys has same hash value, they will be stored in the same idx-th LinkedList of the Array. We use the key itself to find the specific node in this LinkedList.
        */
        class ListNode {
            int key;
            int val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        // Find the prevnode of first node with given key, if no such node with given key, the prevnode should be the tail node of current LinkedList
        ListNode findPrevNodeOfKeyNode(ListNode node, int key) {
            ListNode curNode = node;
            ListNode prev = null;
            while (curNode != null && curNode.key != key) {
                prev = curNode;
                curNode = curNode.next;
            }
            return prev;
        }

        int getLinkedListIdxForKey(int key) {
            return Integer.hashCode(key) % CAPACITY;
        }

        final int CAPACITY = 10000;
        ListNode[] LinkedLists;

        /**
         * Initialize your data structure here.
         */
        public MyHashMap() {
            this.LinkedLists = new ListNode[CAPACITY];
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            int idx = getLinkedListIdxForKey(key);

            ListNode root = LinkedLists[idx];
            if (root == null) {
                root = new ListNode(-1, -1);
                LinkedLists[idx] = root;
            }

            ListNode prev = findPrevNodeOfKeyNode(root, key);
            // If prev node is the tail node at ids-th LinkedList. This is PUT.
            if (prev.next == null) {
                prev.next = new ListNode(key, value);
            } else {
                // If prev is the previous node whcih has given key, so we update that node. This is UPDATE.
                prev.next.val = value;
            }
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int idx = getLinkedListIdxForKey(key);
            ListNode root = LinkedLists[idx];
            if (root == null) {
                return -1;
            }

            ListNode prev = findPrevNodeOfKeyNode(root, key);
            return prev.next == null ? -1 : prev.next.val;
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int idx = getLinkedListIdxForKey(key);
            ListNode root = LinkedLists[idx];
            if (root == null) {
                return;
            }

            ListNode prev = findPrevNodeOfKeyNode(root, key);
            if (prev.next == null) {
                return;
            }

            prev.next = prev.next.next;

        }
    }

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
}

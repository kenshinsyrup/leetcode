package com.myleetcode.design.all_o_one_data_structure;

import java.util.*;

public class Solution {
    class AllOne {

    /*
    Double Linked List and Map
    Similar: LRU, LFU
    https://leetcode.com/problems/all-oone-data-structure/discuss/91443/JAVA(HashMap-%2B-constructed-LinkedList)-guarantee-O(1)-with-explanation

    Map<String, Node>: key is given String key, value is Node in Double Linked List. This helps us find a Node in O(1) time
    Doulbe Linked List: stores Nodes
    Node: stores all keys that has same counting number. To implement this, the Node maintains a Set<String>.

    */

        /*
        Double Linked List Node with a Set<String> and val. Set stores all keys having val counting number, val is the counting number.
        */
        private class Node {
            Set<String> keySet;
            int val;

            Node prevNode;
            Node nextNode;

            public Node() {
                this.keySet = new HashSet<>();
                this.val = 0;

                this.prevNode = null;
                this.nextNode = null;
            }
        }

        public void deleteNode(Node node) {
            node.prevNode.nextNode = node.nextNode;
            node.nextNode.prevNode = node.prevNode;

            node.prevNode = null;
            node.nextNode = null;
        }

        public void removeKeyFromNode(String key, Node node) {
            node.keySet.remove(key);

            // If no any keys stored in this node, delete it.
            if (node.keySet.isEmpty()) {
                deleteNode(node);
            }
        }

        // Insert a node into the Double Linked List, position is behind the given node.
        public void insertNode(Node node, Node newNode) {
            node.nextNode.prevNode = newNode;
            newNode.nextNode = node.nextNode;
            node.nextNode = newNode;
            newNode.prevNode = node;
        }

        Node headNode;
        Node tailNode;
        Map<String, Node> keyNodeMap;

        /**
         * Initialize your data structure here.
         */
        public AllOne() {
            this.headNode = new Node();
            this.tailNode = new Node();
            headNode.nextNode = tailNode;
            tailNode.prevNode = headNode;

            this.keyNodeMap = new HashMap<>();
        }

        /**
         * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
         */
        public void inc(String key) {
            if (!this.keyNodeMap.containsKey(key)) {
                // Retrieve the first node behind the head node.
                Node firstNode = this.headNode.nextNode;

                // If firstNode.val is not 1, we need build a real first node for the brand new given key.
                if (firstNode.val != 1) {
                    Node realFirstNode = new Node();
                    realFirstNode.keySet.add(key);
                    realFirstNode.val = 1;

                    insertNode(firstNode.prevNode, realFirstNode);
                    this.keyNodeMap.put(key, realFirstNode);
                } else {
                    // Otherwise, we just put the given key into the counting number 1 Node's keySet.
                    firstNode.keySet.add(key);

                    this.keyNodeMap.put(key, firstNode);
                }
            } else {
                Node node = this.keyNodeMap.get(key);
                if (node.val + 1 == node.nextNode.val) {
                    node.nextNode.keySet.add(key);

                    this.keyNodeMap.put(key, node.nextNode);
                } else {
                    Node newNode = new Node();
                    newNode.keySet.add(key);
                    newNode.val = node.val + 1;

                    insertNode(node, newNode);
                    this.keyNodeMap.put(key, newNode);
                }

                removeKeyFromNode(key, node);
            }
        }

        /**
         * Decrements an existing key by 1. If Key's value is 1, remove it from the data structure.
         */
        public void dec(String key) {
            if (!this.keyNodeMap.containsKey(key)) {
                return;
            }

            Node node = this.keyNodeMap.get(key);
            if (node.val == 1) {
                this.keyNodeMap.remove(key);
            } else if (node.val - 1 == node.prevNode.val) {
                node.prevNode.keySet.add(key);

                this.keyNodeMap.put(key, node.prevNode);
            } else {
                Node newNode = new Node();
                newNode.keySet.add(key);
                newNode.val = node.val - 1;
                insertNode(node.prevNode, newNode);

                this.keyNodeMap.put(key, newNode);
            }

            removeKeyFromNode(key, node);
        }

        /**
         * Returns one of the keys with maximal value.
         */
        public String getMaxKey() {
            if (this.tailNode.prevNode.keySet.isEmpty()) {
                return "";
            }

            Iterator<String> it = this.tailNode.prevNode.keySet.iterator();
            return it.next();
        }

        /**
         * Returns one of the keys with Minimal value.
         */
        public String getMinKey() {
            if (this.headNode.nextNode.keySet.isEmpty()) {
                return "";
            }

            Iterator<String> it = this.headNode.nextNode.keySet.iterator();
            return it.next();
        }
    }

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
}

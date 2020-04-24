package com.myleetcode.design.lru_cache;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    // https://leetcode.com/problems/lru-cache/discuss/45911/Java-Hashtable-%2B-Double-linked-list-(with-a-touch-of-pseudo-nodes)
//     https://leetcode.com/problems/lru-cache/discuss/46188/My-easy-to-understand-Java-solution-with-HashMap-and-Doubly-Linked-List

//     思路：get的O(1)时间应该有很多，比如array或者map；put(这里可以分为两个操作，插入或者更新)O(1),同时删除也要求O(1)这一点array做不到，但是map可以。
//     另外，这个题目的要求是需要能在O(1)的时间内移动任意元素到排列的最头部，这个是双向链表的特性：双向链表的node可以再只有自身信息的情况下，将自己从链表中删除；这时候再将该节点重新插入到双向链表的头部，一共是两个O(1)的时间，就完成了移动任意元素到头部这个操作。
    // 所以，我们需要的是一个双向链表来保存所有的cache，将所有的cache视作DLinkedList中的node，这个用来完成LRUCache的O(1)时间移动元素到头部的操作(这个map做不到)。同时我们需要一个map，来完成再O(1)时间内取得任意一个节点的操作(这个双向链表做不到),所以我们可以把LRUCache中的cache视作node，将node的key作为map的key，将node自身作为map的value。如此以来，我们通过空间就换到来时间：我们相当于保存了两份缓存，但是将get，put时间都降低到了O(1)

    class LRUCache {

        /*
        Double Linked List with Map, O(1) Solution, far more complex than naive solution.
        */
        class DLinkedNode {
            int key;
            int value;

            DLinkedNode prev;
            DLinkedNode next;
        }

        class DLinkedList {
            private DLinkedNode head;
            private DLinkedNode tail;

            public DLinkedList() {
                // head and tail for help. Nodes between head and tail are those caches node.
                head = new DLinkedNode();
                tail = new DLinkedNode();

                head.prev = null;
                tail.next = null;
                head.next = tail;
                tail.prev = head;
            }

            // addNode always add node right after head
            public void addNode(DLinkedNode node) {
                // keep the next node of head first
                DLinkedNode nextNode = head.next;

                // insert new node after head
                // first link the head
                node.prev = head;
                head.next = node;
                // then link the kept next node
                nextNode.prev = node;
                node.next = nextNode;
            }

            // DLinkedList node can remove itself easily, just link the prev node and next node of itself
            // removeNode remove the given node itself.
            public void removeNode(DLinkedNode node) {
                DLinkedNode prevNode = node.prev;
                DLinkedNode nextNode = node.next;

                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }

            // moveToHead move the given node to right after head
            public void moveToHead(DLinkedNode node) {
                // 先删除
                removeNode(node);

                // 再插入到head后面
                addNode(node);
            }

            // pop出tail node正前方的node, 也就是移除LRC
            // 需要返回该node是因为再从DLinkedList中删除了尾部到节点对应到是从LRUCache中删除最不常用cache到操作，那么也需要从cacheMap中将该node删除，所以需要返回该node取得他到key从而让cacheMap去根据k->node删除
            public DLinkedNode popTail() {
                DLinkedNode node = tail.prev;
                removeNode(node);
                return node;
            }
        }

        // key -> DLinkNode达到*O(1)查找key*
        private Map<Integer, DLinkedNode> cacheMap;
        // key额外用链表存储来*记录顺序*并为了能*O(1)时间增删*使用双向链表, 同时DLinkNode还有*value信息*。
        private DLinkedList dLinkedList;

        // count caches, ie the real size.
        private int count;
        // LRC capacity
        private int capacity;

        public LRUCache(int capacity) {
            this.count = 0;
            this.capacity = capacity;

            cacheMap = new HashMap<Integer, DLinkedNode>();

            dLinkedList = new DLinkedList();
        }

        public int get(int key) {
            DLinkedNode node = cacheMap.get(key);
            // Key not exists in LRUCache, return -1
            if (node == null) {
                return -1;
            }

            // !!!Move the touched cache to head
            dLinkedList.moveToHead(node);

            return node.value;
        }

        public void put(int key, int value) {
            // first check if this key->node exist
            DLinkedNode node = cacheMap.get(key);
            if (node == null) {
                // not exists, we should check if we reached the max capacity
                if (this.count == this.capacity) {
                    // reach max capacity already, poptail first
                    DLinkedNode tailNode = dLinkedList.popTail();

                    // 重要！！！维护这个cacheMap。从DLinkedList中删除节点时，同时从map中删除。
                    cacheMap.remove(tailNode.key);

                    // decrease the count
                    this.count--;
                }

                // now we are ready to insert new node
                node = new DLinkedNode();
                node.key = key;
                node.value = value;

                // 将新node插入DLinkedList
                dLinkedList.addNode(node);

                // 重要！！！维护这个cacheMap。向DLinkedList中加入新节点的时候要同时加入到map中。
                cacheMap.put(key, node);

                // increase the count
                this.count++;

            } else {
                // exists, we only need update its value and move to head
                node.value = value;
                dLinkedList.moveToHead(node);
            }
        }


    /*
    Naive Solution, AC. Get O(N), Put O(N)
    */
//     int capacity;
//     int size;
//     List<Integer> keyList;
//     Map<Integer, Integer> keyValMap;

//     public LRUCache(int capacity) {
//         this.capacity = capacity;
//         this.size = 0;
//         this.keyList = new ArrayList<>();
//         this.keyValMap = new HashMap<>();
//     }

//     public int get(int key) {
//         if(!keyValMap.containsKey(key)){
//              return -1;
//         }

//         // !!!Move the touched cache to head
//         int idx = 0;
//         for(int i = 0; i < keyList.size(); i++){
//             if(keyList.get(i) == key){
//                 idx = i;
//                 break;
//             }
//         }
//         keyList.remove(idx);
//         keyList.add(key);

//         return keyValMap.get(key);
//     }

//     public void put(int key, int value) {
//         // Update.
//         if(keyValMap.containsKey(key)){
//             keyValMap.put(key, value);

//             // !!!Move the touched cache to head
//             int idx = 0;
//             for(int i = 0; i < keyList.size(); i++){
//                 if(keyList.get(i) == key){
//                     idx = i;
//                     break;
//                 }
//             }
//             keyList.remove(idx);
//             keyList.add(key);

//             return;
//         }

//         // Insert.
//         keyValMap.put(key, value);
//         keyList.add(key);
//         size++;
//         // Need remove LRU key.
//         if(size > capacity){
//             int removeKey = keyList.get(0);
//             keyValMap.remove(removeKey);
//             keyList.remove(0);
//             size--;
//         }
//     }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

}

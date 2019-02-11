package com.myleetcode.lru_cache;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    class LRUCache {
        // https://leetcode.com/problems/lru-cache/discuss/45911/Java-Hashtable-%2B-Double-linked-list-(with-a-touch-of-pseudo-nodes)
//     https://leetcode.com/problems/lru-cache/discuss/46188/My-easy-to-understand-Java-solution-with-HashMap-and-Doubly-Linked-List

//     思路：get的O(1)时间应该有很多，比如array或者map；put(这里可以分为两个操作，插入或者更新)O(1),同时删除也要求O(1)这一点array做不到，但是map可以。
//     另外，这个题目的要求是需要能在O(1)的时间内移动任意元素到排列的最头部，这个是双向链表的特性：双向链表的node可以再只有自身信息的情况下，将自己从链表中删除；这时候再将该节点重新插入到双向链表的头部，一共是两个O(1)的时间，就完成了移动任意元素到头部这个操作。
        // 所以，我们需要的是一个双向链表来保存所有的cache，将所有的cache视作DLinkedList中的node，这个用来完成LRUCache的O(1)时间移动元素到头部的操作(这个map做不到)。同时我们需要一个map，来完成再O(1)时间内取得任意一个节点的操作(这个双向链表做不到),所以我们可以把LRUCache中的cache视作node，将node的key作为map的key，将node自身作为map的value。如此以来，我们通过空间就换到来时间：我们相当于保存了两份缓存，但是将get，put时间都降低到了O(1)

        // key -> node
        private Map<Integer, DLinkedNode> cacheMap;
        // 对应的双向链表
        private DLinkedList dLinkedList;

        // count caches
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

            if(node == null){
                return -1; // key not exists in LRUCache, return -1
            }

            // move the touched cache to head
            dLinkedList.moveToHead(node);

            return node.value;
        }

        public void put(int key, int value) {
            // first check if this key->node exist
            DLinkedNode node = cacheMap.get(key);
            if(node == null){
                // not exists, we should check if we reached the max capacity
                if(this.count == this.capacity){
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

            }else{
                // exists, we only need update its value and move to head
                node.value = value;
                dLinkedList.moveToHead(node);
            }
        }
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    class DLinkedNode{
        int key;
        int value;

        DLinkedNode pre;
        DLinkedNode post;
    }

    class DLinkedList{
        private DLinkedNode head;
        private DLinkedNode tail;

        DLinkedList(){
            // head and tail for help
            head = new DLinkedNode();
            tail= new DLinkedNode();

            head.pre = null;
            tail.post = null;
            head.post = tail;
            tail.pre = head;
        }


        // use two extra nodes for help, head node and tail node. Nodes between head and tail are those caches node

        // addNode always add node right after head
        public void addNode(DLinkedNode node){
            node.pre = head;
            node.post = head.post;

            head.post.pre = node;

            head.post = node;
        }

        // DLinkedList node can remove itself easily.
        // removeNode remove the given node itself.
        public void removeNode(DLinkedNode node){
            node.pre.post = node.post;

            node.post.pre = node.pre;
        }

        // moveToHead move the given node to right after head
        public void moveToHead(DLinkedNode node){
            // 先删除
            removeNode(node);

            // 再插入到head后面
            addNode(node);
        }

        // pop出tail node正前方的node, 也就是移除LRC
        // 需要返回该node是因为再从DLinkedList中删除了尾部到节点对应到是从LRUCache中删除最不常用cache到操作，那么也需要从cacheMap中将该node删除，所以需要返回该node取得他到key从而让cacheMap去根据k->node删除
        public DLinkedNode popTail(){
            DLinkedNode node = tail.pre;
            removeNode(node);
            return node;
        }

    }

}

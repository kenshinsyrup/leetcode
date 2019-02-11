package com.myleetcode.lru_cache;

public class LRUCache {
    /**
     * 146. LRU Cache
     * Hard
     *
     * 2303
     *
     * 72
     *
     * Favorite
     *
     * Share
     * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
     *
     * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
     * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
     *
     * Follow up:
     * Could you do both operations in O(1) time complexity?
     *
     * Example:
     *
     * LRUCache cache = new LRUCache( 2  //capacity  );
     *
             *cache.put(1,1);
     *cache.put(2,2);
     *cache.get(1);       // returns 1
     *cache.put(3,3);    // evicts key 2
     *cache.get(2);       // returns -1 (not found)
     *cache.put(4,4);    // evicts key 1
     *cache.get(1);       // returns -1 (not found)
     *cache.get(3);       // returns 3
     *cache.get(4);       // returns 4
     */

//    Topics: Design

    /**
     * Companies: Amazon
     * |
     * 64
     *
     * Bloomberg
     * |
     * 25
     *
     * Microsoft
     * |
     * 20
     *
     * Facebook
     * |
     * 20
     *
     * Apple
     * |
     * 13
     *
     * Uber
     * |
     * 13
     *
     * Google
     * |
     * 12
     *
     * Zillow
     * |
     * 8
     *
     * Two Sigma
     * |
     * 6
     *
     * Oracle
     * |
     * 6
     *
     * Adobe
     * |
     * 5
     *
     * Twilio
     * |
     * 4
     *
     * Yahoo
     * |
     * 4
     *
     * Salesforce
     * |
     * 4
     *
     * Baidu
     * |
     * 3
     *
     * Cisco
     * |
     * 3
     *
     * Nvidia
     * |
     * 3
     *
     * VMware
     * |
     * 2
     *
     * eBay
     * |
     * 2
     *
     * Lyft
     * |
     * 2
     *
     * Snapchat
     * |
     * 2
     *
     * Pinterest
     * |
     * 2
     *
     * GoDaddy
     * |
     * 2
     *
     * Goldman Sachs
     * |
     * 2
     *
     * Visa
     * |
     * 2
     *
     * SAP
     * |
     * 2
     */
}

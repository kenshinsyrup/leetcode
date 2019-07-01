package com.myleetcode.binary_search.time_based_key_value_store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TimeMap {

    // intuition:
    // since one key may have multiple values, so we know we could use the DS Map<String, List<Elem>>, where class Elem has two fields, one field is value that stores the given value, and one is timestamp that stores the given timesstamp
    // "The timestamps for all TimeMap.set operations are strictly increasing." so we could do BS based on Elem.timestamp when do get operation

    /*
     出错点：
     1 审题：题目中set的timestamp已经是升序了，所以List中的Elem按照先后顺序是按照timestamp排好序的，不需要额外的sort
     2 智障：BS的写法，start = mid+1; end = mid-1;错误的写成了start++;end--;
    */

    class Elem {
        String value;
        int timestamp;

        public Elem(String value, int timestamp){
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    /** Initialize your data structure here. */
    public TimeMap() {
        this.map = new HashMap<>();
    }

    /* sol 1: set O(logN), get O(N), TLE
    // store
    Map<String, PriorityQueue<Elem>> map;

    // TC: O(logN), N is the longest length of List in Map
    public void set(String key, String value, int timestamp) {
        this.map.putIfAbsent(key, new PriorityQueue<Elem>(new Comparator<Elem>(){
            public int compare(Elem e1, Elem e2){
                return e1.timestamp - e2.timestamp;
            }
        }));

        this.map.get(key).offer(new Elem(value, timestamp));
    }

    // TC: O(N)
    public String get(String key, int timestamp) {
        if(!this.map.containsKey(key)){
            return "";
        }

        PriorityQueue<Elem> elemPQ = this.map.get(key);
        // O(N)
        List<Elem> elemList = new ArrayList<>(elemPQ);
        // bs to find the given timestamp, O(logN)
        return binarySearchByTimestamp(elemList, timestamp);
    }
    */

    /* sol 2: set O(1), get O(logN)*/
    // store
    Map<String, List<Elem>> map;

    // TC: O(1)
    public void set(String key, String value, int timestamp) {
        this.map.putIfAbsent(key, new ArrayList<>());
        this.map.get(key).add(new Elem(value, timestamp));
    }

    // TC: O(logN), N is the longest length of List in Map
    public String get(String key, int timestamp) {
        if(!this.map.containsKey(key)){
            return "";
        }

        List<Elem> elemList = this.map.get(key);

        // !!! already sorted, because when set, all timestamp are ascending ordered automatically

        // bs to find the given timestamp
        return binarySearchByTimestamp(elemList, timestamp);
    }

    private String binarySearchByTimestamp(List<Elem> elemList, int timestamp){
        int start = 0;
        int end = elemList.size() - 1;
        int idx = -1;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(elemList.get(mid).timestamp > timestamp){ // after out of while loop, end will be the first elem that not larger than timestamp, or -1
                end = mid - 1;
            }else{
                start = mid + 1;
            }
        }

        // if end < 0, means not find
        if(end < 0){
            return "";
        }

        return elemList.get(end).value;
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */

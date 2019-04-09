package com.myleetcode.design.insert_delete_get_random_o_1;

import java.util.*;

class RandomizedSet {

    Map<Integer, Integer> valIdxMap;
    List<Integer> valList;
    Random random;
    //https://leetcode.com/problems/insert-delete-getrandom-o1/discuss/85401/Java-solution-using-a-HashMap-and-an-ArrayList-along-with-a-follow-up.-(131-ms)
    /*
    map could retrive and remove elem in O(1), list could retrive elem in O(1), remove in O(N) and insert with add(val) in O(1) and add(idx,val) in O(N).
    for getRandom, we need to generate a random idx and retrive its value from list. then problem is list remove operation cost O(N). but this is in general, if every time we remove the last elem of list, then this operation only cost O(1).
    so we could use list to store all elems, and build a map to store the value-idx pair. this way, when we could insert

    */
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.valIdxMap = new HashMap<>();
        this.valList = new ArrayList<>();
        this.random = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        // check, O(1)
        if(valIdxMap.containsKey(val)){
            return false;
        }

        // add to tail, O(1)
        valList.add(val);

        // get list size O(1), put an entry to map O(1)
        valIdxMap.put(val, valList.size() - 1);

        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        // check O(1)
        if(!valIdxMap.containsKey(val)){
            return false;
        }

        // get idx of val from map O(1)
        int idx = valIdxMap.get(val);

        // get list size O(1)
        int size = valList.size();
        // move the last elem to idx O(1)
        valList.set(idx, valList.get(size - 1));
        // update map O(1)
        valIdxMap.put(valList.get(idx), idx);

        // delete the tail in list O(1)
        valList.remove(size - 1);
        // delete the val in map O(1)
        valIdxMap.remove(val);

        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        int size = valList.size();
        // random idx,
        int randomIdx = random.nextInt(size);

        return valList.get(randomIdx);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

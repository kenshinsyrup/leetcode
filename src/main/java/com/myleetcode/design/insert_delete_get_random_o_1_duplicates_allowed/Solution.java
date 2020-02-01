package com.myleetcode.design.insert_delete_get_random_o_1_duplicates_allowed;

import java.util.*;

public class Solution {

    class RandomizedCollection {

    /*
    Same like: 380. Insert Delete GetRandom O(1). Just need to store val->Set<idx> instead of val->idx in map. DONT use List<idx> because when we remove val, we need change the idx of some vals, the List<idx> could not give us a O(1) time to change.
    https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/discuss/85555/Clean-O(1)-Java-Solution-with-HashMap-and-Set

    About the LinkedHashSet and HashSet:
    A HashSet instead of a LinkedHashSet will be fine, since we're never iterating over an entire set - you only iterate to get 1 element in the set.
    BUT, See: https://stackoverflow.com/questions/12069877/what-the-iteration-cost-on-a-hashset-also-depend-on-the-capacity-of-backing-map on why a naive hashset takes more time for iteration.
    So, HashSet is OK for this problem, and has same big-O notation with LinkedHashSet, but LinkedHashSet do run faster in LeetCode than HashSet because of the iterator.

    */

        Map<Integer, Set<Integer>> valIdxsMap;
        List<Integer> valList;

        Random random;

        /**
         * Initialize your data structure here.
         */
        public RandomizedCollection() {
            this.valIdxsMap = new HashMap<>();
            this.valList = new ArrayList<>();
            this.random = new Random();
        }

        /**
         * Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
         */
        public boolean insert(int val) {
            boolean newInsert = true;
            Set<Integer> idxSet = new LinkedHashSet<>(); // Runtime: 11 ms, faster than 98.20%; Memory Usage: 44.1 MB, less than 100.00%
            // Set<Integer> idxSet = new HashSet<>(); // Runtime: 27 ms, faster than 13.08%; Memory Usage: 52.1 MB, less than 8.33%
            if (this.valIdxsMap.containsKey(val)) {
                newInsert = false;
                idxSet = this.valIdxsMap.get(val);
            }

            this.valList.add(val);
            idxSet.add(this.valList.size() - 1);

            this.valIdxsMap.put(val, idxSet);

            return newInsert;
        }

        /**
         * Removes a value from the collection. Returns true if the collection contained the specified element.
         */
        public boolean remove(int val) {
            if (!this.valIdxsMap.containsKey(val)) {
                return false;
            }

            // The element to remove:
            // Get the idx set of given val.
            Set<Integer> idxSet = this.valIdxsMap.get(val);
            int removeIdx = idxSet.iterator().next(); // The way to get a element from Set.
            // Get the last element's val and idx in current this.valList
            int lastValIdx = this.valList.size() - 1;
            int lastVal = this.valList.get(lastValIdx);
            // From valList: Remove the element at removeIdx.
            this.valList.set(removeIdx, lastVal);
            this.valList.remove(lastValIdx);
            // From Map:
            idxSet.remove(removeIdx);
            this.valIdxsMap.put(val, idxSet);

            // The element which is affected by val's remove:
            Set<Integer> lastValIdxSet = this.valIdxsMap.get(lastVal);
            lastValIdxSet.add(removeIdx);
            lastValIdxSet.remove(lastValIdx);
            this.valIdxsMap.put(lastVal, lastValIdxSet);

            // Check whether we need to remove the val key from Map.
            if (idxSet.isEmpty()) {
                this.valIdxsMap.remove(val);
            }

            return true;
        }

        /**
         * Get a random element from the collection.
         */
        public int getRandom() {
            return this.valList.get(this.random.nextInt(this.valList.size()));
        }
    }

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

}

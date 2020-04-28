package com.myleetcode.design.first_unique_number;

import java.util.*;

public class Solution {
    class FirstUnique {

        // Linked Map, O(1) showFirstUnique, O(1) add, O(N) construct. Could implement by custimized Double Linked List and Map, or use Java API.
        LinkedHashMap<Integer, Integer> numNumMap;
        Set<Integer> duplicateKeySet;

        // O(N)
        public FirstUnique(int[] nums) {
            this.numNumMap = new LinkedHashMap<>();
            this.duplicateKeySet = new HashSet<>();

            for (int num : nums) {
                this.numNumMap.put(num, this.numNumMap.getOrDefault(num, 0) + 1);
            }

            Iterator<Map.Entry<Integer, Integer>> it = this.numNumMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getValue() != 1) {
                    this.duplicateKeySet.add(entry.getKey());
                    it.remove();
                }
            }
        }

        // O(1)
        public int showFirstUnique() {
            Iterator<Map.Entry<Integer, Integer>> it = this.numNumMap.entrySet().iterator();
            if (it.hasNext()) {
                return it.next().getKey();
            }

            return -1;
        }

        // O(1)
        public void add(int value) {
            // Already duplicate.
            if (this.duplicateKeySet.contains(value)) {
                return;
            }

            // Will make a duplicate.
            if (this.numNumMap.containsKey(value)) {
                this.numNumMap.remove(value);
                this.duplicateKeySet.add(value);
                return;
            }

            // Unique.
            this.numNumMap.put(value, 1);
        }

        // Naive solution by List and Map, O(N) showFirstUnique, O(1) add, O(N) construct
        // Naive Solution. TLE, No matter numList only store unique keys or all keys.
   /* List<Integer> numList;
    Map<Integer, Integer> numNumMap;

    public FirstUnique(int[] nums) {
        this.numList = new ArrayList<>();
        this.numNumMap = new HashMap<>();

        for(int num: nums){
            this.numNumMap.put(num, this.numNumMap.getOrDefault(num, 0) + 1);
            if(this.numNumMap.get(num) == 1){
                this.numList.add(num);
            }else{
                for(int i = 0; i < this.numList.size(); i++){
                    if(this.numList.get(i) == num){
                        this.numList.remove(i);
                        break;
                    }
                }
            }
        }
    }

    public int showFirstUnique() {
        if(this.numList.size() == 0){
            return -1;
        }

        return this.numList.get(0);
    }

    public void add(int value) {
        this.numNumMap.put(value, this.numNumMap.getOrDefault(value, 0) + 1);
        if(this.numNumMap.get(value) == 1){
            this.numList.add(value);
        }else{
            for(int i = 0; i < this.numList.size(); i++){
                if(this.numList.get(i) == value){
                    this.numList.remove(i);
                    break;
                }
            }
        }
    }*/
    }

/**
 * Your FirstUnique object will be instantiated and called as such:
 * FirstUnique obj = new FirstUnique(nums);
 * int param_1 = obj.showFirstUnique();
 * obj.add(value);
 */
}

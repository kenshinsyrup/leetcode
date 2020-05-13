package com.myleetcode.map.unique_number_of_occurrences;

import java.util.*;

public class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        // return uniqueOccurrencesBySort(arr);
        return uniqueOccurencesByMapAndSet(arr);
    }

    /*
    TC: O(N)
    SC: O(N)
    */
    private boolean uniqueOccurencesByMapAndSet(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }

        Map<Integer, Integer> numNumMap = new HashMap<>();
        for (int num : nums) {
            numNumMap.put(num, numNumMap.getOrDefault(num, 0) + 1);
        }

        Set<Integer> occurenceSet = new HashSet<>();
        for (int num : numNumMap.keySet()) {
            int occurence = numNumMap.get(num);
            if (occurenceSet.contains(occurence)) {
                return false;
            }
            occurenceSet.add(occurence);
        }

        return true;
    }

    /*
    TC: O(N*logN)
    SC: O(N)
    */
    private boolean uniqueOccurrencesBySort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }

        // Sort, ascending order.
        Arrays.sort(nums);

        Set<Integer> occurenceSet = new HashSet<>();
        int count = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) {
                if (occurenceSet.contains(count)) {
                    return false;
                }
                occurenceSet.add(count);

                count = 1;
            } else {
                count++;
            }
        }
        if (occurenceSet.contains(count)) {
            return false;
        }

        return true;

    }
}

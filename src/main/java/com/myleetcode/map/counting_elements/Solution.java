package com.myleetcode.map.counting_elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    public int countElements(int[] arr) {
        return countElementsBySet(arr);
        // return countElementsByMap(arr);
    }

    /*
    Map, like two sum with map
    https://leetcode.com/explore/challenge/card/30-day-leetcoding-challenge/528/week-1/3289/discuss/567439/Two-pass-O(n)-is-trivial.-One-pass-O(n)-is-expected

    TC: O(N)
    SC: O(N)
    */
    private int countElementsByMap(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int ret = 0;
        Map<Integer, Integer> numNumMap = new HashMap<>();
        for (int num : arr) {
            // Left has num-1, then all num-1 at left of current num contribute the numNumMap.get(num - 1) in ret.
            if (!numNumMap.containsKey(num) && numNumMap.containsKey(num - 1)) {
                ret += numNumMap.get(num - 1);
            }
            // Left has num+1, then current num contribute 1 in ret.
            if (numNumMap.containsKey(num + 1)) {
                ret++;
            }

            numNumMap.put(num, numNumMap.getOrDefault(num, 0) + 1);
        }

        return ret;
    }

    /*
    Set

    TC: O(N)
    SC: O(N)
    */
    private int countElementsBySet(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : arr) {
            numSet.add(num);
        }

        int ret = 0;
        for (int num : arr) {
            if (numSet.contains(num + 1)) {
                ret++;
            }
        }

        return ret;
    }
}

package com.myleetcode.sliding_window.fruit_into_baskets;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int totalFruit(int[] tree) {
        return totalFruitBySlidingWindow(tree);
    }

    /*
    Sliding Window

    Longest subarry with at most two unique vals.

    TC: O(N)
    SC: O(1)
    */
    private int totalFruitBySlidingWindow(int[] tree) {
        if (tree == null || tree.length == 0) {
            return 0;
        }

        int ret = 0;
        int uniqueCount = 0;
        Map<Integer, Integer> fruitNumMap = new HashMap<>();
        int len = tree.length;
        int left = 0;
        int right = 0;
        while (right < len) {
            int fruit = tree[right];
            fruitNumMap.put(fruit, fruitNumMap.getOrDefault(fruit, 0) + 1);
            if (fruitNumMap.get(fruit) == 1) {
                uniqueCount++;
            }

            // Shrink.
            while (uniqueCount > 2 && left <= right) {
                int leftFruit = tree[left];
                fruitNumMap.put(leftFruit, fruitNumMap.get(leftFruit) - 1);
                if (fruitNumMap.get(leftFruit) == 0) {
                    uniqueCount--;
                }

                left++;
            }

            // Count
            if (uniqueCount <= 2) {
                ret = Math.max(ret, right - left + 1);
            }

            right++;
        }

        return ret;
    }
}
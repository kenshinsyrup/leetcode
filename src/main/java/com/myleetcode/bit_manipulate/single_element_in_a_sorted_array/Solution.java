package com.myleetcode.bit_manipulate.single_element_in_a_sorted_array;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int singleNonDuplicate(int[] nums) {
        // return singleNonDuplicateByMap(nums);
        return singleNonDuplicateByBitManipulate(nums);
    }

    /*
    Since except one num, others appear exactly twice, could use XOR.
    num XOR num is 0
    0 XOR num is num

    TC: O(N)
    SC: O(1)
    */
    private int singleNonDuplicateByBitManipulate(int[] nums) {
        if (nums == null | nums.length == 0) {
            return -1;
        }

        int mask = 0;
        for (int num : nums) {
            mask = mask ^ num;
        }

        return mask;
    }

    /*
    TC: O(N)
    SC: O(N)
    */
    private int singleNonDuplicateByMap(int[] nums) {
        if (nums == null | nums.length == 0) {
            return -1;
        }

        Map<Integer, Integer> numNumMap = new HashMap<>();
        for (int num : nums) {
            numNumMap.put(num, numNumMap.getOrDefault(num, 0) + 1);
        }
        for (int num : numNumMap.keySet()) {
            if (numNumMap.get(num) == 1) {
                return num;
            }
        }

        return -1;
    }
}

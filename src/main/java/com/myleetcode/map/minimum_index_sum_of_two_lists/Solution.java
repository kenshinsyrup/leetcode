package com.myleetcode.map.minimum_index_sum_of_two_lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public String[] findRestaurant(String[] list1, String[] list2) {
        return findRestaurantByMap(list1, list2);
    }

    private String[] findRestaurantByMap(String[] list1, String[] list2) {
        if (list1 == null || list1.length == 0 || list2 == null || list2.length == 0) {
            return new String[0];
        }

        Map<String, Integer> strIdxMap1 = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            if (!strIdxMap1.containsKey(list1[i])) {
                strIdxMap1.put(list1[i], i);
            }
        }

        Map<String, Integer> strIdxMap2 = new HashMap<>();
        for (int i = 0; i < list2.length; i++) {
            if (!strIdxMap2.containsKey(list2[i])) {
                strIdxMap2.put(list2[i], i);
            }
        }

        int sum = Integer.MAX_VALUE;
        List<String> ret = new ArrayList<>();
        for (String key : strIdxMap1.keySet()) {
            if (strIdxMap2.containsKey(key)) {
                if (sum == strIdxMap1.get(key) + strIdxMap2.get(key)) {
                    ret.add(key);
                }
                if (sum > strIdxMap1.get(key) + strIdxMap2.get(key)) {
                    sum = strIdxMap1.get(key) + strIdxMap2.get(key);
                    ret = new ArrayList<>();
                    ret.add(key);
                }
            }
        }

        return ret.stream().toArray((int size) -> new String[size]);
    }
}

package com.myleetcode.array.find_common_characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> commonChars(String[] A) {
        return commonCharsByHashMap(A);
    }

    // 思路: https://leetcode.com/problems/find-common-characters/discuss/247637/Java-Two-HashMap-Solution-Easy-To-Understand
    // https://leetcode.com/problems/find-common-characters/discuss/249739/Java-10ms-38mb-clear-solution-with-comments
    // 这个题就是相当于数每个字符串中字符的数量，取最小值。trick的点在于我们不必建立一个key从a到z的map，然后再数每一个字符串，而是可以直接用第一个字符串的字符来建立初始状态的map，然后对这个map进行操作
    private List<String> commonCharsByHashMap(String[] A){
        List<String> ret = new ArrayList<>();
        if(A == null || A.length == 0){
            return ret;
        }

        // count chars
        Map<Character, Integer> charNumMap = new HashMap<>();
        // initialize map with the first string
        for(char c: A[0].toCharArray()){
            int num = charNumMap.getOrDefault(c, 0);
            charNumMap.put(c, num + 1);
        }

        // other strings, count, and we need keep the min nums of each char
        for(int i = 1; i < A.length; i++){
            String curStr = A[i];
            Map<Character, Integer> tempMap = new HashMap<>();

            for(char c: curStr.toCharArray()){
                // ！！！get the min count num of this char, put into tempMap
                int num = Math.min(charNumMap.getOrDefault(c, 0), tempMap.getOrDefault(c, 0) + 1);
                tempMap.put(c, num);
            }

            // update charNumMap
            charNumMap = tempMap;
        }

        // put into ret
        for(char c: charNumMap.keySet()){
            for(int i = 0; i < charNumMap.get(c); i++){
                ret.add(Character.toString(c));
            }
        }

        return ret;

    }
}

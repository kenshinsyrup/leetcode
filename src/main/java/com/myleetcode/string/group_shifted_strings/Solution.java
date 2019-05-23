package com.myleetcode.string.group_shifted_strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        return groupStringsByMap(strings);
    }

    // TC: O(N * L), L is hte longest word in strings, N is the length of strings
    // SC: O(N)
    // intuition:
    // group, 1 length must same; 2 char difference is the same, when we caculate the difference between two chars, if secChar is larger than firChar in Alphabeta table, we plus 26 to this difference.
    // But, the 2 is the key part, and if we want to differnciate words by their difference in chars, we need use that as the key, ie we need a Map. So the 1 is automatically accomplished.
    private List<List<String>> groupStringsByMap(String[] strings){
        List<List<String>> ret = new ArrayList<>();

        if(strings == null || strings.length == 0){
            return ret;
        }

        Map<String, List<String>> diffStrMap = new HashMap<>();
        for(String str: strings){

            StringBuilder keySB = new StringBuilder();
            // caculate the diff between adjacent chars
            for(int i = 1; i < str.length(); i++){
                int diff =  str.charAt(i) - str.charAt(i - 1);
                // if < 0, means the latter one is smaller, cycle, plus 26
                if(diff < 0){
                    diff += 26;
                }

                keySB.append("_").append(diff);
            }

            // put str with same key to same value list
            String key = keySB.toString();
            diffStrMap.putIfAbsent(key, new ArrayList<>());
            diffStrMap.get(key).add(str);
        }

        for(List<String> strList: diffStrMap.values()){
            ret.add(strList);
        }

        return ret;
    }
}

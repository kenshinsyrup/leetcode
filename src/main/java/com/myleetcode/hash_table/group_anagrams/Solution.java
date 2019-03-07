package com.myleetcode.hash_table.group_anagrams;

import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // special case
        if(strs == null || strs.length == 0){
            return new ArrayList<List<String>>();
        }

        return groupAnagramsByMap(strs);
    }

    // intuition: need a Map<String, List<String>>, normalize every string to ascending order string as key, value is the original sttring
    //
    private List<List<String>> groupAnagramsByMap(String[] strs){
        Map<String, List<String>> strMap = new HashMap<String, List<String>>();

        int len = strs.length;
        String str;
        String normalizedStr;
        for(int i = 0; i < len; i++){// O(n), n is the strs.length
            str = strs[i];
            char[] chars = str.toCharArray();
            Arrays.sort(chars); // O(m*logm), m is the str.length()
            normalizedStr = new String(chars);

            strMap.putIfAbsent(normalizedStr, new ArrayList<String>());
            strMap.get(normalizedStr).add(str);
        }

        // List<List<String>> ret = new ArrayList<List<String>>();
        // for(String k: strMap.keySet()){
        //     ret.add(strMap.get(k));
        // }

        return new ArrayList<List<String>>(strMap.values());
    }
}
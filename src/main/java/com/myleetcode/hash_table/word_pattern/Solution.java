package com.myleetcode.hash_table.word_pattern;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean wordPattern(String pattern, String str) {
        return wordPatternByMap(pattern, str);
    }

    // TC: O(max(N, M)), N is the length of pattern, M is the length of str
    // SC: O(max(N, M))
    // intuition: seems like a Map problem, we could put char in pattern to map as key, and the substring in str as value, when we met the key again, we check current substring and value, if not same, return false
    private boolean wordPatternByMap(String pattern, String str){
        if(pattern == null || pattern.length() == 0 || str == null || str.length() == 0){
            return false;
        }

        Map<Character, String> charStrMap = new HashMap<>();
        Map<String, Character> strCharMap = new HashMap<>();
        // split by space, substring array
        String[] strArray = str.split(" ");
        // check length
        int patternLen = pattern.length();
        if(strArray.length != patternLen){
            return false;
        }
        // check pattern with strArray
        for(int i = 0; i < pattern.length(); i++){
            char patternChar = pattern.charAt(i);
            String substr = strArray[i];

            // if both not in map, put them
            if(!charStrMap.containsKey(patternChar) && !strCharMap.containsKey(substr)){
                charStrMap.put(pattern.charAt(i), strArray[i]);
                strCharMap.put(substr, patternChar);
            }else{
                // check
                if(charStrMap.containsKey(patternChar)){
                    if(!charStrMap.get(patternChar).equals(substr)){
                        return false;
                    }
                }
                if(strCharMap.containsKey(substr)){
                    if(strCharMap.get(substr) != patternChar){
                        return false;
                    }
                }

            }
        }

        return true;
    }
}

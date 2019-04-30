package com.myleetcode.hash_table.isomorphic_strings;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isIsomorphic(String s, String t) {
        // return isIsomorphicByMap(s, t);
        return isIsomorphicByArray(s, t);
    }

    // https://leetcode.com/problems/isomorphic-strings/discuss/57807/Java-3ms-beats-99.25
    // but HashMap is a little overkill for this problem, because every char is ASCII, so we could use an array as the "map"
    public boolean isIsomorphicByArray(String sStr, String tStr) {
        if(sStr == null && tStr == null){
            return true;
        }
        if(sStr.length() != tStr.length()){
            return false;
        }

        char[] sChars = sStr.toCharArray();
        char[] tChars = tStr.toCharArray();

        int len = sStr.length();

        char[] sCharMap = new char[256];
        char[] tCharMap = new char[256];

        for(int i = 0; i < len; i++){
            char sChar = sChars[i];
            char tChar = tChars[i];

            if(sCharMap[sChar] == 0 && tCharMap[tChar] == 0){
                sCharMap[sChar] = tChar;
                tCharMap[tChar] = sChar;
            }else{
                if(sCharMap[sChar] != tChar || tCharMap[tChar] != sChar){
                    return false;
                }
            }
        }

        return true;
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: two pointer and one hash map, map could keep the mapping relationship and two pointers to traverse the two strings
    // remember: No two characters may map to the same character but a character may map to itself.
    private boolean isIsomorphicByMap(String s, String t){
        if(s == null && t == null){
            return true;
        }
        if(s.length() != t.length()){
            return false;
        }

        int len = s.length();
        int start = 0;

        // s to t
        Map<Character, Character> stCharMap = new HashMap<>();
        // t to s
        Map<Character, Character> tsCharMap = new HashMap<>();
        while(start < len){
            char sChar = s.charAt(start);
            char tChar = t.charAt(start);

            if(!stCharMap.containsKey(sChar) && !tsCharMap.containsKey(tChar)){
                stCharMap.put(sChar, tChar);
                tsCharMap.put(tChar, sChar);
            }else{
                if(stCharMap.containsKey(sChar)){
                    if(stCharMap.get(sChar) != tChar){
                        return false;
                    }
                }
                if(tsCharMap.containsKey(tChar)){
                    if(tsCharMap.get(tChar) != sChar){
                        return false;
                    }
                }
            }

            start++;
        }

        return true;
    }
}

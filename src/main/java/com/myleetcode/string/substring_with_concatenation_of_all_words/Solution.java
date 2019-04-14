package com.myleetcode.string.substring_with_concatenation_of_all_words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        // return findSubstringByTraverse(s, words);
        return findSubstringByTraverseII(s, words);
    }

    private List<Integer> findSubstringByTraverseII(String s, String[] words){
        List<Integer> ret = new ArrayList<>();

        if(s == null || s.length() == 0 || words == null || words.length == 0){
            return ret;
        }

        int wordLen = words[0].length();

        Map<String, Integer> wordNumMap = new HashMap<>();
        for(String word: words){
            wordNumMap.put(word, wordNumMap.getOrDefault(word, 0) + 1);
        }

        Map<String, Integer> seenMap = new HashMap<>();
        int start = 0;
        while(start + words.length * wordLen <= s.length()){
            // clear map
            seenMap = new HashMap<>();

            // we operate dummyStart
            int dummyStart = start;

            int loopNum = 0;
            // check if start is valid
            while(loopNum < words.length && dummyStart + wordLen <= s.length()){
                // current string
                String curStr = s.substring(dummyStart, dummyStart + wordLen);

                if(!wordNumMap.containsKey(curStr)){
                    break;
                }

                // put current string to seenMap
                seenMap.put(curStr, seenMap.getOrDefault(curStr, 0) + 1);

                // if not valid, break this loop
                if(seenMap.get(curStr) > wordNumMap.get(curStr)){
                    break;
                }

                // move by wordLen
                dummyStart += wordLen;

                loopNum++;
            }

            if(loopNum == words.length){
                ret.add(start);
            }

            // if not valid, move by one char
            start += 1;
        }

        return ret;

    }


    /*
    Runtime: 1098 ms, faster than 5.03% of Java online submissions for Substring with Concatenation of All Words.
Memory Usage: 52.7 MB, less than 5.00% of Java online submissions for Substring with Concatenation of All Words.
    */
    // TC: O(N^2)
    // SC: O(N)
    // intuition: say every word length is M. we traverse string S every M length, we use a map to store words and its number, if find one word existing in words, we reduce its num in map and if to 0 we remove it, if map is 0 then this start is valid. otherwise we try next start, ie start + wordLen.
    private List<Integer> findSubstringByTraverse(String s, String[] words){
        List<Integer> ret = new ArrayList<>();

        if(s == null || s.length() == 0 || words == null || words.length == 0){
            return ret;
        }

        int wordLen = words[0].length();

        Map<String, Integer> wordNumMap = new HashMap<>();
        int start = 0;
        while(start + wordLen <= s.length()){
            // build map
            wordNumMap = new HashMap<>();
            for(String word: words){
                wordNumMap.put(word, wordNumMap.getOrDefault(word, 0) + 1);
            }

            // we operate dummyStart
            int dummyStart = start;
            // check if start is valid
            while(dummyStart + wordLen <= s.length()){
                // current string
                String curStr = s.substring(dummyStart, dummyStart + wordLen);

                // if not exist in map, this start not valid
                if(!wordNumMap.containsKey(curStr)){
                    break;
                }

                // if exist, we update map
                wordNumMap.put(curStr, wordNumMap.get(curStr) - 1);
                if(wordNumMap.get(curStr) == 0){
                    wordNumMap.remove(curStr);
                }

                // if find all, add start to ret
                if(wordNumMap.size() == 0){
                    ret.add(start);
                    break;
                }

                // move by wordLen
                dummyStart += wordLen;
            }

            // if not valid, move by one char
            start += 1;
        }

        return ret;

    }
}

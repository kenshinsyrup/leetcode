package com.myleetcode.map.find_and_replace_pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        return findAndReplacePatternByMap(words, pattern);
    }

    private List<String> findAndReplacePatternByMap(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) {
            return res;
        }
        if (pattern == null || pattern.length() == 0) {
            return res;
        }

        for (String word : words) {
            if (isValid(word, pattern)) {
                res.add(word);
            }
        }

        return res;
    }

    private boolean isValid(String word, String pattern) {
        Map<Character, Character> mapA = new HashMap<>();
        Map<Character, Character> mapB = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char wCh = word.charAt(i);
            char pCh = pattern.charAt(i);

            if (!mapA.containsKey(wCh)) {
                mapA.put(wCh, pCh);
            }
            if (!mapB.containsKey(pCh)) {
                mapB.put(pCh, wCh);
            }

            if (mapA.get(wCh) != pCh || mapB.get(pCh) != wCh) {
                return false;
            }
        }

        return true;
    }
}
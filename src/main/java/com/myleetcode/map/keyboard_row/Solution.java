package com.myleetcode.map.keyboard_row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public String[] findWords(String[] words) {
        return findWordsByMap(words);
    }

    /*
    Just translate the description to code.
    https://leetcode.com/problems/keyboard-row/discuss/97870/Short-Easy-Java-with-Explanation

    TC: O(N * M)
    SC: O(N)
    */
    private String[] findWordsByMap(String[] words) {
        if (words == null || words.length == 0) {
            return new String[0];
        }

        String[] keyboardStrs = new String[]{"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};
        Map<Character, Integer> chIdxMap = new HashMap<>();
        for (int i = 0; i < keyboardStrs.length; i++) {
            for (char ch : keyboardStrs[i].toCharArray()) {
                chIdxMap.put(ch, i);
            }
        }

        List<String> ret = new ArrayList<>();
        for (String word : words) {
            if (word == null || word.length() == 0) {
                continue;
            }

            // Upper case.
            String upperWord = word.toUpperCase();

            int idx = chIdxMap.get(upperWord.charAt(0));
            boolean find = true;
            for (char ch : upperWord.toCharArray()) {
                if (idx != chIdxMap.get(ch)) {
                    find = false;
                    break;
                }
            }
            if (find) {
                ret.add(word);
            }
        }

        return ret.stream().toArray(size -> new String[size]);
    }
}

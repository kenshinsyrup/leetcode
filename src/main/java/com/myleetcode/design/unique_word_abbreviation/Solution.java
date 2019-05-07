package com.myleetcode.design.unique_word_abbreviation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    class ValidWordAbbr {

        // intuition: use a Map to store abbrev String as key and word List as value
        // when check isUnique, check if value has size more than 1
        // but leetcode think in dictionary may have duplicates and they should not be looked as false when consider abbr, so we need Set
        Map<String, Set<String>> abbrWordsMap;

        // TC: O(N)
        // SC: O(N)
        public ValidWordAbbr(String[] dictionary) {
            this.abbrWordsMap = new HashMap<>();

            if(dictionary == null || dictionary.length == 0){
                return;
            }

            for(String word: dictionary){
                String abbr = this.abbrWord(word);
                Set<String> words = this.abbrWordsMap.getOrDefault(abbr, new HashSet<>());
                words.add(word);
                this.abbrWordsMap.put(abbr, words);
            }
        }

        private String abbrWord(String word){
            int len = word.length();
            if(len <= 2){
                return word;
            }

            return "" + word.charAt(0) + (len - 2) + word.charAt(len - 1);
        }

        // TC: O(1)
        // SC: O(1)
        public boolean isUnique(String word) {
            // here word.length == 0 is not false, because leetcode think it's also a valid string
            if(word == null){
                return false;
            }

            String abbr = abbrWord(word);
            // if contains this abbr
            if(this.abbrWordsMap.containsKey(abbr)){
                // if words Set more than 1, then must not unique>
                Set<String> words = this.abbrWordsMap.get(abbr);
                if(words.size() > 1){
                    return false;
                }

                // if only one word in Set, then if not contains, means not unique
                return words.contains(word);
            }

            // leetcode means, if no such abbr, true
            return true;

        }
    }

/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */
}

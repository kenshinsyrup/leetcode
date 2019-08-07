package com.myleetcode.hash_table.verifying_an_alien_dictionary;

class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        return isAlienSortedByTraversal(words, order);
    }

    // intuition:
    // compare cur word and prior tail word, the tail word is the tail one of subarray before cur word: 1 all chars are queal, then longer one should at back; 2 not all chars not equal, check by order
    // to compare fast, we could use an array as a map to store the char and assign a val to it for comparision
    // TC: O(N * M), N is words length, M is the longest word length
    // SC: O(N)
    private boolean isAlienSortedByTraversal(String[] words, String order){
        if(words == null || words.length == 0 || order == null || order.length() == 0){
            return true;
        }

        // 1 convert order to map represented by array
        int[] map = new int[26];
        for(int i = 0; i < order.length(); i++){
            map[order.charAt(i) - 'a'] = i;
        }

        // 2 traverse the words array, compare cur word with prior word
        for(int i = 1; i < words.length; i++){
            if(!check(words[i - 1], words[i], map)){
                return false;
            }
        }

        return true;
    }

    private boolean check(String priorStr, String curStr, int[] map){
        int priorLen = priorStr.length();
        int curLen = curStr.length();
        int len = Math.min(priorLen, curLen);

        for(int i = 0; i < len; i++){
            char priorCh = priorStr.charAt(i);
            char curCh = curStr.charAt(i);
            if(map[priorCh - 'a'] == map[curCh - 'a']){
                continue;
            }else if(map[priorCh - 'a'] < map[curCh - 'a']){
                return true;
            }else{
                return false;
            }
        }

        // if all chars are equal, longer one should at back
        if(priorLen > curLen){
            return false;
        }
        return true;
    }
}

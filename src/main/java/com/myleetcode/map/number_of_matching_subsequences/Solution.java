package com.myleetcode.map.number_of_matching_subsequences;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        return numMatchingSubseqByMapAndQueue(S, words);
    }

    /*
    Thoughts

    https://leetcode.com/problems/number-of-matching-subsequences/discuss/117598/Java-solution-using-HashMap-and-Queue
    https://leetcode.com/problems/number-of-matching-subsequences/discuss/117598/Java-solution-using-HashMap-and-Queue

    Thoug there's some improvements like build a class to record word and its current processing char's index, that's not as easy as this naive one to make solution clear.

    TC: O(S + N*L), S is str length, N is words length, L is longest word in words length.
    SC: O(N)
    */
    private int numMatchingSubseqByMapAndQueue(String str, String[] words) {
        if (str == null || str.length() == 0 || words == null || words.length == 0) {
            return 0;
        }

        // 1. Store word using its first char as key in map.
        Map<Character, Deque<String>> charWordMap = new HashMap<>();
        for (String word : words) {
            char ch = word.charAt(0);

            Deque<String> wordQueue = charWordMap.getOrDefault(ch, new ArrayDeque<>());
            wordQueue.offer(word);
            charWordMap.put(ch, wordQueue);
        }

        // 2. Sequence search.
        int res = 0;
        for (char ch : str.toCharArray()) {
            Deque<String> wordQueue = charWordMap.getOrDefault(ch, new ArrayDeque<>());

            // !!! Important, could not use this check to loop queue, because maybe targetWord has same starting char with word, then when we offer the targetWord to queue, the targetWord will finally be processed as a word in the same queue polling loop.
            // while(!wordQueue.isEmpty()){
            int size = wordQueue.size();
            for (int i = 0; i < size; i++) {
                String word = wordQueue.poll();
                if (word.length() == 1) {
                    res++;
                } else {
                    String targetWord = word.substring(1, word.length());
                    Deque<String> targetWordQueue = charWordMap.getOrDefault(targetWord.charAt(0), new ArrayDeque<>());
                    targetWordQueue.offer(targetWord);
                    charWordMap.put(targetWord.charAt(0), targetWordQueue);
                }
            }
        }

        return res;

    }
}

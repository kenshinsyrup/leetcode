package com.myleetcode.dynamic_program.longest_string_chain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    public int longestStrChain(String[] words) {
        // return longestStrChainByBacktracking(words);
        return longestStrChainByDPMemo(words);
    }

    /*
    DP with memo
    https://leetcode.com/problems/longest-string-chain/discuss/294890/C++JavaPython-DP-Solution/277824

    N is length of words, M is longest word length.
    TC: O(N * M^2), for a word, worst case we try M^2 recursion
    SC: O(max(N, M^2)), N for memo, M^2 for recursion
    */
    private int longestStrChainByDPMemo(String[] words) {
        if (words == null || words.length == 0) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }

        Map<String, Integer> memo = new HashMap<>();
        int ret = 0;
        for (String word : wordSet) {
            ret = Math.max(ret, dpWithMemo(word, wordSet, memo));
        }

        return ret;
    }

    private int dpWithMemo(String word, Set<String> wordSet, Map<String, Integer> memo) {
        if (memo.containsKey(word)) {
            return memo.get(word);
        }

        int curChainLen = 1;
        int len = word.length();
        // Try to delete a char at i, so we could get a shorter word act as possible prev word.
        for (int i = 0; i < len; i++) {
            String prevWord = word.substring(0, i) + word.substring(i + 1, len);

            if (wordSet.contains(prevWord)) {
                curChainLen = Math.max(curChainLen, 1 + dpWithMemo(prevWord, wordSet, memo));
            }
        }

        memo.put(word, curChainLen);
        return curChainLen;
    }

    class Result {
        int maxChainLen = 0;

        Result() {
            this.maxChainLen = 0;
        }
    }

    /*
    Backtracking
    1. Put all words into Set
    2. For every word, try add a letter from a-z to each possible position in word, and check if this new word exists in Set
    */
    private int longestStrChainByBacktracking(String[] words) {
        if (words == null || words.length == 0) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }

        Result ret = new Result();
        for (String word : wordSet) {
            backtracking(word, wordSet, 1, ret);
        }

        return ret.maxChainLen;

    }

    private void backtracking(String word, Set<String> wordSet, int curChainLen, Result ret) {
        ret.maxChainLen = Math.max(ret.maxChainLen, curChainLen);

        StringBuilder sb = new StringBuilder(word);
        for (int i = 0; i < 26; i++) {
            char curCh = (char) ('a' + i);

            for (int j = 0; j < word.length() + 1; j++) {
                sb.insert(j, curCh);

                String nextWord = sb.toString();
                if (wordSet.contains(nextWord)) {
                    backtracking(nextWord, wordSet, curChainLen + 1, ret);
                }

                sb = new StringBuilder(word);
            }
        }
    }
}

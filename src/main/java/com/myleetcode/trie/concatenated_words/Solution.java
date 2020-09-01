package com.myleetcode.trie.concatenated_words;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        // return findAllConcatenatedWordsInADictByTrieTree(words);
        return findAllConcatenatedWordsInADictByTrieTreeII(words);
    }

    // Root node.
    TrieNode rootNode;

    /*
    Trie Tree with Backtracking:
    https://leetcode.com/problems/concatenated-words/discuss/176805/52ms-Prefix-Tree-(Trie)-and-Backtracking-Java-with-Explanation

    TC: O(N * K), N is the length of String[] words, K is the longest word length in String[] words.
    Build Trie Tree O(N * K)
    Search a word in Trie Tree typically cost O(M), M is the length of given word. But in this problem we use Backtracking during the search(like a WordBreaking problem), so in worst case for each char we have 2 choice and we have M chars, so it cost O(2^M). So totally cost O(N * (2 ^ K)), where K is the largest M.

    SC: O(max(N * K, 2 ^ K))
    N * K is for the Trie Tree to store String[] words.
    2 ^ K is for the recursion stack.
    */
    private List<String> findAllConcatenatedWordsInADictByTrieTreeII(String[] words) {
        // Special case.
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }


        // Build Trie Tree.
        this.rootNode = new TrieNode();
        for (String word : words) {
            addWord(word);
        }

        // Collect concatenated words.
        List<String> ret = new ArrayList<>();
        for (String word : words) {
            if (isConcatenatedWordII(0, word, 0)) {
                ret.add(word);
            }
        }

        return ret;

    }

    // Check if the given word could be concatenated by at least two shorter words, ie at least cut it at least once. This is a transformation of search word in a Trie Tree.
    private boolean isConcatenatedWordII(int curIdx, String word, int cuttingCount) {
        TrieNode curNode = this.rootNode;

        int len = word.length();
        for (int i = curIdx; i < len; i++) {
            char ch = word.charAt(i);
            if (curNode.children[ch - 'a'] == null) {
                return false;
            }

            curNode = curNode.children[ch - 'a'];

            // When meet a node isWord, we have something todo:
            if (curNode.isWord) {
                // 1, we have to check if this is the result. That means, if the i is word.lenght()-1, means we have processed all the chars in the given word, so if cuttingCount is not 0, means we have done cutting to the given word, that makes we have at least 2 shorter words to concatenate this given word, if so, return true, otherwise false.
                if (i == len - 1) {
                    return cuttingCount != 0;
                }

                // 2, since curNode isWord, we need do the choice: cut here or keep going on current branch. Since the for loop it self represents the "going on current branch"(no matter curNode isWord or not) part, so we have this if condition to represents the "cut here" part.
                // Cut here is a implementation of backtracking, we start a brand new search of word[i+1:end].
                if (isConcatenatedWordII(i + 1, word, cuttingCount + 1)) {
                    return true;
                }
            }

        }

        return false;

    }

    /*
    MLE
    Naive Backtracking with Trie Tree
    */
    private List<String> findAllConcatenatedWordsInADictByTrieTree(String[] words) {
        // Special case.
        if (words == null || words.length <= 1) {
            return new ArrayList<>();
        }

        // Build Trie Tree.
        this.rootNode = new TrieNode();
        for (String word : words) {
            addWord(word);
        }

        // Collect concatenated words.
        List<String> ret = new ArrayList<>();
        for (String word : words) {
            if (isConcatenatedWord(words, word, new StringBuilder())) {
                ret.add(word);
            }
        }

        return ret;

    }

    private boolean isConcatenatedWord(String[] words, String tarWord, StringBuilder sb) {
        if (sb.length() > tarWord.length()) {
            return false;
        }
        if (sb.toString().equals(tarWord)) {
            return true;
        }

        for (String word : words) {
            if (word.equals(tarWord)) {
                continue;
            }

            int len = sb.length();
            sb.append(word);
            if (searchPrefix(sb.toString()) == null) {
                sb.delete(len, sb.length());
                continue;
            }

            if (isConcatenatedWord(words, tarWord, sb)) {
                return true;
            }

            sb.delete(len, sb.length());
        }

        return false;
    }

    // Trie Tree node.
    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        TrieNode() {
            this.children = new TrieNode[26];
            this.isWord = false;
        }
    }

    // Add given word to Trie Tree.
    private void addWord(String word) {
        TrieNode curNode = this.rootNode;

        for (char ch : word.toCharArray()) {
            if (curNode.children[ch - 'a'] == null) {
                curNode.children[ch - 'a'] = new TrieNode();
            }

            curNode = curNode.children[ch - 'a'];
        }
        curNode.isWord = true;
    }

    private boolean searchWord(String word) {
        TrieNode curNode = searchPrefix(word);
        if (curNode == null) {
            return false;
        }

        return curNode.isWord;
    }

    private TrieNode searchPrefix(String prefix) {
        TrieNode curNode = this.rootNode;
        for (char ch : prefix.toCharArray()) {
            if (curNode.children[ch - 'a'] == null) {
                return null;
            }

            curNode = curNode.children[ch - 'a'];
        }

        return curNode;
    }

}
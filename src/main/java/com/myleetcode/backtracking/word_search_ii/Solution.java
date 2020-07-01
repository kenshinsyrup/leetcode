package com.myleetcode.backtracking.word_search_ii;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        // return findWOrdsByBacktracking(board, words);
        return findWordsByBacktrackingAndTrie(board, words);
    }

    /*
    Intuition: Since naive backtracking costs too much, we could use Trie to early break when prefix is not valid, this could help us to reduce the real runtime(the theorily RT is the same). And optionaly, we could use Set to check whether a string is a valid word instead of using Trie to get higher performance.

    https://leetcode.com/problems/word-search-ii/discuss/59784/My-simple-and-clean-Java-code-using-DFS-and-Trie

    TC: O((R*C) ^ (R*C))
    SC: O(R*C)
    */
    private List<String> findWordsByBacktrackingAndTrie(char[][] board, String[] words) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return new ArrayList<>();
        }
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }

        // 1. Add words to Trie to check prefix.
        Trie trie = new Trie();
        trie.addWords(words);
        // Add words to Set to check word, actually we could use the Trie to do the check, but Set is O(1), Trie is O(L) where L is the length of given word.
        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }

        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int rowLen = board.length;
        int colLen = board[0].length;
        boolean[][] visited = new boolean[board.length][board[0].length];
        StringBuilder sb = new StringBuilder();
        Set<String> retSet = new HashSet<>();
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                backtrackingWithTrie(trie, board, sb, visited, i, j, directions, wordSet, retSet);
            }
        }

        return new ArrayList<>(retSet);
    }

    private void backtrackingWithTrie(Trie trie, char[][] board, StringBuilder sb, boolean[][] visited, int rowIdx, int colIdx, int[][] direcs, Set<String> wordSet, Set<String> ret) {
        sb.append(board[rowIdx][colIdx]);
        visited[rowIdx][colIdx] = true;

        // Invalid prefix, early break to get higher performance.
        if (!trie.startsWith(sb.toString())) {
            visited[rowIdx][colIdx] = false;
            sb.deleteCharAt(sb.length() - 1);
            return;
        }

        // Valid word. actually we could use the Trie to do the check as the commented codes show, but Set is O(1), Trie is O(L) where L is the length of given word.
        // if(trie.searchWord(sb.toString())){
        //     ret.add(sb.toString());
        // }
        if (wordSet.contains(sb.toString())) {
            ret.add(sb.toString());
        }

        // explore
        int rowLen = board.length;
        int colLen = board[0].length;
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];
            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }

            backtrackingWithTrie(trie, board, sb, visited, nextRowIdx, nextColIdx, direcs, wordSet, ret);
        }

        visited[rowIdx][colIdx] = false;
        sb.deleteCharAt(sb.length() - 1);
    }

    // The classic Trie tree.
    class Trie {
        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        // Add word.
        public void addWord(String word) {
            TrieNode curNode = this.root;

            for (char ch : word.toCharArray()) {
                if (curNode.children[ch - 'a'] == null) {
                    curNode.children[ch - 'a'] = new TrieNode();
                }

                curNode = curNode.children[ch - 'a'];
            }

            curNode.isEnd = true;
        }

        // Search word.
        public boolean searchWord(String word) {
            TrieNode node = searchNodeOfPrefix(word);
            return node != null && node.isEnd;
        }

        // Starts with.
        public boolean startsWith(String prefix) {
            TrieNode node = searchNodeOfPrefix(prefix);
            return node != null;
        }

        // TrieNode of prefix.
        private TrieNode searchNodeOfPrefix(String prefix) {
            TrieNode curNode = this.root;
            for (char ch : prefix.toCharArray()) {
                if (curNode.children[ch - 'a'] == null) {
                    return null;
                }

                curNode = curNode.children[ch - 'a'];
            }

            return curNode;
        }

        public void addWords(String[] words) {
            for (String word : words) {
                addWord(word);
            }
        }
    }

    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }

    /*
    Naive Backtracking, TLE

    TC: O((R*C) ^ (R*C))
    SC: O(R*C)
    */
    // Get all combination, if a combination is a valid word, store it.
    private List<String> findWOrdsByBacktracking(char[][] board, String[] words) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return new ArrayList<>();
        }
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }

        // 1. Store words in Set for easy checking.
        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }

        StringBuilder sb = new StringBuilder();
        Set<String> resSet = new HashSet<>();
        int[][] direcs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int rowLen = board.length;
        int colLen = board[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                backtracking(board, i, j, direcs, resSet, visited, sb, wordSet);
            }
        }

        List<String> res = new ArrayList<>();
        for (String word : resSet) {
            res.add(word);
        }

        return res;
    }

    private void backtracking(char[][] board, int rowIdx, int colIdx, int[][] direcs, Set<String> resSet, boolean[][] visited, StringBuilder sb, Set<String> wordSet) {
        visited[rowIdx][colIdx] = true;
        sb.append(board[rowIdx][colIdx]);
        if (wordSet.contains(sb.toString())) {
            resSet.add(sb.toString());
        }

        int rowLen = board.length;
        int colLen = board[0].length;
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }

            backtracking(board, nextRowIdx, nextColIdx, direcs, resSet, visited, sb, wordSet);
        }

        visited[rowIdx][colIdx] = false;
        sb.deleteCharAt(sb.length() - 1);
    }
}

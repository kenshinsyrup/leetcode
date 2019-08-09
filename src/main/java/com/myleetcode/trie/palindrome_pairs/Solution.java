package com.myleetcode.trie.palindrome_pairs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        // return palindromePairsByTraverse(words); // TLE
        return palindromePairsByTrie(words);
    }

    // Optimization with Trie
    // https://leetcode.com/problems/palindrome-pairs/discuss/79195/O(n-*-k2)-java-solution-with-Trie-structure
    // TC: O(N * M^2), N is words length, M is longest string length
    private List<List<Integer>> palindromePairsByTrie(String[] words){
        List<List<Integer>> ret = new ArrayList<>();
        if(words == null || words.length == 0){
            return ret;
        }

        int len = words.length;
        TrieNode root = new TrieNode();
        for(int i = 0; i < len; i++){
            insertWord(root, words[i], i);
        }

        for(int i = 0; i < len; i++){
            searchWord(root, words[i], i, ret);
        }

        return ret;
    }

    private class TrieNode{
        TrieNode[] children;
        int idx;
        List<Integer> prefixPalindromeList; // suffix end with current TrieNode, and prefix isa palindrome, record the idx

        public TrieNode(){
            this.children = new TrieNode[26];
            this.idx = -1;
            this.prefixPalindromeList = new ArrayList<>();
        }
    }

    private void insertWord(TrieNode root, String word, int idx){
        TrieNode curNode = root;
        for(int i = word.length() - 1; i >= 0; i--){
            char ch = word.charAt(i);
            if(curNode.children[ch - 'a'] == null){
                curNode.children[ch - 'a'] = new TrieNode();
            }

            if(isPalindrome(word, 0, i)){
                curNode.prefixPalindromeList.add(idx);
            }

            curNode = curNode.children[ch - 'a'];
        }

        curNode.prefixPalindromeList.add(idx); // prefix is "" now, also palindrome
        curNode.idx = idx; // mark as a whole word, and record the word's pos at array is idx to distinguish with other words passing but not end here(because unique words)
    }

    private boolean isPalindrome(String word, int startIdx, int endIdx){
        while(startIdx < endIdx){
            if(word.charAt(startIdx) != word.charAt(endIdx)){
                return false;
            }

            startIdx++;
            endIdx--;
        }

        return true;
    }

    private void searchWord(TrieNode root, String word, int idx, List<List<Integer>> ret){
        int len = word.length();
        TrieNode curNode = root;
        // 1 try idx word's all prefix, find the word whose reversed order is same as the prefix, then check if the idx word's rest part is palindrome, means this two words could combine to a palindrome
        for(int i = 0; i < len; i++){
            if(curNode.idx != -1 && curNode.idx != idx && isPalindrome(word, i, len - 1)){
                ret.add(Arrays.asList(idx, curNode.idx));
            }

            curNode = curNode.children[word.charAt(i) - 'a'];
            if(curNode == null){ // trie word end
                return;
            }
        }

        // this curNode as a suffix end, if a word is not idx word but has a prefix palindrome, then this two words could be a palindrome
        for(int j: curNode.prefixPalindromeList){
            if(idx != j){
                ret.add(Arrays.asList(idx, j));
            }
        }
    }



    // Naive Solution: TLE
    // intuition:
    // concate any two words, check if the concatenation is palindrome. Totally cost (N^2 * M), N is lenght of words array, M is word length
    private List<List<Integer>> palindromePairsByTraverse(String[] words) {
        List<List<Integer>> ret = new ArrayList<>();
        if(words == null || words.length == 0){
            return ret;
        }

        int len = words.length;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(i == j){
                    continue;
                }

                if(isPalindrome(words[i] + words[j])){
                    List<Integer> snippet = new ArrayList<>();
                    snippet.add(i);
                    snippet.add(j);
                    ret.add(snippet);
                }
            }
        }

        return ret;
    }

    private boolean isPalindrome(String str){
        int len = str.length();
        int i = 0;
        int j = len - 1;
        while(i <= j){
            if(str.charAt(i) != str.charAt(j)){
                return false;
            }

            i++;
            j--;
        }

        return true;
    }
}

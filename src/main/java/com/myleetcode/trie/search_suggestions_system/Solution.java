package com.myleetcode.trie.search_suggestions_system;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        return suggestedProductsByTrieTree(products, searchWord);
    }

    /*
    Trie tree.
    https://leetcode.com/problems/search-suggestions-system/discuss/436202/Java-Trie-Solution-Clean-code
    https://leetcode.com/problems/search-suggestions-system/discuss/436349/Intuitive-Java-Trie-Solution-(Handles-the-funny-test-case)

    Similar: 642. Design Search Autocomplete System

    */
    private List<List<String>> suggestedProductsByTrieTree(String[] products, String searchWord) {
        if (searchWord == null || searchWord.length() == 0) {
            return new ArrayList<>();
        }

        this.rootNode = new TrieNode();

        for (String product : products) {
            insertWord(product);
        }

        return collectSuggestedWord(searchWord);

    }

    class TrieNode {
        TrieNode[] children;
        boolean isWord;

        // Handle the duplicate product name. In this problem, the products may have same name. To handle this, we remember how many times this name is used.
        int count;
        // Keep the word in the last corresponding TrieNode, so we could know which word this TrieNode represents.
        String word;

        TrieNode() {
            this.children = new TrieNode[26];
            this.isWord = false;
            this.count = 0;
            this.word = "";
        }
    }

    TrieNode rootNode;

    // Insert given word to Trie Tree.
    private void insertWord(String word) {
        TrieNode curNode = this.rootNode;

        for (char ch : word.toCharArray()) {
            if (curNode.children[ch - 'a'] == null) {
                curNode.children[ch - 'a'] = new TrieNode();
            }

            curNode = curNode.children[ch - 'a'];
        }
        curNode.isWord = true;

        curNode.count++;
        curNode.word = word;
    }

    public List<List<String>> collectSuggestedWord(String searchWord) {
        TrieNode curNode = this.rootNode;
        List<List<String>> ret = new ArrayList<>();

        // For each char in searchWord, we need give at most 3 suggested words in lexicographcally ascending order or empty list.
        for (char ch : searchWord.toCharArray()) {
            if (curNode != null) {
                curNode = curNode.children[ch - 'a'];

                List<String> snippet = new ArrayList<>();
                getAtMostThreeWordsLexicographicallyAscending(snippet, curNode);
                ret.add(new ArrayList<>(snippet));
            } else {
                ret.add(new ArrayList<>());
            }
        }

        return ret;
    }

    // From current Trie Tree Node curNode and its children, we try to get at most 3 words in the Trie Tree by Lexicographically ascending order.
    private void getAtMostThreeWordsLexicographicallyAscending(List<String> snippet, TrieNode curNode) {
        if (curNode == null) {
            return;
        }

        // If curNode isWord, try to add word to snippet.
        if (curNode.isWord) {
            int nodeWordCount = curNode.count;

            while (snippet.size() < 3 && nodeWordCount > 0) {
                snippet.add(curNode.word);
                nodeWordCount--;
            }
        }
        if (snippet.size() == 3) {
            return;
        }

        // Lexicographically traverse curNode children to get suggested words into snippet.
        for (TrieNode childNode : curNode.children) {
            getAtMostThreeWordsLexicographicallyAscending(snippet, childNode);
        }
    }


}

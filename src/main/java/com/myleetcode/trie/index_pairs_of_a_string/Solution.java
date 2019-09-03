package com.myleetcode.trie.index_pairs_of_a_string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Solution {
    public int[][] indexPairs(String text, String[] words) {
        return indexPairsBySet(text, words); // 掌握
        // return indexPairsByTrie(text, words); // 掌握
    }

    // intuition:
    // Naive Solution
    // 1. initially want to use a Set to store words, but since the input words are distinct, so we dont neet a Set
    // 2. traverse text, at every idx, check use it as start, use length of wrod in Set with start char as this, see if the substring seam as the word.
    // TC: O(N*M*L + N*logN), N is text length, M is words length, L is the longest string length in words
    // SC: O(N), N is text length to store result
    private int[][] indexPairsBySet(String text, String[] words){
        if(words == null || words.length == 0 || text == null || text.length() == 0){
            return new int[0][0];
        }

        // 1. traverse text
        List<int[]> retList = new ArrayList<>();
        int len = text.length();
        for(int i = 0; i < len; i++){
            char curCh = text.charAt(i);

            for(String word: words){
                if(word.charAt(0) == curCh){
                    int wordLen = word.length();
                    if(i + wordLen <= len && text.substring(i, i + wordLen).equals(word)){
                        int[] pair = new int[2];
                        pair[0] = i;
                        pair[1] = i + wordLen - 1;

                        retList.add(pair);
                    }
                }
            }
        }

        // sort ret
        Collections.sort(retList, new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                if(p1[0] != p2[0]){
                    return p1[0] - p2[0];
                }
                return p1[1] - p2[1];
            }
        });


        return retList.toArray(new int[retList.size()][2]);

    }

    // Trie Solution
    // since input string is constant, so a Trie solution is a little overkill for this problem, but should know how to solve it with Trie
    // TireNode
    private class TrieNode{
        TrieNode[] children;
        boolean isWord;

        public TrieNode(){
            this.children = new TrieNode[26];
        }
    }

    // insert
    private void insertWord(String word){
        TrieNode curNode = this.rootNode;

        int len = word.length();
        for(int i = 0; i < len; i++){
            char ch = word.charAt(i);

            // build next node if not exist in Trie
            if(curNode.children[ch - 'a'] == null){
                curNode.children[ch - 'a'] = new TrieNode();
            }

            // move to next node
            curNode = curNode.children[ch - 'a'];
        }

        // after process the word, mark the node as a end of word
        curNode.isWord = true;
    }

    // search node that has the prefix of given word. No need in this problem
    private TrieNode searchPrefix(String word){
        TrieNode curNode = this.rootNode;

        int len = word.length();
        for(int i = 0; i < len; i++){
            char ch = word.charAt(i);

            // if no such node as child, return null
            if(curNode.children[ch - 'a'] == null){
                return null;
            }

            // move to next node
            curNode = curNode.children[ch - 'a'];
        }

        return curNode;
    }

    // search given word. No need in this problem.
    private boolean searchWord(String word){
        TrieNode node = searchPrefix(word);
        if(node == null){
            return false;
        }

        return node.isWord;
    }

    TrieNode rootNode;
    // TC: O(O(N*M*L + N*logN)), N is length of text, M is length of words array, L is longest string length in words. Total nodes num in Trie is M*L
    // SC: O(M*L + N)
    private int[][] indexPairsByTrie(String text, String[] words){
        if(words == null || words.length == 0 || text == null || text.length() == 0){
            return new int[0][0];
        }

        // 1. init root Trie node
        rootNode = new TrieNode();

        // 2. insert all word to Trie
        for(String word: words){
            insertWord(word);
        }

        List<int[]> retList = new ArrayList<>();
        // 3. search
        // here, we are try from every idx of text, and search all substring in range [idx:end]
        int len = text.length();
        for(int i = 0; i < len; i++){
            // !!! By this way to search, we only traverse the range [i:end] once and searched all substirngs in this range. More efficient than use the Tire searchWord method to search all substrings in this range, because that way, we must get the substring in range [i:end] from text which cost O(N), so totally cost O(N^2 * TrieNodeNum)
            // This way, we used the intermediate results, so only cost O(N * TrieNodeNum)
            // if char at i is a child node of Tire root, then we start a search from here
            if(this.rootNode.children[text.charAt(i) - 'a'] != null){
                TrieNode curNode = this.rootNode;
                // j moves to help us traverse all substrings in range [i:end]
                int j = i;
                // try every substring in range
                while(j < len && curNode != null){
                    // get char at j
                    char ch = text.charAt(j);
                    // find its corresponding node in Trie
                    curNode = curNode.children[ch - 'a'];
                    // if the node exists and is word, we find a pair
                    if(curNode != null && curNode.isWord){
                        int[] pair = new int[2];
                        pair[0] = i;
                        pair[1] = j;

                        retList.add(pair);
                    }

                    // move forward j to build a new substring [i:j].
                    j++;
                }
            }
        }

        // sort ret
        Collections.sort(retList, new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                if(p1[0] != p2[0]){
                    return p1[0] - p2[0];
                }
                return p1[1] - p2[1];
            }
        });


        return retList.toArray(new int[retList.size()][2]);

    }

}

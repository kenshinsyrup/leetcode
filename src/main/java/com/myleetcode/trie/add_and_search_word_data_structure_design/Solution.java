package com.myleetcode.trie.add_and_search_word_data_structure_design;

public class Solution {
    class WordDictionary {

        class TrieNode {
            TrieNode[] children;
            boolean isEnd;

            TrieNode(){
                this.children = new TrieNode[26];
            }
        }

        TrieNode root;

        // https://leetcode.com/problems/add-and-search-word-data-structure-design/discuss/59687/Trie-Tree-java-solution!-very-easy-to-understand!

        /** Initialize your data structure here. */
        public WordDictionary() {
            this.root = new TrieNode();
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            TrieNode curNode = this.root;

            for(char ch: word.toCharArray()){
                if(curNode.children[ch - 'a'] == null){
                    curNode.children[ch - 'a'] = new TrieNode();
                }

                curNode = curNode.children[ch - 'a'];
            }

            curNode.isEnd = true;
        }

        // TC: O(V + E)
        // for the TC, roughly could say O(26 ^ N), N is the length of word
        // because of the '.', the worst case for eg infinite ".....", we have to try all vertices and edges in the Trie, so the TC could be O(V + E), where V and E are the valid vertices and edges in the Trie
        // SC: O(V + E)
        private boolean searchNodeOfPrefix(TrieNode curNode, String prefix, int pos){
            if(pos == prefix.length()){
                return curNode.isEnd;
            }

            char ch = prefix.charAt(pos);

            if(ch == '.'){
                for(TrieNode nextNode: curNode.children){
                    if(nextNode != null){ // !!!
                        if(searchNodeOfPrefix(nextNode, prefix, pos + 1)){
                            return true;
                        }
                    }
                }
            }else{
                if(curNode.children[ch - 'a'] == null){
                    return false;
                }

                return searchNodeOfPrefix(curNode.children[ch - 'a'], prefix, pos + 1);
            }

            return false;
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return searchNodeOfPrefix(this.root, word, 0);
        }
    }

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
}

package com.myleetcode.trie.implement_trie_prefix_tree;

public class Solution {
    class Trie {

        // simplify
        class TrieNode {
            // children nodes, for chaaracters, the children array has length 26
            TrieNode[] children;
            // if the TrieNode is the end of a branch
            boolean isEnd;

            public TrieNode(){
                children = new TrieNode[26];
            }
        }

        private TrieNode root;
        /** Initialize your data structure here. */
        public Trie() {
            this.root = new TrieNode();
        }

        // TC: O(26 * N) => O(N), N is the length of String word
        // SC: O(N)
        /** Inserts a word into the trie. */
        public void insert(String word) {
            TrieNode curNode = this.root;

            for(char ch: word.toCharArray()){
                // if has no this ch in Trie, put it in
                if(curNode.children[ch - 'a'] == null){
                    curNode.children[ch - 'a'] = new TrieNode();
                }

                // update curNode, explore
                curNode = curNode.children[ch - 'a'];
            }

            // set end
            curNode.isEnd = true;
        }

        // TC: O(26 * N) => O(N)
        // SC: O(1)
        // a help function for search and startsWith
        // return the TrieNode if we could find the valid word as prefix, otherwise null
        private TrieNode searchTrieNodeOfPrefix(String word){
            TrieNode curNode = this.root;

            for(char ch: word.toCharArray()){
                // if no such ch, false
                if(curNode.children[ch - 'a'] == null){
                    return null;
                }

                // update curNode, explore
                curNode = curNode.children[ch - 'a'];
            }

            return curNode;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            TrieNode node = searchTrieNodeOfPrefix(word);

            // if not find valid TrieNode, false
            if(node == null){
                return false;
            }

            // if we find all chars in word, but curNode is not the end, means this word is a prefix of other words, not a whole word existing in Trie, false
            if(!node.isEnd){
                return false;
            }

            return true;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            TrieNode node = searchTrieNodeOfPrefix(prefix);

            // if not find valid TrieNode, false
            if(node == null){
                return false;
            }

            return true;
        }


        // solution section

//     class TrieNode {
//         // children nodes, for chaaracters, the children array has length 26
//         TrieNode[] children;
//         // if the TrieNode is the end of a branch
//         boolean isEnd;

//         public TrieNode(){
//             children = new TrieNode[26];
//         }

//         // get
//         public TrieNode get(char ch){
//             return this.children[ch - 'a'];
//         }

//         // put
//         public void put(char ch, TrieNode node){
//             this.children[ch - 'a'] = node;
//         }

//         // set end of branch
//         public void setEnd(){
//             this.isEnd = true;
//         }

//         // check if this node is end of a branch
//         public boolean isEnd(){
//             return this.isEnd;
//         }

//     }

//     private TrieNode root;
//     /** Initialize your data structure here. */
//     public Trie() {
//         this.root = new TrieNode();
//     }

//     // TC: O(26 * N) => O(N), N is the length of String word
//     // SC: O(N)
//     /** Inserts a word into the trie. */
//     public void insert(String word) {
//         TrieNode curNode = this.root;

//         for(char ch: word.toCharArray()){
//             // if has no this ch in Trie, put it in
//             if(curNode.get(ch) == null){
//                 curNode.put(ch, new TrieNode());
//             }

//             // update curNode, explore
//             curNode = curNode.get(ch);
//         }

//         // set end
//         curNode.setEnd();
//     }

//     // TC: O(26 * N) => O(N)
//     // SC: O(1)
//     // a help function for search and startsWith
//     // return the TrieNode if we could find the valid word as prefix, otherwise null
//     private TrieNode searchTrieNodeOfPrefix(String word){
//         TrieNode curNode = this.root;

//         for(char ch: word.toCharArray()){
//             // if no such ch, false
//             if(curNode.get(ch) == null){
//                 return null;
//             }

//             // update curNode, explore
//             curNode = curNode.get(ch);
//         }

//         return curNode;
//     }

//     /** Returns if the word is in the trie. */
//     public boolean search(String word) {
//         TrieNode node = searchTrieNodeOfPrefix(word);

//         // if not find valid TrieNode, false
//         if(node == null){
//             return false;
//         }

//         // if we find all chars in word, but curNode is not the end, means this word is a prefix of other words, not a whole word existing in Trie, false
//         if(!node.isEnd()){
//             return false;
//         }

//         return true;
//     }

//     /** Returns if there is any word in the trie that starts with the given prefix. */
//     public boolean startsWith(String prefix) {
//         TrieNode node = searchTrieNodeOfPrefix(prefix);

//         // if not find valid TrieNode, false
//         if(node == null){
//             return false;
//         }

//         return true;
//     }
    }

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
}

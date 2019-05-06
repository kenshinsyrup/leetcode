package com.myleetcode.trie.word_search_ii;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        return findWordsByTrie(board, words);
    }

    // intuition: this is a practive problem of 208 Trie, but the first intuition is this looks like a dfs backtracking problem. But naive backtracking may cost too much if the board is big and the words array is big too
    private List<String> findWordsByTrie(char[][] board, String[] words){
        List<String> ret = new ArrayList<>();

        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0){
            return ret;
        }
        if(words == null || words.length == 0){
            return ret;
        }

        Trie trie = new Trie();
        trie.addWords(words);
        Set<String> retSet = new HashSet<>();
        trie.searchInBoard(board, retSet);

        return new ArrayList<>(retSet);

    }

    class Trie {
        TrieNode root;

        public Trie(){
            this.root = new TrieNode();
        }

        // add word
        public void addWord(String word){
            TrieNode curNode = this.root;

            for(char ch: word.toCharArray()){
                if(curNode.children[ch - 'a'] == null){
                    curNode.children[ch - 'a'] = new TrieNode();
                }

                curNode = curNode.children[ch - 'a'];
            }

            curNode.isEnd = true;
        }

        // search word
        public boolean searchWord(String word){
            TrieNode node = searchNodeOfPrefix(word);
            return node != null && node.isEnd;
        }

        // starts with
        public boolean startsWith(String prefix){
            TrieNode node = searchNodeOfPrefix(prefix);
            return node != null;
        }

        // TrieNode of prefix
        private TrieNode searchNodeOfPrefix(String prefix){
            TrieNode curNode = this.root;
            for(char ch: prefix.toCharArray()){
                if(curNode.children[ch - 'a'] == null){
                    return null;
                }

                curNode = curNode.children[ch - 'a'];
            }

            return curNode;
        }

        // above is classic Trie, below is solution of this problem

        // basic idean is build the Trie with given words array, here is important, we must use words array to build Trie because we have to know the isEnd. if we build Trie with board[][], we dont know which cell should be valid end of word.
        // after we have a Trie, the next is sth more classic, we traverse the board[][] to use every cell as a start and get all possible string combinations by backtracking, then we search these string in the Trie, if exists, we find one, add to the ret
        // and here, another important thing is, we dont need duplicates, so we use a Set to store the ret, then at last we return it as List
        // https://leetcode.com/problems/word-search-ii/discuss/59784/My-simple-and-clean-Java-code-using-DFS-and-Trie

        public void addWords(String[] words){
            for(String word: words){
                addWord(word);
            }
        }

        private void dfsBacktracking(char[][] board, String str,  boolean[][] visited, int rowIdx, int colIdx, int[][] directions, Set<String> ret){
            if(rowIdx < 0 || rowIdx >= board.length || colIdx < 0 || colIdx >= board[0].length){
                return;
            }

            if(visited[rowIdx][colIdx]){
                return;
            }

            str += board[rowIdx][colIdx];
            if(!startsWith(str)){
                return;
            }

            if(searchWord(str)){
                ret.add(str);
            }

            // mark this cell as visited
            visited[rowIdx][colIdx] = true;

            // explore
            for(int i = 0; i < directions.length; i++){
                dfsBacktracking(board, str, visited, rowIdx + directions[i][0], colIdx + directions[i][1], directions, ret);
            }

            // !!!after process the current cell's 4 adjacent cells, we unmarkd this cell to allow other cells visit again
            visited[rowIdx][colIdx] = false;
        }

        public void searchInBoard(char[][] board, Set<String> ret){
            boolean[][] visited = new boolean[board.length][board[0].length];
            int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[0].length; j++){
                    dfsBacktracking(board, "", visited, i, j, directions, ret);
                }
            }
        }

    }

    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode(){
            children = new TrieNode[26];
        }
    }
}

package com.myleetcode.trie.design_search_autocomplete_system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Solution {

  class AutocompleteSystem {

    // https://leetcode.com/problems/design-search-autocomplete-system/discuss/105376/Java-solution-Trie-and-PriorityQueue/107991

    TrieNode rootNode;
    StringBuilder sb;

    public AutocompleteSystem(String[] sentences, int[] times) {
      this.rootNode = new TrieNode();
      this.sb = new StringBuilder();

      if (sentences == null || times == null) {
        return;
      }

      int len = sentences.length;
      for (int i = 0; i < len; i++) {
        insertToTrie(sentences[i], times[i]);
      }
    }

    public List<String> input(char c) {
      if (c == '#') {
        String word = sb.toString();
        insertToTrie(word, 1);
        sb = new StringBuilder();

        return new ArrayList<>();
      }

      sb.append(c);
      String prefix = sb.toString();
      TrieNode curNode = this.rootNode;
      char[] chars = prefix.toCharArray();
      for (char ch : chars) {
        TrieNode nextNode = curNode.childMap.get(ch);
        if (nextNode == null) {
          return new ArrayList<>();
        }

        curNode = nextNode;
      }

      PriorityQueue<Entry<String, Integer>> pq = new PriorityQueue<>(
          (a, b) -> (a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey())
              : b.getValue() - a.getValue()));
      pq.addAll(curNode.wordNumMap.entrySet());

      List<String> res = new ArrayList<>();
      int k = 3;
      while (!pq.isEmpty() && k > 0) {
        res.add(pq.poll().getKey());
        k--;
      }

      return res;

    }

    class TrieNode {

      Map<String, Integer> wordNumMap;
      Map<Character, TrieNode> childMap;
      boolean isWord;

      public TrieNode() {
        this.wordNumMap = new HashMap<>();
        this.childMap = new HashMap<>();
        this.isWord = false;
      }
    }

    private void insertToTrie(String word, int num) {
      TrieNode curNode = this.rootNode;

      char[] chars = word.toCharArray();
      for (char ch : chars) {
        // find node
        TrieNode nextNode = curNode.childMap.get(ch);
        if (nextNode == null) {
          curNode.childMap.put(ch, new TrieNode());
          nextNode = curNode.childMap.get(ch);
        }
        curNode = nextNode;

        // update node in path
        nextNode.wordNumMap.put(word, nextNode.wordNumMap.getOrDefault(word, 0) + num);

      }

      curNode.isWord = true;
    }

  }

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */

}

package com.myleetcode.string.most_common_word;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {

  public String mostCommonWord(String paragraph, String[] banned) {
    return mostCommonWordByMap(paragraph, banned);
  }

  /*
  Intuition:
  use Set store banned words
  traverse String paragraph, find each word, if word is not banned, store in to a Map stores the String-Number pair.
  traverse the Stirng-Number pair Map and return the word with largest num

  TC: O(N), N is paragraph length
  SC: O(N)
  */
  private String mostCommonWordByMap(String paragraph, String[] banned) {
    if (paragraph == null || paragraph.length() == 0) {
      return paragraph;
    }

    // 1. to lower case
    paragraph = paragraph.toLowerCase();

    // 2. get banned word Set
    Set<String> bannedSet = new HashSet<>(Arrays.asList(banned));

    // 3. get not banned word and count
    Map<String, Integer> wordNumMap = new HashMap<>();
    int len = paragraph.length();
    int i = 0;
    while (i < len) {
      while (i < len && !isAlphabeta(paragraph.charAt(i))) {
        i++;
      }
      // !!!
      if (i >= len) {
        break;
      }

      int j = i;
      while (j < len && isAlphabeta(paragraph.charAt(j))) {
        j++;
      }

      String word = paragraph.substring(i, j);
      if (!bannedSet.contains(word)) {
        wordNumMap.putIfAbsent(word, 0);
        wordNumMap.put(word, wordNumMap.get(word) + 1);
      }

      i = j;
    }

    // 4. get the most frequent word
    int max = 0;
    String retStr = null;
    for (String word : wordNumMap.keySet()) {
      if (max < wordNumMap.get(word)) {
        retStr = word;
        max = wordNumMap.get(word);
      }
    }

    return retStr;

  }

  private boolean isAlphabeta(char ch) {
    return ch >= 'a' && ch <= 'z';
  }
}
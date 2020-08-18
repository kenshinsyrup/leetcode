package com.myleetcode.breadth_first_search.word_ladder;

import java.util.*;

public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        return shortestPathByBFS(wordList, beginWord, endWord);
    }

    // TC: O(N + M * K), K is the word length
    // SC: O(N)
    private int shortestPathByBFS(List<String> wordList, String beginWord, String endWord) {
        // Special case
        if (beginWord == null || endWord == null || wordList == null || wordList.size() == 0) {
            return 0;
        }

        // because no duplicates, transfer the wordList to wordSet, to reduce the search time
        Set<String> wordSet = new HashSet<>();
        for (String wordStr : wordList) {
            wordSet.add(wordStr);
        }
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // visited
        Set<String> visitedStrSet = new HashSet<>();

        // queue for bfs
        Deque<String> strQueue = new ArrayDeque<>();
        // enqueue the start string
        strQueue.offer(beginWord);

        // pathLen
        int pathLen = 0;

        // BFS
        while (!strQueue.isEmpty()) {
            // level size
            int size = strQueue.size();
            // increase pathLen every level
            pathLen++;

            for (int i = 0; i < size; i++) {
                // cur str node
                String curStr = strQueue.poll();
                // add as visited
                visitedStrSet.add(curStr);

                // check if we find the end string
                if (curStr.equals(endWord)) {
                    return pathLen;
                }

                List<String> children = getChildren(curStr, wordSet);
                for (String child : children) {
                    if (!visitedStrSet.contains(child)) {
                        strQueue.offer(child);
                    }
                }
            }
        }

        return 0;
    }

    // Find all children of given word.
    private List<String> getChildren(String word, Set<String> wordSet) {
        List<String> children = new ArrayList<>();
        char chs[] = word.toCharArray();

        for (int i = 0; i < chs.length; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (chs[i] != ch) {
                    // !!! keep the original char to restore because we should only chage one char in chs and we should try 'a' to 'z' so everytime we try, we restore and try next char in 'a' to 'z'
                    char old_ch = chs[i];
                    chs[i] = ch;

                    String child = new String(chs);
                    if (wordSet.contains(child)) {
                        children.add(child);
                    }

                    chs[i] = old_ch;
                }
            }
        }

        return children;
    }
}
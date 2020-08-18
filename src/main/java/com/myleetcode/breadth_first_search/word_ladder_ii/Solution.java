package com.myleetcode.breadth_first_search.word_ladder_ii;

import java.util.*;

public class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // return findLaddersByDFS(beginWord, endWord, wordList);// Naive Backtracking, TLE
        return findLaddersByBFSAndBacktracking(beginWord, endWord, wordList);
    }

    /*
    The proble needs us to return All Shortest Paths. As we know we could use BFS from beginWord to get endWord.

    About how to get shortest path in a BFS:
    Based on the BFS, if we want to know the exact paths we traveled, we could record the parents of each word, and distance from each word to beginWord during the BFS. And then using Backtracking with these two maps to recover all the shortest paths.

    // thought: https://leetcode.com/problems/word-ladder-ii/discuss/172308/A-kind-of-concept-with-diagrams-()
    // solution: https://leetcode.com/problems/word-ladder-ii/discuss/40475/My-concise-JAVA-solution-based-on-BFS-and-DFS

    for eg, input:
        beginWord: "a"
        endWord: "c"
        wordList: ["a","b","c"]

    will give us two map after BFS:
        wordParentMap: {a=[b, c], b=[a, c], c=[a, b]}
        wordDistanceMap: {a=0, b=1, c=1}

    we could recover the path from endWord to beginWord based on these two maps:
        c -> a
        b -> a

    after reverse, these will be what we want.
    */
    private List<List<String>> findLaddersByBFSAndBacktracking(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ret = new ArrayList<>();
        if (beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || wordList == null || wordList.size() == 0) {
            return ret;
        }

        Set<String> wordSet = new HashSet<>(wordList);

        // <word, its parent word>, keep tracking of the parents of each word during BFS.
        Map<String, List<String>> wordParentMap = new HashMap<>();
        // <word, distance to beginWord>, keep tracking of distance from word to the beginWord during BFS.
        Map<String, Integer> wordDistanceMap = new HashMap<>();

        // Search shortest path from beginWord to endWord, and keep the information of parents and distanct to beginWord for each word during BFS.
        Set<String> visited = new HashSet<>();
        BFS(beginWord, endWord, wordSet, wordParentMap, wordDistanceMap);

        // Using Backtracking to recover all the shortest paths from endWord to beginWord.
        List<String> snippet = new ArrayList<>();
        snippet.add(endWord);
        backtrackingII(endWord, beginWord, wordParentMap, wordDistanceMap, snippet, ret);

        return ret;
    }

    // BFS: Trace every node's distance from the start node (level by level).
    private void BFS(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> wordParentMap, Map<String, Integer> wordDistanceMap) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        wordDistanceMap.put(beginWord, 0);
        // wordParentMap.put(beginWord, new ArrayList<>());

        while (!queue.isEmpty()) {
            String cur = queue.poll();

            int curDistance = wordDistanceMap.get(cur);
            List<String> children = getChildren(cur, wordSet);
            for (String child : children) {
                // Add cur word as child's parent.
                List<String> parents = wordParentMap.getOrDefault(child, new ArrayList<>());
                parents.add(cur);
                wordParentMap.put(child, parents);

                // If we don't know the distance from child to beginWord, means this is the first time we meet this word and this is the shortest distance to reach child. So we keep record of this distance, and enque this child word.
                if (!wordDistanceMap.containsKey(child)) {
                    wordDistanceMap.put(child, curDistance + 1);

                    queue.offer(child);
                }
            }
        }
    }

    // Backtracking: output all shortest paths.
    private void backtrackingII(String curWord, String beginWord, Map<String, List<String>> wordParentMap, Map<String, Integer> wordDistanceMap, List<String> snippet, List<List<String>> ret) {
        if (curWord.equals(beginWord)) {
            // !!! Don't reverse snippet directly, in backtracking, the order means a lot.
            List<String> path = new ArrayList<>(snippet);
            Collections.reverse(path);
            ret.add(path);
            return;
        }

        List<String> parents = wordParentMap.getOrDefault(curWord, new ArrayList<>());
        for (String parent : parents) {
            if (wordDistanceMap.get(parent) == wordDistanceMap.get(curWord) - 1) { // !!!
                snippet.add(parent);
                backtrackingII(parent, beginWord, wordParentMap, wordDistanceMap, snippet, ret);
                snippet.remove(snippet.size() - 1);
            }
        }
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

    // intuition: this is different with the 127. Word Ladder. The 127 asks fot the shortest path so we use BFS. This problem asks for all paths, so we need DFS.
    // the thought is we use DFS Backtracking to tray all paths from beginWord, if one path reaches the endWord successfully, we could record it tho the List ret.
    // the implement is, since no duplicates in wordList, so we could convert it to a Map<String, Boolean> wordVisitMap, by this we could know if a word exists in map and if we have visited it in O(1) time. Then, we use a snippetList to record the intermediate paths, if reach endWord, we add it to the List ret.

    // Naive Backtracking: TLE
    // TC: O(2^N), N is the length of wordList, is the length of recursion
    // SC: O(N)
    private List<List<String>> findLaddersByDFS(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ret = new ArrayList<>();

        if (beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || wordList == null || wordList.size() == 0) {
            return ret;
        }

        Map<String, Boolean> wordVisitMap = new HashMap<>();
        for (String word : wordList) {
            wordVisitMap.put(word, false);
        }

        List<String> snippet = new ArrayList<>();

        // DFS: use beginWord word as the root, try to find all paths
        backtracking(beginWord, endWord, wordVisitMap, snippet, ret);

        // choose the shortest paths to return
        int shortest = Integer.MAX_VALUE;
        for (List<String> path : ret) {
            shortest = Math.min(shortest, path.size());
        }

        List<List<String>> shortestPath = new ArrayList<>();
        for (List<String> path : ret) {
            if (path.size() == shortest) {
                path.add(0, beginWord);// must add the real start word into the path according the problem
                shortestPath.add(path);
            }
        }

        return shortestPath;
    }

    // backtracking to try to find all possible path
    private void backtracking(String beginWord, String endWord, Map<String, Boolean> wordVisitMap, List<String> snippet, List<List<String>> ret) {
        if (beginWord.equals(endWord)) {
            ret.add(new ArrayList<>(snippet));
            return;
        }

        wordVisitMap.put(beginWord, true);

        for (int i = 0; i < beginWord.length(); i++) {
            char[] charArray = beginWord.toCharArray();

            for (char c = 'a'; c <= 'z'; c++) {
                charArray[i] = c;
                String nextStr = new String(charArray);

                if (wordVisitMap.containsKey(nextStr) && !wordVisitMap.get(nextStr)) {
                    snippet.add(nextStr);

                    backtracking(nextStr, endWord, wordVisitMap, snippet, ret);

                    snippet.remove(snippet.size() - 1);
                }
            }
        }

        wordVisitMap.put(beginWord, false);
    }
}
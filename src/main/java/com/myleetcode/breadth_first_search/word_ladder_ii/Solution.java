package com.myleetcode.breadth_first_search.word_ladder_ii;

import java.util.*;

public class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // return findLaddersByDFS(beginWord, endWord, wordList);// Wrong, TLE
        return findLaddersByBFSAndDFS(beginWord, endWord, wordList);
    }

    // the proble needs us to return All Shortest Paths
    // so the correct thought is: 1 we use BFS to find the shortest path from beginWord to endWord and during this we could build a Directional Graph using a Map<String, List<String>>. 2 we use DFS to find all paths in this DIrectional Graph from beginWord to endWord
    // thought: https://leetcode.com/problems/word-ladder-ii/discuss/172308/A-kind-of-concept-with-diagrams-()
    // solution: https://leetcode.com/problems/word-ladder-ii/discuss/40475/My-concise-JAVA-solution-based-on-BFS-and-DFS
    private List<List<String>> findLaddersByBFSAndDFS(String beginWord, String endWord, List<String> wordList){
        List<List<String>> ret = new ArrayList<>();

        if(beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || wordList == null || wordList.size() == 0){
            return ret;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        Map<String, List<String>> graph = new HashMap<>();// directional graph for every node
        Map<String, Integer> wordDistanceMap = new HashMap<>();// Distance of every node from the start node
        List<String> snippet = new ArrayList<>();

        // add the given beginWord to the word Set
        wordSet.add(beginWord);

        // build the graph with the shortest path from beginWord to endWord
        bfs(beginWord, endWord, wordSet, graph, wordDistanceMap);

        // get all paths in the graph
        snippet.add(beginWord);// according to the problem, the path should contains the beginWord
        dfs(beginWord, endWord, wordSet, graph, wordDistanceMap, snippet, ret);

        return ret;
    }

    // BFS: Trace every node's distance from the start node (level by level).
    private void bfs(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> graph, Map<String, Integer> wordDistanceMap) {

        for (String str : wordSet){
            graph.put(str, new ArrayList<String>());
        }

        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        wordDistanceMap.put(beginWord, 0);

        while (!queue.isEmpty()) {
            int count = queue.size();
            boolean foundEnd = false;

            for (int i = 0; i < count; i++) {
                String cur = queue.poll();
                int curDistance = wordDistanceMap.get(cur);
                List<String> neighbors = getNeighbors(cur, wordSet);

                for (String neighbor : neighbors) {
                    graph.get(cur).add(neighbor);

                    if (!wordDistanceMap.containsKey(neighbor)) {// Check if visited
                        wordDistanceMap.put(neighbor, curDistance + 1);

                        if (endWord.equals(neighbor)){
                            // Found the shortest path
                            foundEnd = true;
                        }else{
                            queue.offer(neighbor);
                        }
                    }
                }

                if (foundEnd){
                    break;
                }
            }
        }
    }

    // DFS: output all paths with the shortest distance.
    private void dfs(String curWord, String endWord, Set<String> wordSet, Map<String, List<String>> graph, Map<String, Integer> wordDistanceMap, List<String> snippet, List<List<String>> ret) {
        if (endWord.equals(curWord)) {
            ret.add(new ArrayList<String>(snippet));
            return;
        }

        for (String nextWord: graph.get(curWord)) {
            if (wordDistanceMap.get(nextWord) == wordDistanceMap.get(curWord) + 1) {
                snippet.add(nextWord);
                dfs(nextWord, endWord, wordSet, graph, wordDistanceMap, snippet, ret);
                snippet.remove(snippet.size() - 1);
            }
        }
    }

    // Find all neighbors of given word   
    private List<String> getNeighbors(String word, Set<String> wordSet) {
        List<String> neighbors = new ArrayList<>();
        char chs[] = word.toCharArray();

        for (int i = 0; i < chs.length; i++) {
            for (char ch ='a'; ch <= 'z'; ch++) {
                if (chs[i] != ch) {
                    // !!! keep the original char to restore because we should only chage one char in chs and we should try 'a' to 'z' so everytime we try, we restore and try next char in 'a' to 'z'
                    char old_ch = chs[i];
                    chs[i] = ch;

                    String neighborWord = new String(chs);
                    if (wordSet.contains(neighborWord)) {
                        neighbors.add(neighborWord);
                    }

                    chs[i] = old_ch;
                }
            }
        }

        return neighbors;
    }


    // intuition: this is different with the 127. Word Ladder. The 127 asks fot the shortest path so we use BFS. This problem asks for all paths, so we need DFS.
    // the thought is we use DFS Backtracking to tray all paths from beginWord, if one path reaches the endWord successfully, we could record it tho the List ret.
    // the implement is, since no duplicates in wordList, so we could convert it to a Map<String, Boolean> wordVisitMap, by this we could know if a word exists in map and if we have visited it in O(1) time. Then, we use a snippetList to record the intermediate paths, if reach endWord, we add it to the List ret.

    // Naive Backtracking: TLE
    // TC: O(2^N), N is the length of wordList, is the length of recursion
    // SC: O(N)
    private List<List<String>> findLaddersByDFS(String beginWord, String endWord, List<String> wordList){
        List<List<String>> ret = new ArrayList<>();

        if(beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || wordList == null || wordList.size() == 0){
            return ret;
        }

        Map<String, Boolean> wordVisitMap = new HashMap<>();
        for(String word: wordList){
            wordVisitMap.put(word, false);
        }

        List<String> snippet = new ArrayList<>();

        // DFS: use beginWord word as the root, try to find all paths
        backtracking(beginWord, endWord, wordVisitMap, snippet, ret);

        // choose the shortest paths to return
        int shortest = Integer.MAX_VALUE;
        for(List<String> path: ret){
            shortest = Math.min(shortest, path.size());
        }

        List<List<String>> shortestPath = new ArrayList<>();
        for(List<String> path: ret){
            if(path.size() == shortest){
                path.add(0, beginWord);// must add the real start word into the path according the problem
                shortestPath.add(path);
            }
        }

        return shortestPath;
    }

    // backtracking to try to find all possible path
    private void backtracking(String beginWord, String endWord, Map<String, Boolean>wordVisitMap, List<String> snippet, List<List<String>> ret){
        if(beginWord.equals(endWord)){
            ret.add(new ArrayList<>(snippet));
            return;
        }

        wordVisitMap.put(beginWord, true);

        for(int i = 0; i < beginWord.length(); i++){
            char[] charArray = beginWord.toCharArray();

            for(char c = 'a'; c <= 'z'; c++){
                charArray[i] = c;
                String nextStr = new String(charArray);

                if(wordVisitMap.containsKey(nextStr) && !wordVisitMap.get(nextStr)){
                    snippet.add(nextStr);

                    backtracking(nextStr, endWord, wordVisitMap, snippet, ret);

                    snippet.remove(snippet.size() -1);
                }
            }
        }

        wordVisitMap.put(beginWord, false);
    }
}
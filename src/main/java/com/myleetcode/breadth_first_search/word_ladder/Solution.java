package com.myleetcode.breadth_first_search.word_ladder;

import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
//         最短路径问题用bfs

        // special case
        if(beginWord == null || endWord == null || wordList == null || !wordList.contains(endWord)){
            return 0;
        }

        // return shortestPathByBFS(wordList, beginWord, endWord);
        return shortestPathByBFSII(wordList, beginWord, endWord);
    }

    // 写法标准化
    // TC: O(N + M * K), K is the word length
    // SC: O(N)
    private int shortestPathByBFSII(List<String> wordList, String startStr, String endStr){
        // because no duplicates, transfer the wordList to wordSet, to reduce the search time
        Set<String> wordSet = new HashSet<>();
        for(String wordStr: wordList){
            wordSet.add(wordStr);
        }

        // visited
        Set<String> visitedStrSet = new HashSet<>();

        // queue for bfs
        Deque<String> strQueue = new ArrayDeque<>();
        // enqueue the start string
        strQueue.offer(startStr);

        // pathLen
        int pathLen = 0;

        // BFS
        while(!strQueue.isEmpty()){
            // level size
            int size = strQueue.size();
            // increase pathLen every level
            pathLen++;

            for(int i = 0; i < size; i++){
                // cur str node
                String curStr = strQueue.poll();

                // check if we find the end string
                if(curStr.equals(endStr)){
                    return pathLen;
                }

                // stransfer to char[]
                char[] curStrCharArray = curStr.toCharArray();

                // try to find all possible next string
                for(int j = 0; j < curStrCharArray.length; j++){
                    for(char ch = 'a'; ch <= 'z'; ch++){
                        char tempChar = curStrCharArray[j];
                        curStrCharArray[j] = ch;

                        // next possible string
                        String nextPossibleStr = new String(curStrCharArray);
                        if(wordSet.contains(nextPossibleStr) && !visitedStrSet.contains(nextPossibleStr)){
                            // enqueue
                            strQueue.offer(nextPossibleStr);
                            // add as visited
                            visitedStrSet.add(nextPossibleStr);
                        }

                        // restore char[]
                        curStrCharArray[j] = tempChar;
                    }
                }
            }
        }

        return 0;
    }


    private int shortestPathByBFS(List<String> wordList, String start, String end){
        // use queue to bfs
        Queue<String> stringQ = new LinkedList<String>();
        // add first element to queue to start
        stringQ.add(start);

        // need a set to store visited string
        Set<String> visited = new HashSet<>();
        // visited
        visited.add(start);

        // 真实层数，即path length + 1
        // 这个问题最重要的一点，是level起始值是1而不是0，这个读题目就能看出来，最初因为不读题，导致算出来的level少了一。注意，这个题目要找的不是（不只是）从list的第一个元素到最后一个元素的长度，还需要包括一个长度就是从给定字符串到list的第一个元素这个长队，也就是bfs遍历了整个list之后如果能找到ladder需要让level+1，但是如果没有找到level需要是0.所以这个level就需要在最初的时候从1开始，如果没有找到，返回0，如果找到了，那么level从1开始++
        int level = 1;
        // int level = 0;
        // level++;

        // 控制bfs层数，用来记录何时是一层从而正确的让level计数
        int lenControl = 0;
//         初始层数
        lenControl = stringQ.size();

        while(!stringQ.isEmpty()){
            String str = stringQ.poll();
            if(str.equals(end)){
                return level;
            }

            // lenControl减一，queue中该区段的node被取出了一个
            lenControl--;

            // change its character
            for(int i = 0; i < str.length(); i++){
                char[] charArray = str.toCharArray();

                for(char c = 'a'; c <= 'z'; c++){
                    charArray[i] = c;

                    String newStr = new String(charArray);

                    if(wordList.contains(newStr) && !visited.contains(newStr)){
                        stringQ.add(newStr);
                        visited.add(newStr);
                    }
                }
            }

//             根据lenControl是否为０，也就是根据queue中同一层的node是否都被取出作为一层结束的标记
            if(lenControl == 0){
                lenControl = stringQ.size();
                level++;
            }
        }

        return 0;

    }
}
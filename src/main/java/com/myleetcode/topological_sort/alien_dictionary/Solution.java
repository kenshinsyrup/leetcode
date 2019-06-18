package com.myleetcode.topological_sort.alien_dictionary;

import java.util.*;

class Solution {
    public String alienOrder(String[] words) {
        return alienOrderByTopologicalSortWithDAGVerifyAndDFS(words);
    }

    // intuition: Topology Sort with DAG Verify
    // But the hard point of this problem is the graph building part.
    // TC: O(M + N)
    // SC: O(N)
    // 根据示例: wrf在er前面，说明w->e; wrt在wrf前面，说明t->f;er在ett前面说明r->t,ett在rftt前面说明e->r
    // w->e->r->t->f

    // 注意，我们用一个charSet来存储所有曾经出现过的char，这个是我们将来输出的所有chars
    // 我们用一个graph来存储char之间的连接关系，这个是将来我们输出的时候的顺序依据，同corse schedule相类似，如果有一些char之间没有关系，也就是在graph里没有children，那么我们可以任意顺序的输出他们，但是不能不输出
    private String alienOrderByTopologicalSortWithDAGVerifyAndDFS(String[] words){
        // special case
        if(words == null || words.length == 0){
            return "";
        }

        // build graph first
        Map<Character, List<Character>> graph = new HashMap<>();

        // fullfill graph, check given words, in adjacent rows, the first pos that upper row word's char is not the same with lower rot word's char, means the lower char depends on the upper char
        int len = words.length;
        for(int i = 0; i < len - 1; i++){
            String word = words[i];
            String nextWord = words[i + 1];

            int wordLen = Math.min(word.length(), nextWord.length());
            for(int j = 0; j < wordLen; j++){
                if(word.charAt(j) != nextWord.charAt(j)){
                    graph.putIfAbsent(word.charAt(j), new ArrayList<>());
                    graph.get(word.charAt(j)).add(nextWord.charAt(j));
                    break; // !!!
                }
            }
        }

        // need a Set to keep track of what char occurs
        Set<Character> charSet = new HashSet<>();
        for(String word: words){
            for(char ch: word.toCharArray()){
                charSet.add(ch);
            }
        }

        // colors for DAG Verify
        int[] colors = new int[26]; // here 26 because only lower case latin

        // retList, remember to reverse it to get Topological Sort
        List<Character> retList = new ArrayList<>();

        for(char ch: charSet){
            if(colors[ch - 'a'] != -1){
                if(topologicalSortWithDAGVerifyAndDFS(graph, ch, colors, retList, charSet)){
                    return "";
                }
            }
        }

        // Topological Sort is the reverse order of retList
        StringBuilder sb = new StringBuilder();
        for(int i = retList.size() - 1; i >= 0; i--){
            sb.append(retList.get(i));
        }

        return sb.toString();
    }

    private boolean topologicalSortWithDAGVerifyAndDFS(Map<Character, List<Character>> graph, char ch, int[] colors, List<Character> retList, Set<Character> charSet){
        // set status to visiting
        colors[ch - 'a'] = 1;

        // process
        List<Character> nextCharList = graph.getOrDefault(ch, new ArrayList<>());
        for(int i = 0; i < nextCharList.size(); i++){
            char nextChar = nextCharList.get(i);

            if(colors[nextChar - 'a'] == 1){
                return true;
            }else if(colors[nextChar - 'a'] == 0){
                if(topologicalSortWithDAGVerifyAndDFS(graph, nextChar, colors, retList, charSet)){
                    return true;
                }
            }
        }

        // set status to visited
        colors[ch - 'a'] = -1;

        // add to retList
        retList.add(ch);

        return false;
    }
}


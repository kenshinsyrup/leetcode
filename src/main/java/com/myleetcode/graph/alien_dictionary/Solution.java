package com.myleetcode.graph.alien_dictionary;

import java.util.*;

class Solution {
    public String alienOrder(String[] words) {

        // special case
        if(words == null || words.length == 0){
            return "";
        }

        return alienOrderByTopologyAndDFS(words);
    }

    // intuition: the result is a Topology Sort. But the hard point of this problem is the graph building part.
    // 根据示例: wrf在er前面，说明w->e; wrt在wrf前面，说明t->f;er在ett前面说明r->t,ett在rftt前面说明e->r
    // w->e->r->t->f

    // 注意，我们用一个occur map来存储所有曾经出现过的char，这个是我们将来输出的所有chars
    // 我们用一个graph来存储char之间的连接关系，这个是将来我们输出的时候的顺序依据，同corse schedule相类似，如果有一些char之间没有关系，也就是在graph里没有children，那么我们可以任意顺序的输出他们，但是不能不输出，这也是occur的存在的意义以及单独让他自己占据一个for循环填充自己的原因
    // 比如["ab","adc"],我们的output是"cbda",leetcode expected是"abcd"，虽然整体顺序不同，但是只要：所有出现的char都输出了，同时有依赖关系的b和d相对顺序正确，就可以。
    private String alienOrderByTopologyAndDFS(String[] words){

        // build graph first
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        // init graph
        for(int i = 0; i< 26; i++){ // tricky, we know latin has no more than 26 chars
            graph.add(new ArrayList<Integer>());
        }

        // check adjacent rows same char, fullfill graph
        int len = words.length;
        for(int i = 0; i < len - 1; i++){
            String word = words[i];
            String nextWord = words[i + 1];

            int wordLen = Math.min(word.length(), nextWord.length());
            for(int j = 0; j < wordLen; j++){
                if(word.charAt(j) != nextWord.charAt(j)){
                    graph.get(word.charAt(j) - 'a').add(nextWord.charAt(j) - 'a');
                    break;
                }
            }
        }

        // need a dict to keep track of what char occurs
        Map<Integer, Boolean> occur = new HashMap<Integer, Boolean>();
        // occurs, we must keep record of all chars occurs in input
        for(int i = 0; i < len; i++){
            String word = words[i];
            for(char c: word.toCharArray()){
                occur.put(c - 'a', true);
            }
        }

        // status
        boolean[] visiting = new boolean[26];
        boolean[] visited = new boolean[26];

        // store in stack
        Stack<Integer> storeStack = new Stack<Integer>();

        for(int i = 0; i < graph.size(); i++){
            if(!dfs(graph, i, visiting, visited, storeStack, occur)){
                return "";
            }
        }

        // construct output ret
        StringBuilder sb = new StringBuilder();
        while(!storeStack.isEmpty()){
            int v = storeStack.pop();
            sb.append((char)(v + 'a'));
        }

        return sb.toString();

    }

    private boolean dfs(List<List<Integer>> graph, int vertexNum, boolean[] visiting, boolean[] visited, Stack<Integer> storeStack, Map<Integer, Boolean> occur){
        // check and set status
        if(visited[vertexNum]){
            return true;
        }
        if(visiting[vertexNum]){
            return false;
        }

        visiting[vertexNum] = true;

        List<Integer> children = graph.get(vertexNum);
        for(int i = 0; i < children.size(); i++){
            if(!dfs(graph, children.get(i), visiting, visited, storeStack, occur)){
                return false;
            }
        }

        // mark nonvisiting and visited, store
        visiting[vertexNum] = false;
        visited[vertexNum] = true;

        // here is important!!!
        // 我们graph中存了26个字母对应的list，这样的话，我们的dfs其实经历了一些本来不存在于input中的char，如果这些char也存入stack，那肯定不对
        // must check if this 'vertex' occurs, because in our graph, there are all 26 chars so if we just push the vertexNum to stack, there will be 26 chars in stack but that's not true, because we only care about those chars occur in the input
        if(occur.containsKey(vertexNum)){
            storeStack.push(vertexNum);
        }

        return true;
    }
}

package com.myleetcode.graph.loud_and_rich;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        // return loudAndRichByDFS(richer, quiet);//TLE
        return loudAndRichByDFSOpt(richer, quiet);
    }

    // naive的DFS会TLE，解决dfs或者递归TLE的直觉想法就是去做memo
    // 可以发现，在上一个代码中，我们对每一个node都做了一次dfs，这期间是有一些可以保存的的内容来避免重复递归的，否则在图比较大的时候很浪费时间，比如我们已经知道了ret[0],ret[1],ret[2]，在求ret[3]的时候如果我们遇到了dfs到node0或者1或者2的情况，是可以直接return ret[0]等对应的内容的。改起来也并不复杂，主要是有这个想法。
    // https://leetcode.com/problems/loud-and-rich/discuss/149717/Java-DFS-beats-100-still-worst-case-O(N2).-lol.
    public int[] loudAndRichByDFSOpt(int[][] richer, int[] quiet) {
        // special case
        // no people
        if(quiet == null || quiet.length == 0){
            return new int[0];
        }

        // build graph, directed, node points to a richer node because we want to check the nodes richer than us
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // first, get a empty graph
        int num = quiet.length;
        for(int i = 0; i < num; i++){
            graph.put(i, new ArrayList<>());
        }
        // second, fulfill the graph
        for(int i = 0; i < richer.length; i++){
            graph.get(richer[i][1]).add(richer[i][0]);
        }

        int[] ret = new int[num];
        // 初始化ret为-1用做标记
        for(int i = 0; i < num; i++){
            ret[i] = -1;
        }

        for(int i = 0; i < num; i++){
            // we only set ret[i] here, then we could know if we encounter ret[i] != -1 when we are dfs, we know we could return the ret[i] imidiately.
            ret[i]  = dfsOpt(graph, i, ret, quiet);
        }

        return ret;

    }

    // return the least quiet node
    private int dfsOpt(Map<Integer, List<Integer>> graph, int curNode, int[] ret, int[] quiet){
        if(ret[curNode] != -1){
            return ret[curNode];
        }

        // keep the least quiet node, this node initial is the curNode, then we compare it with all curNode's child to choose the least one and return it.
        int leastQuietNode = curNode;
        List<Integer> children = graph.get(curNode);
        // 这段注释掉的代码，注意，这个判断是因为有些node没有children(null)，直接for(int child: children会出错, 用for(int i = 0; i < children.size();i++)当然也不行因为children是null)
        // 这个有另一个解决办法是在构建graph时，给每个node都先存好一个new ArrayList<>(),再去填充图，也就是上面我们用的办法.
        // if(children == null || children.size() == 0){
        //     return leastQuietNode;
        // }
        for(int child: children){
            int leastQuietChild = dfsOpt(graph, child, ret, quiet);
            if(quiet[leastQuietNode] > quiet[leastQuietChild]){
                leastQuietNode = leastQuietChild;
            }
        }

        return leastQuietNode;
    }

    // TLE
    // intuition: build a graph based on richer, this graph is not necessary a connected graph but definitely a directed graph.
    // traverse every node, every time the node used as a root then dfs to find all chianed richers, during this record the min_quiet value, when dfs ends, we get a node-min_quiet pair. because the node is 0-n-1, so it also the index of ans list.
    public int[] loudAndRichByDFS(int[][] richer, int[] quiet) {
        // special case
        // no people
        if(quiet == null || quiet.length == 0){
            return new int[0];
        }
        // has people no richer. 注意！！！this is not correct. answer[x]=y,这里x和y都是人，所以这个判断是不对的，应该是ret[i] = i, where i [0, quiet.length - 1].但其实我们不需要这样写了，后面正常的的代码能处理这个.
        // if(richer == null || richer.length == 0 || richer[0].length == 0){
        //    return quiet;
        // }

        // build graph, directed, node points to a richer node because we want to check the nodes richer than us
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < richer.length; i++){
            graph.putIfAbsent(richer[i][1], new ArrayList<>());
            graph.get(richer[i][1]).add(richer[i][0]);
        }

        int num = quiet.length;
        int[] ret = new int[num];
        int minQuiet = Integer.MAX_VALUE;
        // boolean[] visiting = new boolean[num]; // no cycle in this problem and we dont need keep status
        for(int i = 0; i < num; i++){
            ret[i] = i;
            dfs(graph, i, i, ret, quiet);
        }

        return ret;

    }

    private void dfs(Map<Integer, List<Integer>> graph, int root, int curNode, int[] ret, int[] quiet){
        // update the min quiet to the ret[root]
        if(quiet[ret[root]] > quiet[curNode]){
            ret[root] = curNode;
        }

        List<Integer> children = graph.get(curNode);
        if(children == null || children.size() == 0){
            return;
        }
        for(int child: children){
            dfs(graph, root, child, ret, quiet);
        }
    }
}

package com.myleetcode.graph.graph_valid_tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public boolean validTree(int n, int[][] edges) {
        return validTreeByDFS(n, edges);
    }

    // 这个开始的时候做错是因为没有考虑好如何找环，这个题就是个找环题，如果一个图是树，那么就有两个条件：1 连通图; 2 没有环。
    // 找环的时候，出问题的点在于这是一个无向图，我们建图完了之后，对于边u->v一定存在一个边v->u，但这个不是环，所以我们比如从udfs到了v，那么检查v时，要检查所有v的子除了u，这个是重点。
    // 其他就是常规操作了

    // intuition: dfs to find the cycle, use a visited[] to mark node we have visited and a visiting[] to mark node we are visiting. if it could make up a valid tree, we should be able to mark all node visited but not encounter any visiting node(if we encounter one, then we find one cycle, invalid)
    // and we should consider disconnected graph, so must check visited[] to figure out if we have visited all nodes when our dfs is end.
    private boolean validTreeByDFS(int n, int[][] edges){
        // special case
        // 无边: node个数小于等于1，那么可以组成只有0或1个node的树,true；node个数大于1，非连通图,false
        if(edges == null || edges.length == 0){
            if(n <= 1){
                return true;
            }
            return false;
        }

        // build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int[] edge: edges){
            // 由于是无向图，所以在建图是注意，给出的edge(u,v)，我们不止要建立u->v的key-value，还要建立v->u的key-value，这样才是正确用adj list表达无向图，否则就是有向图了. 无向图是特殊的有向图
            // u->v
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            // v->u
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visiting = new boolean[n]; // mark status

        List<Integer> visited = new ArrayList<>();

        // we could start with any node and we say this is root, then it's parent we say it's -1(ie not existing in graph)
        if(!validateDFS(graph, edges[0][0], -1, visited, visiting)){
            return false;
        }

        // check if we have visited all nodes, if not, then the graph is disconnected, so not a valid tree.
        return visited.size() == n;

    }

    // dfs to find cycle
    private boolean validateDFS(Map<Integer, List<Integer>> graph, int node, int parent, List<Integer> visited, boolean[] visiting){
        // cycle find
        if(visiting[node]){
            return false;
        }

        visiting[node] = true;

        List<Integer> children = graph.get(node);
        for(int child: children){
            if(child != parent && !validateDFS(graph, child, node, visited, visiting)){
                return false;
            }
        }

        visiting[node] = false; // we are done with this node, so mark as not visiting

        visited.add(node); // record visited nodes

        return true;
    }

}
